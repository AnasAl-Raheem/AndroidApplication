package team.csc207.androidapplication;

import java.io.IOException;

import csc207project.Flight;
import csc207project.Main;
import csc207project.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ViewFlightInfo extends Activity {
	
	User user;
	Flight flight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_flight_info);
		Intent intent = getIntent();
		Flight flight = (Flight) intent.getSerializableExtra("Flight");
		EditText flightNumber = (EditText) findViewById(R.id.newflightnumber);
		EditText departureDate = (EditText) findViewById(R.id.newdeparturedate);
		EditText arrivalDate = (EditText) findViewById(R.id.newarrivaldate);
		EditText airline = (EditText) findViewById(R.id.newairline);
		EditText origin = (EditText) findViewById(R.id.neworigin);
		EditText destination = (EditText) findViewById(R.id.newdestination);
		EditText price = (EditText) findViewById(R.id.newprice);
		EditText seatNumber = (EditText) findViewById(R.id.newseatnumber);
		
		flightNumber.setText(flight.getFlightNum());
		departureDate.setText(flight.getDepartureDateTime());
		arrivalDate.setText(flight.getArrivalDateTime());
		airline.setText(flight.getAirline());
		origin.setText(flight.getOrigin());
		destination.setText(flight.getDestination());
		price.setText(String.valueOf(flight.getCost()));
		seatNumber.setText(String.valueOf(flight.getNumSeats()));
	}
	
	/**
	 * Updates the flight information to whatever the user edits it to.
	 * @param view the view 
	 */
	public void updateFlightInfo(View view) {
		Intent intent = getIntent();
		Flight flight = (Flight) intent.getSerializableExtra("Flight");
		
		EditText flightNumberText = (EditText) findViewById(R.id.newflightnumber);
		EditText departureDateText = (EditText) findViewById(R.id.newdeparturedate);
		EditText arrivalDateText = (EditText) findViewById(R.id.newarrivaldate);
		EditText airlineText = (EditText) findViewById(R.id.newairline);
		EditText originText = (EditText) findViewById(R.id.neworigin);
		EditText destinationText = (EditText) findViewById(R.id.newdestination);
		EditText priceText = (EditText) findViewById(R.id.newprice);
		EditText seatNumberText = (EditText) findViewById(R.id.newseatnumber);
		
		String flightNumber = flightNumberText.getText().toString();
		String departureDate = departureDateText.getText().toString();
		String arrivalDate = arrivalDateText.getText().toString();
		String airline = airlineText.getText().toString();
		String origin = originText.getText().toString();
		String destination = destinationText.getText().toString();
		String price = priceText.getText().toString();
		String seatNumber = seatNumberText.getText().toString();
		
		if (!(departureDate.equals(flight.getDepartureDateTime()))) {
			Main.removeFlight(flight.getDepartureDateTime(), flight);
		}
		flight.setFlightNum(flightNumber);
		flight.setDepartureDateTime(departureDate);
		flight.setArrivalDateTime(arrivalDate);
		flight.setAirline(airline);
		flight.setOrigin(origin);
		flight.setDestination(destination);
		flight.setCost(price);
		flight.setNumSeats(seatNumber);
		
		try {
			Main.saveInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.updateFlights(flight);
		
		
		Intent intent2 = new Intent(this, MainActivity.class);
		startActivity(intent2);
	}
}
