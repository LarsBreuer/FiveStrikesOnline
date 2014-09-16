package de.fivestrikes.online;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
//import de.fivestrikes.lite.R;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;


public class SpielEditActivity extends Activity {
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_HATEAM_EXTRA="de.fivestrikes.pro.hateam_ID";
	public final static String ID_GEGNER_EXTRA="de.fivestrikes.pro.gegener_ID";
	private static final int GET_CODE = 0;
	int spiel_ID=0;
	EditText spiel_halbzeitlaenge=null;
	SQLHelper helper=null;
	String spielId=null;
	String lastID=null;
	String haTeam=null;
	int heimID=0;
	int auswID=0;
	Date spielDatum=null;
	String strSpielDatum = null;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;
	final Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.spiel_edit);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
	    
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.spielEditTitel);
		helper=new SQLHelper(this);
		
		spiel_halbzeitlaenge=(EditText)findViewById(R.id.spielHalbzeitlaenge);
	    
	    Button save=(Button)findViewById(R.id.save);
	    Button delete=(Button)findViewById(R.id.delete);
	    Button backButton=(Button) findViewById(R.id.back_button);
	    Button tickerButton=(Button) findViewById(R.id.ticker);
	    Button btnDatum=(Button)findViewById(R.id.spielDatum);
	    Button btnHeim=(Button)findViewById(R.id.spielHeim);
	    Button btnAusw=(Button)findViewById(R.id.spielAusw);
	    
	    save.setOnClickListener(onSave);
	    delete.setOnClickListener(onDelete);
	    
	    spielId=getIntent().getStringExtra(SpielActivity.ID_EXTRA);
	    
	    if (spielId!=null) {   // Wurde ein bestehendes Spiel ausgewählt...
	    	load();
	    }
	    else {				  // ... falls kein bestehendes Spiel ausgewhält wurde
	    	
	    	// Halbzeitlänge-Textfeld füllen
	    	spiel_halbzeitlaenge.setText("30");
	    	
	    	// Aktuelles Datum auf Button schreiben
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			btnDatum.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append(".").append(month + 1).append(".")
				.append(year).append(" "));
			spielDatum=new Date(year-1900, month, day);
	    }
	    
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /* Button Datum */
        btnDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	showDialog(DATE_DIALOG_ID);
            }
        });
        
        /* Button Ticker aufrufen */
        tickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        	    if (heimID==0 || auswID==0 || spielDatum==null) {
        	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        	    	alertDialogBuilder
        	    		.setTitle(R.string.spielMsgboxTitel)
        	    		.setMessage(R.string.spielMsgboxText)
        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog,int id) {

        					}
        	    		});
        	    	AlertDialog alertDialog = alertDialogBuilder.create();
        	    	alertDialog.show();
        	    }
        	    else {
            		if (spielId!=null) {   // Wurde ein bestehendes Spiel ausgewählt...
            			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            			String strSpielDatum = dateFormat.format(spielDatum);
            			helper.updateSpiel(spielId,
            							   strSpielDatum,
            							   Integer.parseInt(spiel_halbzeitlaenge.getText().toString()),
            							   heimID,
            							   auswID);
        				helper.updateTickerSpieler(spielId);	// Spielernamen in Tickermeldungen schreiben
            			Intent newIntent = new Intent(getApplicationContext(), TickerActivity.class);
        				newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
        				startActivity(newIntent);
            		}
            		else {				  // ... falls kein bestehendes Spiel ausgewählt wurde
            			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            			String strSpielDatum = dateFormat.format(spielDatum);
            			helper.insertSpiel(strSpielDatum,
            							   Integer.parseInt(spiel_halbzeitlaenge.getText().toString()),
        	    					   	   heimID,
        	    					   	   auswID);

        				Intent newIntent = new Intent(getApplicationContext(), TickerActivity.class);
        				Cursor c=helper.getLastSpielId();
        				c.moveToFirst();
        				spielId = helper.getSpielId(c);
        				c.close();
        				/** Hinweis: Nächste Zeile kann wahrscheinlich raus, da eine neues Spiel eingerichtet wurde */ 
            			helper.updateTickerSpieler(spielId);	// Spielernamen in Tickermeldungen schreiben
        				newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
        				startActivity(newIntent);
            		}
        	    }
            }
        });
        
        /* Button Heimmannschaft aufrufen */
        btnHeim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	
            	// Abfragen, ob schon eine Aktion im Spiel eingegeben wurde
            	// Falls ja, kann die Mannschaft nicht mehr geändert werden
            	if(spielId!=null){
            		if(helper.countTickerAktionGesamt(spielId)>0){
            			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            			alertDialogBuilder
        	    			.setTitle(R.string.spielTeamauswahlMsgboxTitel)
        	    			.setMessage(R.string.spielTeamauswahlMsgboxText)
        	    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        	    				public void onClick(DialogInterface dialog,int id) {

        	    				}
        	    			});
            			AlertDialog alertDialog = alertDialogBuilder.create();
            			alertDialog.show();
            		} else {
            			haTeam="Heim";
            			Intent newIntent = new Intent(getApplicationContext(), MannschaftAuswahlActivity.class);
            			newIntent.putExtra(ID_HATEAM_EXTRA, haTeam);
            			newIntent.putExtra(ID_GEGNER_EXTRA, String.valueOf(auswID));
            			startActivityForResult(newIntent, GET_CODE);
            		}
            	} else{
        			haTeam="Heim";
        			Intent newIntent = new Intent(getApplicationContext(), MannschaftAuswahlActivity.class);
        			newIntent.putExtra(ID_HATEAM_EXTRA, haTeam);
        			newIntent.putExtra(ID_GEGNER_EXTRA, String.valueOf(auswID));
        			startActivityForResult(newIntent, GET_CODE);
            	}
            }
        });
        
        /* Button Auswärtsmannschaft aufrufen */
        btnAusw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	
            	// Abfragen, ob schon eine Aktion im Spiel eingegeben wurde
            	// Falls ja, kann die Mannschaft nicht mehr geändert werden
            	if(spielId!=null){
            		if(helper.countTickerAktionGesamt(spielId)>0){
            			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            			alertDialogBuilder
        	    			.setTitle(R.string.spielTeamauswahlMsgboxTitel)
        	    			.setMessage(R.string.spielGegnerauswahlMsgboxText)
        	    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        	    				public void onClick(DialogInterface dialog,int id) {
        	    					
        	    				}
        	    			});
            			AlertDialog alertDialog = alertDialogBuilder.create();
            			alertDialog.show();
            		} else {
            			haTeam="Auswaerts";
            			Intent newIntent = new Intent(getApplicationContext(), MannschaftAuswahlActivity.class);
            			newIntent.putExtra(ID_HATEAM_EXTRA, haTeam);
            			newIntent.putExtra(ID_GEGNER_EXTRA, String.valueOf(heimID));
            			startActivityForResult(newIntent, GET_CODE);
            		}
            	} else {
        			haTeam="Auswaerts";
        			Intent newIntent = new Intent(getApplicationContext(), MannschaftAuswahlActivity.class);
        			newIntent.putExtra(ID_HATEAM_EXTRA, haTeam);
        			newIntent.putExtra(ID_GEGNER_EXTRA, String.valueOf(heimID));
        			startActivityForResult(newIntent, GET_CODE);
            	}
            }
        });
	}

	/**
	* This method is called when the sub-activity returns the result back to the
	* main activity.
	*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
				
		if (requestCode == GET_CODE){
			if (resultCode == RESULT_OK) {
				Log.v("SpielEdit", haTeam);
				haTeam=data.getStringExtra("haTeam");
				Log.v("SpielEdit", haTeam);
				if (haTeam.equals("Heim")) {
					
					heimID=Integer.parseInt(data.getStringExtra("Mannschaft"));
					Button btnHeim=(Button)findViewById(R.id.spielHeim);
					Cursor c=helper.getTeamCursor(data.getStringExtra("Mannschaft"));
					c.moveToFirst();
					btnHeim.setText(helper.getTeamName(c));
					c.close();
					
				}
				else{
					
					auswID=Integer.parseInt(data.getStringExtra("Mannschaft"));
					Button btnAusw=(Button)findViewById(R.id.spielAusw);
					Cursor c=helper.getTeamCursor(data.getStringExtra("Mannschaft"));
					c.moveToFirst();
					btnAusw.setText(helper.getTeamName(c));
					c.close();
					
				}	
			}
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	  
	    helper.close();
	}

	private void load() {
	    
		Cursor c=helper.getSpielById(spielId);
		c.moveToFirst();    
		spiel_halbzeitlaenge.setText(helper.getSpielHalbzeitlaenge(c));
		heimID = Integer.parseInt(helper.getSpielHeim(c));
		auswID = Integer.parseInt(helper.getSpielAusw(c));	
		strSpielDatum = helper.getSpielDatum(c);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			spielDatum = sdf.parse(strSpielDatum);
			day = spielDatum.getDate();    // getDate => Day of month; getDay => day of week
			month = spielDatum.getMonth();
			year = spielDatum.getYear()+1900;
		}
		catch (ParseException e) {}
		Button btnHeim=(Button)findViewById(R.id.spielHeim);
		Button btnAusw=(Button)findViewById(R.id.spielAusw);
		Button btnDatum=(Button)findViewById(R.id.spielDatum);
		btnDatum.setText(new StringBuilder()
		// Month is 0 based, just add 1
			.append(day).append(".").append(month + 1).append(".")
			.append(year).append(" "));
		btnHeim.setText(helper.getTeamHeimNameBySpielID(c));
		btnAusw.setText(helper.getTeamAuswNameBySpielID(c));
		c.close();
	}
	
	/** 
	 * Date Picker 
	 * 				*/
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			
			// set current date to buttontext
			
			Button btnDatum=(Button)findViewById(R.id.spielDatum);
			btnDatum.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append(".").append(month + 1).append(".")
				.append(year).append(" "));
			
			spielDatum=new Date(year-1900, month, day);
		}
	};

	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
    	    if (heimID==0 || auswID==0) {
    	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    	    	alertDialogBuilder
    	    		.setTitle(R.string.spielMsgboxTitel)
    	    		.setMessage(R.string.spielMsgboxText)
    	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog,int id) {

    					}
    	    		});
    	    	AlertDialog alertDialog = alertDialogBuilder.create();
    	    	alertDialog.show();
    	    }
    	    else {
    	    	if (spielId!=null) {   // Wurde ein bestehendes Spiel ausgewählt...
    	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    		strSpielDatum = dateFormat.format(spielDatum);
    	    		helper.updateSpiel(spielId,
    	    						   strSpielDatum,
    	    						   Integer.parseInt(spiel_halbzeitlaenge.getText().toString()),
    	    						   heimID,
    	    						   auswID);
    	    		finish();
    	    	}
    	    	else {				  // ... falls kein bestehendes Spiel ausgewhält wurde
    	    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	    		strSpielDatum = dateFormat.format(spielDatum);
    	    		helper.insertSpiel(strSpielDatum,
    	    						   Integer.parseInt(spiel_halbzeitlaenge.getText().toString()),
    	    						   heimID,
    	    						   auswID);
    	    		finish();
    	    	}
    	    }

		}
	};
	
	private View.OnClickListener onDelete=new View.OnClickListener() {
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(SpielEditActivity.this);
			builder
			.setTitle(R.string.spielDelMsgboxTitel)
			.setMessage(R.string.spielDelMsgboxText)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {			      	
					if (spielId!=null) {
						helper.deleteSpiel(spielId);
					}
					finish();
				}
			})
			.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {			      	
					
				}
			})
			.show();
		}
	};
}