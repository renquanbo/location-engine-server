package com.breadcrumbdata.locationengineserver.repository;

import com.breadcrumbdata.locationengineserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
