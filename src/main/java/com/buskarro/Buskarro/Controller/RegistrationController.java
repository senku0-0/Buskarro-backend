package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.Model.RegistrationFields;
import com.buskarro.Buskarro.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Register")
public class RegistrationController {
    @Autowired
    private RegistrationService service;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody RegistrationFields entry){
        try {
            service.saveUser(entry);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
