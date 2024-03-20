package com.example.siki.Bcrypt;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptManager {
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
    public static String hashPassword(String plainTextPassword) {
        // Generate a salt for hashing
        String salt = BCrypt.gensalt();

        // Hash the password using the generated salt
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);

        return hashedPassword;
    }
}
