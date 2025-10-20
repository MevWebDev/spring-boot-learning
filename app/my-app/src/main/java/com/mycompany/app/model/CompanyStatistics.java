package com.mycompany.app.model;

/**
 * Klasa przechowująca statystyki firmy.
 */
public class CompanyStatistics {
    private final int employeeCount;
    private final double averageSalary;
    private final String highestPaidEmployee;

    /**
     * Konstruktor klasy CompanyStatistics.
     *
     * @param employeeCount liczba pracowników w firmie
     * @param averageSalary średnie wynagrodzenie
     * @param highestPaidEmployee pełne imię i nazwisko najlepiej zarabiającego pracownika
     */
    public CompanyStatistics(int employeeCount, double averageSalary, String highestPaidEmployee) {
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.highestPaidEmployee = highestPaidEmployee;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public double getAverageSalary() {
        return averageSalary;
    }

    public String getHighestPaidEmployee() {
        return highestPaidEmployee;
    }

    @Override
    public String toString() {
        return "CompanyStatistics{" +
                "employeeCount=" + employeeCount +
                ", averageSalary=" + averageSalary +
                ", highestPaidEmployee='" + highestPaidEmployee + '\'' +
                '}';
    }
}
