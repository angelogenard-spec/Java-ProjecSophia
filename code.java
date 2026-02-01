import java.util.*;


/*
 * Personal Budget Tracker and Expense Analyzer
 * Author: Taylor Chen
 * Date: October 26, 2025
 * 
 * This program helps users track expenses, categorize spending,
 * analyze patterns, and maintain a balanced budget.
 * It provides insights into spending habits and offers recommendations.
 */

// Expense class to store individual expense details

public class BudgetTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize program data
        double monthlyIncome = 0.0;
        ArrayList<Expense> expenses = new ArrayList<>();
        
        // Available expense categories
        String[] categories = {
            "Food", "Transportation", "Housing", "Utilities",
            "Entertainment", "Personal", "Debt", "Other"
        };
        
        // Display welcome message
        System.out.println("========================================");
        System.out.println("    PERSONAL BUDGET TRACKER");
        System.out.println("========================================");
        
        boolean running = true;
        
        // Main program loop - continues until user chooses to exit
        while (running) {
            // Display main menu options
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Enter Monthly Income");
            System.out.println("2. Add New Expense");
            System.out.println("3. View All Expenses");
            System.out.println("4. View Budget Summary");
            System.out.println("5. Exit Program");
            System.out.print("\nEnter your choice (1-5): ");
            
            int choice;
            // Validate menu choice input
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear newline character
            } else {
                System.out.println("Invalid input. Please enter a number 1-5.");
                scanner.nextLine(); // Clear invalid input
                continue;
            }
            
            // Process user's menu choice
            switch (choice) {
                case 1: // Enter monthly income
                    System.out.print("\nEnter your monthly income: $");
                    if (scanner.hasNextDouble()) {
                        monthlyIncome = scanner.nextDouble();
                        scanner.nextLine();
                        if (monthlyIncome >= 0) {
                            System.out.printf("Income set to: $%.2f%n", monthlyIncome);
                        } else {
                            System.out.println("Income cannot be negative. Setting to $0.00.");
                            monthlyIncome = 0.0;
                        }
                    } else {
                        System.out.println("Invalid input. Income not changed.");
                        scanner.nextLine();
                    }
                    break;
                    
                case 2: // Add new expense
                    System.out.println("\n--- ADD NEW EXPENSE ---");
                    
                    // Get expense date
                    System.out.print("Enter date (MM/DD format): ");
                    String date = scanner.nextLine();
                    
                    // Get expense description
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    
                    // Get expense amount with validation
                    double amount = 0.0;
                    boolean validAmount = false;
                    while (!validAmount) {
                        System.out.print("Enter amount: $");
                        if (scanner.hasNextDouble()) {
                            amount = scanner.nextDouble();
                            scanner.nextLine();
                            if (amount >= 0) {
                                validAmount = true;
                            } else {
                                System.out.println("Amount cannot be negative. Please try again.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.nextLine();
                        }
                    }
                    
                    // Get expense category with validation
                    System.out.println("\nSelect category:");
                    for (int i = 0; i < categories.length; i++) {
                        System.out.println((i + 1) + ". " + categories[i]);
                    }
                    
                    int categoryChoice = 0;
                    boolean validCategory = false;
                    while (!validCategory) {
                        System.out.print("Enter category number (1-" + categories.length + "): ");
                        if (scanner.hasNextInt()) {
                            categoryChoice = scanner.nextInt();
                            scanner.nextLine();
                            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                                validCategory = true;
                            } else {
                                System.out.println("Invalid choice. Please enter a number between 1 and " + categories.length);
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.nextLine();
                        }
                    }
                    
                    // Create and add new expense
                    String category = categories[categoryChoice - 1];
                    Expense newExpense = new Expense(date, description, amount, category);
                    expenses.add(newExpense);
                    
                    System.out.println("✓ Expense added successfully!");
                    break;
                    
                case 3: // View all expenses
                    System.out.println("\n=== ALL EXPENSES ===");
                    if (expenses.isEmpty()) {
                        System.out.println("No expenses recorded yet.");
                    } else {
                        System.out.println("Date     | Description          | Amount    | Category");
                        System.out.println("---------|----------------------|-----------|---------------");
                        
                        double total = 0.0;
                        // Display each expense and calculate running total
                        for (Expense expense : expenses) {
                            System.out.println(expense);
                            total += expense.amount;
                        }
                        
                        System.out.println("---------|----------------------|-----------|---------------");
                        System.out.printf("Total Expenses: $%.2f%n", total);
                    }
                    break;
                    
                case 4: // View budget summary
                    // Check if income has been entered
                    if (monthlyIncome == 0) {
                        System.out.println("\nPlease enter your monthly income first (Option 1).");
                        break;
                    }
                    
                    // Check if there are any expenses
                    if (expenses.isEmpty()) {
                        System.out.println("\nNo expenses recorded yet.");
                        System.out.printf("Monthly Income: $%.2f%n", monthlyIncome);
                        System.out.printf("Total Expenses: $0.00%n");
                        System.out.printf("Balance: $%.2f%n", monthlyIncome);
                        break;
                    }
                    
                    // Calculate total expenses
                    double totalExpenses = 0.0;
                    for (Expense expense : expenses) {
                        totalExpenses += expense.amount;
                    }
                    
                    // Calculate balance (income minus expenses)
                    double balance = monthlyIncome - totalExpenses;
                    
                    // Calculate expenses by category
                    HashMap<String, Double> categoryTotals = new HashMap<>();
                    for (Expense expense : expenses) {
                        String cat = expense.category;
                        double currentTotal = categoryTotals.getOrDefault(cat, 0.0);
                        categoryTotals.put(cat, currentTotal + expense.amount);
                    }
                    
                    // Find highest spending category
                    String highestCategory = "";
                    double highestAmount = 0.0;
                    for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                        if (entry.getValue() > highestAmount) {
                            highestAmount = entry.getValue();
                            highestCategory = entry.getKey();
                        }
                    }
                    
                    // Display summary header
                    System.out.println("\n========================================");
                    System.out.println("        BUDGET SUMMARY");
                    System.out.println("========================================\n");
                    
                    // Display income and expense totals
                    System.out.printf("Monthly Income:     $%10.2f%n", monthlyIncome);
                    System.out.printf("Total Expenses:     $%10.2f%n", totalExpenses);
                    System.out.printf("Balance:            $%10.2f%n", balance);
                    System.out.println();
                    
                    // Display warning if expenses exceed income
                    if (balance < 0) {
                        System.out.println("⚠️  WARNING: Expenses exceed income by $" + 
                                          String.format("%.2f", Math.abs(balance)));
                        System.out.println();
                    }
                    
                    // Display expenses by category
                    System.out.println("EXPENSES BY CATEGORY:");
                    System.out.println("----------------------");
                    for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                        double percent = (entry.getValue() / totalExpenses) * 100;
                        System.out.printf("%-15s: $%8.2f (%5.1f%%)%n", 
                                        entry.getKey(), entry.getValue(), percent);
                    }
                    System.out.println();
                    
                    // Display highest spending category
                    double highestPercent = (highestAmount / totalExpenses) * 100;
                    System.out.printf("Highest Spending: %s ($%.2f, %.1f%% of expenses)%n", 
                                    highestCategory, highestAmount, highestPercent);
                    
                    // Display recommendations based on spending patterns
                    System.out.println("\nRECOMMENDATIONS:");
                    System.out.println("----------------");
                    if (balance < 0) {
                        System.out.println("• You are spending more than you earn!");
                        System.out.println("• Focus on reducing expenses in '" + highestCategory + "' category");
                        System.out.printf("• Need to reduce spending by $%.2f or increase income%n", Math.abs(balance));
                    } else if ((totalExpenses / monthlyIncome) > 0.8) {
                        System.out.printf("• You're spending %.1f%% of your income%n", 
                                        (totalExpenses / monthlyIncome) * 100);
                        System.out.println("• Consider increasing your savings rate");
                    } else {
                        double savingsRate = (balance / monthlyIncome) * 100;
                        System.out.printf("• Good job! You're saving %.1f%% of your income%n", savingsRate);
                        if (highestPercent > 50) {
                            System.out.println("• Consider diversifying your spending across more categories");
                        }
                    }
                    
                    System.out.println("\n========================================");
                    break;
                    
                case 5: // Exit program
                    System.out.println("\nThank you for using Budget Tracker!");
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                    
                default: // Invalid menu choice
                    System.out.println("Invalid choice. Please select 1-5.");
                    break;
            }
        }
        
        scanner.close(); // Close scanner to prevent resource leak
    }
}

class Expense {
    String date;        // Date of expense (MM/DD format)
    String description; // What was purchased
    double amount;      // Cost of the expense
    String category;    // Spending category
    
    // Constructor to initialize expense object
    public Expense(String date, String description, double amount, String category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    
    // Method to display expense details
    @Override
    public String toString() {
        return String.format("%-8s | %-20s | $%-8.2f | %-15s", 
                            date, description, amount, category);
    }
}
