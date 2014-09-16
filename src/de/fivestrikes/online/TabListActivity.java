package de.fivestrikes.online;

import android.app.ListActivity;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.CursorAdapter;
import android.widget.Toast;
import android.util.Log;

public class TabListActivity extends ListActivity {
	
	public final static String ID_TICKER_EXTRA="de.fivestrikes.pro.ticker_ID";
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	Cursor model=null;
	SQLHelper helper=null;
	TickerAdapter adapter=null;
	String spielId=null;
	private static final int GET_CODE = 0;
	final Context context = this;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("TabListActivity", "onCreate");
        setContentView(R.layout.tab_list);
 
        spielId=getIntent().getStringExtra(SpielEditActivity.ID_SPIEL_EXTRA);
        helper=new SQLHelper(this);
        model=helper.getAllTicker(spielId);
        startManagingCursor(model);
        adapter=new TickerAdapter(model);
        setListAdapter(adapter);
    }
    
    @Override
	public void onResume() {
    	super.onResume();  // Always call the superclass method first
    	Log.v("TabListActivity", "onResume");
    	refreshContent();
    }
    
    public void refreshContent() {
    	Log.v("TabListActivity", "refreshContent");
    	helper=new SQLHelper(this);
        model=helper.getAllTicker(spielId);
        startManagingCursor(model);
        adapter=new TickerAdapter(model);
        setListAdapter(adapter);  
    }
    
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		
		Intent i=new Intent(TabListActivity.this, TickerEditActivity.class);
		i.putExtra(ID_TICKER_EXTRA, String.valueOf(id));
		i.putExtra(ID_SPIEL_EXTRA, spielId);
		startActivityForResult(i, GET_CODE);
		
	}
	
	@Override
	public void onDestroy() {
	  super.onDestroy();	  
	  helper.close();
	}
	
	class TickerAdapter extends CursorAdapter {
		TickerAdapter(Cursor c) {
			super(TabListActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			TickerHolder holder=(TickerHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_ticker, parent, false);
			TickerHolder holder=new TickerHolder(row);

			row.setTag(holder);

			return(row);
		}
	}
	
	static class TickerHolder {
	    private TextView aktion=null;
	    private TextView zeit=null;
	    private TextView spieler=null;
	    private TextView icon=null;
	    private View teamColor=null;
	    
	    TickerHolder(View row) {
	      aktion=(TextView)row.findViewById(R.id.rowTickerAktion);
	      zeit=(TextView)row.findViewById(R.id.rowTickerZeit);
	      spieler=(TextView)row.findViewById(R.id.rowTickerSpieler);
	      icon=(TextView)row.findViewById(R.id.rowTickerIcon);
	      teamColor=(View)row.findViewById(R.id.rowTickerTeamColor);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	      aktion.setText(helper.getTickerAktion(c));
	      zeit.setText(helper.getTickerZeit(c) + " Min.");
	      //if(!(helper.getTickerAktionInt(c)).equals("0") && !(helper.getTickerAktionInt(c)).equals("1") && !(helper.getTickerAktionInt(c)).equals("24")){
	      if(!helper.getTickerSpielerId(c).equals("0") ){
	    	  spieler.setText(helper.getSpielerNummerById(helper.getTickerSpielerId(c)) + " - " + helper.getTickerSpieler(c));
	      } else {
			  spieler.setText("");
		  }
	      if (Integer.parseInt(helper.getTickerAktionTeamHeim(c))==1){
	    	  teamColor.setBackgroundColor(0xFF404895);   	  
	      }
	      if (Integer.parseInt(helper.getTickerAktionTeamHeim(c))==0){
	    	  teamColor.setBackgroundColor(0xFFCB061D);   	  
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==2 || Integer.parseInt(helper.getTickerAktionInt(c))==14 || 
	    		  Integer.parseInt(helper.getTickerAktionInt(c))==20){
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_none);
	    	  icon.setText(helper.getTickerErgebnis(c));   	  
	      }   
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==3) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_fehlwurf);
	    	  icon.setText("");
	      } 
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==4) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_techfehl);
	    	  icon.setText("");
	      } 
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==15) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_7m_fehlwurf);
	    	  icon.setText("");
	      } 
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==21) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_tg_fehlwurf);
	    	  icon.setText("");
	      } 
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==6) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_gelbekarte);
	    	  icon.setText("");
	      }	      
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==9) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_rotekarte);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==5 || Integer.parseInt(helper.getTickerAktionInt(c))==11) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_zwei_raus);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==0 || Integer.parseInt(helper.getTickerAktionInt(c))==1) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_ballbesitz);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==8) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_auswechsel);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==10) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_zwei_rein);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==7) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_einwechsel);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==16 || Integer.parseInt(helper.getTickerAktionInt(c))==22) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_torwart_gehalten);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==17 || Integer.parseInt(helper.getTickerAktionInt(c))==23) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_torwart_tor);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==18) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_torwart_7m_gehalten);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==19) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_torwart_7m_tor);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==7) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_einwechsel);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==7) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_einwechsel);
	    	  icon.setText("");
	      }
	      if (Integer.parseInt(helper.getTickerAktionInt(c))==24) {
	    	  icon.setBackgroundResource(R.drawable.ticker_symbol_auszeit);
	    	  icon.setText("");
	      }
	    }
	}
}

