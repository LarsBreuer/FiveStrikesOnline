package de.fivestrikes.online;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;


public class TickerEditWurfeckeActivity extends Activity {
    /** Called when the activity is first created. */

	Cursor model=null;
	SQLHelper helper=null;
	String spielId=null;
	String tickerId=null;
    String torwartTickerId=null;
    String wurfecke=null;
    String wurfposition=null;
    String aktionTeamHeim=null;
    String torwartId=null;
    String aktionInt=null;
    String torwartAktionInt=null;
    String torwartAktionString=null;
    String torwartString=null;
    String zeit=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.ticker_edit_wurfecke);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.tickerAktionWurfecke);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei andren Activitys verfahren*/
        
        helper=new SQLHelper(this);
        wurfecke=getIntent().getStringExtra(TickerEditActivity.ID_TICKEREDITWURFECKE_EXTRA);
        
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
        
        if (wurfecke.equals("OOLL")){
        	fehl_ooll_Button.setText("X");
        }
        if (wurfecke.equals("OOL")){
        	fehl_ool_Button.setText("X");
        }
        if (wurfecke.equals("OOM")){
        	fehl_oom_Button.setText("X");
        }
        if (wurfecke.equals("OOR")){
        	fehl_oor_Button.setText("X");
        }
        if (wurfecke.equals("OORR")){
        	fehl_oorr_Button.setText("X");
        }
        if (wurfecke.equals("OLL")){
        	fehl_oll_Button.setText("X");
        }
        if (wurfecke.equals("OL")){
        	fehl_ol_Button.setText("X");
        }
        if (wurfecke.equals("OM")){
        	fehl_om_Button.setText("X");
        }
        if (wurfecke.equals("OR")){
        	fehl_or_Button.setText("X");
        }
        if (wurfecke.equals("ORR")){
        	fehl_orr_Button.setText("X");
        }
        if (wurfecke.equals("MLL")){
        	fehl_mll_Button.setText("X");
        }
        if (wurfecke.equals("ML")){
        	fehl_ml_Button.setText("X");
        }
        if (wurfecke.equals("MM")){
        	fehl_mm_Button.setText("X");
        }
        if (wurfecke.equals("MR")){
        	fehl_mr_Button.setText("X");
        }
        if (wurfecke.equals("MRR")){
        	fehl_mrr_Button.setText("X");
        }
        if (wurfecke.equals("ULL")){
        	fehl_ull_Button.setText("X");
        }
        if (wurfecke.equals("UL")){
        	fehl_ul_Button.setText("X");
        }
        if (wurfecke.equals("UM")){
        	fehl_um_Button.setText("X");
        }
        if (wurfecke.equals("UR")){
        	fehl_ur_Button.setText("X");
        }
        if (wurfecke.equals("URR")){
        	fehl_urr_Button.setText("X");
        }

        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });

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
	
	public void uebertragen() {

		Intent i=new Intent();
		i.putExtra("Activity", "Wurfecke");
		i.putExtra("Wurfecke", wurfecke);
		setResult(RESULT_OK, i);
		finish();
		
	}
}
