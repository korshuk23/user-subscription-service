package com.andrei.dev.subscription_service.controller;

import com.andrei.dev.subscription_service.dto.subscription.SubscriptionCreateDto;
import com.andrei.dev.subscription_service.dto.subscription.SubscriptionResponseDto;
import com.andrei.dev.subscription_service.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Add a new subscription for the user",
            description = "Creates and assigns a new subscription to the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription successfully created"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid subscription data")
    })
    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> addSubscription(@PathVariable Long userId,
                                                                   @RequestBody SubscriptionCreateDto subscriptionCreateDto) {
        return ResponseEntity.status(201).body(subscriptionService.addSubscription(userId, subscriptionCreateDto));
    }

    @Operation(summary = "Get all subscriptions of the user",
            description = "Retrieves a list of all subscriptions associated with the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriptions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDto>> getUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
    }

    @Operation(summary = "Delete a user's subscription",
            description = "Removes a specific subscription from the user's list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User or subscription not found")
    })
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }
}