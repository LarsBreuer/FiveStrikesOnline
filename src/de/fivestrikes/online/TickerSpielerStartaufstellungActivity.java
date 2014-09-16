package de.fivestrikes.online;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View.OnClickListener;


public class TickerSpielerStartaufstellungActivity extends ListActivity {
    /** Called when the activity is first created. */
	public final static String ID_AUSWECHSEL_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_AUSWECHSEL_TEAM_EXTRA="de.fivestrikes.pro.team_ID";
	public final static String ID_AUSWECHSEL_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_AUSWECHSEL_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	public final static String ID_AUSWECHSEL_TICKER_EXTRA="de.fivestrikes.pro.ticker_ID";
	Cursor model=null;
	SpielerAdapter adapter=null;
	SQLHelper helper=null;
	String mannschaftId=null;
    String spielId=null;
    String aktionString=null;
    String aktionInt=null;
    String aktionTeamHeim=null;
    String spielerId=null;
    String spielerString=null;
    String spielerPosition=null;
    String zeit=null;
    String realzeit=null;
    static boolean[] checkBoxState;
    int spielerZaehler;
    int spielerCount;
    int halbzeitlaenge;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spieler);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back_ok);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackOkText);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei andren Activitys verfahren*/
        
        helper=new SQLHelper(this);
        aktionTeamHeim=getIntent().getStringExtra(TabMenuActivity.ID_AKTIONTEAMHEIM_EXTRA);
        if (aktionTeamHeim.equals("1")){
        	mannschaftId=getIntent().getStringExtra(TabMenuActivity. ID_TEAM_HEIM_EXTRA);
        } else {
        	mannschaftId=getIntent().getStringExtra(TabMenuActivity. ID_TEAM_AUSW_EXTRA);
        }
        model=helper.getAllSpieler(mannschaftId);
        startManagingCursor(model);
        adapter=new SpielerAdapter(model);
        setListAdapter(adapter);
        model.moveToFirst();
        spielerCount = model.getCount();
        checkBoxState=new boolean[spielerCount];
        spielId=getIntent().getStringExtra(TabMenuActivity .ID_SPIEL_EXTRA);
        aktionString=getIntent().getStringExtra(TabMenuActivity .ID_AKTION_EXTRA);
        aktionInt=getIntent().getStringExtra(TabMenuActivity .ID_AKTIONINT_EXTRA);
        zeit=getIntent().getStringExtra(TabMenuActivity .ID_ZEIT_EXTRA);
        realzeit=getIntent().getStringExtra(TabMenuActivity .ID_REALZEIT_EXTRA);
		Cursor cSpiel=helper.getSpielCursor(spielId);
    	cSpiel.moveToFirst();
	    halbzeitlaenge=Integer.parseInt(helper.getSpielHalbzeitlaenge(cSpiel))*60*1000;
	    cSpiel.close();
        
        customTitleText.setText(aktionString);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        Button okButton = (Button) findViewById(R.id.ok_button);
        
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /** OK-Button */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	uebertragen(spielerCount, halbzeitlaenge);
            }
        });

    }
	
	@Override
	public void onDestroy() {
	  super.onDestroy();
	    
	  helper.close();
	}
	
	class SpielerAdapter extends CursorAdapter {
		
		SpielerAdapter(Cursor c) {
			super(TickerSpielerStartaufstellungActivity.this, c);
		}
		  
		@Override
		public void bindView(View row, Context ctxt, Cursor c) {
			final int position = c.getPosition();
			final CheckBox cbListCheck;
			SpielerHolder holder=(SpielerHolder)row.getTag();
			cbListCheck = (CheckBox)row.findViewById(R.id.rowCheckbox);
		    cbListCheck.setOnClickListener(new OnClickListener() {
		    	public void onClick(View v) {
		    		if (cbListCheck.isChecked()) {
		    			checkBoxState[position]=true;
		    			spielerZaehler = spielerZaehler + 1;
		    			if(spielerZaehler == 7) {
		    				uebertragen(spielerCount, halbzeitlaenge);
		    			}
		    		} else if (!cbListCheck.isChecked()) {
		    			checkBoxState[position]=false;
		    			spielerZaehler = spielerZaehler - 1;
		    		}
		    	}
		    });
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_player_checkbox, parent, false);
			SpielerHolder holder=new SpielerHolder(row);
			row.setTag(holder);
			return(row);
		}
	}
	
	static class SpielerHolder {
	    private TextView name=null;
	    private TextView nummer=null;
	    private CheckBox cbListCheck=null;
		    
	    SpielerHolder(View row) {
	      name=(TextView)row.findViewById(R.id.rowMannschaftName);
	      nummer=(TextView)row.findViewById(R.id.rowMannschaftNummer);
	      cbListCheck = (CheckBox)row.findViewById(R.id.rowCheckbox);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	      int position = c.getPosition();
	      name.setText(helper.getSpielerName(c));
	      nummer.setText(helper.getSpielerNummer(c));
	      cbListCheck.setChecked(checkBoxState[position]);
	    }
	}
	
	public void uebertragen(int spielerCount, int halbzeitlaenge) {
    	int zeitAufstellung;
    	model.moveToFirst();
    	for(int i=0;i<spielerCount;i++){
    		if(checkBoxState[i]==true){
    			spielerString = helper.getSpielerName(model);
    			spielerId = helper.getSpielerId(model);
    			spielerPosition = helper.getSpielerPosition(model);
    			if(Integer.parseInt(zeit)<halbzeitlaenge){
    				zeitAufstellung = 0;
    			} else {
    				zeitAufstellung = halbzeitlaenge;
    			}
    			helper.insertTicker(7, getString(R.string.tickerAktionEinwechselung), Integer.parseInt(aktionTeamHeim), 
    								spielerString, Integer.parseInt(spielerId), Integer.parseInt(spielId), 
    								zeitAufstellung, realzeit);
    			if(spielerPosition.equals(getString(R.string.spielerPositionTorwart))){
        			if(Integer.parseInt(aktionTeamHeim)==1){
        				helper.updateSpielTorwartHeim(spielId, Integer.parseInt(spielerId));
        			}
        			if(Integer.parseInt(aktionTeamHeim)==0){
        				helper.updateSpielTorwartAuswaerts(spielId, Integer.parseInt(spielerId));
        			}
        		}
    		}
    		model.moveToNext();
    	}
    	finish();
	}
}
