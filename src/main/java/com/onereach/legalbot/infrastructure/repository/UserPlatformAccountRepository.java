package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.UserPlatformAccount;
import com.onereach.legalbot.infrastructure.model.enums.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用于管理UserPlatformAccount实体的仓库接口。
 * 继承自JpaRepository以获得基本的CRUD操作。
 */
@Repository
public interface UserPlatformAccountRepository extends JpaRepository<UserPlatformAccount, Integer> {

    /**
     * 通过platformUserId和platform的组合查找UserPlatformAccount。
     *
     * @param platformUserId 用户在平台上的唯一标识符
     * @param platform       平台枚举值
     * @return 如果找到则返回UserPlatformAccount，否则返回null
     */
    UserPlatformAccount findByPlatformUserIdAndPlatform(String platformUserId, Platform platform);

    UserPlatformAccount findByUser_UserIdAndPlatform(Integer userId, Platform platform);
}