package com.backend.rscart.service.user;

import com.backend.rscart.dto.UserDto;
import com.backend.rscart.model.User;
import com.backend.rscart.request.CreateUserRequest;
import com.backend.rscart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
