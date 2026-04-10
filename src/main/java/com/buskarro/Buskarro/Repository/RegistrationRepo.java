package com.buskarro.Buskarro.Repository;

import com.buskarro.Buskarro.Model.RegistrationFields;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegistrationRepo extends MongoRepository<RegistrationFields, ObjectId> {
    Optional<RegistrationFields> findByEmail(String email);
    Optional<RegistrationFields> findByName(String name);
    Optional<RegistrationFields> deleteByEmail(String email);
}
