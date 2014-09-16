package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;


public class TickerEditActivity extends Activity {
	public final static String ID_TICKEREDITTEAM_EXTRA="de.fivestrikes.pro.tickereditteam_ID";
	public final static String ID_TICKEREDITWURFECKE_EXTRA="de.fivestrikes.pro.tickereditwurfecke_ID";
	public final static String ID_TICKEREDITPOSITION_EXTRA="de.fivestrikes.pro.tickereditposition_ID";
	SQLHelper helper=null;
	String tickerId=null;
	String spielId=null;
	String tickerZeitString=null;
	String tickerAktionString=null;
	String tickerAktionId=null;
	String tickerSpielerString=null;
	String tickerSpielerId=null;
	String tickerWurfecke=null;
	String tickerPosition=null;
	int aktionAnfangID=0;
	private long tickerZeitLng=0;
	final Context context = this;
	private static final int GET_CODE = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ticker_edit);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
	    getWindow().setWindowAnimations(0);
	    
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.tickerEditTitel);
		
		helper=new SQLHelper(this);
		
	    Button save=(Button)findViewById(R.id.save);
	    Button delete=(Button)findViewById(R.id.delete);
	    Button backButton = (Button) findViewById(R.id.back_button);
	    Button btnZeit = (Button) findViewById(R.id.btnEditSpielzeit);
	    Button btnAktion = (Button) findViewById(R.id.btnEditAktion);
	    Button btnSpieler = (Button) findViewById(R.id.btnEditSpieler);
	    Button btnWurfecke = (Button) findViewById(R.id.btnEditWurfecke);
	    Button btnWurfposition = (Button) findViewById(R.id.btnEditWurfposition);
	    
	    tickerId=getIntent().getStringExtra(TabListActivity.ID_TICKER_EXTRA);
	    spielId=getIntent().getStringExtra(TabListActivity.ID_SPIEL_EXTRA);
	    
	    save.setOnClickListener(onSave);
	    delete.setOnClickListener(onDelete);
	    
        Cursor c=helper.getTickerCursor(tickerId);
		c.moveToFirst();
		tickerZeitLng=Integer.parseInt(helper.getTickerZeitInt(c));
		tickerZeitString=helper.updateTimer(tickerZeitLng);
		((TextView)findViewById(R.id.btnEditSpielzeit)).setText(tickerZeitString);
		tickerAktionString=helper.getTickerAktion(c);
		tickerAktionId=helper.getTickerAktionInt(c);
		((TextView)findViewById(R.id.btnEditAktion)).setText(tickerAktionString);
		tickerSpielerString=helper.getTickerSpieler(c);
		tickerSpielerId=helper.getTickerSpielerId(c);
		((TextView)findViewById(R.id.btnEditSpieler)).setText(tickerSpielerString);
		aktionAnfangID=Integer.parseInt(helper.getTickerAktionInt(c));
		tickerWurfecke=helper.getTickerWurfecke(c);
		tickerPosition=helper.getTickerPosition(c);
		if(tickerWurfecke!=null){
			btnWurfeckeBeschriften();
			btnWurfposition.setText(R.string.tickerEditWurfpositionWaehlen);
		} else {
			tickerWurfecke="";
		}
		if(tickerPosition==null){
			tickerPosition="";
		}
	    c.close();
	    
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /* Button Spielzeit */
        btnZeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            	// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.ticker_set_watch, null);
            	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText editMinutes = (EditText) promptsView.findViewById(R.id.setStopWatchMinutes);
				final EditText editSeconds = (EditText) promptsView.findViewById(R.id.setStopWatchSeconds);

				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton(R.string.tickerMSGBoxEinstellen,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										int min=0;
										int sec=0;
										if(editMinutes.getText().length() != 0){
											min=Integer.parseInt(editMinutes.getText().toString());
										}
										else{
											min=0;
										}
										if(editSeconds.getText().length() != 0){
											sec=Integer.parseInt(editSeconds.getText().toString());
										}
										else{
											sec=0;
										}
										if (sec>59) {
											sec=59;
										}
										tickerZeitLng=(min*60000)+(sec*1000);
										tickerZeitString=helper.updateTimer(tickerZeitLng);
										((TextView)findViewById(R.id.btnEditSpielzeit)).setText(tickerZeitString);
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
            	
            }
            
        });
        
        /* Button Aktion */
        btnAktion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Cursor c=helper.getTickerCursor(tickerId);
            	c.moveToFirst();
            	if(Integer.parseInt(helper.getTickerAktionInt(c))==1 || 
            			Integer.parseInt(helper.getTickerAktionInt(c))==0){
        	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        	    	alertDialogBuilder
        	    		.setTitle(R.string.tickerEditMsgboxTitel)
        	    		.setMessage(R.string.tickerEditMsgboxText)
        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog,int id) {

        					}
        	    		});
        	    	AlertDialog alertDialog = alertDialogBuilder.create();
        	    	alertDialog.show();
            	}
            	else{
            		Intent newIntent = new Intent(getApplicationContext(), TickerEditAktionActivity.class);
    				startActivityForResult(newIntent, GET_CODE);
            	}
				c.close();
            }
        });
        
        /* Button Spieler */
        btnSpieler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent newIntent = new Intent(getApplicationContext(), TickerEditSpielerActivity.class);
				Cursor cTicker=helper.getTickerCursor(tickerId);
				Cursor cSpiel=helper.getSpielCursor(spielId);
				cTicker.moveToFirst();
				cSpiel.moveToFirst();
            	if(Integer.parseInt(helper.getTickerAktionInt(cTicker))==1 || 
            			Integer.parseInt(helper.getTickerAktionInt(cTicker))==0){
        	    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        	    	alertDialogBuilder
        	    		.setTitle(R.string.tickerEditMsgboxTitel)
        	    		.setMessage(R.string.tickerEditMsgboxText)
        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog,int id) {

        					}
        	    		});
        	    	AlertDialog alertDialog = alertDialogBuilder.create();
        	    	alertDialog.show();
            	}
            	else{
            		if(Integer.parseInt(helper.getTickerAktionTeamHeim(cTicker))==1){
            			newIntent.putExtra(ID_TICKEREDITTEAM_EXTRA, helper.getSpielHeim(cSpiel));
            		}
            		else{
            			newIntent.putExtra(ID_TICKEREDITTEAM_EXTRA, helper.getSpielAusw(cSpiel));
            		}
    				startActivityForResult(newIntent, GET_CODE);
            	}
				cTicker.close();
				cSpiel.close();
            }
        });
        
        /* Button Wurfecke */
        btnWurfecke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent newIntent = new Intent(getApplicationContext(), TickerEditWurfeckeActivity.class);
				Cursor cTicker=helper.getTickerCursor(tickerId);
				cTicker.moveToFirst();
            	if(Integer.parseInt(tickerAktionId)==1 || 
            			Integer.parseInt(tickerAktionId)==0 || 
            			Integer.parseInt(tickerAktionId)==4 || 
            			Integer.parseInt(tickerAktionId)==5 || 
            			Integer.parseInt(tickerAktionId)==6 || 
            			Integer.parseInt(tickerAktionId)==7 || 
            			Integer.parseInt(tickerAktionId)==8 || 
            			Integer.parseInt(tickerAktionId)==9 || 
            			Integer.parseInt(tickerAktionId)==11 || 
            			Integer.parseInt(tickerAktionId)==24){
            		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        	    	alertDialogBuilder
        	    		.setTitle(R.string.tickerEditWurfeckeMsgboxTitel)
        	    		.setMessage(R.string.tickerEditWurfeckeMsgboxText)
        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        	    			public void onClick(DialogInterface dialog,int id) {

        	    			}
        	    		});
        	    	AlertDialog alertDialog = alertDialogBuilder.create();
        	    	alertDialog.show();
            	}
            	else{
            		newIntent.putExtra(ID_TICKEREDITWURFECKE_EXTRA, tickerWurfecke);
    				startActivityForResult(newIntent, GET_CODE);
            	}
				cTicker.close();
            }
        });
        
        /* Button Wurfposition */
        btnWurfposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent newIntent = new Intent(getApplicationContext(), TickerEditWurfpositionActivity.class);
				Cursor cTicker=helper.getTickerCursor(tickerId);
				cTicker.moveToFirst();
            	if(Integer.parseInt(tickerAktionId)==1 || 
            			Integer.parseInt(tickerAktionId)==0 || 
            			Integer.parseInt(tickerAktionId)==4 || 
            			Integer.parseInt(tickerAktionId)==5 || 
            			Integer.parseInt(tickerAktionId)==6 || 
            			Integer.parseInt(tickerAktionId)==7 || 
            			Integer.parseInt(tickerAktionId)==8 || 
            			Integer.parseInt(tickerAktionId)==9 || 
            			Integer.parseInt(tickerAktionId)==11 || 
            			Integer.parseInt(tickerAktionId)==24){
            		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        	    	alertDialogBuilder
        	    		.setTitle(R.string.tickerEditWurfeckeMsgboxTitel)
        	    		.setMessage(R.string.tickerEditWurfeckeMsgboxText)
        	    		.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        	    			public void onClick(DialogInterface dialog,int id) {

        	    			}
        	    		});
        	    	AlertDialog alertDialog = alertDialogBuilder.create();
        	    	alertDialog.show();
            	}
            	else{
            		newIntent.putExtra(ID_TICKEREDITPOSITION_EXTRA, tickerPosition);
    				startActivityForResult(newIntent, GET_CODE);
            	}
				cTicker.close();
            }
        });
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	  
	    helper.close();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
				
		if (requestCode == GET_CODE){
			if (resultCode == RESULT_OK) {
				String strActivity=data.getStringExtra("Activity");
				if(strActivity.equals("Aktion")){
					tickerAktionString=data.getStringExtra("AktionName");
					tickerAktionId=data.getStringExtra("AktionId");
					((TextView)findViewById(R.id.btnEditAktion)).setText(tickerAktionString);
				}
				if(strActivity.equals("Spieler")){
					tickerSpielerString=data.getStringExtra("spielerName");
					tickerSpielerId=data.getStringExtra("spielerId");
					((TextView)findViewById(R.id.btnEditSpieler)).setText(tickerSpielerString);
				}
				if(strActivity.equals("Wurfecke")){
					tickerWurfecke=data.getStringExtra("Wurfecke");
					btnWurfeckeBeschriften();
				}
				if(strActivity.equals("Wurfposition")){
					tickerPosition=data.getStringExtra("Wurfposition");
				}
			}
		}
	}
	
	private View.OnClickListener onSave=new View.OnClickListener() {
		public void onClick(View v) {
			
		    helper.updateTickerEdit(spielId, tickerId, tickerZeitString, (int) tickerZeitLng, tickerAktionString, tickerAktionId, tickerSpielerString, 
		    						tickerSpielerId, tickerWurfecke, tickerPosition);
		    // TODO: Brauche ich Long bei tickerZeit?
		    
		    /* Beschriftung der Ticker ändern, falls Aktion Ticker vor oder nach der Änderung ein Tor war */
		    if(aktionAnfangID==2 || Integer.parseInt(tickerAktionId)==2 || 
		    		aktionAnfangID==14 || Integer.parseInt(tickerAktionId)==14 || 
		    		aktionAnfangID==20 || Integer.parseInt(tickerAktionId)==20){
    			String[] args={spielId};
    			SQLiteDatabase db=helper.getWritableDatabase();
    			Cursor cTicker=db.rawQuery("SELECT * FROM ticker WHERE spielID=? ORDER BY zeitInteger ASC", args);
    	    	cTicker.moveToFirst();
    	    	int toreHeim=0;
    	    	int toreAusw=0;
    	    	String strErgebnis=null;
    	    	for (cTicker.moveToFirst(); !cTicker.isAfterLast(); cTicker.moveToNext()) {
    	    		if(Integer.parseInt(helper.getTickerAktionInt(cTicker))==2 || 
    	    				Integer.parseInt(helper.getTickerAktionInt(cTicker))==14 || 
    	    				Integer.parseInt(helper.getTickerAktionInt(cTicker))==20){
    	    			if(Integer.parseInt(helper.getTickerAktionTeamHeim(cTicker))==1){
    	    				toreHeim=toreHeim+1;
    	    			}
    	    			if(Integer.parseInt(helper.getTickerAktionTeamHeim(cTicker))==0){
    	    				toreAusw=toreAusw+1;
    	    			}
    	    		}
    	    		if(Integer.parseInt(helper.getTickerZeitInt(cTicker))>=(int) tickerZeitLng){	// Wenn die Zeit der Schleifeneintrags größer ist als der aktuelle Eintrag, dann hat sich die Torfolge geändert und der Eintrag muss geändert werden
    	    			strErgebnis=String.valueOf(toreHeim)+":"+String.valueOf(toreAusw);
    	    			helper.updateTickerErgebnis(helper.getTickerId(cTicker), toreHeim, toreAusw, strErgebnis);
    	    		}
    	    	}
    	    	cTicker.close();
    	    	// TODO: Eventuell in eigene Funktion ausgliedern
		    }
		    Intent i=new Intent();
		    setResult(RESULT_OK, i);
			finish();
		}
	};
	
	private View.OnClickListener onDelete=new View.OnClickListener() {
		public void onClick(View v) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(TickerEditActivity.this);
			builder
			.setTitle(R.string.tickerDelMsgboxTitel)
			.setMessage(R.string.tickerDelMsgboxText)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {			      	
					helper.deleteTicker(spielId, tickerId);
					
				    /* Beschriftung der Ticker ändern, falls Aktion Ticker vor oder nach der Änderung ein Tor war */
				    if(aktionAnfangID==2 || Integer.parseInt(tickerAktionId)==2 ||
				    		aktionAnfangID==14 || Integer.parseInt(tickerAktionId)==14 || 
				    		aktionAnfangID==20 || Integer.parseInt(tickerAktionId)==20){
		    			String[] args={spielId};
		    			SQLiteDatabase db=helper.getWritableDatabase();
		    			Cursor cTicker=db.rawQuery("SELECT * FROM ticker WHERE spielID=? ORDER BY zeitInteger ASC", args);
		    	    	cTicker.moveToFirst();
		    	    	int toreHeim=0;
		    	    	int toreAusw=0;
		    	    	String strErgebnis=null;
		    	    	for (cTicker.moveToFirst(); !cTicker.isAfterLast(); cTicker.moveToNext()) {
		    	    		if(Integer.parseInt(helper.getTickerAktionInt(cTicker))==2 || 
		    	    				Integer.parseInt(helper.getTickerAktionInt(cTicker))==14 || 
		    	    				Integer.parseInt(helper.getTickerAktionInt(cTicker))==20){
		    	    			if(Integer.parseInt(helper.getTickerAktionTeamHeim(cTicker))==1){
		    	    				toreHeim=toreHeim+1;
		    	    			}
		    	    			if(Integer.parseInt(helper.getTickerAktionTeamHeim(cTicker))==0){
		    	    				toreAusw=toreAusw+1;
		    	    			}
		    	    		}
		    				strErgebnis=String.valueOf(toreHeim)+":"+String.valueOf(toreAusw);
		    				helper.updateTickerErgebnis(helper.getTickerId(cTicker), toreHeim, toreAusw, strErgebnis);
		    	    	}
		    	    	cTicker.close();
		    	    	/** Hinweis: Eventuell in eigene Funktion ausgliedern */
				    }
				    
				    Intent i=new Intent();
				    setResult(RESULT_OK, i);
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
	
	public void btnWurfeckeBeschriften() {

		if(tickerWurfecke.equals("OOLL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOOLL);
		}
		if(tickerWurfecke.equals("OOL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOOL);
		}
		if(tickerWurfecke.equals("OOM")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOOM);
		}
		if(tickerWurfecke.equals("OOR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOOR);
		}
		if(tickerWurfecke.equals("OORR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOORR);
		}
		if(tickerWurfecke.equals("OLL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOLL);
		}
		if(tickerWurfecke.equals("OL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOL);
		}
		if(tickerWurfecke.equals("OM")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOM);
		}
		if(tickerWurfecke.equals("OR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeOR);
		}
		if(tickerWurfecke.equals("ORR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeORR);
		}
		if(tickerWurfecke.equals("MLL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeMLL);
		}
		if(tickerWurfecke.equals("ML")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeML);
		}
		if(tickerWurfecke.equals("MM")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeMM);
		}
		if(tickerWurfecke.equals("MR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeMR);
		}
		if(tickerWurfecke.equals("MRR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeMRR);
		}
		if(tickerWurfecke.equals("ULL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeULL);
		}
		if(tickerWurfecke.equals("UL")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeUL);
		}
		if(tickerWurfecke.equals("UM")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeUM);
		}
		if(tickerWurfecke.equals("UR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeUR);
		}
		if(tickerWurfecke.equals("URR")){
			((Button)findViewById(R.id.btnEditWurfecke)).setText(R.string.wurfeckeURR);
		}
	}
}