package com.alessandro.caracciolo.catchit.api;

import com.alessandro.caracciolo.catchit.bean.RiderBean;
import com.alessandro.caracciolo.catchit.exceptions.NotificationException;
import com.alessandro.caracciolo.catchit.singleton.Configs;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class TelegramNotification implements  Notification {
    private static final Logger logger = Logger.getLogger(Configs.LOGGER_NAME);

    // Per impossibilita' ho indirizzato tutti i messaggi verso un solo utente
    private static final String BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    private static final String CHAT_ID = System.getenv("TELEGRAM_CHAT_ID");

    public void sendNotification(RiderBean rider, String messaggio) throws NotificationException {

        String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";


        String safeMessage = messaggio.replace("\"", "\\\"");
        String jsonPayload = "{\"chat_id\": \"" + CHAT_ID + "\", \"text\": \"" + safeMessage + "\"}";

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.warning("Error Telegram API: " + response.body());
            }
        } catch (Exception e) {
            logger.severe("Impossible to connect to Telegram API: " + e.getMessage());
            throw new NotificationException("Impossible to connect to Telegram API: " + e.getMessage());
        }
    }
}
