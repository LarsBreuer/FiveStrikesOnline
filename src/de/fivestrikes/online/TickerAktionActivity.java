package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TickerAktionActivity extends ListActivity {
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_AKTION_EXTRA="de.fivestrikes.pro.aktion_ID";
	public final static String ID_AKTIONINT_EXTRA="de.fivestrikes.pro.aktionInt_ID";
	public final static String ID_TEAM_EXTRA="de.fivestrikes.pro.team_ID";
	public final static String ID_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	SQLHelper helper=null;
	String teamId=null;
	String spielId=null;
	String aktionTeamHeim=null;
	String zeit=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mannschaft);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.tickerAktionTitel);
        
        helper=new SQLHelper(this);
        
        String[] values = new String[] { getResources().getString(R.string.tickerAktionTor), 
        								 getResources().getString(R.string.tickerAktionFehlwurf), 
        								 getResources().getString(R.string.tickerAktionTechnischerFehler),
        								 getResources().getString(R.string.tickerAktion7mTor),
        								 getResources().getString(R.string.tickerAktion7mFehlwurf),
        								 getResources().getString(R.string.tickerAktionTempogegenstossTor),
        								 getResources().getString(R.string.tickerAktionTempogegenstossFehlwurf),
        								 getResources().getString(R.string.tickerAktionWechsel),
        								 getResources().getString(R.string.tickerAktionStartaufstellung),
        								 getResources().getString(R.string.tickerAktionZweiMinuten),
        								 getResources().getString(R.string.tickerAktionGelbeKarte),
        								 getResources().getString(R.string.tickerAktionZweiMalZwei),
        								 getResources().getString(R.string.tickerAktionRoteKarte),
        								 getResources().getString(R.string.tickerAktionAuszeit)};
		AktionenArrayAdapter adapter = new AktionenArrayAdapter(this, values);
		setListAdapter(adapter);
		
		teamId=getIntent().getStringExtra(TickerAktionActivity.ID_TEAM_EXTRA);
		spielId=getIntent().getStringExtra(TickerAktionActivity.ID_SPIEL_EXTRA);
		aktionTeamHeim=getIntent().getStringExtra(TickerAktionActivity.ID_AKTIONTEAMHEIM_EXTRA);
		zeit=getIntent().getStringExtra(TickerAktionActivity.ID_ZEIT_EXTRA);
		
        Button backButton = (Button) findViewById(R.id.back_button);
        
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
		
	}
	
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		
		String strAktion=null;
		String strAktionInt=null;
		Resources res = getResources();
		switch(position) {
			case 0:
				strAktion=res.getString(R.string.tickerAktionTor);
				strAktionInt="2";
				break;
			case 1:
				strAktion=res.getString(R.string.tickerAktionFehlwurf);
				strAktionInt="3";
				break;
			case 2:
				strAktion=res.getString(R.string.tickerAktionTechnischerFehler);
				strAktionInt="4";
				break;
			case 3:
				strAktion=res.getString(R.string.tickerAktion7mTor);
				strAktionInt="14";
				break;
			case 4:
				strAktion=res.getString(R.string.tickerAktion7mFehlwurf);
				strAktionInt="15";
				break;
			case 5:
				strAktion=res.getString(R.string.tickerAktionTempogegenstossTor);
				strAktionInt="20";
				break;
			case 6:
				strAktion=res.getString(R.string.tickerAktionTempogegenstossFehlwurf);
				strAktionInt="21";
				break;
			case 7:
				strAktion=res.getString(R.string.tickerAktionWechsel);
				strAktionInt="7";
				break;
			case 8:
				strAktion=res.getString(R.string.tickerAktionStartaufstellung);
				strAktionInt="12";
				break;
			case 9:
				strAktion=res.getString(R.string.tickerAktionZweiMinuten);
				strAktionInt="5";
				break;
			case 10:
				strAktion=res.getString(R.string.tickerAktionGelbeKarte);
				strAktionInt="6";
				break;
			case 11:
				strAktion=res.getString(R.string.tickerAktionZweiMalZwei);
				strAktionInt="11";
				break;
			case 12:
				strAktion=res.getString(R.string.tickerAktionRoteKarte);
				strAktionInt="9";
				break;
			case 13:
				strAktion=res.getString(R.string.tickerAktionAuszeit);
				strAktionInt="24";
				break;
		}
		
		if(position==8){
			Intent newIntent = new Intent(getApplicationContext(), TickerSpielerStartaufstellungActivity.class);
			newIntent.putExtra(ID_AKTION_EXTRA, strAktion);
			newIntent.putExtra(ID_AKTIONINT_EXTRA, strAktionInt);
			newIntent.putExtra(ID_TEAM_EXTRA, teamId);
			newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
			newIntent.putExtra(ID_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
			newIntent.putExtra(ID_ZEIT_EXTRA, zeit);
			startActivity(newIntent);
		} else {
			Intent newIntent = new Intent(getApplicationContext(), TickerSpielerActivity.class);
			newIntent.putExtra(ID_AKTION_EXTRA, strAktion);
			newIntent.putExtra(ID_AKTIONINT_EXTRA, strAktionInt);
			newIntent.putExtra(ID_TEAM_EXTRA, teamId);
			newIntent.putExtra(ID_SPIEL_EXTRA, spielId);
			newIntent.putExtra(ID_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
			newIntent.putExtra(ID_ZEIT_EXTRA, zeit);
			startActivity(newIntent);
		}
				
		/** Hinweis: Bislang geht die Activity direkt zurück auf die Ticker Activity (über TickerAktionActivity).
		 *  Dies geschieht auch bei dem Back-Button. Einrichten, dass man bei der Auswahl zurück zum Ticker kommt und 
		 *  bei dem Back-Button nur zurück zur TickerSpielerActivity.  
		 */
		
		Intent i=new Intent();
		setResult(RESULT_OK, i);
		finish();
	}
}