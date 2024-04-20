import java.util.Scanner;

class Account {
    protected String accountNumber;
    protected double balance;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }
}

class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, double overdraftLimit) {
        super(accountNumber);
        this.overdraftLimit = overdraftLimit;
    }

    public boolean withdraw(double amount) {
        if (getBalance() + overdraftLimit >= amount) {
            return super.withdraw(amount);
        }
        return false;
    }
}

class Employee {
    private static int totalEmployees = 0;

    private String name;
    private String role;

    public Employee(String name, String role) {
        this.name = name;
        this.role = role;
        totalEmployees++;
    }

    public static int getTotalEmployees() {
        return totalEmployees;
    }

    public void performDuties(Account account, int transactionChoice) {
        if ("manager".equals(role)) {
            manageAccounts(account, transactionChoice);
        } else if ("clerk".equals(role)) {
            handleTransactions(account, transactionChoice);
        }
    }

    private void manageAccounts(Account account, int transactionChoice) {
        Scanner scanner = new Scanner(System.in);
        if (transactionChoice == 0) {
            System.out.println("Performing deposit...");
            System.out.print("Enter the amount to deposit: ");
            double depositAmount = scanner.nextDouble();
            account.deposit(depositAmount);
        } else if (transactionChoice == 1) {
            System.out.println("Performing withdrawal...");
            System.out.print("Enter the amount to withdraw: ");
            double withdrawAmount = scanner.nextDouble();
            boolean success = account.withdraw(withdrawAmount);
            if (!success) {
                System.out.println("Insufficient funds!");
            }
        } else {
            System.out.println("Invalid choice! Please enter 0 for deposit or 1 for withdraw.");
        }
        
    }

    private void handleTransactions(Account account, int transactionChoice) {
        System.out.println("Handling transactions...");
        manageAccounts(account, transactionChoice); // Clerk can also perform deposit/withdrawal
    }
}

class Customer {
    private static int totalCustomers = 0;

    private String name;

    public Customer(String name) {
        this.name = name;
        totalCustomers++;
    }

    public static int getTotalCustomers() {
        return totalCustomers;
    }

    public Account createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 0 to create a Savings Account or 1 to create a Checking Account: ");
        int accountType = scanner.nextInt();
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();

        Account account = null;
        if (accountType == 0) {
            System.out.print("Enter interest rate for the Savings Account: ");
            double interestRate = scanner.nextDouble();
            account = new SavingsAccount(accountNumber, interestRate);
            System.out.println("Savings Account created successfully!");
        } else if (accountType == 1) {
            System.out.print("Enter overdraft limit for the Checking Account: ");
            double overdraftLimit = scanner.nextDouble();
            account = new CheckingAccount(accountNumber, overdraftLimit);
            System.out.println("Checking Account created successfully!");
        } else {
            System.out.println("Invalid account type! Please enter '0' for Savings Account or '1' for Checking Account.");
        }

       
        return account;
    }

    public void depositBalance(Account account) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount to deposit: ");
        double depositAmount = scanner.nextDouble();
        account.deposit(depositAmount);
        
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName);

        Account selectedAccount = customer.createAccount();
        if (selectedAccount != null) {
            customer.depositBalance(selectedAccount);
            System.out.println("Current Account Balance: " + selectedAccount.getBalance());
        }

        System.out.print("Enter employee name: ");
        String employeeName = scanner.nextLine();
        System.out.print("Enter employee role (manager/clerk): ");
        String employeeRole = scanner.nextLine();
        Employee employee = new Employee(employeeName, employeeRole);

        System.out.println("Total Customers: " + Customer.getTotalCustomers());
        System.out.println("Total Employees: " + Employee.getTotalEmployees());

        scanner.close(); 
    }
}
