package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.Model.SessionFields;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

public class Login {

    @PostMapping
    public ResponseEntity<?> setLoginUser(@RequestBody req, Http){
        SessionFields session = new SessionFields();
        session.setUserId(req.getId());
        session.setToken();
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(24)); // 24-hour session
        String ipAddress = request.getRemoteAddr();
        session.setIpAddress(ipAddress);
    }
}
