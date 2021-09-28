package com.breadcrumbdata.locationengineserver.service;

import com.breadcrumbdata.locationengineserver.model.dto.UserDTO;
import com.breadcrumbdata.locationengineserver.model.vo.UserVO;

public interface UserService {
    UserVO create(UserDTO userDTO);
    UserVO findUserByUsername(String name);
}
