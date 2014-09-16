//Neu=> Neue Activity

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


public class MannschaftServerSearchActivity extends Activity {
	public final static String ID_MANNSCHAFT_EXTRA="de.fivestrikes.pro.mannschaft_ID";
	public final static String TEAM_NAME="de.fivestrikes.online.team_name";
	int mannschaft_ID=0;
	EditText mannschaft_name=null;
	EditText mannschaft_kuerzel=null;
	SQLHelper helper=null;
	String mannschaftId=null;
	String lastID=null;
	EditText edtTeamName=null;
	String strTeamName=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.mannschaft_server);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
	    
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.mannschaftEditTitel);
		
		helper=new SQLHelper(this);
		
		edtTeamName=(EditText)findViewById(R.id.mannschaftName);
	    
	    Button backButton = (Button) findViewById(R.id.back_button);
	    Button btn_serverYes = (Button) findViewById(R.id.serverYes);
	    Button btn_serverNo = (Button) findViewById(R.id.serverNo);
	    
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /* Okay Button */
        btn_serverYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	strTeamName=edtTeamName.getText().toString();
            	Intent in = new Intent(MannschaftServerSearchActivity.this, MannschaftServerActivity.class);
            	in.putExtra(TEAM_NAME, strTeamName);
                startActivity(in);
            }
        });
        
        /* Button Spieler verwalten */
        btn_serverNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
			
			AlertDialog.Builder builder = new AlertDialog.Builder(MannschaftServerSearchActivity.this);
			builder
			.setTitle(R.string.mannschaftMsgboxTitel)
			.setMessage(R.string.mannschaftMsgboxText)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {			      	
					
					if(helper.countSpielTeamId(mannschaftId)>0){	// Existiert noch ein Spiel mit dieser Mannschaft?
	        	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftServerSearchActivity.this);
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