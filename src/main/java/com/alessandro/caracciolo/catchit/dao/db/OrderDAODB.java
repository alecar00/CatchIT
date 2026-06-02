package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.DAOFactory;
import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.RiderQuery;
import com.alessandro.caracciolo.catchit.query.OrderQuery;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAODB implements OrderDAO {

    @Override
    public List<Order> getPendingOrders() throws DAOException {
        //ResultSet rs = null;
        List<Order> orders = new ArrayList<>();

        try (ResultSet rs = OrderQuery.getPendingOrders(Connector.getConnection(), OrderStatus.PENDING)) {
            while (rs.next()) {
                String statusString = rs.getString("status");
                OrderStatus status = OrderStatus.valueOf(statusString);

                var order = new Order(rs.getString("id_order"),
                        rs.getString("address"),
                        rs.getString("costumer"),
                        rs.getString("tel_number"),
                        rs.getTime("delivery_time"),
                        //rs.getDate("date"),
                        //rs.getString("id_restaurant"),
                        status);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException("Impossibile recuperare gli ordini!",e);
        }
        return orders;
    }

    @Override
    public boolean updateOrder(Order order, Rider rider) throws DAOException {
        try {
            int success = RiderQuery.assignRider(Connector.getConnection(), order, rider);
            return success > 0;
        }catch (SQLException e) {
            throw new DAOException("Impossibile assegnare il rider!",e);
        }
    }

    @Override
    public Order getOrderById(String idOrder) throws DAOException {
        //togliere la dao da dentro la dao?
        RiderDAO riderDAO = DAOFactory.getDAOFactory().createRiderDAO();
        try (ResultSet rs = OrderQuery.getOrderById(Connector.getConnection(), idOrder);){
            if (rs.next()) {
                String idRider = rs.getString("id_rider");
                Rider rider = null;
                if(idRider != null){
                    rider = riderDAO.getRiderById(idRider);
                }

                return new Order(rs.getString("id_order"),
                        rs.getString("address"),
                        rs.getString("costumer"),
                        rs.getString("tel_number"),
                        rider,
                        rs.getTime("delivery_time"),
                        OrderStatus.valueOf(rs.getString("status"))
                        );
            }
        }catch (SQLException e) {
            throw new DAOException("Impossibile recuperare l'ordine!", e);
        }
        return null;
    }

    @Override
    public boolean setOrderCompleted(String idOrder) throws DAOException {
        Connection conn = Connector.getConnection();

        try {
            conn.setAutoCommit(false);
            int success = OrderQuery.setOrderCompleted(conn, idOrder);

            if(success == 1){//se viene fatto l'update a 0 o a 2+ righe, c'e' un problema
                conn.commit();
            }else {
                conn.rollback();
            }
        }catch (SQLException e) {
            throw new DAOException("Impossibile impostare l'ordine come completato!", e);
        }
        return true;
    }
}
