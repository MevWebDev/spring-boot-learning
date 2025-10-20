package com.mycompany.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.model.Employee;
import com.mycompany.app.model.Position;

/**
 * Klasa odpowiedzialna za importowanie danych pracowników z pliku CSV.
 */
public class ImportService {

    private final EmployeeService employeeService;

    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Importuje dane pracowników z pliku CSV.
     *
     * @param filePath Ścieżka do pliku CSV.
     * @return Obiekt ImportSummary zawierający liczbę zaimportowanych pracowników i listę błędów.
     */
    public ImportSummary importFromCsv(String filePath) {
        List<String> errors = new ArrayList<>();
        int importedCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            // Pomijamy nagłówek
            reader.readLine();
            lineNumber++;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Pomijamy puste linie
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");

                // Walidacja liczby pól
                if (fields.length != 6) {
                    errors.add("Linia " + lineNumber + ": Nieprawidłowa liczba pól");
                    continue;
                }

                try {
                    String firstName = fields[0].trim();
                    String lastName = fields[1].trim();
                    String email = fields[2].trim();
                    String company = fields[3].trim();
                    double salary = Double.parseDouble(fields[5].trim());

                    // Walidacja stanowiska
                    Position position;
                    try {
                        position = Position.valueOf(fields[4].trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        errors.add("Invalid position at line " + lineNumber + ": " + fields[4]);
                        continue;
                    }

                    // Walidacja wynagrodzenia
                    if (salary <= 0) {
                        errors.add("Linia " + lineNumber + ": Wynagrodzenie musi być dodatnie - " + salary);
                        continue;
                    }

                    // Tworzenie obiektu Employee
                    Employee employee = new Employee(firstName + " " + lastName, email, company, position);

                    // Dodanie pracownika do EmployeeService
                    if (employeeService.addEmployee(employee)) {
                        importedCount++;
                    } else {
                        errors.add("Linia " + lineNumber + ": Pracownik z emailem " + email + " już istnieje");
                    }

                } catch (Exception e) {
                    errors.add("Linia " + lineNumber + ": Błąd parsowania - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            errors.add("Błąd odczytu pliku: " + e.getMessage());
        }

        return new ImportSummary(importedCount, errors);
    }
}