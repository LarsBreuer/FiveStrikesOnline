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
import android.widget.Toast;

public class TabTickerSpielerHeimActivity extends ListActivity {
	
	public final static String ID_AKTIONINT_EXTRA="de.fivestrikes.pro.aktionInt_ID";
	public final static String ID_AKTION_EXTRA="de.fivestrikes.pro.aktion_ID";
	public final static String ID_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_SPIELERID_EXTRA="de.fivestrikes.pro.spielerId";
	public final static String ID_TICKERID_EXTRA="de.fivestrikes.pro.tickerId";
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	public final static String ID_REALZEIT_EXTRA="de.fivestrikes.pro.realzeit_ID";
	public final static String ID_TEAM_HEIM_EXTRA="de.fivestrikes.pro.teamHeim_ID";
	public final static String ID_TEAM_AUSW_EXTRA="de.fivestrikes.pro.teamAusw_ID";
	public final static String ID_TORWARTID_EXTRA="de.fivestrikes.pro.torwartId";
	public final static String ID_AUSWECHSEL_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_AUSWECHSEL_TEAM_EXTRA="de.fivestrikes.pro.team_ID";
	public final static String ID_AUSWECHSEL_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_AUSWECHSEL_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	public final static String ID_AUSWECHSEL_TICKER_EXTRA="de.fivestrikes.pro.ticker_ID";
	Cursor model=null;
	SpielerAdapter adapter=null;
	SQLHelper helper=null;
	String mannschaftId=null;
	String teamHeimId=null;
	String teamAuswId=null;
    String spielId=null;
    String aktionString=null;
    String aktionInt=null;
    String torwartAktionString=null;
    String torwartAktionInt=null;
    String aktionTeamHeim=null;
    String torwartAktionTeamHeim=null;
    String spielerId=null;
    String torwartId=null;
    String spielerString=null;
    String torwartString=null;
    String spielerPosition=null;
    String zeit=null;
    String tickerId=null;
    String torwartTickerId=null;
    String realzeit=null;
    Boolean finish=false;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.tab_spieler_heim);
        
        helper=new SQLHelper(this);
        mannschaftId=getIntent().getStringExtra(TickerSpielerActivity.ID_TEAM_HEIM_EXTRA);
        model=helper.getAllSpieler(mannschaftId);
        startManagingCursor(model);
        adapter=new SpielerAdapter(model);
        setListAdapter(adapter);
        aktionInt=getIntent().getStringExtra(TickerSpielerActivity.ID_AKTIONINT_EXTRA);
        aktionString=getIntent().getStringExtra(TickerSpielerActivity.ID_AKTION_EXTRA);
        aktionTeamHeim="1"; 
        spielId=getIntent().getStringExtra(TickerSpielerActivity.ID_SPIEL_EXTRA);
        zeit=getIntent().getStringExtra(TickerSpielerActivity.ID_ZEIT_EXTRA);
        realzeit=getIntent().getStringExtra(TickerSpielerActivity.ID_REALZEIT_EXTRA);
        
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
		
		int maxZeit = helper.maxTickerZeit(spielId); 	// Zeit der letzten Spielaktion des Spiel ermitteln
		spielerId=String.valueOf(id);
		Cursor c=helper.getSpielerById(spielerId);
		c.moveToFirst();    
		spielerString=helper.getSpielerName(c);
		spielerPosition=helper.getSpielerPosition(c);
		c.close();
		helper.insertTicker(Integer.parseInt(aktionInt), aktionString, Integer.parseInt(aktionTeamHeim), spielerString, 
				Integer.parseInt(spielerId), Integer.parseInt(spielId), Integer.parseInt(zeit), realzeit);
		Cursor lastTickC=helper.getLastTickerId();
		lastTickC.moveToFirst();
		tickerId = helper.getTickerId(lastTickC);
		lastTickC.close();
		helper.updateSpielErgebnis(Integer.parseInt(spielId));
		int zeitZurueck=0;
		Cursor cSpiel=helper.getSpielCursor(spielId);
    	cSpiel.moveToFirst();
	    int halbzeitlaenge=Integer.parseInt(helper.getSpielHalbzeitlaenge(cSpiel))*60*2000;
	    finish=false;
    	if(Integer.parseInt(aktionInt)==2 || 
    			Integer.parseInt(aktionInt)==14 || 
    			Integer.parseInt(aktionInt)==20){		// Wenn Tor geworfen wurde...
    		
    		/* Änderung des Ballbesitzes */
    		if(Integer.parseInt(aktionTeamHeim)==1 && 
    				Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==1){  // ... und Heimmannschaft Tor geworfen, dann trage Ballbesitz Auswärtsmannschaft ein
    			String strBallbesitz="Ballbesitz " + helper.getTeamAuswKurzBySpielID(cSpiel);
    			helper.insertTicker(1, strBallbesitz, 0, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
    			helper.updateSpielBallbesitz(spielId, 0);  // aktuellen Ballbesitz in Spiel eintragen
    		}
    		if(Integer.parseInt(aktionTeamHeim)==0 && 
    				Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==0){
    			String strBallbesitz="Ballbesitz " + helper.getTeamHeimKurzBySpielID(cSpiel);
    			helper.insertTicker(0, strBallbesitz, 1, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
    			helper.updateSpielBallbesitz(spielId, 1);  // aktuellen Ballbesitz in Spiel eintragen
    		}

   			/* Überprüfen, ob bei gegnerischer Mannschaft ein Torwart angegeben wurde. */
   			/* Falls ja, für den Torwart ein Gegentor eintragen. */
    		if(Integer.parseInt(aktionTeamHeim)==1){
    			if(helper.getSpielTorwartAuswaerts(cSpiel)!=null){
    				torwartId=helper.getSpielTorwartAuswaerts(cSpiel);
    			}
    		}
    		if(Integer.parseInt(aktionTeamHeim)==0){
    			if(helper.getSpielTorwartHeim(cSpiel)!=null){
    				torwartId=helper.getSpielTorwartHeim(cSpiel);
    			}
    		}
    		if(torwartId!=null){
				Cursor cTorwart=helper.getSpielerById(torwartId);
				cTorwart.moveToFirst();    
				torwartString=helper.getSpielerName(cTorwart);
				cTorwart.close();
				if(aktionInt.equals("2")){
					torwartAktionInt="17";
					torwartAktionString=getString(R.string.tickerAktionTorwartGegentor);
				}
				if(aktionInt.equals("14")){
					torwartAktionInt="19";
					torwartAktionString=getString(R.string.tickerAktionTorwart7mGegentor);
				}
				if(aktionInt.equals("20")){
					torwartAktionInt="23";
					torwartAktionString=getString(R.string.tickerAktionTorwartTGGegentor);
				}
				if(aktionTeamHeim.equals("0")){
					torwartAktionTeamHeim="1";
				} else {
					torwartAktionTeamHeim="0";
				}
				helper.insertTicker(Integer.parseInt(torwartAktionInt), torwartAktionString, Integer.parseInt(torwartAktionTeamHeim), torwartString, 
						Integer.parseInt(torwartId), Integer.parseInt(spielId), Integer.parseInt(zeit), realzeit);
				Cursor lastTickTorwartC=helper.getLastTickerId();
				lastTickTorwartC.moveToFirst();
				torwartTickerId = helper.getTickerId(lastTickTorwartC);
				lastTickTorwartC.close();
    		} 

    		/* Spielstand in Tickereinträge schreiben falls Tor geworfen wurde */
   			/* Wenn es Tickereinträge nach dem aktuellen Eintrag gibt, ändere die Torfolge bei den Einträgen */
			String[] args={spielId};
			SQLiteDatabase db=helper.getWritableDatabase();
			Cursor cTicker=db.rawQuery("SELECT * FROM ticker WHERE spielID=? ORDER BY zeitInteger ASC", args);
   			cTicker.moveToFirst(); 
			String strErgebnis=null;
   			if(Integer.parseInt(zeit)<=maxZeit){	
   				int toreHeim=0;
   				int toreAusw=0;
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
   					if(Integer.parseInt(helper.getTickerZeitInt(cTicker))>=Integer.parseInt(zeit)){	// Wenn die Zeit der Schleifeneintrags größer ist als der aktuelle Eintrag, dann hat sich die Torfolge geändert und der Eintrag muss geändert werden
   						strErgebnis=String.valueOf(toreHeim)+":"+String.valueOf(toreAusw);
   						helper.updateTickerErgebnis(helper.getTickerId(cTicker), toreHeim, toreAusw, strErgebnis);
   					}
   				}
   			} else {
   				strErgebnis=String.valueOf(helper.countTickerTore(spielId, "1", "9999999"))+
   							":"+
   							String.valueOf(helper.countTickerTore(spielId, "0", "9999999"));
   				helper.updateTickerErgebnis(tickerId, 
   											helper.countTickerTore(spielId, "1", "9999999"), 
   											helper.countTickerTore(spielId, "0", "9999999"), 
   											strErgebnis);
   			}
   			cTicker.close();
   			
   			if(helper.getSpielSpielerWurfecke(cSpiel)!=null){
   				if(helper.getSpielSpielerWurfecke(cSpiel).equals("1")){
   					/* Activity starten um Wurfecke und Wurfposition einzugeben */
   					Intent newIntent = new Intent(getApplicationContext(), TickerSpielerWurfeckeActivity.class);
   					newIntent.putExtra(ID_TICKERID_EXTRA, tickerId);
   					if(torwartTickerId!=null){
   						newIntent.putExtra(ID_TORWARTID_EXTRA, torwartTickerId);	
   					}
   					startActivity(newIntent);
    			
   					/** Hinweis: Bislang geht die Activity direkt zurück auf die Ticker Activity (über TickerAktionActivity).
   					 *  Dies geschieht auch bei dem Back-Button. Einrichten, dass man bei der Auswahl zurück zum Ticker kommt und 
   					 *  bei dem Back-Button nur zurück zur TickerSpielerActivity.  
   					 */
   					finish=true;
   					Intent i=new Intent();
   					setResult(RESULT_OK, i);
   					finish();
   				}
   			}	
    	}
    	if(Integer.parseInt(aktionInt)== 3 || 
    			Integer.parseInt(aktionInt)==15 || 
    			Integer.parseInt(aktionInt)==21){  	// Wenn Fehlwurf...
    		if(helper.getSpielSpielerWurfecke(cSpiel)!=null){
   				if(helper.getSpielSpielerWurfecke(cSpiel).equals("1")){
   					/* Activity starten um Wurfecke und Wurfposition einzugeben */
   					Intent newIntent = new Intent(getApplicationContext(), TickerSpielerFehlwurfActivity.class);
   					newIntent.putExtra(ID_TICKERID_EXTRA, tickerId);
   					newIntent.putExtra(ID_AKTIONINT_EXTRA, aktionInt);
   					newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
   					newIntent.putExtra(ID_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
   					newIntent.putExtra(ID_ZEIT_EXTRA, zeit);
   					newIntent.putExtra(ID_REALZEIT_EXTRA, realzeit);
   					startActivity(newIntent);
    			
   					/** Hinweis: Bislang geht die Activity direkt zurück auf die Ticker Activity (über TickerAktionActivity).
   					 *  Dies geschieht auch bei dem Back-Button. Einrichten, dass man bei der Auswahl zurück zum Ticker kommt und 
   					 *  bei dem Back-Button nur zurück zur TickerSpielerActivity.  
   					 */
   					finish=true;
   					Intent i=new Intent();
   					setResult(RESULT_OK, i);
   					finish();
   				} else {
   					finish=true;
   		    		AlertDialog.Builder tfBuilder = new AlertDialog.Builder(this);
   		   			tfBuilder
   		   			.setTitle(R.string.tickerMSGBoxAktionTitel)
   		   			.setMessage(R.string.tickerMSGBoxAktionNachricht)
   		   			.setIcon(android.R.drawable.ic_dialog_alert)
   		   			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
   		   				public void onClick(DialogInterface dialog, int which) {			      	
   		   	    			Cursor cSpiel=helper.getSpielCursor(spielId);
   		   	    	    	cSpiel.moveToFirst();
   		   	    			if(Integer.parseInt(aktionTeamHeim)==1 && 
   		   	    					Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==1){  // ... und Heimmannschaft Tor geworfen, dann trage Ballbesitz Auswärtsmannschaft ein
   		   	    				String strBallbesitz="Ballbesitz " + helper.getTeamAuswKurzBySpielID(cSpiel);
   		   	    				helper.insertTicker(1, strBallbesitz, 0, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
   		   	    				helper.updateSpielBallbesitz(spielId, 0);  // aktuellen Ballbesitz in Spiel eintragen
   		   	    			}
   		   	    			if(Integer.parseInt(aktionTeamHeim)==0 && 
   		   	    					Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==0){
   		   	    				String strBallbesitz="Ballbesitz " + helper.getTeamHeimKurzBySpielID(cSpiel);
   		   	    				helper.insertTicker(0, strBallbesitz, 1, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
   		   	    				helper.updateSpielBallbesitz(spielId, 1);  // aktuellen Ballbesitz in Spiel eintragen
   		   	    			}
   		   	    			cSpiel.close();
   		   	    			/** Hinweis: Ballbesitzwechsel vielleicht in eigene Funktion schreiben */
   		   					finish();
   		   				}
   		   			})
   		   			.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
   		   				public void onClick(DialogInterface dialog, int which) {
   		   					finish();
   		   				}
   		   			})
   		   			.show();
   				}
    		}
    	}
    	if(Integer.parseInt(aktionInt)== 4){  // Wenn technischer Fehler, dann Abfrage, ob Ballbesitzwechsel...
   			finish=true;
    		AlertDialog.Builder tfBuilder = new AlertDialog.Builder(this);
   			tfBuilder
   			.setTitle(R.string.tickerMSGBoxAktionTitel)
   			.setMessage(R.string.tickerMSGBoxAktionNachricht)
   			.setIcon(android.R.drawable.ic_dialog_alert)
   			.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
   				public void onClick(DialogInterface dialog, int which) {			      	
   	    			Cursor cSpiel=helper.getSpielCursor(spielId);
   	    	    	cSpiel.moveToFirst();
   	    			if(Integer.parseInt(aktionTeamHeim)==1 && 
   	    					Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==1){  // ... und Heimmannschaft Tor geworfen, dann trage Ballbesitz Auswärtsmannschaft ein
   	    				String strBallbesitz="Ballbesitz " + helper.getTeamAuswKurzBySpielID(cSpiel);
   	    				helper.insertTicker(1, strBallbesitz, 0, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
   	    				helper.updateSpielBallbesitz(spielId, 0);  // aktuellen Ballbesitz in Spiel eintragen
   	    			}
   	    			if(Integer.parseInt(aktionTeamHeim)==0 && 
   	    					Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==0){
   	    				String strBallbesitz="Ballbesitz " + helper.getTeamHeimKurzBySpielID(cSpiel);
   	    				helper.insertTicker(0, strBallbesitz, 1, "", 0, Integer.parseInt(spielId), (int) Integer.parseInt(zeit) + 1, realzeit);
   	    				helper.updateSpielBallbesitz(spielId, 1);  // aktuellen Ballbesitz in Spiel eintragen
   	    			}
   	    			cSpiel.close();
   	    			/** Hinweis: Ballbesitzwechsel vielleicht in eigene Funktion schreiben */
   					finish();
   				}
   			})
   			.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
   				public void onClick(DialogInterface dialog, int which) {
   					finish();
   				}
   			})
   			.show();
    	}
    	if(Integer.parseInt(aktionInt)== 5){		// Bei Zeitstrafen eintragen, wann Spieler zurück
    	    zeitZurueck=(int)Integer.parseInt(zeit)+2*60000;
    	    /** Hinweis: Nach Spiellänge 2 Minuten auf Spiellänge setzen */
    	    if(zeitZurueck>halbzeitlaenge){
    	    	zeitZurueck=halbzeitlaenge;
    	    }
    	    helper.insertTicker(10, spielerString+" "+getString(R.string.tickerAktionZurueck), Integer.parseInt(aktionTeamHeim), 
    	    		spielerString, Integer.parseInt(spielerId), Integer.parseInt(spielId), zeitZurueck, realzeit);
    	    finish=true;
    	   	finish();
    	}
    	if(Integer.parseInt(aktionInt)== 7){		// Bei Einwechselung auf den Auswechselungs-Bildschirm springen
    		/* Torwart eintragen, falls ein Torwart ausgewählt wurde */
    		if(spielerPosition.equals(getString(R.string.spielerPositionTorwart))){
    			if(Integer.parseInt(aktionTeamHeim)==1){
    				helper.updateSpielTorwartHeim(spielId, Integer.parseInt(spielerId));
    			}
    			if(Integer.parseInt(aktionTeamHeim)==0){
    				helper.updateSpielTorwartAuswaerts(spielId, Integer.parseInt(spielerId));
    			}
    		}

    		/* Activity Auswechselung aufrufen */
    		Intent newIntent = new Intent(getApplicationContext(), TickerSpielerAuswechselungActivity.class);
    		newIntent.putExtra(ID_AUSWECHSEL_TEAM_EXTRA, mannschaftId);
    		newIntent.putExtra(ID_AUSWECHSEL_SPIEL_EXTRA, spielId);
    		newIntent.putExtra(ID_AUSWECHSEL_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
    		newIntent.putExtra(ID_AUSWECHSEL_ZEIT_EXTRA, zeit);
    		startActivity(newIntent);
    			
    		/** Hinweis: Bislang geht die Activity direkt zurück auf die Ticker Activity (über TickerAktionActivity).
    		 *  Dies geschieht auch bei dem Back-Button. Einrichten, dass man bei der Auswahl zurück zum Ticker kommt und 
    		 *  bei dem Back-Button nur zurück zur TickerSpielerActivity.  
    		 */
    		
    		finish=true;
    		Intent i=new Intent();
    		setResult(RESULT_OK, i);
    		finish();
    	}
    	if(Integer.parseInt(aktionInt)== 9){		// Bei Zeitstrafen eintragen, wann Spieler zurück
    	    zeitZurueck=(int)Integer.parseInt(zeit)+2*60000;
    	    /** Hinweis: Nach Spiellänge 2 Minuten auf Spiellänge setzen */
    	    helper.insertTicker(10, spielerString+" "+getString(R.string.tickerAktionZurueck), Integer.parseInt(aktionTeamHeim), 
    	    		spielerString, Integer.parseInt(spielerId), Integer.parseInt(spielId), zeitZurueck, realzeit);
    	    finish=true;
    	   	finish();
    	}
    	if(Integer.parseInt(aktionInt)== 11){		// Bei Zeitstrafen eintragen, wann Spieler zurück
    	    zeitZurueck=(int)Integer.parseInt(zeit)+4*60000;
    	    /** Hinweis: Nach Spiellänge 2 Minuten auf Spiellänge setzen */
    	    helper.insertTicker(10, spielerString+" "+getString(R.string.tickerAktionZurueck), Integer.parseInt(aktionTeamHeim), 
    	    		spielerString, Integer.parseInt(spielerId), Integer.parseInt(spielId), zeitZurueck, realzeit);
    	    finish=true;
    	   	finish();
    	}
		/** Hinweis: Bislang geht die Activity direkt zurück auf die Ticker Activity (über TickerAktionActivity).
		 *  Dies geschieht auch bei dem Back-Button. Einrichten, dass man bei der Auswahl zurück zum Ticker kommt und 
		 *  bei dem Back-Button nur zurück zur TickerSpielerActivity.  
		 */
		if(finish==false){
			finish();
		}
    	cSpiel.close();
	}
	
	class SpielerAdapter extends CursorAdapter {
		SpielerAdapter(Cursor c) {
			super(TabTickerSpielerHeimActivity.this, c);
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