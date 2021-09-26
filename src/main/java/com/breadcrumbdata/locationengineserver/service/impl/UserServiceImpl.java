package com.breadcrumbdata.locationengineserver.service.impl;

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
}
