package de.fivestrikes.online;

import android.app.TabActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.res.Resources;
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
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TickerSpielerActivity extends TabActivity {
    /** Called when the activity is first created. */

	public final static String ID_AKTIONINT_EXTRA="de.fivestrikes.pro.aktionInt_ID";
	public final static String ID_AKTION_EXTRA="de.fivestrikes.pro.aktion_ID";
	public final static String ID_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	public final static String ID_REALZEIT_EXTRA="de.fivestrikes.pro.realzeit_ID";
	public final static String ID_TEAM_HEIM_EXTRA="de.fivestrikes.pro.teamHeim_ID";
	public final static String ID_TEAM_AUSW_EXTRA="de.fivestrikes.pro.teamAusw_ID";
	
	
	public final static String ID_AUSWECHSEL_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_AUSWECHSEL_TEAM_EXTRA="de.fivestrikes.pro.team_ID";
	public final static String ID_AUSWECHSEL_AKTIONTEAMHEIM_EXTRA="de.fivestrikes.pro.aktionTeamHeim_ID";
	public final static String ID_AUSWECHSEL_ZEIT_EXTRA="de.fivestrikes.pro.zeit_ID";
	public final static String ID_AUSWECHSEL_REALZEIT_EXTRA="de.fivestrikes.pro.realzeit_ID";
	public final static String ID_AUSWECHSEL_TICKER_EXTRA="de.fivestrikes.pro.ticker_ID";
	
	

	String mannschaftId=null;
	String teamHeimId=null;
	String teamAuswId=null;
	String teamHeimString=null;
	String teamAuswString=null;
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
    String realzeit=null;
    String tickerId=null;
    String torwartTickerId=null;
    Boolean finish=false;
	View tabview;
	private TabHost mTabHost;
	SQLHelper helper=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spieler_tab);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei anderen Activitys verfahren*/
        
        aktionInt=getIntent().getStringExtra(TabMenuActivity.ID_AKTIONINT_EXTRA);
        aktionString=getIntent().getStringExtra(TabMenuActivity.ID_AKTION_EXTRA);
        aktionTeamHeim=getIntent().getStringExtra(TabMenuActivity.ID_AKTIONTEAMHEIM_EXTRA); 
        spielId=getIntent().getStringExtra(TabMenuActivity.ID_SPIEL_EXTRA);
        zeit=getIntent().getStringExtra(TabMenuActivity.ID_ZEIT_EXTRA);
        realzeit=getIntent().getStringExtra(TabMenuActivity.ID_REALZEIT_EXTRA);
        teamHeimId=getIntent().getStringExtra(TabMenuActivity.ID_TEAM_HEIM_EXTRA);
        teamAuswId=getIntent().getStringExtra(TabMenuActivity.ID_TEAM_AUSW_EXTRA);
        
        helper=new SQLHelper(this);
        Cursor c=helper.getSpielCursor(spielId);
		c.moveToFirst();
		teamHeimString = helper.getTeamHeimKurzBySpielID(c);
		teamAuswString = helper.getTeamAuswKurzBySpielID(c);
		if(aktionTeamHeim==null){
			aktionTeamHeim = helper.getSpielBallbesitz(c);
		}
		c.close();
        
        if(aktionInt.equals("7")){
        	 customTitleText.setText(R.string.tickerAktionEinwechselung);
        	 aktionString = getString(R.string.tickerAktionEinwechselung);
        } else {
        	customTitleText.setText(aktionString);
        }
        
        Button backButton = (Button) findViewById(R.id.back_button);
        
        /* Tabs einrichten */
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
	    setupTab(new TextView(this), teamHeimString);
	    setupTab(new TextView(this), teamAuswString);
	    if (aktionTeamHeim.equals("1")){
	    	mTabHost.setCurrentTab(0);
	    } else {
	    	mTabHost.setCurrentTab(1);
	    }
	    
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
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
	
	//
	// Tabs einrichten Start
	//
	
	private void setupTab(final View view, final String tag) {
	    View tabview = createTabView(mTabHost.getContext(), tag);
	    Intent tabIntent;
	    TabSpec setContent;
	    
	    if (tag.compareTo(teamHeimString) == 0) {
	    	tabIntent = new Intent().setClass(this, TabTickerSpielerHeimActivity.class);
	    	tabIntent.putExtra(ID_AKTIONINT_EXTRA, aktionInt);
	    	tabIntent.putExtra(ID_AKTION_EXTRA, aktionString);
	    	tabIntent.putExtra(ID_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
	    	tabIntent.putExtra(ID_SPIEL_EXTRA, spielId);
	    	tabIntent.putExtra(ID_ZEIT_EXTRA, String.valueOf(TickerActivity.elapsedTime));
	    	tabIntent.putExtra(ID_REALZEIT_EXTRA, realzeit);
	    	tabIntent.putExtra(ID_TEAM_HEIM_EXTRA, teamHeimId);
			setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(tabIntent);
			mTabHost.addTab(setContent);
	    }

	    if (tag.compareTo(teamAuswString) == 0) {
	    	tabIntent = new Intent().setClass(this, TabTickerSpielerAuswActivity.class);
	    	tabIntent.putExtra(ID_AKTIONINT_EXTRA, aktionInt);
	    	tabIntent.putExtra(ID_AKTION_EXTRA, aktionString);
	    	tabIntent.putExtra(ID_AKTIONTEAMHEIM_EXTRA, aktionTeamHeim);
	    	tabIntent.putExtra(ID_SPIEL_EXTRA, spielId);
	    	tabIntent.putExtra(ID_ZEIT_EXTRA, String.valueOf(TickerActivity.elapsedTime));
	    	tabIntent.putExtra(ID_REALZEIT_EXTRA, realzeit);
	    	tabIntent.putExtra(ID_TEAM_AUSW_EXTRA, teamAuswId);
			setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(tabIntent);
			mTabHost.addTab(setContent);
	    }
		
	}

	private static View createTabView(final Context context, final String text) {
	    View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
	    TextView tv = (TextView) view.findViewById(R.id.tabsText);
	    tv.setText(text);
	    return view;
	}

	//
	// Tabs einrichten Ende
	//
}
