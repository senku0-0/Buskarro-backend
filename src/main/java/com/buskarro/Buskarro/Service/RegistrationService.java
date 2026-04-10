package com.buskarro.Buskarro.Service;

import com.buskarro.Buskarro.Model.RegistrationFields;
import com.buskarro.Buskarro.Repository.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

// create, view(ALL/Specific), update, delete
@Component
public class RegistrationService {
    @Autowired
    private RegistrationRepo repo;

    public List<RegistrationFields> getAllUsers(){ return  repo.findAll(); }

    public Optional<RegistrationFields> getByEmail(String email){
        return repo.findByEmail(email);
    }
    public Optional<RegistrationFields> getByUsername(String name){
        return repo.findByName(name);
    }
    public void saveUser(RegistrationFields entry){ repo.save(entry); }
    public void deleteUser(String email){ repo.deleteByEmail(email); }

}
