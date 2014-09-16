package de.fivestrikes.online;

import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;


public class TickerSpielerAuswechselungActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	Cursor model=null;
	SpielerAdapter adapter=null;
	SQLHelper helper=null;
	String mannschaftId=null;
    String spielId=null;
    String aktionTeamHeim=null;
    String spielerId=null;
    String spielerString=null;
    String zeit=null;
    String realzeit=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spieler);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back_ok);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackOkText);
        customTitleText.setText(R.string.tickerAktionAuswechselung);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei andren Activitys verfahren*/
        
        helper=new SQLHelper(this);
        mannschaftId=getIntent().getStringExtra(TickerSpielerActivity.ID_AUSWECHSEL_TEAM_EXTRA);
        model=helper.getAllSpieler(mannschaftId);
        startManagingCursor(model);
        adapter=new SpielerAdapter(model);
        setListAdapter(adapter);
        spielId=getIntent().getStringExtra(TickerSpielerActivity.ID_AUSWECHSEL_SPIEL_EXTRA);
        aktionTeamHeim=getIntent().getStringExtra(TickerSpielerActivity.ID_AUSWECHSEL_AKTIONTEAMHEIM_EXTRA);
        zeit=getIntent().getStringExtra(TickerSpielerActivity.ID_AUSWECHSEL_ZEIT_EXTRA);
        realzeit=getIntent().getStringExtra(TickerSpielerActivity.ID_AUSWECHSEL_REALZEIT_EXTRA);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        Button okButton = (Button) findViewById(R.id.ok_button);
        
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
        
        /** Button ok */
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
    }
	
	@Override
	public void onDestroy() {
	  super.onDestroy();
	    
	  helper.close();
	}
	
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		
		/** Spielernamen anhand Spieler ID erhalten und neuen Ticker einfügen */
		
		spielerId=String.valueOf(id);
		Cursor c=helper.getSpielerById(spielerId);
		c.moveToFirst();    
		spielerString=helper.getSpielerName(c);
		c.close();
		helper.insertTicker(8, getString(R.string.tickerAktionAuswechselung), Integer.parseInt(aktionTeamHeim), spielerString, 
				Integer.parseInt(spielerId), Integer.parseInt(spielId), Integer.parseInt(zeit) + 1, realzeit);
		finish();
	}
	
	class SpielerAdapter extends CursorAdapter {
		SpielerAdapter(Cursor c) {
			super(TickerSpielerAuswechselungActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			SpielerHolder holder=(SpielerHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_player, parent, false);
			SpielerHolder holder=new SpielerHolder(row);

			row.setTag(holder);

			return(row);
		}
	}
	
	static class SpielerHolder {
	    private TextView name=null;
	    private TextView nummer=null;
	    
	    SpielerHolder(View row) {
	      name=(TextView)row.findViewById(R.id.rowMannschaftName);
	      nummer=(TextView)row.findViewById(R.id.rowMannschaftNummer);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	      name.setText(helper.getSpielerName(c));
	      nummer.setText(helper.getSpielerNummer(c));
	  
	    }
	}
}
