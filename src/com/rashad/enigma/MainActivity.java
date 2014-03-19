package com.rashad.enigma;

//here we start//

import java.util.Arrays;

import com.rashad.enigma.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	/*
	 * this is the beginning
	 * Identifying variables and shit
	 */ 
   
	
	 public final static String STOCK_SYMBOL = "com.example.enigma.TODO";
	 
	 
	    // Manages key valued pairs
	 
		private SharedPreferences TODOEntered;
		
		// Table inside the scroll view that holds Stuff
		private TableLayout TableView;
		
		// Where the user enters a new to do
		private EditText editText;
		
		// Button that enters a new to do and another that
		
		Button buttonEnter;
		
		
		// deletes all to do
		
		Button buttondeleteall;
	

		
	
//-----------------------------------------------------------------------------------------------------------------------------------//		
		
		
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
/* now, I will make them initialized to work later ~ */
		
		
/* Retrieve saved entries entered by the user*/ TODOEntered = getSharedPreferences("stockList", MODE_PRIVATE);
/*the table layout inside the scroll view*/		TableView = (TableLayout) findViewById(R.id.TableView);
/*where I enter my to do*/                   	editText = (EditText) findViewById(R.id.editText);
/*this the enter button*/                       buttonEnter = (Button) findViewById(R.id.buttonEnter);
/*Delete every thing */		                    buttondeleteall = (Button) findViewById(R.id.buttondeleteall);
		



/* Add ClickListeners to the enter button */    buttonEnter.setOnClickListener(enterButtonListener);
/* Add ClickListeners to the delete button */	buttondeleteall.setOnClickListener(deleteallButtonListener);
		
/* Add saved entry to the Scroll view*/ updateSavedStockList(null);
		

		
		
	}

//---------------------------------------------------------------------------------------------------------------------------------------------//
	
	
/*the real coding starts in 3..2..1..action */	
	
	
	
	
	
	
	// Either adds a new todo or if null is entered the todo
    // list is updated with saved stocks
		private void updateSavedStockList(String newStockSymbol){
			
			// Get the saved entry
			String[] stocks = TODOEntered.getAll().keySet().toArray(new String[0]);
			
						
			// If the attribute sent to this method isn't null
			if(newStockSymbol != null){
				
				// Enter in sorted order into the array
				insertStockInScrollView(newStockSymbol, Arrays.binarySearch(stocks, newStockSymbol));
				
			} else {
				
				// Display saved  list
				for(int i = 0; i < stocks.length; ++i){
					
					insertStockInScrollView(stocks[i], i);
					
				}
				
			}
			
		}
		
	
//-------------------------------------------------------------------------------------------------------------------------------------------//
		
		
		
		
		
		
		
		
		
		
		
/*now save*/
		
		private void saveStockSymbol(String newStock){
			
			// Used to check if this is a new stock
			String isTheStockNew = TODOEntered.getString(newStock, null);
			

			SharedPreferences.Editor preferencesEditor = TODOEntered.edit();
			preferencesEditor.putString(newStock, newStock);
			preferencesEditor.apply();
			
			// If this is a new stock add its components
			if(isTheStockNew == null){
				updateSavedStockList(newStock);
			}
			
		}
	
//-----------------------------------------------------------------------------------------------------------------------------//	

		/*NOW all the magic begins abraca dabra */
		
		
		private void insertStockInScrollView(String stock, int arrayIndex){
			
			// Get the LayoutInflator 
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// Use the inflater to inflate a stock row from row.xml
			View newStockRow = inflater.inflate(R.layout.row, null);
			
			
			
			
			
			
			// Create the TextView for the ScrollView Row
			TextView newStockTextView = (TextView) newStockRow.findViewById(R.id.stockSymbolTextView);
			
			newStockTextView.setOnClickListener(TextListener);
			
			// Add the stock symbol to the TextView
			newStockTextView.setText(stock);
			
			Button DeleteButton = (Button) newStockRow.findViewById(R.id.DeleteButton);
			DeleteButton.setOnClickListener(DeleteButtonListener);
			
			
			// Add the new components for the stock to the TableLayout
			TableView.addView(newStockRow, arrayIndex);
			
		}
	
	
	
//-----------------------------------------------------------------------------------------------------------------------------------//
		
		
		
		/*what will happen when you press the enter button*/
	
		public OnClickListener enterButtonListener = new OnClickListener(){

			@Override
			public void onClick(View theView) {
				
				// If there is a stock symbol entered into the EditText
				// field
				if(editText.getText().length() > 0){
					
					// Save 
					saveStockSymbol(editText.getText().toString());
					
					editText.setText(""); // Clear EditText box
					
					// Force the keyboard to close
					InputMethodManager imm = (InputMethodManager)getSystemService(
						      Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				} else {
					
					/*why didnt U enter something faggit? */
					
					// Create an alert dialog box
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					
					// Set alert title 
					builder.setTitle(R.string.INVAILD);
					
					// Set the value for the positive reaction from the user
					// You can also set a listener to call when it is pressed
					builder.setPositiveButton(R.string.ok, null);
					
					// The message
					builder.setMessage(R.string.missing);
					
					// Create the alert dialog
					AlertDialog theAlertDialog = builder.create();
					theAlertDialog.show();
					
				}
				
			}
			
		};
	
	
	
		
		
		
	
//--------------------------------------------------------------------------------------------------------------------------------------//	
	
	/* wanna delete something? let me help */
	
	
		public OnClickListener deleteallButtonListener = new OnClickListener(){


			public void onClick(View v) {
				
				TableView.removeAllViews();

				SharedPreferences.Editor TODOEditor = TODOEntered.edit();

				TODOEditor.clear();
				TODOEditor.apply();
				
			}
			
		};

	
//------------------------------------------------------------------------------------------------------------------------------------------//	
	
	
	
	/* what about deleting specific thing?? */
	
	
		public OnClickListener DeleteButtonListener = new OnClickListener(){

			public void onClick(View v) {
				
				TableRow stockSymbolRow = (TableRow) v.getParent();
	            TextView stockTextView = (TextView) stockSymbolRow.findViewById(R.id.stockSymbolTextView);
	            String stockSymbol = stockTextView.getText().toString();
	            
	            
	            TableView.removeView(stockSymbolRow);
	            
	            SharedPreferences.Editor TODOEditor = TODOEntered.edit();

				TODOEditor.clear();
				TODOEditor.apply();
				
			}
			
		};
	
//-----------------------------------------------------------------------------------------------------------------------------------------//
	
		
		
		
		
		
		
		
		public OnClickListener TextListener = new OnClickListener(){

			public void onClick(View v) {
				TableRow stockSymbolRow = (TableRow) v.getParent();
	            TextView newStockTextView = (TextView) stockSymbolRow.findViewById(R.id.stockSymbolTextView);
	            
				
	            newStockTextView.setTextColor(Color.WHITE);
			
		}
		
		

};

		
//---------------------------------------------------------------------------------------------------------------------------------------//

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
		    	
		    	Intent myIntent = new Intent(MainActivity.this, About.class);
				MainActivity.this.startActivity(myIntent);
				
		        ;
		        return true;
		    case 2:
		        finish();
		        return true;
		    }
		    return false;
		}

}

