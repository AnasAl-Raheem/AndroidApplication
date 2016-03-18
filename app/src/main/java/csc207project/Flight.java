package csc207project;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.text.DecimalFormat;

/** A Flight class which represents a flight from an origin to a destination.
 * Stores all necessary information for a single flight.
 */
public class Flight implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5016815223860293751L;
	private String flightNum;      // The flight number unique to all flights.
	private String departureDateTime; // Departure date and time.
	private String departureDate;    // Departure Date. 
	private double departureTime;   // Departure Time in decimal form.
	private String arrivalDateTime;  // Arrival date and time.
	private String arrivalDate;      // Arrival date.
	private double arrivalTime;       // Arrival time in decimal form.
	private String airline;         // The airline that operates this flight.
	private String origin;        // The place from where this flight departs.
	private String destination;    // The place where this flight lands.
	private double cost;          // The price of buying this flight.
	private double travelTime;  // Time this flight takes to reach destination
	private List<User> bookedSeats;  // List of users traveling on this flight
	private int numSeats;
	
	/** Array stores departure date and time separately by splitting
	 * departureDateTime variable.
	 */
	private String[] departureParts;
	
	/** Array stores arrival date and time separately by splitting
	 * departureDateTime variable.
	 */
	private String[] arrivalParts;
	private String newTravelTime; //Stores travel time as a String in HH:MM.
	
	/**
	 * Creates a new Flight with flight number as fligthNum,
	 * departure date and time as departureDateTime, arrival date and
	 * time as arrivalDateTime, origin as origin, destination as destination,
	 * cost as cost. Then given above it generates travel time of this flight.
	 * It also creates a list(initially empty) bookedSeats which will keep
	 * track of all Users who would book this flight in future.
	 * @param flightNum the flight number
	 * @param departureDateTime the departure date and time
	 * @param arrivalDateTime the arrival date and time
	 * @param airline the airline
	 * @param origin the origin from where this flight departs
	 * @param destination the destination where this flight arrives
	 * @param cost the price
	 */
	public Flight(String flightNum, String departureDateTime,
			String arrivalDateTime,String airline, String origin,
			String destination, String cost, String numSeats){
		this.flightNum = flightNum;
		this.departureDateTime = departureDateTime;
		this.departureParts = this.departureDateTime.split(" ");
		this.departureDate = this.departureParts[0];
		this.departureTime = generateDepartureTime(this.departureParts[1]);
		this.arrivalDateTime = arrivalDateTime;
		this.arrivalParts = this.arrivalDateTime.split(" ");
		this.arrivalDate = this.arrivalParts[0];
		this.arrivalTime = generateArrivalTime(this.arrivalParts[1]);
		this.airline = airline;
		this.origin = origin;
		this.destination = destination;
		this.cost = Double.parseDouble(cost);
		this.travelTime = generateTravelTime();
		this.bookedSeats = new ArrayList<User>();
		this.numSeats = Integer.parseInt(numSeats);
	}
	
	/**
	 * Gets the flight number of this flight.
	 * @return the flight number
	 */
	public String getFlightNum(){
		return this.flightNum;
	}
	
	/**
	 * Sets the flight number to flightNum.
	 * @param flightNum the new flight number
	 */
	public void setFlightNum(String flightNum){
		this.flightNum = flightNum;
	}
	
	/**
	 * Gets the departure date and time.
	 * @return the departure date and time
	 */
	public String getDepartureDateTime(){
		return this.departureDateTime;
	}
	
	/**
	 * Changes the departure date and time to a new
	 * departure date and time. Also changes travel time
	 * accordingly.
	 * @param departureDateTime the new departure date and time
	 */
	public void setDepartureDateTime(String departureDateTime){
		this.departureDateTime = departureDateTime;
		this.departureParts = this.departureDateTime.split(" ");
		this.departureDate = this.departureParts[0];
		this.departureTime = generateDepartureTime(this.departureParts[1]);
		this.travelTime = generateTravelTime();
	}
	
	/**
	 * Gets the departure date.
	 * @return the departure date
	 */
	public String getDepartureDate(){
		return this.departureDate;
	}
	
	
	/**
	 * Gets the departure time in decimals.
	 * @return the departure time
	 */
	public double getDepartureTime(){
		return this.departureTime;
	}
	
	/**
	 * Converts departure time given as a string,
	 * into a double equivalent for calculation purposes.
	 * @param timeInString the departure time as a string
	 * @return the departure time as a double equivalent of the
	 * timeInString
	 */
	private double generateDepartureTime(String timeInString){
		String[] time = timeInString.split(":");
		double hours = Double.parseDouble(time[0]);
		double minutes = Double.parseDouble(time[1]);
		this.departureTime = (hours + minutes/60);
		return this.departureTime;
	}
	
	/**
	 * Gets the arrival date and time.
	 * @return arrival date and time
	 */
	public String getArrivalDateTime(){
		return this.arrivalDateTime;
	}
	
	/**
	 * Changes the arrival date and time to a new
	 * arrival date and time. Also changes travel time
	 * accordingly.
	 * @param arrivalDateTime the new arrival date and time
	 */
	public void setArrivalDateTime(String arrivalDateTime){
		this.arrivalDateTime = arrivalDateTime;
		this.arrivalParts = this.arrivalDateTime.split(" ");
		this.arrivalDate = this.arrivalParts[0];
		this.arrivalTime = generateArrivalTime(this.arrivalParts[1]);
		this.travelTime = generateTravelTime();
	}
	
	/**
	 * Gets the arrival date of this flight.
	 * @return the arrival date
	 */
	public String getArrivalDate(){
		return this.arrivalDate;
	}
	
	/**
	 * Converts arrival time given as a string,
	 * into a double equivalent for calculation purposes.
	 * @param timeInString the arrival time as a string
	 * @return the arrival time as a double equivalent of the
	 * timeInString
	 */
	private double generateArrivalTime(String timeInString){
		String[] time = timeInString.split(":");
		double hours = Double.parseDouble(time[0]);
		double minutes = Double.parseDouble(time[1]);
		this.arrivalTime = (hours + minutes/60);
		return this.arrivalTime;
	}
	
	/**
	 * Gets the arrival time as a decimal.
	 * @return the arrival time
	 */
	public double getArrivalTime(){
		return this.arrivalTime;
	}
	
	/**
	 * Gets the airline of this flight.
	 * @return the airline
	 */
	public String getAirline(){
		return this.airline;
	}
	
	/**
	 * Sets the airline to the new airline airline.
	 * @param airline the new airline
	 */
	public void setAirline(String airline){
		this.airline = airline;
	}
	
	/**
	 * Gets the origin.
	 * @return the origin
	 */
	public String getOrigin(){
		return this.origin;
	}
	
	/**
	 * Sets the origin to new origin origin.
	 * @param origin the new origin
	 */
	public void setOrigin(String origin){
		this.origin = origin;
	}
	
	/**
	 * Gets the destination.
	 * @return the destination
	 */
	public String getDestination(){
		return this.destination;
	}
	 /**
	  * Sets the destination to new destination destination.
	  * @param destination the new destination
	  */
	public void setDestination(String destination){
		this.destination = destination;
	}
	
	/**
	 * Gets the cost of the flight.
	 * @return the cost
	 */
	public double getCost(){
		return this.cost;
	}
	
	/**
	 * Sets the cost of this flight to new cost cost.
	 * @param cost the new cost.
	 */
	public void setCost(String cost){
		this.cost = Double.parseDouble(cost);;
	}
	
	/**
	 * Returns the travel time in double rounded of to
	 * 2 decimal digits.
	 * @return the travel time
	 */
	public double getTravelTime(){
		return this.travelTime;
	}
	
	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(String numSeats) {
		this.numSeats = Integer.parseInt(numSeats);;
	}
	
	/**
	 * Returns the travel time as a string in the format
	 * HH:MM. It does so by converting the travel time from
	 * a double to a string. 
	 * @return the travel time as a string.
	 */
	public String getTravelTimeString(){
		DecimalFormat df = new DecimalFormat("#.00");
		double travelTime2 = this.travelTime;
		String s = df.format(travelTime2);
		String[] sArray = s.split("\\.");
		int hours;
		if (travelTime2 < 1){
			hours = 0;
		} else{
			hours = Integer.parseInt(sArray[0]);
		}
		double minute = Double.parseDouble(sArray[1]);
		minute = (minute) * (0.6);
		minute = Math.round(minute);
		int l = (int) minute;
		if (hours<10) {
			this.newTravelTime = "0" + hours + ":";
		} else {
			this.newTravelTime = hours + ":";
		}
		if (l < 10) {
			this.newTravelTime = this.newTravelTime + "0" + l;
		} else{
			this.newTravelTime = this.newTravelTime + l;
		} return this.newTravelTime;
	}
	
	/**
	 * Calculates the travel time by taking the difference
	 * of times between the arrival times and departure times
	 * as well as date considerations.
	 * @return the travel time as a double
	 */
	private double generateTravelTime(){
		//If arrival date is the same day.
		if (this.arrivalDate.equals(this.departureDate)){
			this.travelTime = this.arrivalTime - this.departureTime;
			this.travelTime = Math.round(this.travelTime * 100);
			this.travelTime = this.travelTime/100;
			return this.travelTime;
		} else{
			//If arrival date is the next day.
			this.travelTime = 24.0 - this.departureTime + this.arrivalTime;
			this.travelTime = Math.round(this.travelTime * 100);
			this.travelTime = this.travelTime/100;
			return this.travelTime;
		}
	}
	
	/**
	 * Gets a list of all users who have booked a seat in this flight.
	 * NOTE: This is for future phases.
	 * @return list of users who have booked seats
	 */
	public List<User> getBookedSeats(){
		return this.bookedSeats;
	}
	
	/**
	 * Sets the list of all users who have booked seats in this flight
	 * to a new list of booked seats bookedSeats. 
	 * NOTE: This is for future phases.
	 * @param bookedSeats the new list of booked seats containing Users.
	 */
	public void setBookedSeats(List<User> bookedSeats){
		this.bookedSeats = bookedSeats;
	}
	
	/**
	 * Adds User user to list of booked Seats.
	 * NOTE: This is for future phases.
	 * @param user the User who booked this Flight.
	 */
	public void bookSeat(User user){
		if (this.numSeats > 0) {
			this.bookedSeats.add(user);
			this.numSeats = this.numSeats - 1;
		}
	}
	
	/**
	 * Returns this flight as a String with description of what it stores.
	 * @return this flight as a string.
	 */
	public String toString(){
		return this.flightNum + "," + this.departureDateTime + "," +
				this.arrivalDateTime + "," + this.airline + "," + this.origin
				+ "," + this.destination;
	}
	
}