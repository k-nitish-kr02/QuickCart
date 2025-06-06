package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
