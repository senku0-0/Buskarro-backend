package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.DTO.RegistrationDTO;
import com.buskarro.Buskarro.Model.RegistrationFields;
import com.buskarro.Buskarro.Model.SessionFields;
import com.buskarro.Buskarro.Repository.SessionRepo;
import com.buskarro.Buskarro.Service.RegistrationService;
import com.buskarro.Buskarro.Utility.JWTUtility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


@RestController
@RequestMapping("/Register")
public class RegistrationController {
    @Autowired
    private RegistrationService service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    SessionRepo ses;
    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody RegistrationDTO req, HttpServletRequest request){
        try {
            RegistrationFields user = new RegistrationFields();
            String password = req.getPassword();
            String encode_password = passwordEncoder.encode(password);
            user.setName(req.getName());
            user.setEmail(req.getEmail());
            user.setPhone(req.getPhone());
            user.setPassword(encode_password);
            user.setRole("DEVELOPER");
            // Save user first
            RegistrationFields savedUser = service.saveUser(user);
            // Create session (auto-login)
            SessionFields session = new SessionFields();
            String getJwt = jwtUtility.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), savedUser.getName() );
            session.setUserId(savedUser.getId());
            session.setToken(getJwt);
            session.setCreatedAt(LocalDateTime.now()); // store current time
            session.setExpiresAt(LocalDateTime.now().plusHours(24)); // 24 hrs of session
            session.setIpAddress(request.getRemoteAddr()); // user ip Address
            session.setActive(true);
            ses.save(session);
            System.out.println("User saved successfully!");
            return ResponseEntity.ok(Map.of("jwt_token",getJwt));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
