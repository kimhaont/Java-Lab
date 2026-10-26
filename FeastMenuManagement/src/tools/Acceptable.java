/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Admin
 */
public interface Acceptable {
     // Customer code: A unique 5-character string. The first character is “C”, “G”or “K”, followed by 4 digits.
    String CUSTOMER_COD_VALID = "^[CcGgKk]\\d{4}$";
    
    // Name: A non-empty string between 2 and 25 characters long.
    String NAME_VALID = "^[\\p{L} ]{2,25}$";
    
    // Double number validation
    String DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    
    // Integer validation
    String INTEGER_VALID = "\\d+";
    
    // Phone Number: 10-digit number (Vietnamese mobile network operators)
    String PHONE_VALID = "^(03|05|07|08|09)\\d{8}$";
    
    // Email: Standard format
    String EMAIL_VALID = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
     
    static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }
    
    
}
