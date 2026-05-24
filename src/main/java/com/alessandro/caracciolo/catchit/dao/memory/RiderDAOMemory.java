package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;

import java.sql.Time;
import java.util.List;

public class RiderDAOMemory implements RiderDAO {
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

    @Override
    public List<Rider> getAvailableRiders(Order order, Time time) {
        return List.of();
    }
}
