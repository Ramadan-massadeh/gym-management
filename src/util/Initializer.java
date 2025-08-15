package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Initializer {

    private static final Logger logger = Logger.getLogger(Initializer.class.getName());

    // Run this at application startup to ensure all required tables exist
    public static void setup() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Users Table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "user_id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "email VARCHAR(100) UNIQUE NOT NULL, " +
                            "phone_number VARCHAR(20), " +
                            "address TEXT, " +
                            "password_hash VARCHAR(255) NOT NULL, " +
                            "role VARCHAR(20) NOT NULL" +
                            ");"
            );

            // Memberships Table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS memberships (" +
                            "membership_id SERIAL PRIMARY KEY, " +
                            "type VARCHAR(50), " +
                            "description TEXT, " +
                            "price NUMERIC(10, 2), " +
                            "duration_months INTEGER, " +
                            "start_date VARCHAR(50), " +
                            "end_date VARCHAR(50), " +
                            "member_id INTEGER REFERENCES users(user_id)" +
                            ");"
            );

            // Workout Classes Table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS workout_classes (" +
                            "class_id SERIAL PRIMARY KEY, " +
                            "title VARCHAR(100), " +
                            "description TEXT, " +
                            "schedule VARCHAR(100), " +
                            "trainer_id INTEGER REFERENCES users(user_id)" +
                            ");"
            );

            // Gym Merch Table
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS gym_merch (" +
                            "item_id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(100), " +
                            "type VARCHAR(50), " +
                            "price NUMERIC(10, 2), " +
                            "quantity_in_stock INTEGER" +
                            ");"
            );

            System.out.println("All tables verified or created.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating tables", e);
        }
    }
}
