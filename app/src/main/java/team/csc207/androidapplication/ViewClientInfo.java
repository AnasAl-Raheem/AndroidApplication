package team.csc207.androidapplication;

import java.io.IOException;

import csc207project.Client;
import csc207project.Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ViewClientInfo extends Activity {
	
	Client client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_client_info);
		
		Intent intent = getIntent();
		Client client = (Client) intent.getSerializableExtra("User");
		
		EditText firstName = (EditText) findViewById(R.id.ClientFirstName);
		EditText lastName = (EditText) findViewById(R.id.ClientLastName);
		EditText address = (EditText) findViewById(R.id.ClientAddress);
		EditText email = (EditText) findViewById(R.id.ClientEmail);
		EditText creditCard = (EditText) findViewById(R.id.ClientCreditCard);
		EditText expiryDate = (EditText) findViewById(R.id.ClientExpiryDate);
		
		firstName.setText(client.getFirstName());
		lastName.setText(client.getLastName());
		address.setText(client.getAddress());
		email.setText(client.getEmail());
		creditCard.setText(client.getCreditCardNumber());
		expiryDate.setText(client.getExpiryDate());
	}
	
	/**
	 * Changes the Client's information to whatever the user edits
	 * it to.
	 * @param view the view.
	 */
	public void save(View view){
		Intent intent = getIntent();
		Client client = (Client) intent.getSerializableExtra("User");
		
		EditText firstNameText = (EditText) findViewById(R.id.ClientFirstName);
		EditText lastNameText = (EditText) findViewById(R.id.ClientLastName);
		EditText addressText = (EditText) findViewById(R.id.ClientAddress);
		EditText emailText = (EditText) findViewById(R.id.ClientEmail);
		EditText creditCardText = (EditText) findViewById(R.id.ClientCreditCard);
		EditText expiryDateText = (EditText) findViewById(R.id.ClientExpiryDate);
		
		String firstName = firstNameText.getText().toString();
		String lastName = lastNameText.getText().toString();
		String email = emailText.getText().toString();
		String address = addressText.getText().toString();
		String creditCard = creditCardText.getText().toString();
		String expiry = expiryDateText.getText().toString();
		
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setAddress(address);
		client.setEmail(email);
		client.setCreditCardNumber(creditCard);
		client.setExpiryDate(expiry);
		
		firstNameText.setText(client.getFirstName());
		lastNameText.setText(client.getLastName());
		addressText.setText(client.getAddress());
		emailText.setText(client.getEmail());
		creditCardText.setText(client.getCreditCardNumber());
		expiryDateText.setText(client.getExpiryDate());
		
		try {
			Main.saveInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.updateUsers(client);
		
		Intent intent2 = new Intent(this, MainActivity.class);
		intent2.putExtra("User", intent.getSerializableExtra("User"));
		startActivity(intent2);
	}
}
