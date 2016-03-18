package team.csc207.androidapplication;

import java.io.IOException;
import csc207project.Client;
import csc207project.Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	}
	
	/**
	 * Signs up a client
	 * @param view the view.
	 * @throws IOException 
	 */
	public void SignUp(View view){
	
		EditText firstNameText = (EditText) findViewById(R.id.firstName);
		EditText lastNameText = (EditText)findViewById(R.id.lastName);
		EditText emailText = (EditText) findViewById(R.id.client);
		EditText addressText = (EditText) findViewById(R.id.Address);
		EditText creditCardNumberText 
		= (EditText) findViewById(R.id.creditcardnumber);
		EditText expiryDateText = (EditText) findViewById(R.id.expiryDate);
		EditText passwordText = (EditText) findViewById(R.id.clientpassword);
		
		String firstName = firstNameText.getText().toString();
		String lastName = lastNameText.getText().toString();
		String email = emailText.getText().toString();
		String address = addressText.getText().toString();
		String creditCard = creditCardNumberText.getText().toString();
		String expiry = expiryDateText.getText().toString();
		String password = passwordText.getText().toString();
		
		if (firstName.equals("") || lastName.equals("") || email.equals("") || 
				address.equals("") || creditCard.equals("") || expiry.equals("") ||
				password.equals("")) {
			Toast.makeText(getBaseContext(), "Please Fill In All The Fields", 
					Toast.LENGTH_LONG).show();
		}else {
			try { 
				Main.findUser(email).getEmail();
				Toast.makeText(getBaseContext(), "User Already Exists", 
						Toast.LENGTH_LONG).show();
			} catch (NullPointerException e) {
				Client client = new Client(lastName,firstName,email,address,
						creditCard,expiry);
				Main.addUser(client);
				Main.updatePass(email, password);
				Toast.makeText(getBaseContext(), "Client Created Successfuly", 
						Toast.LENGTH_LONG).show();
				try {
					Main.saveInfo();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Intent intent = new Intent(this, LogIn.class);
				startActivity(intent);	
			}
		}
	}
}