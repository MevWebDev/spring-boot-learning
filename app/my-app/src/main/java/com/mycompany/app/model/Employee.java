package com.mycompany.app.model;

import java.util.Objects;

/**
 * Klasa reprezentująca pracownika w systemie.
 * Email jest unikalnym identyfikatorem - używany w equals() i hashCode().
 */
public class Employee {
    private String fullName;
    private String email;
    private String companyName;
    private Position position;
    private double salary;

    /**
     * Konstruktor tworzący nowego pracownika
     * @param fullName imię i nazwisko
     * @param email unikalny email (identyfikator)
     * @param companyName nazwa firmy
     * @param position stanowisko (enum Position)
     */
    public Employee(String fullName, String email, String companyName, 
                   Position position) {
        this.fullName = fullName;
        this.email = email;
        this.companyName = companyName;
        this.position = position;
        this.salary = position.getBaseSalary(); // Pensja z bazowej stawki stanowiska
    }

    // Gettery - enkapsulacja: prywatne pola, publiczny dostęp
    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Position getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    // Settery - umożliwiają modyfikację danych
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPosition(Position position) {
        this.position = position;
        this.salary = position.getBaseSalary(); // Aktualizuj pensję przy zmianie stanowiska
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * equals() - porównuje pracowników na podstawie emaila
     * Dwa pracowników są równi jeśli mają ten sam email
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Ten sam obiekt w pamięci
        if (o == null || getClass() != o.getClass()) return false; // Różne typy
        Employee employee = (Employee) o;
        return Objects.equals(email, employee.email); // Porównanie emaili
    }

    /**
     * hashCode() - musi być spójny z equals()
     * Obiekty równe (equals = true) muszą mieć ten sam hashCode
     * Używane w HashSet, HashMap do szybkiego wyszukiwania
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * toString() - czytelna reprezentacja tekstowa obiektu
     * Przydatne przy debugowaniu i wyświetlaniu
     */
    @Override
    public String toString() {
        return "Employee{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position=" + position +
                ", salary=" + salary +
                '}';
    }

    /**
     * Pomocnicza metoda do wyciągania nazwiska z pełnego imienia
     * Zakłada format "Imię Nazwisko"
     */
    public String getLastName() {
        String[] parts = fullName.split(" ");
        return parts.length > 1 ? parts[parts.length - 1] : fullName;
    }
}