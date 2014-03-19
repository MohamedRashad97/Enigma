package com.rashad.enigma;

import com.rashad.enigma.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class About extends Activity {

	Button xda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		

		xda = (Button) findViewById(R.id.xda);
		
		xda.setOnClickListener(xdaListener);
		
}
	
	public OnClickListener xdaListener = new OnClickListener(){


		public void onClick(View v) {
			
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forum.xda-developers.com/showthread.php?t=2658056"));
			startActivity(browserIntent);
		}
		
	};
	




@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main, menu);
	
	 menu.add(0, 1, 0, "About Enigma");
	 menu.add(0, 2, 1, "Quit");
	 
	return true;
}
	

	 public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    case 1:
		    	
		    	Intent myIntent = new Intent(About.this, MainActivity.class);
				About.this.startActivity(myIntent);
				
		        ;
		        return true;
		    case 2:
		        finish();
		        return true;
		    }
		    return false;
		}

}
