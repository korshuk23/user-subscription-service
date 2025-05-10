package com.andrei.dev.subscription_service.service;

import com.andrei.dev.subscription_service.dto.subscription.SubscriptionCreateDto;
import com.andrei.dev.subscription_service.dto.subscription.SubscriptionResponseDto;
import com.andrei.dev.subscription_service.entity.Subscription;
import com.andrei.dev.subscription_service.entity.User;
import com.andrei.dev.subscription_service.exception.NotFoundException;
import com.andrei.dev.subscription_service.mapper.SubscriptionMapper;
import com.andrei.dev.subscription_service.repository.SubscriptionRepository;
import com.andrei.dev.subscription_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public SubscriptionResponseDto addSubscription(Long userId, SubscriptionCreateDto subscriptionCreateDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found");
            return new NotFoundException("User not found");
        });
        Subscription subscription = subscriptionMapper.toEntity(subscriptionCreateDto);
        subscription.setUser(user);
        subscription.setStatus("active");

        Subscription saved = subscriptionRepository.save(subscription);
        log.info("Subscription created for user: {}", user.getUsername());
        return subscriptionMapper.toDto(saved);
    }

    public List<SubscriptionResponseDto> getUserSubscriptions(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("User not found with id: {}", userId);
            throw new NotFoundException("User not found with id: " + userId);
        }

        List<SubscriptionResponseDto> subscriptions = subscriptionRepository.findAllByUserId(userId).stream()
                .map(subscriptionMapper::toDto)
                .toList();

        log.info("Retrieved {} subscriptions for user with id: {}", subscriptions.size(), userId);
        return subscriptions;
    }

    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findByIdAndUserId(subscriptionId, userId)
                .orElseThrow(() -> {
                    log.error("Subscription not found with id: {}", subscriptionId);
                    return new NotFoundException("Subscription not found for user: " + userId);
                });

        subscriptionRepository.delete(subscription);
        log.info("Subscription with id: {} deleted for user with id: {}", subscriptionId, userId);
    }

    public List<String> getTopSubscriptions() {
        Pageable top3 = PageRequest.of(0, 3);
        List<String> topSubscriptions = subscriptionRepository.findTopSubscriptions(top3).stream()
                .map(Subscription::getServiceName)
                .toList();
        log.info("Retrieved top 3 subscriptions: {}", topSubscriptions);
        return topSubscriptions;
    }
}
