package team.csc207.androidapplication;

import java.io.FileNotFoundException;
import java.io.IOException;

import csc207project.Main;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UploadInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_info);
	}
	
	/**
	 * Uploads flight information.
	 * @param view the view
	 */
	public void UploadFlightInfo(View view) {
		EditText directoryText = (EditText) findViewById(R.id.directory);
		String directory = directoryText.getText().toString();
		
		try {
			Main.uploadFlightInfo(directory);
			Toast.makeText(getBaseContext(), 
					"Flight Information Uploaded Successfully", 
					Toast.LENGTH_LONG).show();
			Main.saveInfo();
		} catch (FileNotFoundException e) {
			Toast.makeText(getBaseContext(), "Flight File Does Not Exist", 
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Uploads User information.
	 * @param view the view.
	 */
	public void UploadUserInfo(View view) {
		EditText directoryText = (EditText) findViewById(R.id.directory);
		String directory = directoryText.getText().toString();
		
		try {
			Main.uploadUserInfo(directory);
			Toast.makeText(getBaseContext(), 
					"User Information Uploaded Successfully", 
					Toast.LENGTH_LONG).show();
			Main.saveInfo();
		} catch (IOException e) {
			Toast.makeText(getBaseContext(), "User File Does Not Exist", 
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
	}
}
