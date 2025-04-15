package main;

import dao.InsuranceServiceImpl;
import entity.Policy;
import exception.PolicyNotFoundException;
import exception.InvalidPolicyException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainModule {

    private static InsuranceServiceImpl insuranceService = new InsuranceServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice =0;

        do {
            System.out.println("\n===== Insurance Management System =====");
            System.out.println("1. Create Policy");
            System.out.println("2. View Policy");
            System.out.println("3. View All Policies");
            System.out.println("4. Update Policy");
            System.out.println("5. Delete Policy");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  
                
                switch (choice) {
                    case 1:
                        createPolicy(scanner);
                        break;
                    case 2:
                        viewPolicy(scanner);
                        break;
                    case 3:
                        viewAllPolicies();
                        break;
                    case 4:
                        updatePolicy(scanner);
                        break;
                    case 5:
                        deletePolicy(scanner);
                        break;
                    case 6:
                        System.out.println("Exiting the system...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();  
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void createPolicy(Scanner scanner) {
        try {
            System.out.print("Enter Policy ID: ");
            int policyId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Enter Policy Name: ");
            String policyName = scanner.nextLine();

            System.out.print("Enter Coverage Amount: ");
            double coverageAmount = scanner.nextDouble();

            System.out.print("Enter Premium: ");
            double premium = scanner.nextDouble();

            Policy policy = new Policy(policyId, policyName, coverageAmount, premium);
            boolean isCreated = insuranceService.createPolicy(policy);

            if (isCreated) {
                System.out.println("Policy created successfully!");
            } else {
                System.out.println("Failed to create policy.");
            }
        } catch (InvalidPolicyException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine(); 
        }
    }

    private static void viewPolicy(Scanner scanner) {
        try {
            System.out.print("Enter Policy ID: ");
            int policyId = scanner.nextInt();

            Policy policy = insuranceService.getPolicy(policyId);
            System.out.println("Policy Details: " + policy);
        } catch (PolicyNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
            scanner.nextLine(); 
        }
    }

    private static void viewAllPolicies() {
        System.out.println("Listing All Policies:");
        insuranceService.getAllPolicies().forEach(System.out::println);
    }

    private static void updatePolicy(Scanner scanner) {
        try {
            System.out.print("Enter Policy ID: ");
            int policyId = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Enter New Policy Name: ");
            String policyName = scanner.nextLine();

            System.out.print("Enter New Coverage Amount: ");
            double coverageAmount = scanner.nextDouble();

            System.out.print("Enter New Premium: ");
            double premium = scanner.nextDouble();

            Policy policy = new Policy(policyId, policyName, coverageAmount, premium);
            boolean isUpdated = insuranceService.updatePolicy(policy);

            if (isUpdated) {
                System.out.println("Policy updated successfully!");
            } else {
                System.out.println("Failed to update policy.");
            }
        } catch (PolicyNotFoundException | InvalidPolicyException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please try again.");
            scanner.nextLine(); 
        }
    }

    private static void deletePolicy(Scanner scanner) {
        try {
            System.out.print("Enter Policy ID: ");
            int policyId = scanner.nextInt();

            boolean isDeleted = insuranceService.deletePolicy(policyId);

            if (isDeleted) {
                System.out.println("Policy deleted successfully!");
            } else {
                System.out.println("Failed to delete policy.");
            }
        } catch (PolicyNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input format. Please enter a valid number.");
            scanner.nextLine(); 
        }
    }
}
