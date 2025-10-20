package org.jewelry.jewelryshop.userservice.service;

import org.jewelry.jewelryshop.userservice.dto.UserCreateDTO;
import org.jewelry.jewelryshop.userservice.dto.UserDTO;
import org.jewelry.jewelryshop.userservice.dto.UserDetailsDTO;
import org.jewelry.jewelryshop.userservice.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO create(UserCreateDTO dto);
    UserDTO update(Long id, UserUpdateDTO dto);
    void delete(Long id);
    UserDTO getCurrent(String username);
    UserDTO updateCurrent(String username, UserUpdateDTO dto);
    String createPasswordResetToken(String email);
    void resetPassword(String token, String newPassword);
    UserDetailsDTO getByUsernameDetails(String username);
}
