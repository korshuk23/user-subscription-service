package com.andrei.dev.subscription_service.repository;

import com.andrei.dev.subscription_service.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
