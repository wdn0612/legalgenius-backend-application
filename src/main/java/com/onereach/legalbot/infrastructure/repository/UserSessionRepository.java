package com.onereach.legalbot.infrastructure.repository;

import com.onereach.legalbot.infrastructure.model.UserSession;
import com.onereach.legalbot.infrastructure.model.enums.Scene;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {
    // Additional query methods can be defined here

    /**
     * 通过 user_id + scene 查找 UserSession
     * 
     * @param userId 用户的唯一标识符
     * @param scene  场景枚举值
     * @return 如果找到则返回UserSession，否则返回null
     */

    UserSession findByUser_UserIdAndScene(Integer userId, Scene scene);
}