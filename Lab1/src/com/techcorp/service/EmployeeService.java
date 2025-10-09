package com.techcorp.service;

import com.techcorp.model.Employee;
import com.techcorp.model.Position;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serwis zawierający logikę biznesową zarządzania pracownikami.
 * Wykorzystuje Stream API do operacji analitycznych.
 */
public class EmployeeService {
    // HashSet zapewnia unikalność emaili (wykorzystuje equals/hashCode)
    private final Set<Employee> employees;

    public EmployeeService() {
        this.employees = new HashSet<>();
    }

    /**
     * Dodaje pracownika z walidacją unikalności emaila
     * @param employee pracownik do dodania
     * @return true jeśli dodano, false jeśli email już istnieje
     */
    public boolean addEmployee(Employee employee) {
        // HashSet.add() zwraca false jeśli element już istnieje
        // Sprawdza to używając equals() - w naszym przypadku po emailu
        return employees.add(employee);
    }

    /**
     * Zwraca kopię listy wszystkich pracowników
     * @return lista pracowników (nie można modyfikować oryginalnej kolekcji)
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    /**
     * Wyszukuje pracowników z konkretnej firmy
     * Wykorzystuje Stream API do filtrowania
     * 
     * @param companyName nazwa firmy
     * @return lista pracowników z danej firmy
     */
    public List<Employee> findEmployeesByCompany(String companyName) {
        return employees.stream() // Tworzy strumień z kolekcji
                .filter(emp -> emp.getCompanyName().equals(companyName)) // Filtruje po nazwie firmy
                .collect(Collectors.toList()); // Zbiera wyniki do listy
    }

    /**
     * Sortuje pracowników alfabetycznie według nazwiska
     * Wykorzystuje Comparator do definiowania porządku sortowania
     * 
     * @return posortowana lista pracowników
     */
    public List<Employee> getEmployeesSortedByLastName() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getLastName)) // Sortuje po nazwisku
                .collect(Collectors.toList());
    }

    /**
     * Grupuje pracowników według stanowiska
     * Zwraca Map<Position, List<Employee>>
     * 
     * @return mapa: stanowisko -> lista pracowników na tym stanowisku
     */
    public Map<Position, List<Employee>> groupEmployeesByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition)); // Grupuje po stanowisku
    }

    /**
     * Zlicza liczbę pracowników na każdym stanowisku
     * Zwraca Map<Position, Long>
     * 
     * @return mapa: stanowisko -> liczba pracowników
     */
    public Map<Position, Long> countEmployeesByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(
                    Employee::getPosition,    // Grupuje po stanowisku
                    Collectors.counting()     // Zlicza elementy w każdej grupie
                ));
    }

    /**
     * Oblicza średnie wynagrodzenie wszystkich pracowników
     * 
     * @return średnia pensja lub 0.0 jeśli brak pracowników
     */
    public double calculateAverageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)  // Mapuje do strumienia liczb double
                .average()                          // Oblicza średnią
                .orElse(0.0);                      // Wartość domyślna jeśli pusty strumień
    }

    /**
     * Znajduje pracownika z najwyższym wynagrodzeniem
     * Wykorzystuje Optional do obsługi przypadku pustej kolekcji
     * 
     * @return Optional z pracownikiem lub pusty Optional
     */
    public Optional<Employee> findHighestPaidEmployee() {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary)); // Znajduje max po pensji
    }

    /**
     * Zwraca liczbę wszystkich pracowników
     */
    public int getEmployeeCount() {
        return employees.size();
    }
}