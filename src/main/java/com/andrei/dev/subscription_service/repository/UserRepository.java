package com.andrei.dev.subscription_service.repository;

import com.andrei.dev.subscription_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {
}
