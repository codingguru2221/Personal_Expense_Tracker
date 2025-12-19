package com.expense.tracker.service;

import com.expense.tracker.dto.UserDto;
import com.expense.tracker.entity.User;
import com.expense.tracker.entity.UserPreference;
import com.expense.tracker.repository.UserPreferenceRepository;
import com.expense.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserDto userDto) {
        // Create user
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setCity(userDto.getCity());
        user.setCountry(userDto.getCountry());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAppPin(passwordEncoder.encode(userDto.getAppPin()));
        user.setFingerprintEnabled(userDto.getFingerprintEnabled());

        User savedUser = userRepository.save(user);

        // Create user preferences
        UserPreference userPreference = new UserPreference();
        userPreference.setUser(savedUser);
        userPreference.setMonthlyBudget(userDto.getMonthlyBudget());
        userPreference.setCurrency(userDto.getCurrency());

        userPreferenceRepository.save(userPreference);

        return savedUser;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}