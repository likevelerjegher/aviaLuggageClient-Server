package com.example.api;

import com.example.util.TokenStorage;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String post(String endpoint, Object requestBody) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        // Если есть токен
        String token = TokenStorage.getToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            objectMapper.writeValue(os, requestBody);
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        return response.toString();
    }

    public static String get(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        String token = TokenStorage.getToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        return response.toString();
    }
    public static void delete(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        String token = TokenStorage.getToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.getInputStream().close();
    }

    public static void put(String endpoint, Object requestBody) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        String token = TokenStorage.getToken();
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setDoOutput(true);
        try (OutputStream os = conn.getOutputStream()) {
            objectMapper.writeValue(os, requestBody);
        }
        conn.getInputStream().close();
    }

}

