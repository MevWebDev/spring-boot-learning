package com.techcorp;

import com.techcorp.model.Employee;
import com.techcorp.model.Position;
import com.techcorp.service.EmployeeService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Główna klasa demonstrująca działanie systemu zarządzania pracownikami
 */
public class Main {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();

        System.out.println("=== SYSTEM ZARZĄDZANIA PRACOWNIKAMI TECHCORP ===\n");

        // ===== 1. DODAWANIE PRACOWNIKÓW =====
        System.out.println("1. DODAWANIE PRACOWNIKÓW");
        System.out.println("------------------------");

        Employee emp1 = new Employee("Jan Kowalski", "jan.kowalski@techcorp.pl", 
                                     "TechCorp", Position.PREZES);
        Employee emp2 = new Employee("Anna Nowak", "anna.nowak@techcorp.pl", 
                                     "TechCorp", Position.MANAGER);
        Employee emp3 = new Employee("Piotr Wiśniewski", "piotr.wisniewski@techcorp.pl", 
                                     "TechCorp", Position.PROGRAMISTA);
        Employee emp4 = new Employee("Maria Kowalczyk", "maria.kowalczyk@softdev.pl", 
                                     "SoftDev", Position.WICEPREZES);
        Employee emp5 = new Employee("Tomasz Zając", "tomasz.zajac@softdev.pl", 
                                     "SoftDev", Position.PROGRAMISTA);
        Employee emp6 = new Employee("Katarzyna Adamska", "katarzyna.adamska@techcorp.pl", 
                                     "TechCorp", Position.STAZYSTA);

        // Dodawanie z walidacją
        addEmployeeWithValidation(service, emp1);
        addEmployeeWithValidation(service, emp2);
        addEmployeeWithValidation(service, emp3);
        addEmployeeWithValidation(service, emp4);
        addEmployeeWithValidation(service, emp5);
        addEmployeeWithValidation(service, emp6);

        // Próba dodania duplikatu (ten sam email)
        Employee duplicate = new Employee("Jan Kowalski Jr", "jan.kowalski@techcorp.pl", 
                                         "TechCorp", Position.MANAGER);
        addEmployeeWithValidation(service, duplicate);

        System.out.println("\nŁączna liczba pracowników: " + service.getEmployeeCount());

        // ===== 2. WYŚWIETLANIE WSZYSTKICH PRACOWNIKÓW =====
        System.out.println("\n\n2. WSZYSCY PRACOWNICY");
        System.out.println("---------------------");
        service.getAllEmployees().forEach(System.out::println);

        // ===== 3. WYSZUKIWANIE PO FIRMIE =====
        System.out.println("\n\n3. PRACOWNICY FIRMY TECHCORP");
        System.out.println("-----------------------------");
        List<Employee> techCorpEmployees = service.findEmployeesByCompany("TechCorp");
        techCorpEmployees.forEach(emp -> 
            System.out.println(emp.getFullName() + " - " + emp.getPosition())
        );

        // ===== 4. SORTOWANIE ALFABETYCZNE =====
        System.out.println("\n\n4. PRACOWNICY POSORTOWANI ALFABETYCZNIE");
        System.out.println("----------------------------------------");
        List<Employee> sortedEmployees = service.getEmployeesSortedByLastName();
        sortedEmployees.forEach(emp -> 
            System.out.println(emp.getFullName())
        );

        // ===== 5. GRUPOWANIE PO STANOWISKU =====
        System.out.println("\n\n5. PRACOWNICY POGRUPOWANI PO STANOWISKU");
        System.out.println("---------------------------------------");
        Map<Position, List<Employee>> byPosition = service.groupEmployeesByPosition();
        byPosition.forEach((position, employees) -> {
            System.out.println("\n" + position + " (" + employees.size() + " osób):");
            employees.forEach(emp -> 
                System.out.println("  - " + emp.getFullName() + " (pensja: " + emp.getSalary() + ")")
            );
        });

        // ===== 6. ZLICZANIE PO STANOWISKU =====
        System.out.println("\n\n6. LICZBA PRACOWNIKÓW NA STANOWISKACH");
        System.out.println("--------------------------------------");
        Map<Position, Long> countByPosition = service.countEmployeesByPosition();
        countByPosition.forEach((position, count) -> 
            System.out.println(position + ": " + count + " pracowników")
        );

        // ===== 7. ŚREDNIE WYNAGRODZENIE =====
        System.out.println("\n\n7. STATYSTYKI FINANSOWE");
        System.out.println("-----------------------");
        double avgSalary = service.calculateAverageSalary();
        System.out.printf("Średnie wynagrodzenie: %.2f PLN\n", avgSalary);

        // ===== 8. NAJWYŻEJ ZARABIAJĄCY =====
        Optional<Employee> highestPaid = service.findHighestPaidEmployee();
        highestPaid.ifPresentOrElse(
            emp -> System.out.printf("Najwyżej zarabiający: %s (%.2f PLN)\n", 
                                    emp.getFullName(), emp.getSalary()),
            () -> System.out.println("Brak pracowników w systemie")
        );

        // ===== 9. RANKING WYNAGRODZEŃ =====
        System.out.println("\n\n8. RANKING WYNAGRODZEŃ (OD NAJWYŻSZYCH)");
        System.out.println("---------------------------------------");
        service.getAllEmployees().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .forEach(emp -> 
                    System.out.printf("%s (%s): %.2f PLN\n",
                        emp.getFullName(), 
                        emp.getPosition(),
                        emp.getSalary()
                    )
                );
    }

    /**
     * Pomocnicza metoda dodająca pracownika z komunikatami
     */
    private static void addEmployeeWithValidation(EmployeeService service, Employee employee) {
        boolean added = service.addEmployee(employee);
        if (added) {
            System.out.println("✓ Dodano: " + employee.getFullName() + " (" + employee.getEmail() + ")");
        } else {
            System.out.println("✗ Nie można dodać: " + employee.getEmail() + " - email już istnieje!");
        }
    }
}