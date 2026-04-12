package com.buskarro.Buskarro.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document
public class Session {
    @Id
    private ObjectId id;

    private ObjectId userId;       // link to RegistrationFields
    private String token;          // random session token or JWT ID
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    private String ipAddress;      // for security
    private String userAgent;      // track device/browser
    private boolean isActive;      // soft revoke
}
