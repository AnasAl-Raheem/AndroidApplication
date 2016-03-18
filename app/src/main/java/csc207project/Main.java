package csc207project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Manages all User objects, all Flight objects and all flight searches.
 */
public class Main implements Serializable{
	
	
	private static final long serialVersionUID = -1374983573358582762L;
	
	/**
	 * A map of all the users in the program, divided into Clients and Admins.
	 */
	private static Map<String,List<User>> users;
	
	/**
	 * A map of all the flights in the program, divided into lists based on their departure date.
	 */
	private static Map<String,List<Flight>> flights;
	
	/**
	 * A map containing each user Email and their password.
	 */
	private static Map<String,String> Passwords;

	/**
	 * A list containing all the dates that contain departing flights.
	 */
	private static List<String> availableDates;

	/**
	 * Represents the current logged in user.
	 */
	private static char CurrentUser = 'N';
	
	/**
	 * The path where all User objects are saved.
	 */
	private static final String USERS_PATH = "/data/data/team.csc207.androidapplication/Users.ser";
	
	/**
	 * The path wjere all users passwords are saved.
	 */
	private static final String PASSWORDS_PATH = "/data/data/team.csc207.androidapplication/passwords.txt";
	
	/**
	 * The path where all Flight objects are saved.
	 */
	private static final String FLIGHTS_PATH = "/data/data/team.csc207.androidapplication/Flights.ser";
	
	private static final Main main = new Main();
	
	
	/**
	 * creates a main object.
	 */
	private Main() {
		Main.users = new HashMap<String,List<User>>();
		Main.users.put("Clients", new ArrayList<User>());
		Main.users.put("Admins", new ArrayList<User>());
		Main.flights = new HashMap<String,List<Flight>>();
		Main.availableDates = new ArrayList<String>();
		Main.Passwords = new HashMap<String,String>();
	}
	public static void cleanup() {
		Main.users = new HashMap<String,List<User>>();
		Main.users.put("Clients", new ArrayList<User>());
		Main.users.put("Admins", new ArrayList<User>());
		Main.flights = new HashMap<String,List<Flight>>();
		Main.availableDates = new ArrayList<String>();
	}
	
	public static char getCurrentUser() {
		return CurrentUser;
	}
	
