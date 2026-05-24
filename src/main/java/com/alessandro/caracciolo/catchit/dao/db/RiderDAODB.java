package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.SearchAvailableRiders;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RiderDAODB implements RiderDAO {
    @Override
    public void saveRider(Rider rider) {
        //not implemented
    }

    @Override
    public List<Rider> getAllRiders() {
        return List.of();
    }

    @Override
    public Rider getRiderById(String id) {
        return null;
    }

    public List<Rider> getAvailableRiders(Order order, Time time) {
        ResultSet rs = null;
        List<Rider> riders = new ArrayList<>();

        try {
            rs = SearchAvailableRiders.getAvailableRiders(Connector.getConnection(), time);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try{
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                var rider = new Rider(
                        rs.getString("r.id_rider"),
                        rs.getString("r.name"),
                        rs.getBoolean("r.permit_ztl")
                );
                riders.add(rider);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return riders;
    }

}
