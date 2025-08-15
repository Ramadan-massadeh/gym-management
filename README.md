# Gym Management System - Project Documentation

**Date:** August 15, 2025

## 1. User Documentation

### Overview

The Gym Management System is a console-based application designed to help gym facilities manage users, memberships, classes, and gym merchandise. It supports three types of users: Admins, Trainers, and Members. Each user type has access to specific features based on their role.

Admins can manage users, track revenue, and manage merchandise. Trainers can manage workout classes and view merch. Members can register, purchase memberships, and browse workout classes and merchandise.

### Classes and Interactions

- **User**: Base model for all users. Stores userId, name, email, phone number, address, password (hashed), and role.
- **Admin, Trainer, Member**: Roles based on the 'role' field in the User class. They do not extend a class but are logically separated via role.
- **Membership**: Represents a gym membership. Tied to a member (userId).
- **WorkoutClass**: Represents a class that trainers create. Tied to a trainer (userId).
- **Merch**: Represents a gym item (e.g., drinks, gear) for purchase.
- **DAO Classes (UserDao, MembershipDao, etc.)**: Handle database operations for each model.
- **Service Classes (UserService, AdminService, etc.)**: Contain business logic and interact with DAO classes.
- **App.java**: Main application that runs the interface and drives program flow.

### Class Diagram

```
User --> [Membership, WorkoutClass, Merch (via role)]
AdminService --> [UserDao, MembershipDao, GymMerchDao]
TrainerService --> [WorkoutClassDao, MembershipDao]
MemberService --> [WorkoutClassDao, MembershipDao, GymMerchDao]
App --> [UserService, AdminService, TrainerService, MemberService]
```

## How to Start and Use the System

1. **Run the Program**: Start the program by running `App.java` in your IDE or through command line.
2. **Main Menu**: Choose to login or register.
3. **Registration**: Enter your name, email, phone, address, password, and role.
4. **Login**: Use your registered email and password.
5. **Role-Based Menus**:
   - Admins can manage users, view reports, and manage merchandise.
   - Trainers can manage workout classes.
   - Members can buy memberships and browse classes/merchandise.
6. **Exit**: Select the Exit option from the main menu to close the program.

### Role-Based Menu System

The Gym Management System provides different features to users based on their role. Upon login, the user is shown a custom menu tailored to their role: **Admin**, **Trainer**, or **Member**.

#### Admin Menu

Admins have full system access and can manage users, memberships, merchandise, and track revenue.

| Option | Description                                                                    |
| ------ | ------------------------------------------------------------------------------ |
| `1`    | **View all users** - Displays all registered users with contact info           |
| `2`    | **Delete user by ID** - Removes a user from the system by user ID              |
| `3`    | **View all memberships** - Lists all gym memberships that have been purchased  |
| `4`    | **Total membership revenue** - Shows total revenue generated from memberships  |
| `5`    | **List merch items** - Displays all gym merchandise currently in inventory     |
| `6`    | **Add merch item** - Add a new item to the merch inventory                     |
| `7`    | **Update merch item** - Edit existing merch information (name, price, etc.)    |
| `8`    | **Delete merch item** - Remove a merch item by ID                              |
| `9`    | **Total stock value** - Calculates total value of inventory (price × quantity) |
| `0`    | **Back** - Return to the main menu                                             |

#### Trainer Menu

Trainers can manage workout classes, purchase memberships, and view merch.

| Option | Description                                                                    |
| ------ | ------------------------------------------------------------------------------ |
| `1`    | **Create class** - Adds a new workout class (title, description, schedule)     |
| `2`    | **Update class** - Edit a workout class’s information                          |
| `3`    | **Delete class** - Remove a class by its ID                                    |
| `4`    | **List my classes** - View only the trainer's own assigned classes             |
| `5`    | **Buy membership (for yourself)** - Purchase a gym membership for personal use |
| `6`    | **View merch items** - View all gym merchandise available                      |
| `0`    | **Back** - Return to the main menu                                             |

#### Member Menu

Members can purchase memberships, track their spending, browse classes, and view merch.

| Option | Description                                                                           |
| ------ | ------------------------------------------------------------------------------------- |
| `1`    | **Browse workout classes** - See all available workout classes                        |
| `2`    | **Buy membership** - Purchase a gym membership                                        |
| `3`    | **View my memberships** - See a list of memberships purchased by the member           |
| `4`    | **View my total spent** - Calculate and display the total amount spent on memberships |
| `5`    | **View merch items** - Browse gym merchandise available for purchase                  |
| `0`    | **Back** - Return to the main menu                                                    |

## 2. Development Documentation

### Javadoc Overview

- The classes and main app.java have detailed comments explaining method purpose and parameters.
- Use your IDE (IntelliJ IDEA recommended) to build and run the java application.
- Make sure to install and add Maven to your project.

### Project Structure

```
/gym-management
│
├── /src
│   ├── app/                 → Main entry point (App.java)
│   ├── dao/                 → Data Access Object classes (UserDao, MembershipDao, etc.)
│   ├── model/               → (User, Membership, etc.)
│   ├── service/             → App logic and services (UserService, AdminService, etc.)
│   ├── util/                → Utility classes (DBConnection, Initializer, LogSetup)
├── pom.xml                  → Maven project file
│
├── /logs                   → Output directory for log files (e.g., gym-app.log)
│
└── README.md               → Project info and instructions

```

### Build Process & Dependencies

- **Language**: Java 15+
- **Database**: PostgreSQL
- **Logger**: Java Util Logging (configured in `LogSetup.java`). The program automatically generates this file if it doesn't exist at the path already.
- **BCrypt**: For password hashing (ensure the JAR is included or use Maven)

### Database Setup

1. Install PostgreSQL and create a database named `gymdb`.
2. Create a user `postgres` with password `:^(((` (or update connection string in util/DBConnection class according to your own local database credentials).
3. Tables are auto-created via the `Initializer.setup()` method when `App.java` runs, so there is no need to create the tables yourself.

### Clone and Run from GitHub

Run the following commands in your IDE's terminal.

```bash
git clone https://github.com/Ramadan-massadeh/gym-management.git
cd gym-management
# Open in your IDE and run App.java
```

After setting up Maven, make sure to refresh Maven from the Maven tab in IntelliJ.

## Project Demo Video

https://drive.google.com/file/d/14CPkrAqf2nVxJLA09C-ZtDxLMc-nQqQy/view?usp=share_link
