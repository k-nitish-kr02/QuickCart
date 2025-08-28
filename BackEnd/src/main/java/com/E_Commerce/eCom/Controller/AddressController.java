package com.E_Commerce.eCom.Controller;

import com.E_Commerce.eCom.Model.Address;
import com.E_Commerce.eCom.Payload.AddressPayload.AddressDTO;
import com.E_Commerce.eCom.Payload.AddressPayload.AddressResponse;
import com.E_Commerce.eCom.Service.AddressService;
import com.E_Commerce.eCom.Utils.AuthUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final AuthUtils authUtils;
    private final AddressService addressService;

    @Autowired
    public AddressController(AuthUtils authUtils,AddressService addressService) {
        this.authUtils = authUtils;
        this.addressService = addressService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createUserAddress(@Valid  @RequestBody Address address){
        String username = authUtils.loggedInUsername();
        AddressDTO addressDTO = addressService.createUserAddress(address,username);
        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/admin/addresses")
    public ResponseEntity<AddressResponse> getAllAddress(){
        AddressResponse response = addressService.getAllAddress();
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO,HttpStatus.FOUND);
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressResponse> getAddressByUser(){
        String username = authUtils.loggedInUsername();
        AddressResponse addresses = addressService.getAddressByUser(username);
        return new ResponseEntity<>(addresses,HttpStatus.FOUND);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO,@PathVariable Long addressId){

        AddressDTO updatedAddressDTO = addressService.updateAddress(addressDTO,addressId);
        return ResponseEntity.ok(updatedAddressDTO);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable Long addressId){

        AddressDTO updatedAddressDTO = addressService.deleteAddress(addressId);
        return ResponseEntity.ok(updatedAddressDTO);
    }
}
