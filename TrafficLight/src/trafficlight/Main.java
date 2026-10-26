/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trafficlight;

import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter color");
        String color = scanner.nextLine();
        
        System.out.println("Enter duration");
        int duration = scanner.nextInt();
        
        TrafficLight light = new TrafficLight(color, duration);
        System.out.println("Color: " + light.getColor());
        System.out.println("Duration: " + light.getDuration());
        System.out.println("is red?" + light.isRed());
        System.out.println("is green?" + light.isGreen());
        
        scanner.nextLine();
        System.out.print("\nEnter new color: ");
        String newColor = scanner.nextLine();
        light.changeColor(newColor);
        
        System.out.println("\n=== After Change Color ===");
        System.out.println("Color: " + light.getColor());
        System.out.println("Duration: " + light.getDuration());
        System.out.println("Is Red? " + light.isRed());
        System.out.println("Is Green? " + light.isGreen());

        
    }
    
    
}
