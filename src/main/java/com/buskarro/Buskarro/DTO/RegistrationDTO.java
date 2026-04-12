package com.buskarro.Buskarro.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationDTO {
    private String name;
    private String phone;
    private String email;
    private String password;
}
