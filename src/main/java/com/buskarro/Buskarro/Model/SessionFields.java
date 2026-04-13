package com.buskarro.Buskarro.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document
@Accessors(chain = true)
public class SessionFields {
    @Id
    private ObjectId id;
    private ObjectId userId;       // link to RegistrationFields, get from Registration
    private String token;          // random session token or JWT ID, google auth
    private LocalDateTime createdAt; // auto filled
    private LocalDateTime expiresAt; // auto filled
    private String ipAddress;      // for security, get from user
    private boolean isActive;      // soft revoke, set by default
}
