package com.E_Commerce.eCom.Repository;

import com.E_Commerce.eCom.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Long> {
    @Query("SELECT ad FROM Address ad WHERE ad.user.username = ?1")
    List<Address> findByUsername(String username);
}
