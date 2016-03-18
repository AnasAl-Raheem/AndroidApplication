package team.csc207.androidapplication;

import java.util.ArrayList;
import java.util.List;

import csc207project.Flight;
import csc207project.Main;
import csc207project.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (Main.getCurrentUser() == 'A') {
			TextView clientText = (TextView) findViewById(R.id.client);
			clientText.setVisibility(View.VISIBLE);
			
			EditText flightNumberText = (EditText) findViewById(R.id.flightnumber);
			flightNumberText.setVisibility(View.VISIBLE);
			
			Button flightInfoText = (Button) findViewById(R.id.flightinfo);
			flightInfoText.setVisibility(View.VISIBLE);
			
			Button uploadInfoText = (Button) findViewById(R.id.uploadinfo);
			uploadInfoText.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Searches for flights based on the text given.
	 * @param view the view
	 */
	public void SearchFlight(View view) {
		User user = (User) getIntent().getSerializableExtra("User");
		
		EditText dateText = (EditText) findViewById(R.id.date);
		String date = dateText.getText().toString();
		
		EditText originText = (EditText) findViewById(R.id.origin);
		String origin = originText.getText().toString();

		EditText destinationText = (EditText) findViewById(R.id.destination);
		String destination = destinationText.getText().toString();
		
		RadioGroup sort = (RadioGroup) findViewById(R.id.sortmethod);
		int choice = sort.getCheckedRadioButtonId();
		RadioButton sorting = (RadioButton) findViewById(choice);
		String sortMethod = (String) sorting.getText();
		
		Intent intent = new Intent(this, SearchResults.class);
		ArrayList<Flight> lst 
		= new ArrayList<Flight>(Main.sortFlight(sortMethod, 
				Main.searchFlight(date, origin, destination)));
		intent.putExtra("SearchFlight", lst);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
	/**
	 * Calls the activity to searches for itineraries based
	 * on the text entered.
	 * @param view the view.
	 */
	public void SearchItinerary(View view) {
		User user = (User) getIntent().getSerializableExtra("User");
		
		EditText dateText = (EditText) findViewById(R.id.date);
		String date = dateText.getText().toString();
		
		EditText originText = (EditText) findViewById(R.id.origin);
		String origin = originText.getText().toString();

		EditText destinationText = (EditText) findViewById(R.id.destination);
		String destination = destinationText.getText().toString();
		
		RadioGroup sort = (RadioGroup) findViewById(R.id.sortmethod);
		int choice = sort.getCheckedRadioButtonId();
		RadioButton sorting = (RadioButton) findViewById(choice);
		String sortmethod = (String) sorting.getText();
		
		Intent intent = new Intent(this, SearchResults.class);

		ArrayList<List<Flight>> lst 
		= new ArrayList<List<Flight>>(Main.sortItineraries(sortmethod, 
				Main.searchItineraries(date, origin, destination)));
		intent.putExtra("SearchItenirary", lst);
		intent.putExtra("User", user);
		startActivity(intent);
	}
	
	/**
	 * Calls the activity to view client info.
	 * @param view
	 */
	public void ClientInfo(View view) {
		User user = (User) getIntent().getSerializableExtra("User");
		Intent intent = new Intent(this, ViewClientInfo.class);
		
		if (Main.getCurrentUser() == 'A') {
			EditText clientText = (EditText) findViewById(R.id.client);
			String client = clientText.getText().toString();
			intent.putExtra("User", Main.findUser(client));
		} else {
			intent.putExtra("User", user);
		}
		startActivity(intent);
	}
	
	/**
	 * Calls the activity to view a client's booked flights
	 * @param view the view.
	 */
	public void ViewBookedFlights(View view) {
		User user = (User) getIntent().getSerializableExtra("User");
		Intent intent = new Intent(this, DisplayBooked.class);
		
		if (Main.getCurrentUser() == 'A') {
			EditText clientText = (EditText) this.findViewById(R.id.client);
			String client = clientText.getText().toString();
			intent.putExtra("User", Main.findUser(client));
		} else {
			intent.putExtra("User", user);
		}
		startActivity(intent);
	}
	
	/**
	 * Calls the activity to upload information.
	 * @param view the view.
	 */
	public void UploadInfo(View view) {
		Intent intent = new Intent(this, UploadInfo.class);
		startActivity(intent);
	}
	
	/**
	 * Calls the activity to views flight's information
	 * @param view the view.
	 */
	public void ViewFlightInfo(View view) {
		EditText flightNumberText = (EditText) findViewById(R.id.flightnumber);
		String flightNumber = flightNumberText.getText().toString();
		
		Intent intent = new Intent(this, ViewFlightInfo.class);
		if (Main.findflight(flightNumber).equals(null)) {
			Toast.makeText(getBaseContext(), "Flight Does Not Exist", 
					Toast.LENGTH_LONG).show();
		} else {
			intent.putExtra("Flight", Main.findflight(flightNumber));
			startActivity(intent);
		}
	}
	
}
