package com.alessandro.caracciolo.catchit.dao.fs;

import com.alessandro.caracciolo.catchit.dao.UserDAO;
import com.alessandro.caracciolo.catchit.exceptions.DAOException;
import com.alessandro.caracciolo.catchit.model.User;
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

public class UserDAOFS implements UserDAO {

    @Override
    public User getUserByUsername(String username) throws DAOException {
        String filePath = FSConfiguration.FS_DIR + FSConfiguration.FS_USER;

        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        Gson gson = GsonProvider.getGson();

        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, listType);

            if (users != null) {
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        return user;
                    }
                }
            }
        } catch (IOException e) {
            throw new DAOException("Impossible get users", e);
        }

        return null;
    }

    @Override
    public void saveUser(User newUser) throws DAOException {
        String filePath = FSConfiguration.FS_DIR + FSConfiguration.FS_USER;
        File file = new File(filePath);

        Gson gson = GsonProvider.getGson();
        List<User> users;

        //leggo tutto il file per scrivere bene il file json, altrimenti non riuscirei a gestire le parentesi
        if (!file.exists()) {
            users = new ArrayList<>();
        } else {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<User>>() {}.getType();
                users = gson.fromJson(reader, listType);
                if (users == null) {
                    users = new ArrayList<>();
                }
            } catch (IOException e) {
                throw new DAOException("Error reading existing users for registration", e);
            }
        }

        users.add(newUser);

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            throw new DAOException("Critical error saving user to filesystem", e);
        }
    }

    @Override
    public void deleteUser(String username) throws DAOException {
        //to be implemented
    }
}