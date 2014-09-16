package de.fivestrikes.online;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;


public class TickerSpielerFehlwurfActivity extends Activity {
    /** Called when the activity is first created. */

	Cursor model=null;
	SQLHelper helper=null;
	String spielId=null;
	String tickerId=null;
    String torwartTickerId=null;
    String wurfecke=null;
    String wurfposition=null;
    String aktionTeamHeim=null;
    String torwartAktionTeamHeim=null;
    String torwartId=null;
    String aktionInt=null;
    String torwartAktionInt=null;
    String torwartAktionString=null;
    String torwartString=null;
    String zeit=null;
    String realzeit=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.ticker_fehlwurf);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.tickerAktionWurfecke);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei andren Activitys verfahren*/
        
        RadioButton radio_ja=(RadioButton)findViewById(R.id.radio_ja);
        radio_ja.setChecked(true);
        
        helper=new SQLHelper(this);
        tickerId=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_TICKERID_EXTRA);
        aktionInt=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_AKTIONINT_EXTRA);
        spielId=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_SPIEL_EXTRA);
        aktionTeamHeim=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_AKTIONTEAMHEIM_EXTRA);
        zeit=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_ZEIT_EXTRA);
        realzeit=getIntent().getStringExtra(TabTickerSpielerHeimActivity.ID_REALZEIT_EXTRA);
        if(tickerId==null){
        	tickerId=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_TICKERID_EXTRA);
            aktionInt=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_AKTIONINT_EXTRA);
            spielId=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_SPIEL_EXTRA);
            aktionTeamHeim=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_AKTIONTEAMHEIM_EXTRA);
            zeit=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_ZEIT_EXTRA);
            realzeit=getIntent().getStringExtra(TabTickerSpielerAuswActivity.ID_REALZEIT_EXTRA);
        }
		Cursor c=helper.getSpielCursor(spielId);
    	c.moveToFirst();
		if(Integer.parseInt(aktionTeamHeim)==1){
			if(helper.getSpielTorwartAuswaerts(c)!=null){
				torwartId=helper.getSpielTorwartAuswaerts(c);
			}
		}
		if(Integer.parseInt(aktionTeamHeim)==0){
			if(helper.getSpielTorwartHeim(c)!=null){
				torwartId=helper.getSpielTorwartHeim(c);
			}
		}
		c.close();
        if(torwartId==null){
        	Toast.makeText(getApplicationContext(), getString(R.string.tickerWarnmeldungTorwartEinsatz), Toast.LENGTH_SHORT).show();
        }
        
        Button backButton = (Button) findViewById(R.id.back_button);
        final Button fehl_ooll_Button = (Button) findViewById(R.id.fehl_ooll);
        final Button fehl_ool_Button = (Button) findViewById(R.id.fehl_ool);
        final Button fehl_oom_Button = (Button) findViewById(R.id.fehl_oom);
        final Button fehl_oor_Button = (Button) findViewById(R.id.fehl_oor);
        final Button fehl_oorr_Button = (Button) findViewById(R.id.fehl_oorr);
        final Button fehl_oll_Button = (Button) findViewById(R.id.fehl_oll);
        final Button fehl_ol_Button = (Button) findViewById(R.id.fehl_ol);
        final Button fehl_om_Button = (Button) findViewById(R.id.fehl_om);
        final Button fehl_or_Button = (Button) findViewById(R.id.fehl_or);
        final Button fehl_orr_Button = (Button) findViewById(R.id.fehl_orr);
        final Button fehl_mll_Button = (Button) findViewById(R.id.fehl_mll);
        final Button fehl_ml_Button = (Button) findViewById(R.id.fehl_ml);
        final Button fehl_mm_Button = (Button) findViewById(R.id.fehl_mm);
        final Button fehl_mr_Button = (Button) findViewById(R.id.fehl_mr);
        final Button fehl_mrr_Button = (Button) findViewById(R.id.fehl_mrr);
        final Button fehl_ull_Button = (Button) findViewById(R.id.fehl_ull);
        final Button fehl_ul_Button = (Button) findViewById(R.id.fehl_ul);
        final Button fehl_um_Button = (Button) findViewById(R.id.fehl_um);
        final Button fehl_ur_Button = (Button) findViewById(R.id.fehl_ur);
        final Button fehl_urr_Button = (Button) findViewById(R.id.fehl_urr);
        final Button feld_1_1_Button = (Button) findViewById(R.id.feld_1_1);
        final Button feld_2_1_Button = (Button) findViewById(R.id.feld_2_1);
        final Button feld_3_1_Button = (Button) findViewById(R.id.feld_3_1);
        final Button feld_4_1_Button = (Button) findViewById(R.id.feld_4_1);
        final Button feld_5_1_Button = (Button) findViewById(R.id.feld_5_1);
        final Button feld_1_2_Button = (Button) findViewById(R.id.feld_1_2);
        final Button feld_2_2_Button = (Button) findViewById(R.id.feld_2_2);
        final Button feld_3_2_Button = (Button) findViewById(R.id.feld_3_2);
        final Button feld_4_2_Button = (Button) findViewById(R.id.feld_4_2);
        final Button feld_5_2_Button = (Button) findViewById(R.id.feld_5_2);
        final Button feld_1_3_Button = (Button) findViewById(R.id.feld_1_3);
        final Button feld_2_3_Button = (Button) findViewById(R.id.feld_2_3);
        final Button feld_3_3_Button = (Button) findViewById(R.id.feld_3_3);
        final Button feld_4_3_Button = (Button) findViewById(R.id.feld_4_3);
        final Button feld_5_3_Button = (Button) findViewById(R.id.feld_5_3);
        final Button feld_1_4_Button = (Button) findViewById(R.id.feld_1_4);
        final Button feld_2_4_Button = (Button) findViewById(R.id.feld_2_4);
        final Button feld_3_4_Button = (Button) findViewById(R.id.feld_3_4);
        final Button feld_4_4_Button = (Button) findViewById(R.id.feld_4_4);
        final Button feld_5_4_Button = (Button) findViewById(R.id.feld_5_4);
        
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
		
		/** Bei Siebenmeter, den Siebenmeterpunkt einrichten **/
		Cursor cTicker=helper.getTickerCursor(tickerId);
		cTicker.moveToFirst();
		if(Integer.parseInt(helper.getTickerAktionInt(cTicker))==15){
			wurfposition="3_3";
			wurf_button_leeren();
			feld_3_3_Button.setText("X");
		}
		cTicker.close();
		
        /** Button Wurfecke*/
        fehl_ooll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOLL";
            	fehl_button_leeren();
            	fehl_ooll_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ool_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOL";
            	fehl_button_leeren();
            	fehl_ool_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_oom_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOM";
            	fehl_button_leeren();
            	fehl_oom_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_oor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOR";
            	fehl_button_leeren();
            	fehl_oor_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_oorr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OORR";
            	fehl_button_leeren();
            	fehl_oorr_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_oll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OLL";
            	fehl_button_leeren();
            	fehl_oll_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ol_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OL";
            	fehl_button_leeren();
            	fehl_ol_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_om_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OM";
            	fehl_button_leeren();
            	fehl_om_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_or_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OR";
            	fehl_button_leeren();
            	fehl_or_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_orr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ORR";
            	fehl_button_leeren();
            	fehl_orr_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_mll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MLL";
            	fehl_button_leeren();
            	fehl_mll_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ml_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ML";
            	fehl_button_leeren();
            	fehl_ml_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_mm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MM";
            	fehl_button_leeren();
            	fehl_mm_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_mr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MR";
            	fehl_button_leeren();
            	fehl_mr_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_mrr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MRR";
            	fehl_button_leeren();
            	fehl_mrr_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ull_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ULL";
            	fehl_button_leeren();
            	fehl_ull_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ul_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UL";
            	fehl_button_leeren();
            	fehl_ul_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_um_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UM";
            	fehl_button_leeren();
            	fehl_um_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_ur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UR";
            	fehl_button_leeren();
            	fehl_ur_Button.setText("X");
            	uebertragen();
            }
        });
        fehl_urr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="URR";
            	fehl_button_leeren();
            	fehl_urr_Button.setText("X");
            	uebertragen();
            }
        });
        
        /** Button Wurfposition*/
        feld_1_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_1";
            	wurf_button_leeren();
            	feld_1_1_Button.setText("X");
            	uebertragen();
            }
        });
        feld_2_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_1";
            	wurf_button_leeren();
            	feld_2_1_Button.setText("X");
            	uebertragen();
            }
        });
        feld_3_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_1";
            	wurf_button_leeren();
            	feld_3_1_Button.setText("X");
            	uebertragen();
            }
        });
        feld_4_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_1";
            	wurf_button_leeren();
            	feld_4_1_Button.setText("X");
            	uebertragen();
            }
        });
        feld_5_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_1";
            	wurf_button_leeren();
            	feld_5_1_Button.setText("X");
            	uebertragen();
            }
        });
        feld_1_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_2";
            	wurf_button_leeren();
            	feld_1_2_Button.setText("X");
            	uebertragen();
            }
        });
        feld_2_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_2";
            	wurf_button_leeren();
            	feld_2_2_Button.setText("X");
            	uebertragen();
            }
        });
        feld_3_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_2";
            	wurf_button_leeren();
            	feld_3_2_Button.setText("X");
            	uebertragen();
            }
        });
        feld_4_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_2";
            	wurf_button_leeren();
            	feld_4_2_Button.setText("X");
            	uebertragen();
            }
        });
        feld_5_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_2";
            	wurf_button_leeren();
            	feld_5_2_Button.setText("X");
            	uebertragen();
            }
        });
        feld_1_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_3";
            	wurf_button_leeren();
            	feld_1_3_Button.setText("X");
            	uebertragen();
            }
        });
        feld_2_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_3";
            	wurf_button_leeren();
            	feld_2_3_Button.setText("X");
            	uebertragen();
            }
        });
        feld_3_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_3";
            	wurf_button_leeren();
            	feld_3_3_Button.setText("X");
            	uebertragen();
            }
        });
        feld_4_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_3";
            	wurf_button_leeren();
            	feld_4_3_Button.setText("X");
            	uebertragen();
            }
        });
        feld_5_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_3";
            	wurf_button_leeren();
            	feld_5_3_Button.setText("X");
            	uebertragen();
            }
        });
        feld_1_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_4";
            	wurf_button_leeren();
            	feld_1_4_Button.setText("X");
            	uebertragen();
            }
        });
        feld_2_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_4";
            	wurf_button_leeren();
            	feld_2_4_Button.setText("X");
            	uebertragen();
            }
        });
        feld_3_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_4";
            	wurf_button_leeren();
            	feld_3_4_Button.setText("X");
            	uebertragen();
            }
        });
        feld_4_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_4";
            	wurf_button_leeren();
            	feld_4_4_Button.setText("X");
            	uebertragen();
            }
        });
        feld_5_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_4";
            	wurf_button_leeren();
            	feld_5_4_Button.setText("X");
            	uebertragen();
            }
        });
    }
	
	@Override
	public void onDestroy() {
	  super.onDestroy();
	    
	  helper.close();
	}

	public void fehl_button_leeren() {

        final Button fehl_ooll_Button = (Button) findViewById(R.id.fehl_ooll);
        final Button fehl_ool_Button = (Button) findViewById(R.id.fehl_ool);
        final Button fehl_oom_Button = (Button) findViewById(R.id.fehl_oom);
        final Button fehl_oor_Button = (Button) findViewById(R.id.fehl_oor);
        final Button fehl_oorr_Button = (Button) findViewById(R.id.fehl_oorr);
        final Button fehl_oll_Button = (Button) findViewById(R.id.fehl_oll);
        final Button fehl_ol_Button = (Button) findViewById(R.id.fehl_ol);
        final Button fehl_om_Button = (Button) findViewById(R.id.fehl_om);
        final Button fehl_or_Button = (Button) findViewById(R.id.fehl_or);
        final Button fehl_orr_Button = (Button) findViewById(R.id.fehl_orr);
        final Button fehl_mll_Button = (Button) findViewById(R.id.fehl_mll);
        final Button fehl_ml_Button = (Button) findViewById(R.id.fehl_ml);
        final Button fehl_mm_Button = (Button) findViewById(R.id.fehl_mm);
        final Button fehl_mr_Button = (Button) findViewById(R.id.fehl_mr);
        final Button fehl_mrr_Button = (Button) findViewById(R.id.fehl_mrr);
        final Button fehl_ull_Button = (Button) findViewById(R.id.fehl_ull);
        final Button fehl_ul_Button = (Button) findViewById(R.id.fehl_ul);
        final Button fehl_um_Button = (Button) findViewById(R.id.fehl_um);
        final Button fehl_ur_Button = (Button) findViewById(R.id.fehl_ur);
        final Button fehl_urr_Button = (Button) findViewById(R.id.fehl_urr);
        fehl_ooll_Button.setText("");
        fehl_ool_Button.setText("");
        fehl_oom_Button.setText("");
        fehl_oor_Button.setText("");
        fehl_oorr_Button.setText("");
        fehl_oll_Button.setText("");
        fehl_ol_Button.setText("");
        fehl_om_Button.setText("");
        fehl_or_Button.setText("");
        fehl_orr_Button.setText("");
        fehl_mll_Button.setText("");
        fehl_ml_Button.setText("");
        fehl_mm_Button.setText("");
        fehl_mr_Button.setText("");
        fehl_mrr_Button.setText("");
        fehl_ull_Button.setText("");
        fehl_ul_Button.setText("");
        fehl_um_Button.setText("");
        fehl_ur_Button.setText("");
        fehl_urr_Button.setText("");

	}

	public void wurf_button_leeren() {

        final Button feld_1_1_Button = (Button) findViewById(R.id.feld_1_1);
        final Button feld_2_1_Button = (Button) findViewById(R.id.feld_2_1);
        final Button feld_3_1_Button = (Button) findViewById(R.id.feld_3_1);
        final Button feld_4_1_Button = (Button) findViewById(R.id.feld_4_1);
        final Button feld_5_1_Button = (Button) findViewById(R.id.feld_5_1);
        final Button feld_1_2_Button = (Button) findViewById(R.id.feld_1_2);
        final Button feld_2_2_Button = (Button) findViewById(R.id.feld_2_2);
        final Button feld_3_2_Button = (Button) findViewById(R.id.feld_3_2);
        final Button feld_4_2_Button = (Button) findViewById(R.id.feld_4_2);
        final Button feld_5_2_Button = (Button) findViewById(R.id.feld_5_2);
        final Button feld_1_3_Button = (Button) findViewById(R.id.feld_1_3);
        final Button feld_2_3_Button = (Button) findViewById(R.id.feld_2_3);
        final Button feld_3_3_Button = (Button) findViewById(R.id.feld_3_3);
        final Button feld_4_3_Button = (Button) findViewById(R.id.feld_4_3);
        final Button feld_5_3_Button = (Button) findViewById(R.id.feld_5_3);
        final Button feld_1_4_Button = (Button) findViewById(R.id.feld_1_4);
        final Button feld_2_4_Button = (Button) findViewById(R.id.feld_2_4);
        final Button feld_3_4_Button = (Button) findViewById(R.id.feld_3_4);
        final Button feld_4_4_Button = (Button) findViewById(R.id.feld_4_4);
        final Button feld_5_4_Button = (Button) findViewById(R.id.feld_5_4);
        feld_1_1_Button.setText("");
        feld_2_1_Button.setText("");
        feld_3_1_Button.setText("");
        feld_4_1_Button.setText("");
        feld_5_1_Button.setText("");
        feld_1_2_Button.setText("");
        feld_2_2_Button.setText("");
        feld_3_2_Button.setText("");
        feld_4_2_Button.setText("");
        feld_5_2_Button.setText("");
        feld_1_3_Button.setText("");
        feld_2_3_Button.setText("");
        feld_3_3_Button.setText("");
        feld_4_3_Button.setText("");
        feld_5_3_Button.setText("");
        feld_1_4_Button.setText("");
        feld_2_4_Button.setText("");
        feld_3_4_Button.setText("");
        feld_4_4_Button.setText("");
        feld_5_4_Button.setText("");
        

	}
	
	public void uebertragen() {

		if(wurfecke!=null && wurfposition!=null){
			
			/* Wurfecke und -position des Spielers eingeben */ 
			helper.updateTickerWurfecke(tickerId, wurfecke);
			helper.updateTickerWurfposition(tickerId, wurfposition);

			/* Wurfecke und -position bei Torwart eintragen */
			if(wurfecke.equals("OL") || wurfecke.equals("OM") || wurfecke.equals("OR") 
					|| wurfecke.equals("ML") || wurfecke.equals("MM") || wurfecke.equals("MR")
					|| wurfecke.equals("UL") || wurfecke.equals("UM") || wurfecke.equals("UR")){
	    		if(torwartId!=null){
					Cursor cTorwart=helper.getSpielerById(torwartId);
					cTorwart.moveToFirst();    
					torwartString=helper.getSpielerName(cTorwart);
					cTorwart.close();
					if(aktionInt.equals("3")){
						torwartAktionInt="16";
						torwartAktionString=getString(R.string.tickerAktionTorwartGehalten);
					}
					if(aktionInt.equals("15")){
						torwartAktionInt="18";
						torwartAktionString=getString(R.string.tickerAktionTorwart7mGehalten);
					}
					if(aktionInt.equals("21")){
						torwartAktionInt="22";
						torwartAktionString=getString(R.string.tickerAktionTorwartTGGehalten);
					}
					if(aktionTeamHeim.equals("0")){
						torwartAktionTeamHeim="1";
					} else {
						torwartAktionTeamHeim="0";
					}
					helper.insertTicker(Integer.parseInt(torwartAktionInt), torwartAktionString, Integer.parseInt(torwartAktionTeamHeim), torwartString, 
							Integer.parseInt(torwartId), Integer.parseInt(spielId), Integer.parseInt(zeit)+1, realzeit);
					Cursor lastTickTorwartC=helper.getLastTickerId();
					lastTickTorwartC.moveToFirst();
					torwartTickerId = helper.getTickerId(lastTickTorwartC);
					helper.updateTickerWurfecke(torwartTickerId, wurfecke);
					helper.updateTickerWurfposition(torwartTickerId, wurfposition);
					lastTickTorwartC.close();
	    		}
			}
    		
    		/* Ballbesitzwechsel eintragen, falls Ballverlust */
    		RadioGroup rBallverlust=(RadioGroup)findViewById(R.id.radio_ballverlust);
    		switch(rBallverlust.getCheckedRadioButtonId()) {
    			case R.id.radio_ja:
   	    			Cursor cSpiel=helper.getSpielCursor(spielId);
   	    	    	cSpiel.moveToFirst();
   	    			if(Integer.parseInt(aktionTeamHeim)==1 && 
   	    					Integer.parseInt(helper.getSpielBallbesitz(cSpiel))==1){  // ... und Heimmannschaft Fehlwurf, dann trage Ballbesitz Auswärtsmannschaft ein
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
    				break;
    			case R.id.radio_nein:
    				
    				break;
    		}
    		
			finish();
		}
	}
}
