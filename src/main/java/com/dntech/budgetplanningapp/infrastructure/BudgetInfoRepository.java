/*
 * Ant Group
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package com.dntech.budgetplanningapp.infrastructure;

import com.dntech.budgetplanningapp.infrastructure.model.BudgetInfoDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 记账数据仓库
 *
 * @author wangdaini
 * @version BudgetRepository.java, v 0.1 2024年02月15日 11:08 pm wangdaini
 */
@Repository
public interface BudgetInfoRepository extends MongoRepository<BudgetInfoDO, String> {

    @Query(value="{authKey:'?0'}")
    BudgetInfoDO selectByAuthKey(String authKey);
}