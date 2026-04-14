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
    private String authProvider; // "GOOGLE" or "LOCAL"
    private Boolean email_verified;
    private String google_id;
    private String google_token;
}
