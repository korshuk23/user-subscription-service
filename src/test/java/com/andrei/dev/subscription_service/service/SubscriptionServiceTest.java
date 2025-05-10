package com.andrei.dev.subscription_service.service;

import com.andrei.dev.subscription_service.dto.subscription.SubscriptionCreateDto;
import com.andrei.dev.subscription_service.dto.subscription.SubscriptionResponseDto;
import com.andrei.dev.subscription_service.entity.Subscription;
import com.andrei.dev.subscription_service.entity.User;
import com.andrei.dev.subscription_service.exception.NotFoundException;
import com.andrei.dev.subscription_service.mapper.SubscriptionMapper;
import com.andrei.dev.subscription_service.repository.SubscriptionRepository;
import com.andrei.dev.subscription_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private User user;
    private SubscriptionCreateDto createDto;
    private Subscription subscription;
    private Subscription savedSubscription;
    private SubscriptionResponseDto responseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john");

        createDto = new SubscriptionCreateDto("Netflix", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 6, 1));

        subscription = new Subscription();
        subscription.setServiceName("Netflix");

        savedSubscription = new Subscription();
        savedSubscription.setId(42L);
        savedSubscription.setServiceName("Netflix");
        savedSubscription.setStatus("active");

        responseDto = SubscriptionResponseDto.builder()
                .id(42L)
                .serviceName("Netflix")
                .status("active")
                .build();
    }

    @Test
    void addSubscription_ShouldCreateAndReturnDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(subscriptionMapper.toEntity(createDto)).thenReturn(subscription);
        when(subscriptionRepository.save(subscription)).thenReturn(savedSubscription);
        when(subscriptionMapper.toDto(savedSubscription)).thenReturn(responseDto);

        SubscriptionResponseDto result = subscriptionService.addSubscription(1L, createDto);

        assertEquals("Netflix", result.getServiceName());
        assertEquals("active", result.getStatus());
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    void addSubscription_ShouldThrowIfUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                subscriptionService.addSubscription(1L, createDto));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void getUserSubscriptions_ShouldReturnList() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(subscriptionRepository.findAllByUserId(1L)).thenReturn(List.of(savedSubscription));
        when(subscriptionMapper.toDto(savedSubscription)).thenReturn(responseDto);

        List<SubscriptionResponseDto> result = subscriptionService.getUserSubscriptions(1L);

        assertEquals(1, result.size());
        assertEquals("Netflix", result.get(0).getServiceName());
    }

    @Test
    void getUserSubscriptions_ShouldThrowIfUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                subscriptionService.getUserSubscriptions(1L));

        assertEquals("User not found with id: 1", ex.getMessage());
    }

    @Test
    void deleteSubscription_ShouldDeleteIfExists() {
        when(subscriptionRepository.findByIdAndUserId(42L, 1L)).thenReturn(Optional.of(savedSubscription));

        subscriptionService.deleteSubscription(1L, 42L);

        verify(subscriptionRepository).delete(savedSubscription);
    }

    @Test
    void deleteSubscription_ShouldThrowIfNotFound() {
        when(subscriptionRepository.findByIdAndUserId(42L, 1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                subscriptionService.deleteSubscription(1L, 42L));

        assertEquals("Subscription not found for user: 1", ex.getMessage());
    }

    @Test
    void getTopSubscriptions_ShouldReturnTop3() {
        Subscription s1 = new Subscription();
        s1.setServiceName("Netflix");
        Subscription s2 = new Subscription();
        s2.setServiceName("Spotify");
        Subscription s3 = new Subscription();
        s3.setServiceName("YouTube");

        when(subscriptionRepository.findTopSubscriptions(PageRequest.of(0, 3)))
                .thenReturn(List.of(s1, s2, s3));

        List<String> result = subscriptionService.getTopSubscriptions();

        assertEquals(List.of("Netflix", "Spotify", "YouTube"), result);
    }
}