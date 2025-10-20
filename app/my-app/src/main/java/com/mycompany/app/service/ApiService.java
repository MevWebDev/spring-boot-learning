package com.mycompany.app.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.app.model.Employee;
import com.mycompany.app.model.Position;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Klasa odpowiedzialna za integrację z REST API.
 */
public class ApiService {

    private static final Gson gson = new Gson();

    /**
     * Wykonuje zapytanie GET do podanego API, parsuje odpowiedź JSON i zwraca listę obiektów Employee.
     *
     * @param apiUrl URL API do pobrania danych.
     * @return Lista obiektów Employee.
     * @throws ApiException W przypadku błędów HTTP lub problemów z parsowaniem JSON.
     */
    public List<Employee> fetchEmployeesFromApi(String apiUrl) throws ApiException {
        List<Employee> employees = new ArrayList<>();

        try {
            // Wykonanie zapytania HTTP GET
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ApiException("HTTP error: " + response.statusCode());
            }

            // Odczyt odpowiedzi
            String responseContent = response.body();

            // Parsowanie JSON
            JsonArray jsonArray = gson.fromJson(responseContent.toString(), JsonArray.class);
            for (JsonElement element : jsonArray) {
                String fullName = element.getAsJsonObject().get("name").getAsString();
                String email = element.getAsJsonObject().get("email").getAsString();
                String companyName = element.getAsJsonObject().get("company").getAsJsonObject().get("name").getAsString();

                // Rozdzielenie pełnego imienia na firstName i lastName
                String[] nameParts = fullName.split(" ", 2);
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                // Tworzenie obiektu Employee
                Employee employee = new Employee(firstName + " " + lastName, email, companyName, Position.PROGRAMISTA);
                employees.add(employee);
            }

        } catch (JsonSyntaxException e) {
            throw new ApiException("Błąd parsowania JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ApiException("Błąd podczas pobierania danych z API: " + e.getMessage(), e);
        }

        return employees;
    }
}
