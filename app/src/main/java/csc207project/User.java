package csc207project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable{
	//Changed visibility to protected.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3705444668684901674L;

	/**
	 * The password of the User.
	 */
	protected String password;
	
	/**
	 * The first name of the User.
	 */
	protected String firstName;
	
	/**
	 * The email of the user.
	 */
	protected String email; 
	
	/**
	 * The last name of the user.
	 */
	protected String lastName; 
	
	/**
	 * The address of the user.
	 */
	protected String address;
	
	/**
	 * The list of booked flights of the user.
	 */
	protected ArrayList<Flight> bookedFlights;
	
	/**
	 * Creates a User with last name LastName, first name FirstName, email Email
	 * email Email, address Address
	 * @param LastName the last name
	 * @param FirstName the first name
	 * @param Email the email
	 * @param Address the address
	 */
	public User (String lastName, String firstName, String email, 
			String Address){
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.address = Address;
		this.bookedFlights = new ArrayList<Flight>();
	}
	
	/**
	 * Gets the last name of the user
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Gets the first name of the user.
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the address
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Gets the password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets list of booked flights
	 * @return list of booked flights
	 */
	public List<Flight> getBookedFlights() {
		return bookedFlights;
	}
	
	/**
	 * Edits the user a on parameter b, with value c
	 * @param a The user to edit
	 * @param b The parameter to edit
	 * @param c The value to edit to.
	 */
	public void editUserInfo(User usr, String attribute, String str){
		if (usr.getClass() == Client.class){
			Client client = (Client) usr;
			if (attribute.equals("creditCardNumber")){
				client.setCreditCardNumber(str);
			}
			else if (attribute.equals("expiryDate")){
				client.setCreditCardNumber(str);
			}
		}
		if (attribute.equals( "email") ){
			usr.setEmail(str);	
		}
		else if (attribute.equals("password")){
			usr.setPassword(str);
		}
		else if (attribute.equals("lastName")){
			usr.setLastName(str);
		}
		else if (attribute.equals("firstName")){
			usr.setFirstName(str);
		}
		else if (attribute.equals("address")){
			usr.setAddress(str);
		}
		else{
			System.out.println(attribute + " was given, but don't know how to handle");
		}
	}
	
	/**
	 * Uploads the flight info from a file path.
	 * @param path the path to upload the file from.
	 */
//	public void uploadFlightInfo(String path){
//		//Create file based on path
//		//Upload information to System.flights.
//		//Checks if it exists
//		try{
//			@SuppressWarnings("unused")
//			File newFile = new File(path);
//			Main.uploadFlightInfo();
//		}catch (FileNotFoundException e){
//			System.out.println("File Does not exist");
//		}
//	}
	
	/**
	 * Sets the address to a.
	 * @param a The address to change to.
	 */
	public void setAddress(String str){
		this.address = str;
	}
	
	/**
	 * Sets the last name to a.
	 * @param a The last name to change to.
	 */
	public void setLastName(String str){
		this.lastName = str;
	}
	
	/**
	 * Sets the first name to a.
	 * @param a The first name to change to.
	 */
	public void setFirstName(String str){
		this.firstName = str;
	}
	
	/**
	 * Sets the email to a.
	 * @param a The email to change to.
	 */
	public void setEmail(String str){
		this.email = str;
	}
	
	/**
	 * Sets the password to a.
	 * @param a The password to change to.
	 */
	public void setPassword(String str){
		this.password = str;
	}
	
	/**
	 * Upload the user info from a file with path path
	 * @param path The path to upload the file from.
	 */
//	public void uploadUserInfo(String path){
//		try{
//			@SuppressWarnings("unused")
//			File f = new File(path);
//			Main.uploadFlightInfo();
//		}catch (FileNotFoundException e){
//			return;
//		}
//	}
	
	/**
	 * Edits the flight f for parameter a to value b.
	 * @param f The flight to edit
	 * @param a The parameter that will be changed
	 * @param b The value to be changed to.
	 */
	public void editFlightInfo(Flight flight, String attribute, String str){
		if (attribute.equals("FlightNumber")){
			flight.setFlightNum(str);
		}
		else if (attribute.equals("DepartureDateTime")){
			flight.setDepartureDateTime(str);
		}
		else if (attribute.equals("ArrivalDateTime")){
			flight.setArrivalDateTime(str);
		}
		else if (attribute.equals("Airline")){
			flight.setAirline(str);
		}
		else if (attribute.equals("Origin")){
			flight.setOrigin(str);
		}
		else if (attribute.equals("Destination")){
			flight.setDestination(str);
		}
		else if (attribute.equals("Cost")){
			flight.setCost(str);
		}
		else{
			System.out.println(attribute + " was given, but don't know how to handle");
		}
	}
	
	/**
	 * Book a flight flight
	 * @param flight The flight to be booked.
	 */
	public void bookFlight(Flight flight) {
		if (!bookedFlights.contains(flight)) {
			bookedFlights.add(flight);
		}
	}
	
	public List<Flight> searchFlight(String date, String origin, String destination, String method) {
		return Main.sortFlight(method, Main.searchFlight(date, origin, destination));
	}
	
	public List<List<Flight>> searchItineraries(String date, String origin, String destination, String method) {
		return Main.sortItineraries(method, Main.searchItineraries(date, origin, destination));
	}
	
	/**
	 * Displays an object as a string
	 * @param a The object to be displayed
	 * @return The String representation of a
	 */
	public String displayObject(Object object){
		if (object.getClass() == Client.class){
			Client client = (Client) object;
			return client.toString();
		}
		else if (object.getClass() == Admin.class){
			Admin admin = (Admin) object;
			return admin.toString();
		}
		else{
			return "Object was not found";
		}
	}
	
	public abstract String toString();
	
}
