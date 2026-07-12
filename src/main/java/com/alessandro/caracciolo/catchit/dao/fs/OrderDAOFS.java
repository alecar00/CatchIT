package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.OrderDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.Order;
import com.alessandro.caracciolo.catchit.model.OrderStatus;
import com.alessandro.caracciolo.catchit.model.Rider;
import com.alessandro.caracciolo.catchit.utils.FSConfiguration;
import com.alessandro.caracciolo.catchit.utils.GsonProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOFS implements OrderDAO {
    private static final String FS_DIR = FSConfiguration.FS_DIR;
    private static final String FS_ORDER = FSConfiguration.FS_ORDER;

    private final Gson gson = GsonProvider.getGson();

    @Override
    public List<Order> getPendingOrders() throws DAOException {
        List<Order> pendingOrders = new ArrayList<>();
        File file = new File(FS_DIR + FS_ORDER);

        if (!file.exists() || file.length() == 0) {
            return pendingOrders;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
            List<Order> allOrders = gson.fromJson(reader, listType);

            if (allOrders != null) {
                for (Order order : allOrders) {
                    if (order.getStatus() == OrderStatus.PENDING) {
                        pendingOrders.add(order);
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException("Impossibile leggere gli ordini dal File System.", e);
        }

        return pendingOrders;
    }

    @Override
    public void assignOrder(Order order, Rider rider) throws DAOException {
        File file = new File(FS_DIR + FS_ORDER);
        if (!file.exists()) {
            throw new DAOException("Impossibile aggiornare: il file degli ordini non esiste.");
        }

        List<Order> allOrders;
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
            allOrders = gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new DAOException("Errore durante la lettura degli ordini per l'aggiornamento.", e);
        }

        // 2. Anomalia Dati: Il file c'è ma è vuoto
        if (allOrders == null) {
            throw new DAOException("Impossibile aggiornare: la lista degli ordini risulta vuota o corrotta.");
        }

        boolean founded = false;
        for (Order o : allOrders) {
            if (o.getIdOrder().equals(order.getIdOrder())) {
                o.setRider(rider);
                o.setStatus(OrderStatus.ASSIGNED);
                founded = true;
                break; // Usciamo dal ciclo appena troviamo l'ordine
            }
        }

        if(founded) {
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(allOrders, writer);
            } catch (IOException e) {
                throw new DAOException("Impossibile salvare l'ordine aggiornato nel File System.", e);
            }
        }else{
            throw new DAOException("Aggiornamento fallito: ordine con ID " + order.getIdOrder() + " non trovato nel sistema.");
        }
    }

    @Override
    public Order getOrderById(String id) throws DAOException {
        File file = new File(FS_DIR + FS_ORDER);
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
            List<Order> allOrders = gson.fromJson(reader, listType);

            if (allOrders != null) {
                for (Order order : allOrders) {
                    if (order.getIdOrder().equals(id)) {
                        return order;
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException("Errore durante il recupero dell'ordine ID: " + id, e);
        }
        return null;
    }

    @Override
    public void setOrderCompleted(String id) throws DAOException {
        File file = openOrderFile();

        List<Order> allOrders;
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
            allOrders = gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new DAOException("Errore durante la lettura degli ordini per il completamento.", e);
        }

        if (allOrders == null) {
            throw new DAOException("Impossibile aggiornare: la lista degli ordini risulta vuota o corrotta.");
        }

        boolean founded = false;
        for (Order o : allOrders) {
            if (o.getIdOrder().equals(id)) {
                o.setStatus(OrderStatus.COMPLETED);
                founded = true;
                break;
            }
        }

        if (founded) {
            try (FileWriter writer = new FileWriter(file)) {
                gson.toJson(allOrders, writer);
            } catch (IOException e) {
                throw new DAOException("Impossibile completare l'ordine nel File System.", e);
            }
        }else{
            throw new DAOException("Aggiornamento fallito: ordine con ID " + id + " non trovato nel sistema.");
        }
    }

    @Override
    public List<Order> getOrdersByRider(String riderId) {
        return List.of();
    }

    @Override
    public void setOrderInDelivery(String orderId) {
        //To be implemented
    }

    @Override
    public List<Order> getOrders() throws DAOException {
        List<Order> allOrders = new ArrayList<>();
        File file = new File(FS_DIR + FS_ORDER);

        if (!file.exists() || file.length() == 0) {
            return allOrders;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
            allOrders = gson.fromJson(reader, listType);
        } catch (IOException e) {
            throw new DAOException("Impossibile leggere gli ordini dal File System.", e);
        }

        return allOrders;
    }

    private File openOrderFile() throws DAOException {
        File file = new File(FS_DIR + FS_ORDER);
        if (!file.exists()) {
            throw new DAOException("Impossibile aggiornare: il file degli ordini non esiste.");
        }
        return file;
    }
}
