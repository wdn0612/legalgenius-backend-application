/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.service.impl;

import com.dntech.budgetplanningapp.core.model.AppTablesResponse;
import com.dntech.budgetplanningapp.core.model.BudgetRecord;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppRequest;
import com.dntech.budgetplanningapp.core.model.CopyBaseAppResponse;
import com.dntech.budgetplanningapp.core.model.FieldInfoResponse;
import com.dntech.budgetplanningapp.core.model.TableVO;
import com.dntech.budgetplanningapp.service.FeiShuService;
import com.dntech.budgetplanningapp.service.template.IntegrationCallback;
import com.dntech.budgetplanningapp.service.template.IntegrationTemplate;
import com.lark.oapi.Client;
import com.lark.oapi.service.bitable.v1.model.AppTableRecord;
import com.lark.oapi.service.bitable.v1.model.CopyAppReq;
import com.lark.oapi.service.bitable.v1.model.CopyAppReqBody;
import com.lark.oapi.service.bitable.v1.model.CopyAppResp;
import com.lark.oapi.service.bitable.v1.model.CopyAppRespBody;
import com.lark.oapi.service.bitable.v1.model.CreateAppTableRecordReq;
import com.lark.oapi.service.bitable.v1.model.CreateAppTableRecordResp;
import com.lark.oapi.service.bitable.v1.model.ListAppTableFieldReq;
import com.lark.oapi.service.bitable.v1.model.ListAppTableFieldResp;
import com.lark.oapi.service.bitable.v1.model.ListAppTableReq;
import com.lark.oapi.service.bitable.v1.model.ListAppTableResp;
import com.lark.oapi.service.bitable.v1.model.ListAppTableRespBody;
import com.lark.oapi.service.drive.v1.model.PatchPermissionPublicReq;
import com.lark.oapi.service.drive.v1.model.PatchPermissionPublicResp;
import com.lark.oapi.service.drive.v1.model.PermissionPublicRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 飞书API调用实现类
 *
 * @author wangdaini
 * @version FeiShuServiceImpl.java, v 0.1 2024年02月16日 2:33 pm wangdaini
 */
@Service
public class FeiShuServiceImpl implements FeiShuService {

    private static final String appId     = "cli_a555e5c21cf89010";
    private static final String appSecret = "fmFqpQNIIrdxI370lgPjYbmuP7QgZfIG";

    private static final String appToken = "KcDdbrHPDale2NsMZMNldam9gjF";

    // TODO：设置超时时间
    private static final Client client = Client.newBuilder(appId, appSecret).build();

    @Override
    public CopyBaseAppResponse copyBaseApp(CopyBaseAppRequest copyBaseAppRequest) {

        // 发起请求
        return IntegrationTemplate.invoke(new IntegrationCallback<CopyBaseAppResponse>() {
            @Override
            public CopyBaseAppResponse execute() throws Exception {
                // 发起请求
                // 创建请求对象
                CopyAppReq req = CopyAppReq.newBuilder()
                        .appToken(appToken)
                        .copyAppReqBody(CopyAppReqBody.newBuilder()
                                .name(copyBaseAppRequest.getName())
                                .withoutContent(false)
                                .timeZone("Asia/Singapore")
                                .build())
                        .build();
                CopyAppResp copyResponse = client.bitable().v1().app().copy(req);

                CopyBaseAppResponse internalResponse = new CopyBaseAppResponse();
                internalResponse.setSuccess(copyResponse.success());
                internalResponse.setErrorCode(copyResponse.getCode());
                internalResponse.setErrorMsg(copyResponse.getMsg());

                CopyAppRespBody data = copyResponse.getData();
                internalResponse.setAppToken(Optional.ofNullable(data).map(d -> d.getApp().getAppToken()).orElse(null));
                internalResponse.setUrl(Optional.ofNullable(data).map(d -> d.getApp().getUrl()).orElse(null));
                internalResponse.setName(Optional.ofNullable(data).map(d -> d.getApp().getName()).orElse(null));
                internalResponse.setFolderToken(Optional.ofNullable(data).map(d -> d.getApp().getFolderToken()).orElse(null));
                internalResponse.setDefaultTableId(Optional.ofNullable(data).map(d -> d.getApp().getDefaultTableId()).orElse(null));
                internalResponse.setTimeZone(Optional.ofNullable(data).map(d -> d.getApp().getTimeZone()).orElse(null));
                return internalResponse;
            }

            @Override
            public void handleFailResult(CopyBaseAppResponse result) {
                if (result == null) {
                    result = new CopyBaseAppResponse();
                    result.setErrorCode(1);
                    result.setErrorMsg("发送请求异常");
                }
            }

            @Override
            public boolean success(CopyBaseAppResponse result) {
                return result.isSuccess();
            }
        });
    }

    @Override
    public boolean updatePermissionPublic(String appToken) {
        return IntegrationTemplate.invoke(new IntegrationCallback<Boolean>() {
            @Override
            public Boolean execute() throws Exception {

                // 创建请求对象
                PatchPermissionPublicReq req = PatchPermissionPublicReq.newBuilder()
                        .token(appToken)
                        .type("bitable")
                        .permissionPublicRequest(PermissionPublicRequest.newBuilder()
                                .externalAccess(true)
                                .securityEntity("only_full_access")
                                .commentEntity("anyone_can_edit")
                                .shareEntity("anyone")
                                .linkShareEntity("anyone_editable")
                                .inviteExternal(true)
                                .build())
                        .build();

                // 发起请求
                PatchPermissionPublicResp resp = client.drive().permissionPublic().patch(req);
                return resp.success();
            }

            @Override
            public void handleFailResult(Boolean result) {
                throw new RuntimeException("更新文档权限失败");
            }

            @Override
            public boolean success(Boolean result) {
                return result;
            }
        });
    }

