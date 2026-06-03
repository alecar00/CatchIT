package com.alessandro.caracciolo.catchit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Time;

public class GsonProvider {
    private GsonProvider() {}
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Time.class, new TypeAdapter<Time>() {
                @Override
                public void write(JsonWriter out, Time value) throws IOException {
                    if (value == null) {
                        out.nullValue();
                    } else {
                        out.value(value.toString());
                    }
                }

                @Override
                public Time read(JsonReader in) throws IOException {
                    if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                        in.nextNull();
                        return null;
                    }
                    return Time.valueOf(in.nextString());
                }
            })
            .setPrettyPrinting()
            .create();

    public static Gson getGson() {
        return gson;
    }
}
