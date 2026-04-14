package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.DTO.RegistrationDTO;
import com.buskarro.Buskarro.Model.RegistrationFields;
import com.buskarro.Buskarro.Model.SessionFields;
import com.buskarro.Buskarro.Repository.SessionRepo;
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

import java.time.LocalDateTime;
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
    @Autowired
    SessionRepo ses;

    @PostMapping
    public ResponseEntity<?> setLoginUser(@RequestBody RegistrationDTO req, HttpServletRequest request){
        try {
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
            SessionFields session = new SessionFields();
            session
                    .setUserId(user.getId())
                    .setToken(token)
                    .setCreatedAt(LocalDateTime.now()) // store current time
                    .setExpiresAt(LocalDateTime.now().plusHours(24))// 24 hrs of session
                    .setIpAddress(request.getRemoteAddr()) // user ip Address
                    .setActive(true);
            ses.save(session);
            return ResponseEntity.ok(Map.of("jwt_token",token));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/google-auth")
    public ResponseEntity<?> setGoogleLoginUser(@RequestBody RegistrationDTO req, HttpServletRequest request){
        try {
            JWTClaimsSet claims = GoogleJWTUtility.verify(req.getGoogle_token());
            String email = claims.getStringClaim("email");
            String name = claims.getStringClaim("name");
            String googleUserId = claims.getSubject();
            Boolean emailVerified = claims.getBooleanClaim("email_verified");
            Optional<RegistrationFields> userOpt = regserv.getByEmail(email);
            RegistrationFields user;
            SessionFields session = new SessionFields();
            if (userOpt.isEmpty()) {
                RegistrationFields reg = new RegistrationFields();
                reg.setName(name)
                        .setEmail(email)
                        .setRole("USER")
                        .setGoogle_id(googleUserId)
                        .setEmail_verified(emailVerified)
                        .setAuthProvider("GOOGLE");
                user = regserv.saveUser(reg);
            } else {
                user = userOpt.get();
            }
            String jwtToken = jwtUtility.generateToken(
                    user.getId(),
                    user.getEmail(),
                    user.getRole(),
                    user.getName()
            );
            session
                    .setUserId(user.getId())
                    .setToken(jwtToken)
                    .setCreatedAt(LocalDateTime.now()) // store current time
                    .setExpiresAt(LocalDateTime.now().plusHours(24))// 24 hrs of session
                    .setIpAddress(request.getRemoteAddr()) // user ip Address
                    .setActive(true);
            ses.save(session);
            return ResponseEntity.ok(Map.of("jwt_token", jwtToken));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "error", "Google authentication failed: " + e.getMessage()
                    ));
        }
    }
}
