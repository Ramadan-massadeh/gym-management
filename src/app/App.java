package app;

import model.*;
import service.*;
import dao.UserDao;
import util.Initializer;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private final Scanner in = new Scanner(System.in);

    // Services
    private final UserService userSvc = new UserService(new UserDao());
    private final AdminService adminSvc = new AdminService();
    private final TrainerService trainerSvc = new TrainerService();
    private final MemberService memberSvc = new MemberService();
    private final MembershipService membershipSvc = new MembershipService();

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Initializer.setup(); // initialize tables if they don’t exist
        System.out.println("Gym Management System Started");
        new App().run();
    }

    private void loginFlow() {
        System.out.println("\n-- User Login --");
        System.out.print("Email: ");
        String email = in.nextLine().trim();
        System.out.print("Password: ");
        String password = in.nextLine().trim();

        try {
            User user = userSvc.login(email, password);
            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getName());
                switch (user.getRole()) {
                    case "admin" -> adminMenu(user);
                    case "trainer" -> trainerMenu(user);
                    case "member" -> memberMenu(user);
                    default -> System.out.println("Unknown role: " + user.getRole());
                }
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Login error", e);
        }
    }

    private void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Welcome to Gym Management ===");
            System.out.println("1) Login");
            System.out.println("2) Register User");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> loginFlow();
                    case "2" -> registerUserFlow();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error in main menu", e);
            }
        }
        System.out.println("Goodbye!");
    }

    // Registration
    private void registerUserFlow() {
        System.out.println("\n-- Register New User --");

        User u = new User();

        System.out.print("Name: ");
        String name = in.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        u.setName(name);

        System.out.print("Email: ");
        String email = in.nextLine().trim();
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            System.out.println("Invalid email format.");
            return;
        }
        u.setEmail(email);

        System.out.print("Phone: ");
        String phone = in.nextLine().trim();
        if (phone.isEmpty()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }
        u.setPhoneNumber(phone);

        System.out.print("Address: ");
        String address = in.nextLine().trim();
        if (address.isEmpty()) {
            System.out.println("Address cannot be empty.");
            return;
        }
        u.setAddress(address);

        System.out.print("Role (Admin/Trainer/Member): ");
        String role = in.nextLine().trim().toLowerCase();
        if (!role.equals("admin") && !role.equals("trainer") && !role.equals("member")) {
            System.out.println("Invalid role. Please enter Admin, Trainer, or Member.");
            return;
        }
        u.setRole(role);

        System.out.print("Password: ");
        String pw = in.nextLine().trim();
        if (pw.length() < 6) {
            System.out.println("Password must be at least 6 characters long.");
            return;
        }

        try {
            userSvc.registerWithPlainPassword(u, pw);
            System.out.println("Registration successful.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Registration failed", e);
        }
    }

    private void adminMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1) View all users");
            System.out.println("2) Delete user by ID");
            System.out.println("3) View all memberships");
            System.out.println("4) Total membership revenue");
            System.out.println("5) List merch items");
            System.out.println("6) Add merch item");
            System.out.println("7) Update merch item");
            System.out.println("8) Delete merch item");
            System.out.println("9) Total stock value");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();

            try {
                switch (c) {
                    case "1" -> adminSvc.viewAllUsers().forEach(System.out::println);
                    case "2" -> {
                        int id = askInt("User ID to delete: ");
                        adminSvc.deleteUser(id);
                    }
                    case "3" -> adminSvc.viewAllMemberships().forEach(System.out::println);
                    case "4" -> System.out.println("Total membership revenue: $" + adminSvc.totalMembershipRevenue());
                    case "5" -> adminSvc.listItems().forEach(System.out::println);
                    case "6" -> addMerchFlow();
                    case "7" -> updateMerchFlow();
                    case "8" -> {
                        int id = askInt("Item ID to delete: ");
                        adminSvc.deleteItem(id);
                    }
                    case "9" -> System.out.println("Total stock value: $" + adminSvc.totalStockValue());
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Admin action failed", e);
            }
        }
    }

    private void addMerchFlow() {
        try {
            Merch m = new Merch();
            System.out.print("Name: ");
            m.setName(in.nextLine().trim());
            System.out.print("Type: ");
            m.setType(in.nextLine().trim());
            m.setPrice(askDouble("Price: "));
            m.setQuantityInStock(askInt("Quantity: "));
            adminSvc.addItem(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to add merch item", e);
        }
    }

    private void updateMerchFlow() {
        try {
            Merch m = new Merch();
            m.setItemId(askInt("Item ID: "));
            System.out.print("New name: ");
            m.setName(in.nextLine().trim());
            System.out.print("New type: ");
            m.setType(in.nextLine().trim());
            m.setPrice(askDouble("New price: "));
            m.setQuantityInStock(askInt("New quantity: "));
            adminSvc.updateItem(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update merch item", e);
        }
    }

    //Trainer Menu
    private void trainerMenu(User user) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Trainer Menu ===");
            System.out.println("1) Create class");
            System.out.println("2) Update class");
            System.out.println("3) Delete class");
            System.out.println("4) List my classes");
            System.out.println("5) Buy membership (for yourself)");
            System.out.println("6) View merch items");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();

            try {
                switch (c) {
                    case "1" -> createClassFlow();
                    case "2" -> updateClassFlow();
                    case "3" -> {
                        int id = askInt("Class ID to delete: ");
                        trainerSvc.deleteClass(id);
                    }
                    case "4" -> trainerSvc.myClasses(user.getUserId()).forEach(System.out::println);
                    case "5" -> buyMembershipFlow(true);
                    case "6" -> trainerSvc.viewMerch().forEach(System.out::println);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Trainer action failed", e);
            }
        }
    }

    private void createClassFlow() {
        try {
            WorkoutClass c = new WorkoutClass();
            System.out.print("Title: ");
            c.setTitle(in.nextLine().trim());
            System.out.print("Description: ");
            c.setDescription(in.nextLine().trim());
            System.out.print("Schedule (string): ");
            c.setSchedule(in.nextLine().trim());
            c.setTrainerId(askInt("Trainer ID: "));
            trainerSvc.createClass(c);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create class", e);
        }
    }

    private void updateClassFlow() {
        try {
            WorkoutClass c = new WorkoutClass();
            c.setClassId(askInt("Class ID: "));
            System.out.print("New title: ");
            c.setTitle(in.nextLine().trim());
            System.out.print("New description: ");
            c.setDescription(in.nextLine().trim());
            System.out.print("New schedule: ");
            c.setSchedule(in.nextLine().trim());
            c.setTrainerId(askInt("Trainer ID: "));
            trainerSvc.updateClass(c);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update class", e);
        }
    }

    // Member Menu
    private void memberMenu(User user) {
        boolean back = false;
        while (!back) {
            System.out.println("\n=== Member Menu ===");
            System.out.println("1) Browse workout classes");
            System.out.println("2) Buy membership");
            System.out.println("3) View my memberships");
            System.out.println("4) View my total spent");
            System.out.println("5) View merch items");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();

            try {
                switch (c) {
                    case "1" -> memberSvc.browseClasses().forEach(System.out::println);
                    case "2" -> buyMembershipFlow(false);
                    case "3" -> memberSvc.myMemberships(user.getUserId()).forEach(System.out::println);
                    case "4" -> System.out.println("Total spent: $" + memberSvc.myTotalSpent(user.getUserId()));
                    case "5" -> memberSvc.viewMerch().forEach(System.out::println);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Member action failed", e);
            }
        }
    }

    private void buyMembershipFlow(boolean asTrainer) {
        try {
            System.out.println("\n-- Purchase Membership --");
            Membership m = new Membership();
            int ownerId = askInt(asTrainer ? "Your trainerId (userId): " : "Your memberId (userId): ");
            m.setMemberId(ownerId);
            System.out.print("Type: ");
            m.setType(in.nextLine().trim());
            System.out.print("Description: ");
            m.setDescription(in.nextLine().trim());
            m.setPrice(askDouble("Price: "));
            m.setDurationMonths(askInt("Duration (months): "));
            System.out.print("Start date (string): ");
            m.setStartDate(in.nextLine().trim());
            System.out.print("End date (string): ");
            m.setEndDate(in.nextLine().trim());

            membershipSvc.purchase(m);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to purchase membership", e);
        }
    }

    private int askInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double askDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
