package de.fivestrikes.online;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Environment;


public class MannschaftEditActivity extends Activity {
	public final static String ID_MANNSCHAFT_EXTRA="de.fivestrikes.pro.mannschaft_ID";
	int mannschaft_ID=0;
	EditText mannschaft_name=null;
	EditText mannschaft_kuerzel=null;
	SQLHelper helper=null;
	String mannschaftId=null;
	String lastID=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.mannschaft_edit);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
	    
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.mannschaftEditTitel);
		
		helper=new SQLHelper(this);
		
	    mannschaft_name=(EditText)findViewById(R.id.mannschaftName);
	    mannschaft_kuerzel=(EditText)findViewById(R.id.mannschaftKuerzel);
	    
	    Button save=(Button)findViewById(R.id.save);
	    Button delete=(Button)findViewById(R.id.delete);
	    Button backButton = (Button) findViewById(R.id.back_button);
	    Button spielerButton = (Button) findViewById(R.id.spieler);
	    Button spielerImportButton = (Button) findViewById(R.id.spielerimport);
	    Button spielerInfoButton = (Button) findViewById(R.id.spielerimportinfo);
	    Button okButton = (Button) findViewById(R.id.okay);
	    
	    save.setOnClickListener(onSave);
	    delete.setOnClickListener(onDelete);
	    
	    mannschaftId=getIntent().getStringExtra(MannschaftActivity.ID_EXTRA);
	    
	    if (mannschaftId!=null) {   // Wurde eine Mannschaft ausgewählt...
	    	load();
	    }
	    else {						// ... oder neue eingefügt?
	    	Cursor c=helper.getLastTeamId();
	    	if (c.moveToFirst()!=false) {		// Bereits Mannschaften vorhanden...
		    	c.moveToFirst();
		    	lastID = String.valueOf(Integer.parseInt(helper.getTeamId(c)) + 1);
	        }
	        else {								// ... oder erste Mannschaft?
	        	lastID = "1";
	        }
	    	c.close();
	    	helper.insertTeam("Mannschaft " + lastID,
      			  		  "M" + lastID, "");
	        mannschaft_name.setText("Mannschaft " + lastID);
			mannschaft_kuerzel.setText("M" + lastID);
			
			Cursor newC=helper.getLastTeamId();
			newC.moveToFirst();
			mannschaftId = helper.getTeamId(newC);
			newC.close();
		}
	    
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /* Okay Button */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); 
            	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        
        /* Button Spieler verwalten */
        spielerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	helper.updateTeam(mannschaftId,
	        			  	  mannschaft_name.getText().toString(),
	        			  	  mannschaft_kuerzel.getText().toString());
				Intent newIntent = new Intent(getApplicationContext(), SpielerActivity.class);
				newIntent.putExtra(ID_MANNSCHAFT_EXTRA, mannschaftId);
				startActivity(newIntent);
            }
        });
        
        /* Button Spieler importieren */
        spielerImportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	try{
            		File sdcard = Environment.getExternalStorageDirectory();
            		File file = new File(sdcard,"Download/Player.csv");
            		BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    String number;
                    String name;
                    String position;
                    while ((line = reader.readLine()) != null) {
                         String[] RowData = line.split(";");
                         number = RowData[0];
                         name = RowData[1];
                         position = RowData[2];
                 		if (position.equals(getString(R.string.spielerPositionTorwartKurz))) {
                			position = getString(R.string.spielerPositionTorwart);
                		}
                		if (position.equals(getString(R.string.spielerPositionLinksaussenKurz))) {
                			position = getString(R.string.spielerPositionLinksaussen);
                		}
                		if (position.equals(getString(R.string.spielerPositionRueckraumLinksKurz))) {
                			position = getString(R.string.spielerPositionRueckraumLinks);
                		}
                		if (position.equals(getString(R.string.spielerPositionRueckraumMitteKurz))) {
                			position = getString(R.string.spielerPositionRueckraumMitte);
                		}
                		if (position.equals(getString(R.string.spielerPositionRueckraumRechtsKurz))) {
                			position = getString(R.string.spielerPositionRueckraumRechts);
                		}
                		if (position.equals(getString(R.string.spielerPositionRechtsaussenKurz))) {
                			position = getString(R.string.spielerPositionRechtsaussen);
                		}
                		if (position.equals(getString(R.string.spielerPositionKreislaeuferKurz))) {
                			position = getString(R.string.spielerPositionKreislaeufer);
                		}
                         helper.insertSpieler(name, number, mannschaftId, position, "");
                    }
        			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftEditActivity.this);
        			alertDialogBuilder
    	    			.setTitle(R.string.teamEditFertigMsgboxTitel)
    	    			.setMessage(R.string.teamEditFertigMsgboxText)
    	    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
    	    				public void onClick(DialogInterface dialog,int id) {
    	    					
    	    				}
    	    			});
        			AlertDialog alertDialog = alertDialogBuilder.create();
        			alertDialog.show();
            	}
            	catch (IOException e) {
            		e.printStackTrace();
        			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftEditActivity.this);
        			alertDialogBuilder
    	    			.setTitle(R.string.teamEditFehlerMsgboxTitel)
    	    			.setMessage(R.string.teamEditFehlerMsgboxText)
    	    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
    	    				public void onClick(DialogInterface dialog,int id) {
    	    					
    	    				}
    	    			});
        			AlertDialog alertDialog = alertDialogBuilder.create();
        			alertDialog.show();
            	}

            }
        });
        
        /* Button Import Info */
        spielerInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftEditActivity.this);
    			alertDialogBuilder
	    			.setTitle(R.string.teamEditInfoMsgboxTitel)
	    			.setMessage(R.string.teamEditInfoMsgboxText)
	    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
	    					
	    				}
	    			});
    			AlertDialog alertDialog = alertDialogBuilder.create();
    			alertDialog.show();
            }
        });
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	  
	    helper.close();
	}

	private void load() {
		Cursor c=helper.getTeamById(mannschaftId);

		c.moveToFirst();    
		mannschaft_name.setText(helper.getTeamName(c));
		mannschaft_kuerzel.setText(helper.getTeamKuerzel(c));
		    
		c.close();
	}

	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
			
		    helper.updateTeam(mannschaftId,
		        		  mannschaft_name.getText().toString(),
	                      mannschaft_kuerzel.getText().toString());

			finish();
		}
	};
	
	private View.OnClickListener onDelete=new View.OnClickListener() {
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(MannschaftEditActivity.this);
			builder
			.setTitle(R.string.mannschaftMsgboxTitel)
			.setMessage(R.string.mannschaftMsgboxText)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {			      	
					
					if(helper.countSpielTeamId(mannschaftId)>0){	// Existiert noch ein Spiel mit dieser Mannschaft?
	        	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftEditActivity.this);
	        	    	alertDialogBuilder
	        	    		.setTitle(R.string.mannschaftDelMsgboxTitel)
	        	    		.setMessage(R.string.mannschaftDelMsgboxText)
	        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	        					public void onClick(DialogInterface dialog,int id) {

	        					}
	        	    		});
	        	    	AlertDialog alertDialog = alertDialogBuilder.create();
	        	    	alertDialog.show();
					}
					else{
						helper.deleteTeam(mannschaftId,
		      			  		  		  mannschaft_name.getText().toString(),
		      			  		  		  mannschaft_kuerzel.getText().toString());
						finish();
					}
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