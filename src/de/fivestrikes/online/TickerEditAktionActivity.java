package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TickerEditAktionActivity extends ListActivity {
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
        
        /** Hinweis: Ballbesitz Heim und Auswärts hinzufügen in strings.xml schreiben */
        
        String[] values = new String[] { getResources().getString(R.string.tickerAktionTor), 
				 						 getResources().getString(R.string.tickerAktionFehlwurf), 
				 						 getResources().getString(R.string.tickerAktionTechnischerFehler),
				 						 getResources().getString(R.string.tickerAktion7mTor),
				 						 getResources().getString(R.string.tickerAktion7mFehlwurf),
				 						 getResources().getString(R.string.tickerAktionTempogegenstossTor),
				 						 getResources().getString(R.string.tickerAktionTempogegenstossFehlwurf),
				 						 getResources().getString(R.string.tickerAktionTorwartGehalten), 
				 						 getResources().getString(R.string.tickerAktionTorwartGegentor), 
				 						 getResources().getString(R.string.tickerAktionTorwart7mGehalten),
				 						 getResources().getString(R.string.tickerAktionTorwart7mGegentor),
				 						 getResources().getString(R.string.tickerAktionTorwartTGGehalten),
				 						 getResources().getString(R.string.tickerAktionTorwartTGGegentor),
				 						 getResources().getString(R.string.tickerAktionEinwechselung),
				 						 getResources().getString(R.string.tickerAktionAuswechselung),
				 						 getResources().getString(R.string.tickerAktionZweiMinuten),
				 						 getResources().getString(R.string.tickerAktionGelbeKarte),
				 						 getResources().getString(R.string.tickerAktionZweiMalZwei),
				 						 getResources().getString(R.string.tickerAktionRoteKarte),
				 						 getResources().getString(R.string.tickerAktionAuszeit)};
		AktionenArrayAdapter adapter = new AktionenArrayAdapter(this, values);
		setListAdapter(adapter);
		
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
			strAktion=res.getString(R.string.tickerAktionTorwartGehalten);
			strAktionInt="16";
			break;
		case 8:
			strAktion=res.getString(R.string.tickerAktionTorwartGegentor);
			strAktionInt="17";
			break;
		case 9:
			strAktion=res.getString(R.string.tickerAktionTorwart7mGehalten);
			strAktionInt="18";
			break;
		case 10:
			strAktion=res.getString(R.string.tickerAktionTorwart7mGegentor);
			strAktionInt="19";
			break;
		case 11:
			strAktion=res.getString(R.string.tickerAktionTorwartTGGehalten);
			strAktionInt="22";
			break;
		case 12:
			strAktion=res.getString(R.string.tickerAktionTorwartTGGegentor);
			strAktionInt="23";
			break;
		case 13:
			strAktion=res.getString(R.string.tickerAktionEinwechselung);
			strAktionInt="7";
			break;
		case 14:
			strAktion=res.getString(R.string.tickerAktionAuswechselung);
			strAktionInt="8";
			break;
		case 15:
			strAktion=res.getString(R.string.tickerAktionZweiMinuten);
			strAktionInt="5";
			break;
		case 16:
			strAktion=res.getString(R.string.tickerAktionGelbeKarte);
			strAktionInt="6";
			break;
		case 17:
			strAktion=res.getString(R.string.tickerAktionZweiMalZwei);
			strAktionInt="11";
			break;
		case 18:
			strAktion=res.getString(R.string.tickerAktionRoteKarte);
			strAktionInt="9";
			break;
		case 19:
			strAktion=res.getString(R.string.tickerAktionAuszeit);
			strAktionInt="24";
			break;
		}
		Intent i=new Intent();
		i.putExtra("Activity", "Aktion");
		i.putExtra("AktionId", strAktionInt);
		i.putExtra("AktionName", strAktion);
		setResult(RESULT_OK, i);
		finish();
	}
}