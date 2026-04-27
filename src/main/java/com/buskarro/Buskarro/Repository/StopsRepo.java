package com.buskarro.Buskarro.Repository;

import com.buskarro.Buskarro.Model.StopsFields;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StopsRepo extends MongoRepository<StopsFields, ObjectId> {
    Optional<StopsFields> findByStopName(String stopName);
    void deleteByStopId(String stopId);
}

