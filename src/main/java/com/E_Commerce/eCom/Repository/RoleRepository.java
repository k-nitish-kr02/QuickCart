package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.AppRole;
import com.E_Commerce.eCom.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(AppRole appRole);
}
