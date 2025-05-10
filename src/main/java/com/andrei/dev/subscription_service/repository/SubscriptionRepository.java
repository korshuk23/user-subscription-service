package com.andrei.dev.subscription_service.repository;

import com.andrei.dev.subscription_service.entity.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Collection<Subscription> findAllByUserId(Long userId);

    Optional<Subscription> findByIdAndUserId(Long subscriptionId, Long userId);

    @Query("SELECT s.serviceName AS serviceName, COUNT(s) AS count " +
            "FROM Subscription s " +
            "GROUP BY s.serviceName " +
            "ORDER BY COUNT(s) DESC")
    List<Subscription> findTopSubscriptions(Pageable pageable);
}
