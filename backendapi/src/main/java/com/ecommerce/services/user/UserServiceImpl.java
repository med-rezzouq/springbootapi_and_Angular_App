package com.ecommerce.services.user;

import com.ecommerce.dto.SignupRequest;
import com.ecommerce.dto.UserDto;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.enums.OrderStatus;
import com.ecommerce.enums.UserRole;
import com.ecommerce.repo.OrderRepo;
import com.ecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void createAdminAccount() {
        User adminAccount = userRepo.findFirstByEmail("ayoub@cigma.ma");
        if (null == adminAccount) {
            User user = new User();
            user.setEmail("ayoub@cigma.ma");
            user.setName("Ayoub");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepo.save(user);
        }
    }

    public void createClientAccount() {
        User clientAccount = userRepo.findFirstByEmail("mohammed@cigma.ma");
        if (null == clientAccount) {
            User user = new User();
            user.setEmail("mohammed@cigma.ma");
            user.setName("Mohammed");
            user.setRole(UserRole.USER);
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userRepo.save(user);
        }
    }

    @Transactional
    public UserDto createUser(SignupRequest signupRequest) throws Exception {
        User user = new User(signupRequest.getEmail(), new BCryptPasswordEncoder().encode(signupRequest.getPassword()), signupRequest.getName(), UserRole.USER);
        user = userRepo.save(user);
        if (user == null) return null;
        Order order = new Order();
        order.setAmount(0L);
        order.setUser(user);
        order.setStatus(OrderStatus.Pending);
        orderRepo.save(order);
        return user.mapUsertoUserDto();
    }


    public Boolean hasUserWithEmail(String email) {
        return userRepo.findFirstByEmail(email) != null;
    }

    @Override
    public UserDto getUserById(Long userId) {
        UserDto userDto = null;
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            userDto = optionalUser.get().mapUsertoUserDto();
            userDto.setReturnedImg(optionalUser.get().getImg());
        }
        return userDto;
    }

    public UserDto updateUser(UserDto userDto) throws IOException {
        Optional<User> userOptional = userRepo.findById(userDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userDto.getName());
            user.setImg(userDto.getImg().getBytes());
            userRepo.save(user);
            return userDto;
        } else {
            return null;
        }
    }

}
