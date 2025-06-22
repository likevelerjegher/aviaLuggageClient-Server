package com.example.service;

import com.example.model.UserDTO;
import com.example.model.Auth;
import com.example.model.Baggage;
import com.example.model.Flight;
import com.example.model.Passenger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ApiClient {
    private static final Logger logger = Logger.getLogger(ApiClient.class.getName());
    private final WebClient webClient;
    private String token;

    public ApiClient(@Value("${client.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public List<UserDTO> getAllEmployees() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching employees with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/users/employees")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(UserDTO.class)
                    .collectList()
                    .block();
        });
    }

    public UserDTO createEmployee(UserDTO user) {
        return executeWithTokenCheck(() -> {
            logger.info("Creating employee with token: " + (token != null ? "Present" : "Null"));
            return webClient.post()
                    .uri("/api/users/employee")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        });
    }

    public void deleteEmployee(Long id) {
        executeWithTokenCheck(() -> {
            logger.info("Deleting employee ID " + id + " with token: " + (token != null ? "Present" : "Null"));
            webClient.delete()
                    .uri("/api/users/employee/{id}", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            return null;
        });
    }

    public UserDTO updateEmployee(Long id, UserDTO user) {
        return executeWithTokenCheck(() -> {
            logger.info("Updating employee ID " + id + " with token: " + (token != null ? "Present" : "Null"));
            return webClient.put()
                    .uri("/api/users/employee/{id}", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(UserDTO.class)
                    .block();
        });
    }

    public String login(String username, String password) {
        logger.info("Attempting login for username: " + username);
        Auth authRequest = new Auth();
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        try {
            Auth response = webClient.post()
                    .uri("/api/auth/login")
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(Auth.class)
                    .block();
            this.token = response.getToken();
            logger.info("Login successful, token: " + (token != null ? token.substring(0, Math.min(token.length(), 10)) + "..." : "Null"));
            return token;
        } catch (WebClientResponseException e) {
            logger.severe("Login failed with status: " + e.getStatusCode() + ", message: " + e.getMessage());
            throw new RuntimeException("Login failed: " + e.getStatusCode());
        } catch (Exception e) {
            logger.severe("Unexpected login error: " + e.getMessage());
            throw new RuntimeException("Unexpected login error: " + e.getMessage());
        }
    }

    public String register(String username, String password) {
        return executeWithTokenCheck(() -> {
            logger.info("Attempting registration for username: " + username);
            Auth authRequest = new Auth();
            authRequest.setUsername(username);
            authRequest.setPassword(password);
            Auth response = webClient.post()
                    .uri("/api/auth/register")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(Auth.class)
                    .block();
            logger.info("Registration successful for username: " + username);
            return response.getToken();
        });
    }

    public List<Baggage> getAllBaggage() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching baggage with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        });
    }

    public Baggage createBaggage(Baggage baggage) {
        return executeWithTokenCheck(() -> {
            logger.info("Creating baggage with token: " + (token != null ? "Present" : "Null"));
            return webClient.post()
                    .uri("/api/baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(baggage)
                    .retrieve()
                    .bodyToMono(Baggage.class)
                    .block();
        });
    }

    public Baggage updateBaggageStatus(Long id, String status) {
        return executeWithTokenCheck(() -> {
            logger.info("Updating baggage status ID " + id + " with token: " + (token != null ? "Present" : "Null"));
            return webClient.put()
                    .uri(uriBuilder -> uriBuilder.path("/api/baggage/{id}/status")
                            .queryParam("status", status).build(id))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Baggage.class)
                    .block();
        });
    }

    public List<Baggage> getBaggageByPassenger(Long passengerId) {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching baggage for passenger ID " + passengerId + " with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/baggage/passenger/{passengerId}", passengerId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        });
    }

    public List<Flight> getAllFlights() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching flights with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/flights")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Flight.class)
                    .collectList()
                    .block();
        });
    }

    public Flight createFlight(Flight flight) {
        return executeWithTokenCheck(() -> {
            logger.info("Creating flight with token: " + (token != null ? "Present" : "Null"));
            return webClient.post()
                    .uri("/api/flights")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(flight)
                    .retrieve()
                    .bodyToMono(Flight.class)
                    .block();
        });
    }

    public List<Passenger> getAllPassengers() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching passengers with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/passengers")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Passenger.class)
                    .collectList()
                    .block();
        });
    }

    public Passenger createPassenger(Passenger passenger) {
        return executeWithTokenCheck(() -> {
            logger.info("Creating passenger with token: " + (token != null ? "Present" : "Null"));
            return webClient.post()
                    .uri("/api/passengers")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(passenger)
                    .retrieve()
                    .bodyToMono(Passenger.class)
                    .block();
        });
    }

    public Auth createUserForPassenger(Long id, Auth authRequest) {
        return executeWithTokenCheck(() -> {
            logger.info("Creating user for passenger ID " + id + " with token: " + (token != null ? "Present" : "Null"));
            return webClient.post()
                    .uri("/api/passengers/{id}/user", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(Auth.class)
                    .block();
        });
    }

    public List<Baggage> getLostBaggageReport() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching lost baggage report with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/reports/lost-baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        });
    }

    public String getBaggageStatistics() {
        return executeWithTokenCheck(() -> {
            logger.info("Fetching baggage statistics with token: " + (token != null ? "Present" : "Null"));
            return webClient.get()
                    .uri("/api/reports/baggage-statistics")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        });
    }

    public String getToken() {
        return token;
    }

    private <T> T executeWithTokenCheck(Supplier<T> apiCall) {
        if (token == null || token.isEmpty()) {
            logger.warning("No valid token, cannot execute API call");
            throw new RuntimeException("Please log in to continue");
        }
        try {
            return apiCall.get();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is4xxClientError() && e.getStatusCode().value() == 401) {
                logger.severe("401 Unauthorized, token may be expired or invalid");
                throw new RuntimeException("Session expired, please log in again");
            }
            logger.severe("API call failed: " + e.getStatusCode() + ", message: " + e.getMessage());
            throw new RuntimeException("API call failed: " + e.getStatusCode());
        }
    }

    @FunctionalInterface
    private interface Supplier<T> {
        T get();
    }
}