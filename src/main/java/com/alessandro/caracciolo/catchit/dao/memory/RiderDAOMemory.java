package com.alessandro.caracciolo.catchit.dao.memory;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.model.User;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.alessandro.caracciolo.catchit.dao.memory.OrderDAOMemory.orders;

public class RiderDAOMemory implements RiderDAO {
    private static List<Rider> riders = new ArrayList<>();

    static{
        riders.add(new Rider("1", "Pasquale Bianchi", true));
    }

    @Override
    public void saveRider(Rider newRider) {
        riders.add(newRider);
    }

    @Override
    public List<Rider> getAllRiders() {
        return riders;
    }

    @Override
    public Rider getRiderById(String id) {
        for (Rider rider : riders) {
            if (rider.getIdRider().equals(id)) return rider;
        }
        return null;
    }

    @Override
    public List<Rider> getAvailableRiders(Order selectedOrder, Time time) {
        List<Rider> availableRiders = riders;
        for (Order order : orders) {
            if (order.getTime().equals(time)) {
                availableRiders.remove(order.getRider());
            }
        }
        return availableRiders;
    }
}
