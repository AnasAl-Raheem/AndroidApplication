package team.csc207.androidapplication;

import java.util.Map;

import csc207project.Admin;
import csc207project.Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		try {
			Main.launchApp();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Finds the user based on the password and email.
	 * @param view the view
	 */
	public void findUser(View view) {
		EditText usernameText = (EditText) findViewById(R.id.username);
		String username = usernameText.getText().toString();

		EditText passwordText = (EditText) findViewById(R.id.password);
		String password = passwordText.getText().toString();
		
		Map<String,String> pass = Main.getPasswords();
		if (pass.containsKey(username)) {
			if (pass.get(username).equals(password)) {
				Intent intent = new Intent(this, MainActivity.class);
				if (Main.findUser(username).getClass().equals(Admin.class)) {
					Main.setCurrentUser('A');
				} else {
					Main.setCurrentUser('C');
				}
				intent.putExtra("User", Main.findUser(username));
				startActivity(intent);
			} else {
				Toast.makeText(this, "Password is Incorrect", 
						Toast.LENGTH_SHORT).show();
			}
		} else { 
				Toast.makeText(this, "Username does not exist", 
						Toast.LENGTH_SHORT).show();
			}
	}
	
	/**
	 * Creates a User.
	 * @param view the view.
	 */
	public void createUser(View view){
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}
}
