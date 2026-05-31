package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.dao.RiderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.AssignRider;
import com.alessandro.caracciolo.catchit.query.SearchOrdersByStatus;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAODB implements OrderDAO {

    @Override
    public List<Order> getPendingOrders() throws DAOException {
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();

        try {
            rs = SearchOrdersByStatus.getPendingOrders(Connector.getConnection(), OrderStatus.PENDING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (true){
                assert rs != null;
                if (!rs.next()) break;
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
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public boolean updateOrder(Order order, Rider rider) throws DAOException {
        try {
            int success = AssignRider.assignRider(Connector.getConnection(), order, rider);
            return success > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Order getOrderById(String idOrder) throws DAOException {
        ResultSet rs = null;
        RiderDAO riderDAO = new RiderDAODB();
        try {
            rs = SearchOrdersByStatus.getOderById(Connector.getConnection(), idOrder);
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
            e.printStackTrace();
        }
        return null;
    }
}
