package de.fivestrikes.online;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.view.Window;
import android.net.Uri;
import android.database.sqlite.SQLiteDatabase;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

public class FiveStrikesProActivity extends Activity {
    /** Called when the activity is first created. */
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	Cursor model=null;
	SQLHelper helper=null;
	String spielId=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_main);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleMainText);
        customTitleText.setText(R.string.uebersichtTitel);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        
        /**
		 * On Click event for Single Gridview Item
		 * */
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				
				switch(position) {
        			case 0:
        				startActivity(new Intent(getApplicationContext(), MannschaftActivity.class));
        				break;
        			case 1:
        				startActivity(new Intent(getApplicationContext(), SpielActivity.class));
        				break;
        			case 2:
        				startActivity(new Intent(getApplicationContext(), StatActivity.class));
        				break;
        			case 3:
        				AlertDialog.Builder builder = new AlertDialog.Builder(FiveStrikesProActivity.this);
        				builder
        				.setTitle(R.string.infoMsgboxTitel)
        				.setMessage(R.string.infoMsgboxText)
        				.setIcon(android.R.drawable.ic_dialog_alert)
        				.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int which) {			      	
        				        
        						Uri uri = Uri.parse( "http://www.fivestrikes.de/FiveStrikes_Handbuch.pdf" );
        						startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
        						
        					}
        				})
        				.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int which) {			      	
        						
        					}
        				})
        				.show();
        				break;
        			case 4:
        				helper=new SQLHelper(FiveStrikesProActivity.this);
        				helper.createSchnellesSpiel(FiveStrikesProActivity.this);
        				Intent newIntent = new Intent(getApplicationContext(), TickerActivity.class);
        				Cursor c=helper.getLastSpielId();
        				c.moveToFirst();
        				spielId = helper.getSpielId(c);
        				c.close();
        				newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
        				startActivity(newIntent);
        				break;
        			case 5:
        				startActivity(new Intent(getApplicationContext(), ImportExportActivity.class));
        				break;
					}
			}
		});
    }
    
	@Override
	public void onDestroy() {
	  super.onDestroy();
	  
	  helper.close();
	}
}
