package team.csc207.androidapplication;

import java.io.IOException;
import java.util.ArrayList;

import csc207project.Client;
import csc207project.Flight;
import csc207project.Main;
import csc207project.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayFlight extends Activity {

	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_flight);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		TextView flight_info = (TextView) findViewById(R.id.flight);
		if (getIntent().hasExtra("Flight")) {
			Flight flight = (Flight) intent.getSerializableExtra("Flight");
			flight_info.setText(flight.toString());
		} else {
			ArrayList<Flight> flights 
			= (ArrayList<Flight>) intent.getSerializableExtra("Flights");
			flight_info.setText(Main.displayFlights(flights));
		}
		if (Main.getCurrentUser() == 'A') {
			EditText client_info = (EditText) findViewById(R.id.clientemail);
			client_info.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Books a flight based on what the user chose.
	 * @param view the view.
	 */
	public void BookFlight(View view) {
		Intent intent = getIntent();
		Intent mainIntent = new Intent(this, MainActivity.class);
		mainIntent.putExtra("User", user);
		
		if (Main.getCurrentUser() == 'C') {
			if (getIntent().hasExtra("Flight")) {
				Flight flight = (Flight) intent.getSerializableExtra("Flight");
				if (flight.getNumSeats() > 0) {
					int j = 0;
					for (Flight f:user.getBookedFlights()) {
						if (f.getFlightNum().equals(flight.getFlightNum())) {
							j++;
						}
					}
					if (j > 0) {
						Toast.makeText(getBaseContext(), "Flight Already Booked", Toast.LENGTH_LONG).show();
					} else {
					user.bookFlight(flight);
					Toast.makeText(getBaseContext(), "Flight Booked", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getBaseContext(), "No Seats Available", Toast.LENGTH_LONG).show();
				}
			} else {
				ArrayList<Flight> flights = (ArrayList<Flight>) intent.getSerializableExtra("Flights");
				int i = 0;
				for (Flight flight : flights) {
					if (flight.getNumSeats() == 0) {
						i++;
					}
				}
				if (i > 0) {
					Toast.makeText(getBaseContext(), "No Seats Available", Toast.LENGTH_LONG).show();
				}else {
					for (Flight flight : flights) {
						user.bookFlight(flight);
							}
					Toast.makeText(getBaseContext(), "Itinerary Booked", Toast.LENGTH_LONG).show();
				}
			}
		} else if (Main.getCurrentUser() == 'A') {
			EditText clientEmailText = (EditText) findViewById(R.id.clientemail);
			String clientEmail = clientEmailText.getText().toString();
			Client client = (Client) Main.findUser(clientEmail);
			if (getIntent().hasExtra("Flight")) {
				Flight flight = (Flight) intent.getSerializableExtra("Flight");
				if (flight.getNumSeats() > 0) {
					int j = 0;
					for (Flight f: client.getBookedFlights()) {
						if (f.getFlightNum().equals(flight.getFlightNum())) {
							j++;
						}
					}
					if (j > 0) {
						Toast.makeText(getBaseContext(), "Flight Already Booked", Toast.LENGTH_LONG).show();
					} else {
				client.bookFlight(flight);
				Toast.makeText(getBaseContext(), "Flight Booked", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getBaseContext(), "No Seats Available", Toast.LENGTH_LONG).show();
				}
				startActivity(mainIntent);
			} else {
				ArrayList<Flight> flights = (ArrayList<Flight>) intent.getSerializableExtra("Flights");
				int i = 0;
				for (Flight flight : flights) {
					if (flight.getNumSeats() == 0) {
						i++;
					}
				}
				if (i > 0) {
					Toast.makeText(getBaseContext(), "No Seats Available", Toast.LENGTH_LONG).show();
				} else {
					for (Flight flight : flights) {
						client.bookFlight(flight);
						}
				}Toast.makeText(getBaseContext(), "Itinerary Booked", Toast.LENGTH_LONG).show();
			}
		}
		try {
			Main.saveInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		startActivity(mainIntent);
	}
}
