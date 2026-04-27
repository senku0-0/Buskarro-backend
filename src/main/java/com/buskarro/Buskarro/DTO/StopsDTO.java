package com.buskarro.Buskarro.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StopsDTO {
    private String stopId;
    private String stopName;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private String location_link;
}