	/**
	 * Updates the password file and adds email, and password.
	 * @param email
	 * @param password
	 * @throws IOException
	 */
	public static void updatePass(String email, String password) {
		FileWriter writer;
		try {
			writer = new FileWriter(PASSWORDS_PATH, true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			bufferWriter.newLine();
			bufferWriter.write(email + "," + password);
			bufferWriter.newLine();
			bufferWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCurrentUser(char currentUser) {
		CurrentUser = currentUser;
	}
	
	public static Map<String, String> getPasswords() {
		return Passwords;
	}
		
	/**
	 * Loads data from the files to the application.
	 * @throws ClassNotFoundException
	 */
	public static void launchApp() throws ClassNotFoundException {
		try {
			Main.usersPass();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Main.loadUserInfo();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			Main.loadFlightInfo();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Search for direct flights leaving on the given date from origin to destination.
	 * @param date the date on which the flights leave the origin.
	 * @param origin the origin of the flights.
	 * @param destination the destination of the flights.
	 * @return a list containing flight objects.
	 */
	public static List<Flight> searchFlight(String date, String origin, String destination) {
		List<Flight> oneFlight = new ArrayList<Flight>();
		if (flights.isEmpty() || !(flights.containsKey(date)) || flights.get(date).isEmpty()) {
			return oneFlight;
		}
		for (Flight f : flights.get(date)) {
			if (f.getOrigin().equals(origin) && f.getDestination().equals(destination)) {
				oneFlight.add(f);
				}
			}return oneFlight;
		}
	
	/**
	 * Search for Itineraries leaving on the given date from origin to destination.
	 * @param date the date on which the flights leave the origin.
	 * @param origin the origin of the flights.
	 * @param destination the destination of the flights.
	 * @return a list of lists, each containing at least one flight object.
	 */
	public static List<List<Flight>> searchItineraries(String date, String origin,
			String destination) {
		List<List<Flight>> allFlights = new ArrayList<List<Flight>>();
		if (flights.isEmpty() || !(flights.containsKey(date)) || flights.get(date).isEmpty()) {
			return allFlights;
		}
		List<Flight> availableFlights = flights.get(date);
		
		for (Flight f : availableFlights) {
			if (f.getOrigin().equals(origin) && f.getDestination().equals(destination)) {
				List<Flight> lst = new ArrayList<Flight>();
				lst.add(f);
				allFlights.add((List<Flight>) lst);
			}
			else if (f.getOrigin().equals(origin)) {
				List<String> originLst = new ArrayList<String>();
				originLst.add(f.getOrigin());
				List<List<Flight>> tempLst = new ArrayList<List<Flight>>(refineItineraries(
						f.getArrivalDate(), f.getDestination(),destination,f,originLst));
				for (List<Flight> l: tempLst) {
					l.add(0, f);
					}
				allFlights.addAll(tempLst);
				}
			}
		if (allFlights.size() > 0) {
		for (List<Flight> lst:allFlights) {
			List<Flight> lstremove = new ArrayList<Flight>();
			if (lst.size() > 1)
			for (Flight flight: lst) {
				if (!(lst.indexOf(flight) +1 == lst.size())) {
				if (!(flight.getDestination().equals(lst.get(lst.indexOf(flight)+1).getOrigin()))) {
					lstremove.add(flight);}
				}
			} for (Flight f: lstremove) {
				lst.remove(f);}
			}
		
		}
		return allFlights;
	}
	

	/**
	 * Adds additional flights to the itinerary.
	 * @param date the date on which the flights leave the current origin.
	 * @param origin the current origin of the flights.
	 * @param destination the destination of the flights.
	 * @param flight the object connecting to the next Itineraries.
	 * @param Originlst a list of cities  already visited.
	 * @return a list of lists, each containing at least one flight object.
	 */
	private static List<List<Flight>> refineItineraries(String date, String origin,
			String destination, Flight flight, List<String> originLst) {
		List<List<Flight>> allFlights = new ArrayList<List<Flight>>();
		if (flights.isEmpty()) {
			return allFlights;
		}
		if (!(!(flights.containsKey(date)) || flights.get(date).isEmpty())) {
			List<Flight> availableFlights = flights.get(date);
			for (Flight f:availableFlights) {
				List<Flight> lst = new ArrayList<Flight>();
				if (!(f.equals(flight)) && (f.getOrigin().equals(origin) &&
						!(originLst.contains(f.getDestination())))) {
					Double diff = f.getDepartureTime() - flight.getArrivalTime();
					if ((f.getDepartureTime() > flight.getArrivalTime()) &&
							((0.01 < diff) && (diff < 6.01))) {
						if (f.getDestination().equals(destination)) {
							lst.add(f);
							allFlights.add(lst);
						} else {
					originLst.add(f.getOrigin());
					allFlights.addAll(refineItineraries(f.getArrivalDate(),
							f.getDestination(), destination,f, originLst));
					for (List<Flight> l: allFlights) {
						if (f.getDestination().equals(l.get(0).getOrigin())) {
						l.add(0, f);}
						}
					}
					}	
				}
			}
		}if (flight.getArrivalTime() > 17.59) {
			if (availableDates.size() > availableDates.indexOf(date) + 1) {
				if (!(flights.containsKey(availableDates.get(availableDates.indexOf(date) + 1))) ||
						flights.get(availableDates.get(availableDates.indexOf(date) + 1)).isEmpty()) {
					return allFlights;
				}
				List<Flight> availableFlights2 = flights.get(availableDates.get(
						availableDates.indexOf(date) + 1));
				for (Flight f2:availableFlights2) {
					List<Flight> lst2 = new ArrayList<Flight>();
					if (f2.getOrigin().equals(origin) && !(originLst.contains(
							f2.getDestination()))) {
						Double diff = 24.00 - flight.getArrivalTime() + f2.getDepartureTime();
						if (diff > 1 && diff <= 6.00) {
							if (f2.getDestination().equals(destination)) {
								lst2.add(f2);
								allFlights.add(lst2);
								} else {
						originLst.add(f2.getOrigin());
						allFlights.addAll(refineItineraries(f2.getArrivalDate(), f2.getDestination(),
								destination, f2,originLst));
						for (List<Flight> l: allFlights) {
							l.add(0, f2);}	
							}
						}
					}
				}
			}	
		} return allFlights;
	}
	
	/**
	 * sorts Flight objects in the given list based on the method given.
	 * @param method to sort by.
	 * @param lst a list of flight to sort.
	 * @return a sorted list of flights.
	 */
	public static List<Flight> sortFlight(String method, List<Flight> lst) {
		if (lst.isEmpty() || lst.size() == 1){
			return lst;
		}
		if (method != "Cost" && method != "Time") {
			return lst;
		}
		if (method == "Time") {
			for (int n = 1; n < lst.size(); n++) {
				if (lst.get(n-1).getTravelTime() > lst.get(n).getTravelTime()) {
					Collections.swap(lst, n-1, n);
				}
			}
		}
		if (method == "Cost") {
			for (int n = 1; n < lst.size(); n++) {
				if (lst.get(n-1).getCost() > lst.get(n).getCost()) {
					Collections.swap(lst, n-1, n);
				}
			}
		}
		if (!(checkFlightSort(method, lst))) {
			sortFlight(method, lst);
		}return lst;	
	}
	
	/**
	 * checks if the given list is sorted according to the method given.
	 * @param method to sort by.
	 * @param lst the list of flights to be sorted.
	 * @return true if the list is sorted in the right method, otherwise false.
	 */
	private static boolean checkFlightSort(String method, List<Flight> lst) {
		Integer i = 0;
		if (method == "Time") {
			for (int j = 1; j < lst.size(); j++) {
				if (lst.get(j-1).getTravelTime() <= lst.get(j).getTravelTime()){
					i++;
				}
			}
		}
		if (method == "Cost") {
			for (int j = 1; j < lst.size(); j++) {
				if (lst.get(j-1).getCost() <= lst.get(j).getCost()) {
					i++;
				}
			}
		}
		i = i + 1;
		if (i.equals(lst.size())) {
			return true;
		} return false;
	}
		
	
	/**
	 * sorts itineraries of Flight objects in the given list based on the method given.
	 * @param method to sort by.
	 * @param lst a list of lists of flights to sort.
	 * @return a sorted list of lists of flights.
	 */
	public static List<List<Flight>> sortItineraries(String method, List<List<Flight>> lst) {
		if (lst.isEmpty() || lst.size() == 1){
			return lst;
		}
		if (!(method.equals("Cost")) && (!(method.equals("Time")))) {
			return lst;
		}
		if (method.equals("Time")) {
			for (int n = 1; n < lst.size(); n++) {
				if (calcTime(lst.get(n-1)) > calcTime(lst.get(n))) {
					Collections.swap(lst, n-1, n);
				}
			}
		}
		if (method.equals("Cost")) {
			for (int n = 1; n < lst.size(); n++) {
				if (calcCost(lst.get(n-1)) > calcCost(lst.get(n))) {
					Collections.swap(lst, n-1, n);
				}
			}
		}
		if (!(checkItinerariesSort(method, lst))) {
			sortItineraries(method, lst);
		}return lst;
	}
	
	/**
	 * checks if the given list of lists is sorted according to the method given.
	 * @param method to sort by.
	 * @param lst the list of lists of flights to be sorted.
	 * @return true if the list of lists of flights is sorted in the right method, otherwise false.
	 */
	private static boolean checkItinerariesSort(String method, List<List<Flight>> lst) {
		int i = 0;
		if (method.equals("Time")) {
			for (int j = 1; j < lst.size(); j++) {
				if (calcTime(lst.get(j-1)) <= calcTime(lst.get(j))) {
					i++;
				}
			}
		}
		if (method.equals("Cost")) {
			for (int j = 1; j < lst.size(); j++) {
				if (calcCost(lst.get(j-1)) <= calcCost(lst.get(j))) {
					i++;
				}
			}
		}
		i = i + 1;
		if (i == lst.size()) {
			return true;
		} return false;
	}
	
	/**
	 * returns the sum of all flight objects' travel times in the list.
	 * @param lst a list of flights.
	 * @return a Double representing travel time of all the flights in the list.
	 */
	private static Double calcTime(List<Flight> lst) {
		Double total = 0.0;
		if (lst.size() == 1) {
			total = lst.get(0).getTravelTime();
		} else {
			for (int i = 0; i < lst.size() - 1; i++) {
				total = total + lst.get(i).getTravelTime() + 
						(lst.get(i + 1).getDepartureTime() - lst.get(i).getArrivalTime());
			} total = total + lst.get(lst.size()-1).getTravelTime();
		} return total;
	}
	
	/**
	 * returns the sum of all flight objects' cost in the list.
	 * @param lst a list of flights.
	 * @return a Double representing the combined cost of all the flights in the list.
	 */
	private static Double calcCost(List<Flight> lst) {
		Double total = 0.0;
		for (Flight f: lst) {
			total = total + f.getCost();
		} return total;
	}
	
	/**
	 * returns the User corresponding to the given email.
	 * @param email a unique string to find a specific user.
	 * @return a User object.
	 */
	public static User findUser(String email) {
		if (users.get("Clients").size() == 0 && users.get("Admins").size() == 0) {
			return null;
		}
		List<User> clients = users.get("Clients");
		for (User c:clients) {
			if (c.getEmail().equals(email)) {
				return c;
			}
		}
		List<User> admins = users.get("Admins");
		for (User a:admins) {
			if (a.getEmail().equals(email)) {
				return a;
			}
		} return null;
	}
	
	public static void addUser(User user) {
		if (user.getClass().equals(Admin.class)) {
			users.get("Admins").add(user);
		}else if (user.getClass().equals(Client.class)) {
			users.get("Clients").add(user);
		}
	}
	
	public static void updateUsers(User user) {
		if (user.getClass().equals(Admin.class)) {
			users.get("Admins").remove(Main.findUser(user.getEmail()));
			users.get("Admins").add(user);
		}else if (user.getClass().equals(Client.class)) {
			users.get("Clients").remove(Main.findUser(user.getEmail()));
			users.get("Clients").add(user);
		}
	}
	
	public static void updateFlights(Flight flight) {
		if (!(flights.containsKey(flight.getDepartureDate()))) {
			ArrayList<Flight> lst = new ArrayList<Flight>();
			lst.add(flight);
			flights.put(flight.getDepartureDate(), lst);
		} else {
			flights.get(flight.getDepartureDate()).remove(Main.findflight(flight.getFlightNum()));
			flights.get(flight.getDepartureDate()).add(flight);
		}
	}
	
	public static void removeFlight(String date, Flight flight) {
		if (flights.containsKey(date)) {
		flights.get(date).remove(flight);
		}
	}
	
	public static Flight findflight(String flightnumber) {
		if (flights.isEmpty()) {
			return null;
		}
		Set<String> dates = flights.keySet();
		for (String str: dates) {
			if (!(flights.get(str).isEmpty())) {
				for (Flight flight: flights.get(str)) {
					if (flight.getFlightNum().equals(flightnumber)) {
						return flight;
					}
				}
			}
		} return null;
	}
	
	/**
	 * returns a string representation of list of flights. 
	 * @param lst a list of flight objects.
	 * @return each flight as a string in a separate line.
	 */
	public static String displayFlights(List<Flight> lst) {
		String str = "";
		if (lst.size() == 0) {
			return str;
		}
		for (Flight f: lst) {
			DecimalFormat df = new DecimalFormat("#.00");
			str = str + f.toString() + "," + df.format(f.getCost());
		}
		return str;
	}
	
	/**
	 * returns a string representation of an Itineraries. 
	 * @param lst a list of lists of flight objects.
	 * @return each flight in an Itinerary in a separate line,
	 *  followed by total cost and total travel time.
	 */
	public static String displayItineraries(List<List<Flight>> lst) {
		String str = "";
		if (lst.size() == 0) {
			return str;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		for (List<Flight> l :lst) {
			if (!(l.size() == 0)) {
				for (Flight f : l) {
					str = str + f.toString() +"\n";
				} str = str + df.format(calcCost(l)) + "\n" + totalTravelTime(l) 
						+ "\n";
			}
		}
		return str;
	}
	
	/**
	 * Uploads new User objects from a file found on filepath.
	 * @param filePath the path to the file containing user information.
	 * @throws IOException 
	 */
	public static void uploadUserInfo(String filepath) throws IOException {
		Scanner scanner = new Scanner(new FileInputStream(filepath));
        String[] record;
        Client client;
        Admin admin;
        if (users.isEmpty()) {
        	List<User> lst1 = new ArrayList<User>();
        	List<User> lst2 = new ArrayList<User>();
        	users.put("Clients", lst1);
        	users.put("Admins", lst2);
        }

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            
            if (record.length == 6) {
            	int i = 0;
            	for (User usr: users.get("Clients")) {
            		if (usr.getEmail().equals(record[2])) {
            			i++;
            		}
            	} if (i == 0) {
            		client = new csc207project.Client(record[0], record[1], record[2], record[3],
            				record[4], record[5]);
            		users.get("Clients").add(client);
            	}
            } 
            if (record.length == 4) {
            	int i = 0;
            	for (User usr : users.get("Admins")) {
            		if (usr.getEmail().equals(record[2])) {
            			i++;
            		}
            	} if (i == 0) {
            		admin = new Admin(record[0], record[1], record[2], record[3]);
            		users.get("Admins").add(admin);
            	}
            }
        }
        scanner.close();
	}

	/**
	 * returns a string representing the total travel time of the given list of flights.
	 * @param lst a list of flights.
	 * @return a string in the form HH:MM.
	 */
	private static String totalTravelTime(List<Flight> lst) {
		Double time = calcTime(lst);
		Integer timedecimal = (int) Math.round((double)Math.round((time % 1) * 100) * 0.6);
		Integer newtime = (int) Math.round(time - time % 1);
		String timestr;
		if (newtime < 10) {
			timestr = "0" + newtime.toString() + ":";
		} else{
			timestr = newtime.toString() + ":";
		}
		if (timedecimal < 10) {
			timestr += "0" + timedecimal.toString();
		} else{
			timestr += timedecimal.toString();
		}
		return timestr;
	}
	
	/**
	 * Uploads new Flight objects from a file found on filepath.
	 * @param filePath the path to the file containing flight information.
	 * @throws FileNotFoundException if filepath is not a valid path.
	 */
	public static void uploadFlightInfo(String filepath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filepath));
        String[] record;
        Flight flight;
        

        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            if (record.length == 8) {
            	List<Flight> toberemoved = new ArrayList<Flight>();
            	if (flights.size() > 0) {
            		for (String l: flights.keySet()) {
            			for (Flight f: flights.get(l)) {
            				if (f.getFlightNum().equals(record[0])) {
            					toberemoved.add(f);
            				}
            			}
            		}
            	}
            	if (!(toberemoved.isEmpty())) {
            		for (Flight someflight: toberemoved) {
            			for (String str : flights.keySet()) {
            				if (flights.get(str).contains(someflight)) {
            					flights.get(str).remove(someflight);
            					}
            				}
            			}
            		}
            	flight = new Flight(record[0], record[1], record[2], record[3], record[4],
            				record[5], record[6], record[7]);
            	if (!(availableDates.contains(flight.getDepartureDate()))) {
            			availableDates.add(flight.getDepartureDate());
            	}
            	if (!(flights.containsKey(flight.getDepartureDate()))) {
            		List<Flight> lst = new ArrayList<Flight>();
            		flights.put(flight.getDepartureDate(), lst);
            	}
            	flights.get(flight.getDepartureDate()).add(flight);
            }
        }
        scanner.close();
	}
	
	/**
	 * Loads User objects from a saved file into the application.
	 * @throws ClassNotFoundException
	 */
	private static void loadUserInfo() throws ClassNotFoundException {
		
		try {
            InputStream file = new FileInputStream(USERS_PATH);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            
            users.get("Clients").addAll((List<User>) input.readObject());
            users.get("Admins").addAll((List<User>) input.readObject());
            
            input.close();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
	}
	
	/**
	 * Loads Flight objects from a saved file into the application.
	 * @throws ClassNotFoundException
	 */
	private static void loadFlightInfo() throws ClassNotFoundException {
		
		try { 
            InputStream file = new FileInputStream(FLIGHTS_PATH);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            
            while (input.read() != 0) {
            	List<Flight> lst = new ArrayList<Flight>((List<Flight>) input.readObject());
            	for (Flight f: lst) {
            		if (flights.containsKey(f.getDepartureDate())) {
            			flights.get(f.getDepartureDate()).add(f);
            		} else {
            			List<Flight> newlst = new ArrayList<Flight>();
            			newlst.add(f);
            			flights.put(f.getDepartureDate(), newlst);
            			availableDates.add(f.getDepartureDate());
            		}
            	}
            }
            input.close();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
	}
	
	/**
	 * Updates the map LoginInfo with all users Email and password.
	 * @throws FileNotFoundException 
	 */
	private static void usersPass() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(PASSWORDS_PATH));
        String[] record;
        
        while(scanner.hasNextLine()) {
            record = scanner.nextLine().split(",");
            Passwords.put(record[0], record[1]);
        }
	}
	
	/**
	 * Saves all objects in the application to a file.
	 * @throws IOException
	 */
	public static void saveInfo() throws IOException {
		
		if (!(users.isEmpty())) {
		OutputStream usersFile = new FileOutputStream(USERS_PATH);
        OutputStream usersBuffer = new BufferedOutputStream(usersFile);
        ObjectOutput usersOutput = new ObjectOutputStream(usersBuffer);

        usersOutput.writeObject(users.get("Clients"));
        usersOutput.writeObject(users.get("Admins"));
        usersOutput.close();
		}
		if (!(flights.isEmpty())) {
        OutputStream flightsFile = new FileOutputStream(FLIGHTS_PATH);
        OutputStream flightsBuffer = new BufferedOutputStream(flightsFile);
        ObjectOutput flightsOutput = new ObjectOutputStream(flightsBuffer);

        Set<String> set = flights.keySet();
        for (String s: set) {
        	flightsOutput.writeObject(flights.get(s));
        }
        flightsOutput.close();
		}
		
	}
	

}
