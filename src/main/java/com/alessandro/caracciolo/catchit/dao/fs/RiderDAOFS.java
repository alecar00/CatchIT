package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.util.List;

public class RiderDAOFS implements RiderDAO {
    @Override
    public void saveRider(Rider rider) {

    }

    @Override
    public List<Rider> getAllRiders() {
        return List.of();
    }

    @Override
    public Rider getRiderById(String id) {
        return null;
    }
}
