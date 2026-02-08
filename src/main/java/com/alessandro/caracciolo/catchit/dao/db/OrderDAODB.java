package com.alessandro.caracciolo.catchit.dao.db;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.query.SearchPendingOrders;
import com.alessandro.caracciolo.catchit.singleton.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAODB implements OrderDAO {
    @Override
    public void saveOrder(Order order) {

    }

    @Override
    public List<Order> getPendingOrders() {
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();

        try {
            rs = SearchPendingOrders.SearchOrdersByStatus(Connector.getConnection(), OrderStatus.PENDING);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            while (true){
                assert rs != null;
                if (!rs.next()) break;
                String statusString = rs.getString("status");
                OrderStatus status = OrderStatus.valueOf(statusString);

                var order = new Order(rs.getString("id"),
                        rs.getString("address"),
                        rs.getString("costumer"),
                        rs.getString("telNumber"),
                        rs.getTime("time"),
                        rs.getDate("date"),
                        status);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public void updateOrder(Order order) {

    }
}
