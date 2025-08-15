package app;

import model.*;
import service.*;
import dao.UserDao;
import java.util.Scanner;

public class App {

    private final Scanner in = new Scanner(System.in);

    // Services
    private final UserService userSvc = new UserService(new UserDao());
    private final AdminService adminSvc = new AdminService();
    private final TrainerService trainerSvc = new TrainerService();
    private final MemberService memberSvc = new MemberService();
    private final MembershipService membershipSvc = new MembershipService();

    public static void main(String[] args) {
        System.out.println("Gym Management System Started");
        new App().run();
    }

    private void loginFlow() {
        System.out.println("\n-- User Login --");
        System.out.print("Email: ");
        String email = in.nextLine().trim();
        System.out.print("Password: ");
        String password = in.nextLine().trim();

        User user = userSvc.login(email, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getName());
            switch (user.getRole().toLowerCase()) {
                case "admin" -> adminMenu(user);
                case "trainer" -> trainerMenu(user);
                case "member" -> memberMenu(user);
                default -> System.out.println("Unknown role: " + user.getRole());
            }
        } else {
            System.out.println("Login failed. Invalid credentials.");
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
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    // Registration 
    private void registerUserFlow() {
        System.out.println("\n-- Register New User --");
        User u = new User();
        System.out.print("Name: "); u.setName(in.nextLine().trim());
        System.out.print("Email: "); u.setEmail(in.nextLine().trim());
        System.out.print("Phone: "); u.setPhoneNumber(in.nextLine().trim());
        System.out.print("Address: "); u.setAddress(in.nextLine().trim());
        System.out.print("Role (Admin/Trainer/Member): "); u.setRole(in.nextLine().trim());
        System.out.print("Password: "); String pw = in.nextLine().trim();

        userSvc.registerWithPlainPassword(u, pw);
    }

    //Admin Menu
    private void adminMenu(User user) {
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
                System.out.println("Admin action failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addMerchFlow() {
        Merch m = new Merch();
        System.out.print("Name: "); m.setName(in.nextLine().trim());
        System.out.print("Type: "); m.setType(in.nextLine().trim());
        m.setPrice(askDouble("Price: "));
        m.setQuantityInStock(askInt("Quantity: "));
        adminSvc.addItem(m);
    }

    private void updateMerchFlow() {
        Merch m = new Merch();
        m.setItemId(askInt("Item ID: "));
        System.out.print("New name: "); m.setName(in.nextLine().trim());
        System.out.print("New type: "); m.setType(in.nextLine().trim());
        m.setPrice(askDouble("New price: "));
        m.setQuantityInStock(askInt("New quantity: "));
        adminSvc.updateItem(m);
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
                    case "4" -> {
                        int trainerId = user.getUserId();
                        trainerSvc.myClasses(trainerId).forEach(System.out::println);
                    }
                    case "5" -> buyMembershipFlow(true);
                    case "6" -> trainerSvc.viewMerch().forEach(System.out::println);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Trainer action failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void createClassFlow() {
        WorkoutClass c = new WorkoutClass();
        System.out.print("Title: "); c.setTitle(in.nextLine().trim());
        System.out.print("Description: "); c.setDescription(in.nextLine().trim());
        System.out.print("Schedule (string): "); c.setSchedule(in.nextLine().trim());
        c.setTrainerId(askInt("Trainer ID: "));
        trainerSvc.createClass(c);
    }

    private void updateClassFlow() {
        WorkoutClass c = new WorkoutClass();
        c.setClassId(askInt("Class ID: "));
        System.out.print("New title: "); c.setTitle(in.nextLine().trim());
        System.out.print("New description: "); c.setDescription(in.nextLine().trim());
        System.out.print("New schedule: "); c.setSchedule(in.nextLine().trim());
        c.setTrainerId(askInt("Trainer ID: "));
        trainerSvc.updateClass(c);
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
                    case "3" -> {
                        int memberId = user.getUserId();
                        memberSvc.myMemberships(memberId).forEach(System.out::println);
                    }
                    case "4" -> {
                        int memberId = user.getUserId();
                        System.out.println("Total spent: $" + memberSvc.myTotalSpent(memberId));
                    }
                    case "5" -> memberSvc.viewMerch().forEach(System.out::println);
                    case "0" -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Member action failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void buyMembershipFlow(boolean asTrainer) {
        System.out.println("\n-- Purchase Membership --");
        Membership m = new Membership();
        int ownerId = askInt(asTrainer ? "Your trainerId (userId): " : "Your memberId (userId): ");
        m.setMemberId(ownerId);
        System.out.print("Type: "); m.setType(in.nextLine().trim());
        System.out.print("Description: "); m.setDescription(in.nextLine().trim());
        m.setPrice(askDouble("Price: "));
        m.setDurationMonths(askInt("Duration (months): "));
        System.out.print("Start date (string): "); m.setStartDate(in.nextLine().trim());
        System.out.print("End date (string): "); m.setEndDate(in.nextLine().trim());

        membershipSvc.purchase(m);
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
