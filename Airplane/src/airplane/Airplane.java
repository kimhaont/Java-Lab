/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airplane;

/**
 *
 * @author ADMIN
 */
public class Airplane {
    private String flightNumber;
    private String destination;
    private String departureTime;
    private String status;

    public Airplane(String flightNumber, String destination, String departureTime, String status) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.departureTime = departureTime;
        this.status = status;
    }

   
    
    
    public String checkStatus() {
        return getStatus();
    }
    
    public String checkDelay(String actualTime){
       if (getStatus().equals("Cancelled")) {
            return "Flight is cancelled";
        }
       
       String[] scheduled = getDepartureTime().split(":");
       String[] actual = actualTime.split(":");
       
       int scheduledMinutes =
                Integer.parseInt(scheduled[0]) * 60
                + Integer.parseInt(scheduled[1]);
       
       int actualMinutes =
                Integer.parseInt(actual[0]) * 60
                + Integer.parseInt(actual[1]);
       
       int delay = actualMinutes- scheduledMinutes;
       
       if (delay > 0) {
            return "Flight is delayed " + delay + " minutes.";
        } else if (delay == 0) {
            return "Flight is on time.";
        } else {
            return "Flight departed early " + (-delay) + " minutes.";
        }
    }

    /**
     * @return the flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * @param flightNumber the flightNumber to set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
}

