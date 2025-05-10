package com.andrei.dev.subscription_service.service;

import com.andrei.dev.subscription_service.dto.UserCreateDto;
import com.andrei.dev.subscription_service.dto.UserResponseDto;
import com.andrei.dev.subscription_service.dto.UserUpdateDto;
import com.andrei.dev.subscription_service.entity.User;
import com.andrei.dev.subscription_service.exception.AlreadyExistsException;
import com.andrei.dev.subscription_service.exception.NotFoundException;
import com.andrei.dev.subscription_service.mapper.UserMapper;
import com.andrei.dev.subscription_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {
        checkUniqueness(dto);
        User user = userMapper.toEntity(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        log.info("Created user: {}", user.getUsername());
        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto dto) {
        validateAtLeastOneFieldPresent(dto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userMapper.updateEntity(user, dto);
        log.info("Updated user with id: {}", id);
        return userMapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }

    private void checkUniqueness(UserCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new AlreadyExistsException("Email already in use: " + dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new AlreadyExistsException("Username already in use: " + dto.getUsername());
        }
    }

    private void validateAtLeastOneFieldPresent(UserUpdateDto dto) {
        if (dto.getEmail() == null && dto.getFirstName() == null && dto.getLastName() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update");
        }
    }
}
