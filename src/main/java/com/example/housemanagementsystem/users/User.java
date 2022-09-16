package com.example.housemanagementsystem.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {
    private Integer userID;
    private String apartmentNo;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;

}
