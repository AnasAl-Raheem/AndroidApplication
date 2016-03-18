package team.csc207.androidapplication;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import csc207project.Flight;
import csc207project.User;
import android.view.View;

public class SearchResults extends Activity {
	
	ArrayList<Flight> flights;
	ArrayList<List<Flight>> itineraries;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_results);
		ListView listView = (ListView) findViewById(R.id.listView);
		user = (User) getIntent().getSerializableExtra("User");
		if (getIntent().hasExtra("SearchFlight")) {
			flights = (ArrayList<Flight>) getIntent()
					.getSerializableExtra("SearchFlight");
			if (flights.isEmpty()) {
				Toast.makeText(getBaseContext(), "No Results Found", 
						Toast.LENGTH_LONG).show();
			}
			
			ArrayAdapter<Flight> adapter = new ArrayAdapter<Flight>(this,
					android.R.layout.simple_list_item_1, flights);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, 
						int position, long id) {
					Flight flight= flights.get(position);
					Intent intent = new Intent(SearchResults.this, 
							DisplayFlight.class);
	        		intent.putExtra("User", user);

	        		intent.putExtra("Flight", flight);
					startActivity(intent);
	            		}
	        	});
			} 
		else if (getIntent().hasExtra("SearchItenirary")) {
			itineraries = (ArrayList<List<Flight>>) getIntent()
					.getSerializableExtra("SearchItenirary");
			if (itineraries.isEmpty()) {
				Toast.makeText(getBaseContext(), "No Results Found", 
						Toast.LENGTH_LONG).show();
			}
			
			ArrayAdapter<List<Flight>> adapter = new ArrayAdapter<List<Flight>>(this,
							android.R.layout.simple_list_item_1, itineraries);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, 
						int position, long id) {
					ArrayList<Flight> lst = (ArrayList<Flight>) itineraries.get(position);
					Intent intent = new Intent(SearchResults.this, 
							DisplayFlight.class);
	        		intent.putExtra("User", user);
	        		intent.putExtra("Flights", lst);
					startActivity(intent);
	            	}
	        });
		}
	}
}
	
    
