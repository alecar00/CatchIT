package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.RiderDAO;
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
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RiderDAOFS implements RiderDAO {
    private static final String FS_DIR = FSConfiguration.FS_DIR;
    private static final String FS_RIDER = FSConfiguration.FS_RIDER;
    private static final String FS_ORDER = FSConfiguration.FS_ORDER;

    private final Gson gson = GsonProvider.getGson();
    private static final Logger log = Logger.getLogger(RiderDAOFS.class.getName());

    @Override
    public void saveRider(Rider rider) {
        //not implemented
    }

    @Override
    public List<Rider> getAllRiders() throws DAOException {
        List<Rider> riders = new ArrayList<>();
        File file = new File(FS_DIR + FS_RIDER);

        if (!file.exists() || file.length() == 0) {
            return riders;
        }

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Rider>>() {}.getType();
            riders = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new DAOException("Impossibile leggere i rider dal File System.", e);
        }
        log.log(Level.INFO, "Riders lista: {0} ", riders);
        return riders;
    }

    @Override
    public Rider getRiderById(String id) {
        //not implemented
        return null;
    }

    @Override
    public List<Rider> getAvailableRiders(Order order, Time time) throws DAOException {
        List<Rider> allRiders = getAllRiders();
        if (allRiders == null || allRiders.isEmpty()) {
            return new ArrayList<>(); // Ritorna direttamente una lista vuota
        }

        List<String> busyRiderIds = new ArrayList<>();
        File ordersFile = new File(FS_DIR + FS_ORDER);

        if (ordersFile.exists() && ordersFile.length() > 0) {
            try (FileReader reader = new FileReader(ordersFile)) {
                Type orderListType = new TypeToken<ArrayList<Order>>() {
                }.getType();
                List<Order> allOrders = gson.fromJson(reader, orderListType);

                if (allOrders != null) {
                    busyRiderIds = allOrders.stream()
                            .filter(o -> o.getTime() != null && o.getTime().equals(time))
                            .filter(o -> o.getRider() != null)
                            .filter(o -> o.getStatus() != OrderStatus.COMPLETED)
                            .map(o -> o.getRider().getIdRider())
                            .toList();
                }
            } catch (IOException e) {
                throw new DAOException("Impossibile leggere gli ordini per verificare le disponibilità.", e);
            }
        }

        List<String> finalBusyRiderIds = busyRiderIds;
        return allRiders.stream()
                .filter(r -> !finalBusyRiderIds.contains(r.getIdRider()))
                .toList();
    }
}
