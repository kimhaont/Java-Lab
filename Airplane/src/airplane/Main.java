/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package airplane;

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
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter flight number: ");
        String flightNumber = sc.nextLine();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine();

        System.out.print("Enter departure time (HH:mm): ");
        String departureTime = sc.nextLine();

        System.out.print(
                "Enter status (Scheduled/Cancelled): ");
        String status = sc.nextLine();

        Airplane airplane = new Airplane(
                flightNumber,
                destination,
                departureTime,
                status
        );

        System.out.println("\n===== FLIGHT INFORMATION =====");

        System.out.println("Flight Number: "
                + airplane.getFlightNumber());

        System.out.println("Destination: "
                + airplane.getDestination());

        System.out.println("Departure Time: "
                + airplane.getDepartureTime());

        System.out.println("Status: "
                + airplane.checkStatus());

        if (!status.equals("Cancelled")) {

            System.out.print(
                    "Enter actual departure time (HH:mm): ");

            String actualTime = sc.nextLine();

            System.out.println("Delay: "
                    + airplane.checkDelay(actualTime));
        }

        System.out.println("Final Status: "
                + airplane.checkStatus());

        sc.close();
    }
}  
    

