package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.Query;
import com.alessandro.caracciolo.catchit.query.RiderQuery;
import com.alessandro.caracciolo.catchit.singleton.Configs;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RiderDAODB implements RiderDAO {
    Logger logger = Logger.getLogger(Configs.LOGGER_NAME);
    @Override
    public void saveRider(Rider newRider) throws DAOException {
        try{
            RiderQuery.addRider(Connector.getConnection(), newRider);
        }catch(SQLException e){
            throw new DAOException("Can't save Rider: " + newRider.getIdRider(), e);
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
        try (Connection conn = Connector.getConnection();
             PreparedStatement ps = conn.prepareStatement(Query.GET_RIDER_BY_ID)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Rider(
                            rs.getString("id_rider"),
                            rs.getString("name"),
                            rs.getBoolean("permit_ztl")
                    );
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e, () -> "Can't get Rider: " + id);
            throw new DAOException("Impossibile ottenere il rider!", e);
        }
    }

    public List<Rider> getAvailableRiders(Order order, Time time) throws DAOException {
        ResultSet rs;
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
