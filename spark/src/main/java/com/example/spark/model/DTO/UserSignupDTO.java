package com.example.spark.model.DTO;

import com.example.spark.model.Role;
import com.example.spark.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserSignupDTO {

    private String email;
    private String password;
    private String fullname;
    private String username;
    private Role role;



    public final static UserSignupDTO convertToDTO(User user) {
        UserSignupDTO dto = new UserSignupDTO();
        dto.setEmail(user.getEmail());
        dto.setFullname(user.getFullname());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        return dto;
    }
}
