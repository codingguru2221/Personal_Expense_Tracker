package com.expense.tracker.service;

import com.expense.tracker.dto.UserDto;
import com.expense.tracker.entity.User;

public interface UserService {
    User createUser(UserDto userDto);
    User getUserById(Long id);
    User getUserByUsername(String username);
}