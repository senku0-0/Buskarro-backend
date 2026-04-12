package com.buskarro.Buskarro.Repository;

import com.buskarro.Buskarro.Model.SessionFields;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepo extends MongoRepository<SessionFields, ObjectId> {
}
