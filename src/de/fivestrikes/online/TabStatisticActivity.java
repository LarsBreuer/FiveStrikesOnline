package de.fivestrikes.online;

import android.app.ListActivity;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TabStatisticActivity extends ListActivity {
	Cursor model=null;
	StatTickerToreAdapter adapter=null;
	SQLHelper helper=null;
	String spielId=null;
	String teamHeimId=null;
	String teamAuswId=null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("TabStatisticActivity", "onCreate");
        setContentView(R.layout.tab_list);
 
        spielId=getIntent().getStringExtra(TickerActivity.ID_SPIEL_EXTRA);
        helper=new SQLHelper(this);  
        Cursor c=helper.getSpielById(spielId);
		c.moveToFirst();    
		teamHeimId = helper.getSpielHeim(c);
		teamAuswId = helper.getSpielAusw(c);
		c.close();     
        helper.createStatTickerTore(spielId, teamHeimId, teamAuswId, this);
        
        model=helper.getAllStatTickerTore();
        startManagingCursor(model);
        adapter=new StatTickerToreAdapter(model);
        setListAdapter(adapter);
    }
    
	@Override
	public void onDestroy() {
	  super.onDestroy();
	  
	  helper.close();
	}
	
	class StatTickerToreAdapter extends CursorAdapter {
		StatTickerToreAdapter(Cursor c) {
			super(TabStatisticActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			StatTorHolder holder=(StatTorHolder)row.getTag();
			Resources res = ctxt.getResources();
			      
			holder.populateFrom(c, helper);
			TextView spieler=(TextView)row.findViewById(R.id.rowTickerTorSpieler);
			TextView team=(TextView)row.findViewById(R.id.rowTickerTorTeam);
			TextView tore=(TextView)row.findViewById(R.id.rowTickerTorTore);
			TextView chancen=(TextView)row.findViewById(R.id. rowTickerTorChancen);
			TextView quote=(TextView)row.findViewById(R.id. rowTickerTorProzent);
			if(helper.getStatTickerTorTore(c).equals(res.getString(R.string.statBezeichnungToreKurz))){
				row.setBackgroundColor(getResources().getColor(R.color.standardfarbe));
				spieler.setTextColor(Color.WHITE);
				team.setTextColor(Color.WHITE);
				tore.setTextColor(Color.WHITE);
				chancen.setTextColor(Color.WHITE);
				quote.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundColor(getResources().getColor(R.color.transparent));
				spieler.setTextColor(getResources().getColor(R.color.standardfarbe));
				team.setTextColor(getResources().getColor(R.color.standardfarbe));
				tore.setTextColor(getResources().getColor(R.color.standardfarbe));
				chancen.setTextColor(getResources().getColor(R.color.standardfarbe));
				quote.setTextColor(getResources().getColor(R.color.standardfarbe));
			}
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {			
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_ticker_tor, parent, false);
			StatTorHolder holder=new StatTorHolder(row);
			Resources res = ctxt.getResources();

			row.setTag(holder);
			TextView spieler=(TextView)row.findViewById(R.id.rowTickerTorSpieler);
			TextView team=(TextView)row.findViewById(R.id.rowTickerTorTeam);
			TextView tore=(TextView)row.findViewById(R.id.rowTickerTorTore);
			TextView chancen=(TextView)row.findViewById(R.id. rowTickerTorChancen);
			TextView quote=(TextView)row.findViewById(R.id. rowTickerTorProzent);
			if(helper.getStatTickerTorTore(c).equals(res.getString(R.string.statBezeichnungToreKurz))){
				row.setBackgroundColor(getResources().getColor(R.color.standardfarbe));
				spieler.setTextColor(Color.WHITE);
				team.setTextColor(Color.WHITE);
				tore.setTextColor(Color.WHITE);
				chancen.setTextColor(Color.WHITE);
				quote.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundColor(getResources().getColor(R.color.transparent));
				spieler.setTextColor(getResources().getColor(R.color.standardfarbe));
				team.setTextColor(getResources().getColor(R.color.standardfarbe));
				tore.setTextColor(getResources().getColor(R.color.standardfarbe));
				chancen.setTextColor(getResources().getColor(R.color.standardfarbe));
				quote.setTextColor(getResources().getColor(R.color.standardfarbe));
			}

			return(row);
		}
	}
	
	static class StatTorHolder {
	    private TextView spieler=null;
	    private TextView team=null;
	    private TextView tore=null;
	    private TextView chancen=null;
	    private TextView quote=null;
	    
	    StatTorHolder(View row) {
	    	spieler=(TextView)row.findViewById(R.id.rowTickerTorSpieler);
	    	team=(TextView)row.findViewById(R.id.rowTickerTorTeam);
			tore=(TextView)row.findViewById(R.id.rowTickerTorTore);
			chancen=(TextView)row.findViewById(R.id.rowTickerTorChancen);
			quote=(TextView)row.findViewById(R.id.rowTickerTorProzent);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	    	spieler.setText(helper.getStatTickerTorSpieler(c));
	    	team.setText(helper.getStatTickerTorTeam(c));
	    	tore.setText(helper.getStatTickerTorTore(c));
	    	chancen.setText(helper.getStatTickerTorChancen(c));
	    	quote.setText(helper.getStatTickerTorQuote(c));
	    }
	}
}

