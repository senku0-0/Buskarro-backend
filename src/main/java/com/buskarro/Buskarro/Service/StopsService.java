package com.buskarro.Buskarro.Service;

import com.buskarro.Buskarro.Model.StopsFields;
import com.buskarro.Buskarro.Repository.StopsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StopsService {
    @Autowired
    private StopsRepo repo;

    public List<StopsFields> getAllStops(){ return repo.findAll(); }

    public Optional<StopsFields> getByStopName(String stopName){
        return repo.findByStopName(stopName);
    }

    public StopsFields saveStop(StopsFields entry){ return repo.save(entry); }

    public void deleteByStopId(String stopId){ repo.deleteByStopId(stopId); }
}

