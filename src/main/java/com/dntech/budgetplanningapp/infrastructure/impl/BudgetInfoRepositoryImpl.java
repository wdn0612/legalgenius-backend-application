///*
// * Ant Group
// * Copyright (c) 2004-2024 All Rights Reserved.
// */
//package com.dntech.budgetplanningapp.infrastructure.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.dntech.budgetplanningapp.infrastructure.BudgetInfoRepository;
//import com.dntech.budgetplanningapp.infrastructure.model.BudgetInfoDO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.springframework.stereotype.Repository;
//
//import java.util.Map;
//import java.util.Optional;
//
//import static com.mongodb.client.model.Filters.eq;
//
///**
// * 记账信息存储仓库
// *
// * @author wangdaini
// * @version BudgetRepositoryImpl.java, v 0.1 2024年02月16日 2:49 pm wangdaini
// */
//@Repository
//@Slf4j
//public class BudgetInfoRepositoryImpl implements BudgetInfoRepository {
//
//    private static final String SERVER_URL = "mongodb+srv://dbUser:yr5dSHi9hqOi8nnY@autobudget.zl0476k.mongodb.net/?retryWrites=true&w=majority";
//
//    private static MongoDatabase DATABASE;
//
//    private static final String DB_NAME = "AutoBudget";
//
//    private static final String COLLECTION_NAME = "UserInfo";
//
//    @Override
//    public int insert(BudgetInfoDO budgetInfoDO) {
//        MongoCollection<Document> collection = getMongoDB().getCollection(COLLECTION_NAME);
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.convertValue(budgetInfoDO, Map.class);
//        Document doc = new Document(map);
//        collection.insertOne(doc);
//        return 1;
//    }
//
//    @Override
//    public int delete(String authKey) {
//        MongoCollection<Document> collection = getMongoDB().getCollection(COLLECTION_NAME);
//        collection.deleteOne(eq("authKey", authKey));
//        return 1;
//    }
//
//    @Override
//    public BudgetInfoDO selectByAuthKey(String authKey) {
//        MongoCollection<Document> collection = getMongoDB().getCollection(COLLECTION_NAME);
//        FindIterable<Document> iterable = collection.find(new Document("authKey", authKey));
//        int count = 0;
//        for (Document doc : iterable) {
//            count++;
//        }
//        if (count > 1) {
//            throw new RuntimeException("命中一条以上的记账关联记录 authKey: " + authKey);
//        }
//        return Optional.ofNullable(iterable.first())
//                .map(doc -> JSON.parseObject(doc.toJson(), BudgetInfoDO.class))
//                .orElse(null);
//    }
//
//    /**
//     * 获取mongoDB链接
//     * @return
//     */
//    public MongoDatabase getMongoDB() {
//
//        if (DATABASE == null) {
//            ServerApi serverApi = ServerApi.builder()
//                    .version(ServerApiVersion.V1)
//                    .build();
//            MongoClientSettings settings = MongoClientSettings.builder()
//                    .applyConnectionString(new ConnectionString(SERVER_URL))
//                    .serverApi(serverApi)
//                    .build();
//            // Create a MongoDB client
//            try (MongoClient mongoClient = MongoClients.create(settings)) {
//                // Connect to the database
//                DATABASE = mongoClient.getDatabase(DB_NAME);
//                log.info("Connected to MongoDB.");
//            }
//        }
//        return DATABASE;
//    }
//}