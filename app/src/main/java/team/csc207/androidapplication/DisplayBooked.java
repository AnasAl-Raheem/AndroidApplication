package team.csc207.androidapplication;


import java.util.List;

import csc207project.Flight;
import csc207project.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayBooked extends Activity {

	User user;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_booked);
		
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra("User");
		List<Flight> flights = user.getBookedFlights();

		ArrayAdapter<Flight> adapter = new ArrayAdapter<Flight>(this, 
				android.R.layout.simple_list_item_1, flights);
		
		ListView listView = (ListView) findViewById(R.id.BookedFlights);
		
		listView.setAdapter(adapter);
		
		if (flights.isEmpty()) {
			Toast.makeText(getBaseContext(), "No Results Found", 
					Toast.LENGTH_LONG).show();
		}
		
	}

}
