package com.ecommerce.services.user;


import com.ecommerce.dto.SignupRequest;
import com.ecommerce.dto.UserDto;
import com.ecommerce.responce.GeneralResponse;

import java.io.IOException;

public interface UserService {

     UserDto createUser(SignupRequest signupRequest) throws Exception;

     Boolean hasUserWithEmail(String email);

     void createAdminAccount();

     void createClientAccount();

     UserDto getUserById(Long userId);

     UserDto updateUser(UserDto userDto) throws IOException;

}
