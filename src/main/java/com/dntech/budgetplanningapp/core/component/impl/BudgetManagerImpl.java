/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.core.component.impl;

import com.dntech.budgetplanningapp.core.component.BudgetManager;
import com.dntech.budgetplanningapp.core.model.AppTablesResponse;
import com.dntech.budgetplanningapp.core.model.BudgetRecord;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppRequest;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppResponse;
import com.dntech.budgetplanningapp.core.model.FieldInfoResponse;
import com.dntech.budgetplanningapp.core.model.TableVO;
import com.dntech.budgetplanningapp.infrastructure.BudgetInfoRepository;
import com.dntech.budgetplanningapp.infrastructure.model.BudgetInfoDO;
import com.dntech.budgetplanningapp.service.EmailService;
import com.dntech.budgetplanningapp.service.FeiShuService;
import com.lark.oapi.service.bitable.v1.model.AppTableFieldPropertyOption;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 记账管理中心
 *
 * @author wangdaini
 * @version BudgetManagerImpl.java, v 0.1 2024年02月16日 3:02 pm wangdaini
 */
@Component
@Slf4j
public class BudgetManagerImpl implements BudgetManager {

    @Resource
    private FeiShuService feiShuService;

    @Resource
    private EmailService emailService;

    @Resource
    private BudgetInfoRepository budgetInfoRepository;

    // TODO: 从库里取
    private final String appToken = "VFq6bCHQbaULRxsJsdfcY4fBnrb";

    @Override
    public boolean create(String email) {
        // 1. 生成authKey
        String authKey = generateAuthKey(System.currentTimeMillis() + email);
        log.info("生成authKey: " + authKey);
        // 2. 复制文档模版
        CopyBaseAppRequest copyBaseAppRequest = new CopyBaseAppRequest();
        copyBaseAppRequest.setName("[Dev] Auto Budgeting App");
        copyBaseAppRequest.setWithoutContent(false);
        CopyBaseAppResponse copyBaseAppResponse = feiShuService.copyBaseApp(copyBaseAppRequest);
        // 3. 绑定用户、authKey、文档模版、交易记录表
        String url = copyBaseAppResponse.getUrl();
        String appToken = copyBaseAppResponse.getAppToken();
        AppTablesResponse appTablesResponse = feiShuService.listAppTables(appToken);

        // 4. 落库
        BudgetInfoDO budgetInfoDO = new BudgetInfoDO();
        budgetInfoDO.setAuthKey(authKey);
        budgetInfoDO.setEmail(email);
        budgetInfoDO.setFileUrl(url);
        budgetInfoDO.setAppToken(appToken);
        budgetInfoDO.setTransactionTableId(getTransactionTableId(appTablesResponse));

        // 5. 更新文档编辑权限
        feiShuService.updatePermissionPublic(appToken);

        log.info("生成新记账用户: " + budgetInfoDO);
        budgetInfoRepository.insert(budgetInfoDO);
        // 6. 触发邮件通知
        emailService.send("successfully created");
        return true;
    }

    @Override
    public boolean delete(String authKey) {
        // 1. 删除行
        budgetInfoRepository.deleteById(authKey);
        log.info("记账用户已删除, authKey：" + authKey);
        return true;
    }

    @Override
    public String queryInfoByAuthKey(String authKey) {
        // 1. 查询行
        BudgetInfoDO budgetInfoDO = budgetInfoRepository.selectByAuthKey(authKey);
        return budgetInfoDO.toString();
    }

    @Override
    public Map<String, List<String>> queryBudgetParams(String authKey) {
         BudgetInfoDO budgetInfoDO = budgetInfoRepository.selectByAuthKey(authKey);

        if (budgetInfoDO == null) {
            throw new RuntimeException("未找到授权记录");
        }

        // 1. 查询authKey关联文档
        String appToken = budgetInfoDO.getAppToken();
        String tableId = budgetInfoDO.getTransactionTableId();

        // 3. 组装结果返回
        List<FieldInfoResponse> transactionFieldsInfo = feiShuService.listTableFields(appToken, tableId);

        Map<String, List<String>> result = new HashMap<>();
        result.put("category", getOptionsByField(transactionFieldsInfo, "category"));
        result.put("transactiontype", getOptionsByField(transactionFieldsInfo, "transactiontype"));
        result.put("paymentmethod", getOptionsByField(transactionFieldsInfo, "paymentmethod"));
        return result;
    }

    /**
     * 获取交易记录tableId
     *
     * @param appTablesResponse
     * @return
     */
    private static String getTransactionTableId(AppTablesResponse appTablesResponse) {
        return Optional.ofNullable(appTablesResponse.getTableVOS())
                .flatMap(vos -> vos.stream()
                        .filter(vo -> "transactions".equals(vo.getName().trim().toLowerCase()))
                        .findFirst()
                        .map(TableVO::getTableId))
                .orElseThrow(() -> new IllegalArgumentException("找不到交易明细的tableId"));
    }

    /**
     * 根据字段获取的值
     *
     * @param transactionFieldsInfo
     * @param fieldName
     * @return
     */
    private List<String> getOptionsByField(List<FieldInfoResponse> transactionFieldsInfo, String fieldName) {
        return transactionFieldsInfo.stream()
                .filter(info -> fieldName.equals(info.getFieldName().trim().toLowerCase().replaceAll(" ", "")))
                .findFirst()
                .map(info ->
                        Arrays.stream(info.getProperty().getOptions())
                                .map(AppTableFieldPropertyOption::getName)
                                .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public void addRecord(String authKey, BudgetRecord budgetRecord) {

        BudgetInfoDO budgetInfoDO = budgetInfoRepository.selectByAuthKey(authKey);

        if (budgetInfoDO == null) {
            throw new RuntimeException("未找到授权记录");
        }

        // 1. 根据authKey获取相应appToken及url
        String appToken = budgetInfoDO.getAppToken();
        AppTablesResponse appTablesResponse = feiShuService.listAppTables(appToken);
        String tableId = getTransactionTableId(appTablesResponse);

        // 2. 加数据
        feiShuService.createBudgetRecord(appToken, tableId, budgetRecord);
    }

    /**
     * 生成授权key
     *
     * @param data
     * @return
     */
    private String generateAuthKey(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array into a hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {hexString.append('0');}
                hexString.append(hex);
            }

            // Return the first 8 characters of the MD5 hash (32 bits)
            return hexString.toString().substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 hashing algorithm not found", e);
        }
    }
}