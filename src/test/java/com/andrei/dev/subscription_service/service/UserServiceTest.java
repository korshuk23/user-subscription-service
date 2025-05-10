package com.andrei.dev.subscription_service.service;

import com.andrei.dev.subscription_service.dto.UserCreateDto;
import com.andrei.dev.subscription_service.dto.UserResponseDto;
import com.andrei.dev.subscription_service.dto.UserUpdateDto;
import com.andrei.dev.subscription_service.entity.User;
import com.andrei.dev.subscription_service.exception.AlreadyExistsException;
import com.andrei.dev.subscription_service.exception.NotFoundException;
import com.andrei.dev.subscription_service.mapper.UserMapper;
import com.andrei.dev.subscription_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldCreateAndReturnUser() {
        UserCreateDto dto = new UserCreateDto("john_doe", "john@example.com", "password", "John", "Doe");

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(dto.getUsername());

        UserResponseDto responseDto = new UserResponseDto(1L, "john_doe", "john@example.com", "John", "Doe", LocalDateTime.now());

        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(false);
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(responseDto);

        UserResponseDto result = userService.createUser(dto);

        assertEquals("john_doe", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void getUser_shouldReturnUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("john_doe");

        UserResponseDto responseDto = new UserResponseDto(userId, "john_doe", "john@example.com", "John", "Doe", LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getUser(userId);

        assertEquals(userId, result.getId());
    }

    @Test
    void getUser_shouldThrowNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void updateUser_shouldUpdateAndReturnUser() {
        Long userId = 1L;
        UserUpdateDto dto = new UserUpdateDto("new@example.com", "New", null);

        User user = new User();
        user.setId(userId);

        UserResponseDto responseDto = new UserResponseDto(userId, "john_doe", "new@example.com", "New", "Doe", LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateEntity(user, dto);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.updateUser(userId, dto);

        assertEquals("new@example.com", result.getEmail());
        verify(userMapper).updateEntity(user, dto);
    }

    @Test
    void updateUser_shouldThrowWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserUpdateDto dto = new UserUpdateDto("email", null, null);

        assertThrows(NotFoundException.class, () -> userService.updateUser(99L, dto));
    }

    @Test
    void updateUser_shouldThrowWhenNoFieldsProvided() {
        UserUpdateDto dto = new UserUpdateDto(null, null, null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, dto));
    }

    @Test
    void deleteUser_shouldDeleteWhenExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowWhenNotFound() {
        when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(2L));
    }

    @Test
    void createUser_shouldThrowWhenEmailExists() {
        UserCreateDto dto = new UserCreateDto("user", "email@example.com", "pass", "a", "b");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_shouldThrowWhenUsernameExists() {
        UserCreateDto dto = new UserCreateDto("user", "email@example.com", "pass", "a", "b");
        when(userRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(dto.getUsername())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userService.createUser(dto));
    }
}