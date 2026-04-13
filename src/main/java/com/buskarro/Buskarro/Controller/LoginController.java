package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.DTO.LoginDTO;
import com.buskarro.Buskarro.Model.RegistrationFields;
import com.buskarro.Buskarro.Service.RegistrationService;
import com.buskarro.Buskarro.Utility.GoogleJWTUtility;
import com.buskarro.Buskarro.Utility.JWTUtility;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private RegistrationService regserv;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> setLoginUser(@RequestBody LoginDTO req, HttpServletRequest request){
        try {
            if(req.getGoogle_token() != null && !req.getGoogle_token().trim().isEmpty()){
                JWTClaimsSet claims = GoogleJWTUtility.verify(req.getGoogle_token());
                String email = claims.getStringClaim("email");
                String name = claims.getStringClaim("name");
                RegistrationFields reg = new RegistrationFields();
                reg
                        .setName(name)
                        .setEmail(email)
                        .setRole("USER");
                return ResponseEntity.ok(Map.of(
                        "email", email,
                        "name", name
                ));
            }else {
                String email = req.getEmail();
                String rawpassword = req.getPassword();
                System.out.println(email + " " + rawpassword);
                Optional<RegistrationFields> userOpt = regserv.getByEmail(email);

                if (userOpt.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
                } // if the user does not exit the get() method throws an Exception os handling it beforehand safely

                RegistrationFields user = userOpt.get(); // get method is used unwrap the optional container and only return RegistrationField
                if(!passwordEncoder.matches(rawpassword, user.getPassword())){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                }
                String token = jwtUtility.generateToken(user.getId(), user.getEmail(), user.getRole(), user.getName());

                return ResponseEntity.ok(Map.of("jwt_token",token));
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
