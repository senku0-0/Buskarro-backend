package com.buskarro.Buskarro.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StopsFields {
    @Id
    private ObjectId id;
    private String stopId;
    private String stopName;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private String location_link;
}
