package com.breadcrumbdata.locationengineserver.service.impl;

import com.breadcrumbdata.locationengineserver.config.exceptions.UserNameNotFoundException;
import com.breadcrumbdata.locationengineserver.model.User;
import com.breadcrumbdata.locationengineserver.model.dto.UserDTO;
import com.breadcrumbdata.locationengineserver.model.vo.UserVO;
import com.breadcrumbdata.locationengineserver.repository.UserRepository;
import com.breadcrumbdata.locationengineserver.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserVO create(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User resultUser = userRepository.save(user);
        UserVO userVO = new UserVO();
        userVO.setId(resultUser.getId());
        userVO.setEmail(resultUser.getEmail());
        userVO.setRole(resultUser.getRole());
        return userVO;
    }

    @Override
    public UserVO findUserByUsername(String name) {
        User user = userRepository.findByEmail(name);
        if(user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setEmail(user.getEmail());
        userVO.setRole(user.getRole());
        return userVO;
    }

    @Override
    public Boolean check(String currentPassword, String username) {
        User user = userRepository.findByEmail(username);
        return this.passwordEncoder.matches(currentPassword, user.getPassword());
    }

    @Override
    public Boolean usernameExist(String email) {
        if(findUserByUsername(email) != null) {
            return true;
        }
        return false;
    }
}