    @Override
    public AppTablesResponse listAppTables(String appToken) {
        return IntegrationTemplate.invoke(new IntegrationCallback<AppTablesResponse>() {
            @Override
            public AppTablesResponse execute() throws Exception {
                // 创建请求对象
                ListAppTableReq req = ListAppTableReq.newBuilder()
                        .appToken(appToken)
                        .pageSize(20)
                        .build();

                // 发起请求
                ListAppTableResp resp = client.bitable().v1().appTable().list(req);

                AppTablesResponse appTablesResponse = new AppTablesResponse();
                appTablesResponse.setSuccess(resp.success());
                appTablesResponse.setErrorCode(resp.getCode());
                appTablesResponse.setErrorMsg(resp.getMsg());
                appTablesResponse.setHasMore(Optional.ofNullable(resp.getData()).map(ListAppTableRespBody::getHasMore).orElse(null));
                appTablesResponse.setPageToken(Optional.ofNullable(resp.getData()).map(ListAppTableRespBody::getPageToken).orElse(null));
                appTablesResponse.setTotalNumber(Optional.ofNullable(resp.getData()).map(ListAppTableRespBody::getTotal).orElse(null));

                List<TableVO> tableVOList = new ArrayList<>();
                Optional.ofNullable(resp.getData()).map(ListAppTableRespBody::getItems).ifPresent(
                        items -> {
                            Arrays.stream(items).forEach(item -> {
                                TableVO tableVO = new TableVO();
                                tableVO.setTableId(item.getTableId());
                                tableVO.setRevision(item.getRevision());
                                tableVO.setName(item.getName());
                                tableVOList.add(tableVO);
                            });
                        }
                );
                appTablesResponse.setTableVOS(tableVOList);

                return appTablesResponse;
            }

            @Override
            public void handleFailResult(AppTablesResponse result) {
                throw new RuntimeException("无法获取到app表格信息");
            }

            @Override
            public boolean success(AppTablesResponse result) {
                return result.isSuccess();
            }
        });
    }

    @Override
    public String getTenantAccessToken(String appId, String appSecret) {
        // TODO 暂时好像不用?
        return null;
    }

    @Override
    public List<FieldInfoResponse> listTableFields(String appToken, String tableId) {

        return IntegrationTemplate.invoke(new IntegrationCallback<List<FieldInfoResponse>>() {
            @Override
            public List<FieldInfoResponse> execute() throws Exception {

                // 创建请求对象
                ListAppTableFieldReq req = ListAppTableFieldReq.newBuilder()
                        .appToken(appToken)
                        .tableId(tableId)
                        .textFieldAsArray(true)
                        .build();

                // 发起请求
                ListAppTableFieldResp resp = client.bitable().v1().appTableField().list(req);
                return Optional.ofNullable(resp.getData())
                        .map(d -> Arrays.stream(d.getItems()).map(item-> {
                                    FieldInfoResponse fieldInfoResponse = new FieldInfoResponse();
                                    fieldInfoResponse.setFieldId(item.getFieldId());
                                    fieldInfoResponse.setFieldName(item.getFieldName());
                                    fieldInfoResponse.setProperty(item.getProperty());
                                    fieldInfoResponse.setType(item.getType());
                                    fieldInfoResponse.setUiType(item.getUiType());
                                    return fieldInfoResponse;
                                })
                                .collect(Collectors.toList()))
                        .orElse(null);
            }

            @Override
            public void handleFailResult(List<FieldInfoResponse> result) {
                throw new RuntimeException("获取表格字段信息失败");
            }

            @Override
            public boolean success(List<FieldInfoResponse> result) {
                return !CollectionUtils.isEmpty(result);
            }
        });
    }

    @Override
    public boolean createBudgetRecord(String appToken, String tableId, BudgetRecord budgetRecord) {
        return IntegrationTemplate.invoke(new IntegrationCallback<Boolean>() {
            @Override
            public Boolean execute() throws Exception {

                Map<String, Object> fields = new HashMap<>();
                fields.put("Category", budgetRecord.getCategory());
                fields.put("Amount", Double.valueOf(budgetRecord.getAmount()));
                fields.put("Transaction Type", budgetRecord.getTransactionType());
                fields.put("Payment Method", budgetRecord.getPaymentMethod());
                fields.put("Remark", budgetRecord.getRemark());

                // 创建请求对象
                CreateAppTableRecordReq req = CreateAppTableRecordReq.newBuilder()
                        .appToken(appToken)
                        .tableId(tableId)
                        .appTableRecord(AppTableRecord.newBuilder()
                                .fields(fields)
                                .build())
                        .build();

                // 发起请求
                CreateAppTableRecordResp resp = client.bitable().v1().appTableRecord().create(req);
                System.out.println(resp.getCode() + resp.getMsg());
                return resp.success();
            }

            @Override
            public void handleFailResult(Boolean result) {
                throw new RuntimeException("创建记账记录失败");
            }

            @Override
            public boolean success(Boolean result) {
                return result;
            }
        });
    }

}