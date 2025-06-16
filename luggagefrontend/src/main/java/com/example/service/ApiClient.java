package com.example.service;

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

@Service
public class ApiClient {
    private final WebClient webClient;
    private String token;

    public ApiClient(@Value("${client.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String login(String username, String password) {
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
            return token;
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Login failed: " + e.getStatusCode());
        }
    }

    public String register(String username, String password) {
        Auth authRequest = new Auth();
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        try {
            Auth response = webClient.post()
                    .uri("/api/auth/register")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(Auth.class)
                    .block();
            return response.getToken();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Registration failed: " + e.getStatusCode());
        }
    }

    public List<Baggage> getAllBaggage() {
        try {
            return webClient.get()
                    .uri("/api/baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get baggage: " + e.getStatusCode());
        }
    }

    public Baggage createBaggage(Baggage baggage) {
        try {
            return webClient.post()
                    .uri("/api/baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(baggage)
                    .retrieve()
                    .bodyToMono(Baggage.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to create baggage: " + e.getStatusCode());
        }
    }

    public Baggage updateBaggageStatus(Long id, String status) {
        try {
            return webClient.put()
                    .uri(uriBuilder -> uriBuilder.path("/api/baggage/{id}/status")
                            .queryParam("status", status).build(id))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Baggage.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to update baggage status: " + e.getStatusCode());
        }
    }

    public List<Baggage> getBaggageByPassenger(Long passengerId) {
        try {
            return webClient.get()
                    .uri("/api/baggage/passenger/{passengerId}", passengerId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get baggage by passenger: " + e.getStatusCode());
        }
    }

    public List<Flight> getAllFlights() {
        try {
            return webClient.get()
                    .uri("/api/flights")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Flight.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get flights: " + e.getStatusCode());
        }
    }

    public Flight createFlight(Flight flight) {
        try {
            return webClient.post()
                    .uri("/api/flights")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(flight)
                    .retrieve()
                    .bodyToMono(Flight.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to create flight: " + e.getStatusCode());
        }
    }

    public List<Passenger> getAllPassengers() {
        try {
            return webClient.get()
                    .uri("/api/passengers")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Passenger.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get passengers: " + e.getStatusCode());
        }
    }

    public Passenger createPassenger(Passenger passenger) {
        try {
            return webClient.post()
                    .uri("/api/passengers")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(passenger)
                    .retrieve()
                    .bodyToMono(Passenger.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to create passenger: " + e.getStatusCode());
        }
    }

    public Auth createUserForPassenger(Long id, Auth authRequest) {
        try {
            return webClient.post()
                    .uri("/api/passengers/{id}/user", id)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .bodyValue(authRequest)
                    .retrieve()
                    .bodyToMono(Auth.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to create user for passenger: " + e.getStatusCode());
        }
    }

    public List<Baggage> getLostBaggageReport() {
        try {
            return webClient.get()
                    .uri("/api/reports/lost-baggage")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToFlux(Baggage.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get lost baggage report: " + e.getStatusCode());
        }
    }

    public String getBaggageStatistics() {
        try {
            return webClient.get()
                    .uri("/api/reports/baggage-statistics")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Failed to get baggage statistics: " + e.getStatusCode());
        }
    }
}