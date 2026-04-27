package com.buskarro.Buskarro.Controller;

import com.buskarro.Buskarro.DTO.StopsDTO;
import com.buskarro.Buskarro.Model.StopsFields;
import com.buskarro.Buskarro.Service.StopsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stops")
public class StopsController {
    @Autowired
    private StopsService service;
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
            return new ResponseEntity<>(service.getAllStops(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody StopsDTO req, HttpServletRequest request){
        try {
            StopsFields stops = new StopsFields();
            stops
                    .setStopName(req.getStopName())
                    .setStopId(req.getStopId())
                    .setCity(req.getCity())
                    .setState(req.getState())
                    .setLatitude(req.getLatitude())
                    .setLongitude(req.getLongitude())
                    .setLocation_link(req.getLocation_link());
            service.saveStop(stops);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
