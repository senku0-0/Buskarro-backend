package com.buskarro.Buskarro.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class RegistrationFields {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String username;
    private String phoneno;
    private String password;
}
