package com.E_Commerce.eCom.Service;

import com.E_Commerce.eCom.ExceptionHandler.APIException;
import com.E_Commerce.eCom.ExceptionHandler.ResourceNotFoundException;
import com.E_Commerce.eCom.Model.Address;
import com.E_Commerce.eCom.Model.User;
import com.E_Commerce.eCom.Payload.AddressPayload.AddressDTO;
import com.E_Commerce.eCom.Payload.AddressPayload.AddressResponse;
import com.E_Commerce.eCom.Repository.AddressRepo;
import com.E_Commerce.eCom.Repository.UserRepo;
import com.E_Commerce.eCom.Utils.ObjectUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepo addressRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    public AddressService(AddressRepo addressRepo, ModelMapper modelMapper, UserRepo userRepo) {
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }

    public AddressDTO createUserAddress(@Valid Address address, String username) {
        User user = userRepo.findByUsername(username);
        address.setUser(user);


        List<Address> addresses = user.getAddresses();
        addresses.add(address);
        user.setAddresses(addresses);
//        userRepo.save(user);

        Address savedAddress = addressRepo.save(address);

        return modelMapper.map(savedAddress,AddressDTO.class);
    }

    public AddressResponse getAllAddress() {
        List<Address> addresses = addressRepo.findAll();
        if(addresses.isEmpty()) throw new APIException("No addresses found .. ");

        List<AddressDTO> addressDTOs = addresses.stream().map(address ->
                modelMapper.map(address, AddressDTO.class)).toList();
        return new AddressResponse(addressDTOs);
    }

    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(()->
            new ResourceNotFoundException("Address","addressId",addressId)
        );
        return  modelMapper.map(address,AddressDTO.class);
    }

    public AddressResponse getAddressByUser(String username) {
        List<Address> addresses = addressRepo.findByUsername(username);
        if(addresses.isEmpty()) throw new ResourceNotFoundException("Address","user",username);
        List<AddressDTO> addressDTOs = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();

        return new AddressResponse(addressDTOs);
    }

    public AddressDTO updateAddress(AddressDTO addressDTO, Long addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","addressId",addressId)
        );
        ObjectUtils.setIfNotNull(addressDTO.getName(),address::setName);
        ObjectUtils.setIfNotNull(addressDTO.getPhoneNumber(),address::setPhoneNumber);
        ObjectUtils.setIfNotNull(addressDTO.getBuilding(),address::setBuildingName);
        ObjectUtils.setIfNotNull(addressDTO.getState(),address::setState);
        ObjectUtils.setIfNotNull(addressDTO.getCountry(),address::setCountry);
        ObjectUtils.setIfNotNull(addressDTO.getPincode(),address::setPincode);

        Address savedAddress = addressRepo.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);// this returning some values null only of DTO but saving correct updated details ?
    }

    public AddressDTO deleteAddress( Long addressId) {
        Address address = addressRepo.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","addressId",addressId)
        );
        addressRepo.delete(address);
        return modelMapper.map(address, AddressDTO.class);
    }
}
