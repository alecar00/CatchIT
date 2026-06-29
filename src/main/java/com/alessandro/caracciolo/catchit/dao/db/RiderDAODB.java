package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.RiderQuery;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class RiderDAODB implements RiderDAO {
    @Override
    public void saveRider(Rider newRider) throws DAOException {
        try{
            RiderQuery.addRider(Connector.getConnection(), newRider);
        }catch(SQLException e){
            throw new DAOException("Cant't save Rider: " + newRider.getIdRider(), e);
        }
    }

    /*
    /Qui e' vuoto perche' faccio il filtering direttamente in query.
    /TO DO: meglio aggiungere il metodo che restituisce tutti i rider
    /in modo tale che se in futuro mi serve funziona
    */
    @Override
    public List<Rider> getAllRiders() {
        return List.of();
    }

    @Override
    public Rider getRiderById(String id) throws DAOException {
        try {
            ResultSet rs = RiderQuery.getRiderById(Connector.getConnection(), id);
            return new Rider (
                    rs.getString("id_rider"),
                    rs.getString("name"),
                    rs.getBoolean("permit_ztl")

            );
        }catch (SQLException e) {
            throw new DAOException("Impossibile ottenere il rider!", e);
        }
    }

    public List<Rider> getAvailableRiders(Order order, Time time) throws DAOException {
        ResultSet rs = null;
        List<Rider> riders = new ArrayList<>();

        try{
            rs = RiderQuery.getAvailableRiders(Connector.getConnection(), time);
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
            throw new DAOException("Impossibile recuperare i riders dal db!",e);
        }
        return riders;
    }

}
