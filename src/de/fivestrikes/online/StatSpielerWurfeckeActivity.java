package de.fivestrikes.online;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;


public class StatSpielerWurfeckeActivity extends Activity {
    /** Called when the activity is first created. */

	Cursor model=null;
	SQLHelper helper=null;
    String wurfecke=null;
    String wurfposition=null;
    String spielerId=null;
    String spielId=null;
    String spielerPosition=null;
    int positiv;
    int negativ;
    int gesamt;
    int prozent;
    String strButton=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.stat_spieler_wurfecke);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.statSpielerstatistik); 
        
        helper=new SQLHelper(this);
        spielerId=getIntent().getStringExtra(StatSpielerStatActivity.ID_SPIELER_EXTRA);
        spielId=getIntent().getStringExtra(StatSpielerStatActivity.ID_SPIEL_EXTRA);
		Cursor c=helper.getSpielerById(spielerId);
		c.moveToFirst();
		spielerPosition=helper.getSpielerPosition(c);
		c.close();
		statistikAlle();
        
        Button backButton = (Button) findViewById(R.id.back_button);
        Button statAlleButton = (Button) findViewById(R.id.statistik_alle);
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

        /** Button Statistik gesamt */
        statAlleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	statistikAlle();
            }
        });
        
        /** Button Wurfecke*/
        fehl_ooll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOLL";
            	statistikWurfecke(wurfecke);
            	fehl_ooll_Button.setText("X");
            }
        });
        fehl_ool_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOL";
            	statistikWurfecke(wurfecke);
            	fehl_ool_Button.setText("X");
            }
        });
        fehl_oom_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOM";
            	statistikWurfecke(wurfecke);
            	fehl_oom_Button.setText("X");
            }
        });
        fehl_oor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OOR";
            	statistikWurfecke(wurfecke);
            	fehl_oor_Button.setText("X");
            }
        });
        fehl_oorr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OORR";
            	statistikWurfecke(wurfecke);
            	fehl_oorr_Button.setText("X");
            }
        });
        fehl_oll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OLL";
            	statistikWurfecke(wurfecke);
            	fehl_oll_Button.setText("X");
            }
        });
        fehl_ol_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OL";
            	statistikWurfecke(wurfecke);
            	fehl_ol_Button.setText("X");
            }
        });
        fehl_om_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OM";
            	statistikWurfecke(wurfecke);
            	fehl_om_Button.setText("X");
            }
        });
        fehl_or_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="OR";
            	statistikWurfecke(wurfecke);
            	fehl_or_Button.setText("X");
            }
        });
        fehl_orr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ORR";
            	statistikWurfecke(wurfecke);
            	fehl_orr_Button.setText("X");
            }
        });
        fehl_mll_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MLL";
            	statistikWurfecke(wurfecke);
            	fehl_mll_Button.setText("X");
            }
        });
        fehl_ml_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ML";
            	statistikWurfecke(wurfecke);
            	fehl_ml_Button.setText("X");
            }
        });
        fehl_mm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MM";
            	statistikWurfecke(wurfecke);
            	fehl_mm_Button.setText("X");
            }
        });
        fehl_mr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MR";
            	statistikWurfecke(wurfecke);
            	fehl_mr_Button.setText("X");
            }
        });
        fehl_mrr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="MRR";
            	statistikWurfecke(wurfecke);
            	fehl_mrr_Button.setText("X");
            }
        });
        fehl_ull_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="ULL";
            	statistikWurfecke(wurfecke);
            	fehl_ull_Button.setText("X");
            }
        });
        fehl_ul_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UL";
            	statistikWurfecke(wurfecke);
            	fehl_ul_Button.setText("X");
            }
        });
        fehl_um_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UM";
            	statistikWurfecke(wurfecke);
            	fehl_um_Button.setText("X");
            }
        });
        fehl_ur_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="UR";
            	statistikWurfecke(wurfecke);
            	fehl_ur_Button.setText("X");
            }
        });
        fehl_urr_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfecke="URR";
            	statistikWurfecke(wurfecke);
            	fehl_urr_Button.setText("X");
            }
        });
        
        /** Button Wurfposition*/
        feld_1_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_1";
            	statistikWurfposition(wurfposition);
            	feld_1_1_Button.setText("X");
            }
        });
        feld_2_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_1";
            	statistikWurfposition(wurfposition);
            	feld_2_1_Button.setText("X");
            }
        });
        feld_3_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_1";
            	statistikWurfposition(wurfposition);
            	feld_3_1_Button.setText("X");
            }
        });
        feld_4_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_1";
            	statistikWurfposition(wurfposition);
            	feld_4_1_Button.setText("X");
            }
        });
        feld_5_1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_1";
            	statistikWurfposition(wurfposition);
            	feld_5_1_Button.setText("X");
            }
        });
        feld_1_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_2";
            	statistikWurfposition(wurfposition);
            	feld_1_2_Button.setText("X");
            }
        });
        feld_2_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_2";
            	statistikWurfposition(wurfposition);
            	feld_2_2_Button.setText("X");
            }
        });
        feld_3_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_2";
            	statistikWurfposition(wurfposition);
            	feld_3_2_Button.setText("X");
            }
        });
        feld_4_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_2";
            	statistikWurfposition(wurfposition);
            	feld_4_2_Button.setText("X");
            }
        });
        feld_5_2_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_2";
            	statistikWurfposition(wurfposition);
            	feld_5_2_Button.setText("X");
            }
        });
        feld_1_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_3";
            	statistikWurfposition(wurfposition);
            	feld_1_3_Button.setText("X");
            }
        });
        feld_2_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_3";
            	statistikWurfposition(wurfposition);
            	feld_2_3_Button.setText("X");
            }
        });
        feld_3_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_3";
            	statistikWurfposition(wurfposition);
            	feld_3_3_Button.setText("X");
            }
        });
        feld_4_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_3";
            	statistikWurfposition(wurfposition);
            	feld_4_3_Button.setText("X");
            }
        });
        feld_5_3_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_3";
            	statistikWurfposition(wurfposition);
            	feld_5_3_Button.setText("X");
            }
        });
        feld_1_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="1_4";
            	statistikWurfposition(wurfposition);
            	feld_1_4_Button.setText("X");
            }
        });
        feld_2_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="2_4";
            	statistikWurfposition(wurfposition);
            	feld_2_4_Button.setText("X");
            }
        });
        feld_3_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="3_4";
            	statistikWurfposition(wurfposition);
            	feld_3_4_Button.setText("X");
            }
        });
        feld_4_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="4_4";
            	statistikWurfposition(wurfposition);
            	feld_4_4_Button.setText("X");

            }
        });
        feld_5_4_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	wurfposition="5_4";
            	statistikWurfposition(wurfposition);
            	feld_5_4_Button.setText("X");

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
	
	public void statistikAlle() {
		
    	fehl_button_leeren();
    	wurf_button_leeren();
		String statWurfecke=null;
		String statWurfposition=null;
		
		/* Beschriftung der Wurfecken */
		
		final Button fehl_ooll_Button = (Button) findViewById(R.id.fehl_ooll);
		wurfecke="OOLL";
		fehl_ooll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
		final Button fehl_ool_Button = (Button) findViewById(R.id.fehl_ool);
		wurfecke="OOL";
		fehl_ool_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oom_Button = (Button) findViewById(R.id.fehl_oom);
		wurfecke="OOM";
		fehl_oom_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oor_Button = (Button) findViewById(R.id.fehl_oor);
		wurfecke="OOR";
		fehl_oor_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oorr_Button = (Button) findViewById(R.id.fehl_oorr);
		wurfecke="OORR";
		fehl_oorr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oll_Button = (Button) findViewById(R.id.fehl_oll);
		wurfecke="OLL";
		fehl_oll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ol_Button = (Button) findViewById(R.id.fehl_ol);
		wurfecke="OL";
		fehl_ol_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_om_Button = (Button) findViewById(R.id.fehl_om);
		wurfecke="OM";
		fehl_om_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_or_Button = (Button) findViewById(R.id.fehl_or);
		wurfecke="OR";
		fehl_or_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_orr_Button = (Button) findViewById(R.id.fehl_orr);
		wurfecke="ORR";
		fehl_orr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mll_Button = (Button) findViewById(R.id.fehl_mll);
		wurfecke="MLL";
		fehl_mll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ml_Button = (Button) findViewById(R.id.fehl_ml);
		wurfecke="ML";
		fehl_ml_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mm_Button = (Button) findViewById(R.id.fehl_mm);		
        wurfecke="MM";
		fehl_mm_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mr_Button = (Button) findViewById(R.id.fehl_mr);
		wurfecke="MR";
		fehl_mr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mrr_Button = (Button) findViewById(R.id.fehl_mrr);
		wurfecke="MRR";
		fehl_mrr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ull_Button = (Button) findViewById(R.id.fehl_ull);
		wurfecke="ULL";
		fehl_ull_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ul_Button = (Button) findViewById(R.id.fehl_ul);
		wurfecke="UL";
		fehl_ul_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_um_Button = (Button) findViewById(R.id.fehl_um);
		wurfecke="UM";
		fehl_um_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ur_Button = (Button) findViewById(R.id.fehl_ur);
		wurfecke="UR";
		fehl_ur_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_urr_Button = (Button) findViewById(R.id.fehl_urr);
		wurfecke="URR";
		fehl_urr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
		/* Beschriftung der Positionsfelder */
		
        final Button feld_1_1_Button = (Button) findViewById(R.id.feld_1_1);
        wurfposition="1_1";
        feld_1_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_1_Button = (Button) findViewById(R.id.feld_2_1);
        wurfposition="2_1";
        feld_2_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_1_Button = (Button) findViewById(R.id.feld_3_1);
        wurfposition="3_1";
        feld_3_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_1_Button = (Button) findViewById(R.id.feld_4_1);
        wurfposition="4_1";
        feld_4_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_1_Button = (Button) findViewById(R.id.feld_5_1);
        wurfposition="5_1";
        feld_5_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_2_Button = (Button) findViewById(R.id.feld_1_2);
        wurfposition="1_2";
        feld_1_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_2_Button = (Button) findViewById(R.id.feld_2_2);
        wurfposition="2_2";
        feld_2_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_2_Button = (Button) findViewById(R.id.feld_3_2);
        wurfposition="3_2";
        feld_3_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_2_Button = (Button) findViewById(R.id.feld_4_2);
        wurfposition="4_2";
        feld_4_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_2_Button = (Button) findViewById(R.id.feld_5_2);
        wurfposition="5_2";
        feld_5_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_3_Button = (Button) findViewById(R.id.feld_1_3);
        wurfposition="1_3";
        feld_1_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_3_Button = (Button) findViewById(R.id.feld_2_3);
        wurfposition="2_3";
        feld_2_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_3_Button = (Button) findViewById(R.id.feld_3_3);
        wurfposition="3_3";
        feld_3_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_3_Button = (Button) findViewById(R.id.feld_4_3);
        wurfposition="4_3";
        feld_4_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_3_Button = (Button) findViewById(R.id.feld_5_3);
        wurfposition="5_3";
        feld_5_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_4_Button = (Button) findViewById(R.id.feld_1_4);
        wurfposition="1_4";
        feld_1_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_4_Button = (Button) findViewById(R.id.feld_2_4);
        wurfposition="2_4";
        feld_2_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_4_Button = (Button) findViewById(R.id.feld_3_4);
        wurfposition="3_4";
        feld_3_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_4_Button = (Button) findViewById(R.id.feld_4_4);
        wurfposition="4_4";
        feld_4_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_4_Button = (Button) findViewById(R.id.feld_5_4);
        wurfposition="5_4";
        feld_5_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
	}
	
	public void statistikWurfecke(String statWurfecke) {
		
    	fehl_button_leeren();
    	wurf_button_leeren();
    	
		/* Beschriftung der Positionsfelder */
		
        final Button feld_1_1_Button = (Button) findViewById(R.id.feld_1_1);
        wurfposition="1_1";
        feld_1_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_1_Button = (Button) findViewById(R.id.feld_2_1);
        wurfposition="2_1";
        feld_2_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_1_Button = (Button) findViewById(R.id.feld_3_1);
        wurfposition="3_1";
        feld_3_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_1_Button = (Button) findViewById(R.id.feld_4_1);
        wurfposition="4_1";
        feld_4_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_1_Button = (Button) findViewById(R.id.feld_5_1);
        wurfposition="5_1";
        feld_5_1_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_2_Button = (Button) findViewById(R.id.feld_1_2);
        wurfposition="1_2";
        feld_1_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_2_Button = (Button) findViewById(R.id.feld_2_2);
        wurfposition="2_2";
        feld_2_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_2_Button = (Button) findViewById(R.id.feld_3_2);
        wurfposition="3_2";
        feld_3_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_2_Button = (Button) findViewById(R.id.feld_4_2);
        wurfposition="4_2";
        feld_4_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_2_Button = (Button) findViewById(R.id.feld_5_2);
        wurfposition="5_2";
        feld_5_2_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_3_Button = (Button) findViewById(R.id.feld_1_3);
        wurfposition="1_3";
        feld_1_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_3_Button = (Button) findViewById(R.id.feld_2_3);
        wurfposition="2_3";
        feld_2_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_3_Button = (Button) findViewById(R.id.feld_3_3);
        wurfposition="3_3";
        feld_3_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_3_Button = (Button) findViewById(R.id.feld_4_3);
        wurfposition="4_3";
        feld_4_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_3_Button = (Button) findViewById(R.id.feld_5_3);
        wurfposition="5_3";
        feld_5_3_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_1_4_Button = (Button) findViewById(R.id.feld_1_4);
        wurfposition="1_4";
        feld_1_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_2_4_Button = (Button) findViewById(R.id.feld_2_4);
        wurfposition="2_4";
        feld_2_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_3_4_Button = (Button) findViewById(R.id.feld_3_4);
        wurfposition="3_4";
        feld_3_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_4_4_Button = (Button) findViewById(R.id.feld_4_4);
        wurfposition="4_4";
        feld_4_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
        
        final Button feld_5_4_Button = (Button) findViewById(R.id.feld_5_4);
        wurfposition="5_4";
        feld_5_4_Button.setText(beschriftungPositionAlle(wurfposition, statWurfecke));
		
	}
	
	public void statistikWurfposition(String statWurfposition) {
		
    	fehl_button_leeren();
    	wurf_button_leeren();
		
		/* Beschriftung der Wurfecken */
		
		final Button fehl_ooll_Button = (Button) findViewById(R.id.fehl_ooll);
		wurfecke="OOLL";
		fehl_ooll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
		final Button fehl_ool_Button = (Button) findViewById(R.id.fehl_ool);
		wurfecke="OOL";
		fehl_ool_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oom_Button = (Button) findViewById(R.id.fehl_oom);
		wurfecke="OOM";
		fehl_oom_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oor_Button = (Button) findViewById(R.id.fehl_oor);
		wurfecke="OOR";
		fehl_oor_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oorr_Button = (Button) findViewById(R.id.fehl_oorr);
		wurfecke="OORR";
		fehl_oorr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_oll_Button = (Button) findViewById(R.id.fehl_oll);
		wurfecke="OLL";
		fehl_oll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ol_Button = (Button) findViewById(R.id.fehl_ol);
		wurfecke="OL";
		fehl_ol_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_om_Button = (Button) findViewById(R.id.fehl_om);
		wurfecke="OM";
		fehl_om_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_or_Button = (Button) findViewById(R.id.fehl_or);
		wurfecke="OR";
		fehl_or_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_orr_Button = (Button) findViewById(R.id.fehl_orr);
		wurfecke="ORR";
		fehl_orr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mll_Button = (Button) findViewById(R.id.fehl_mll);
		wurfecke="MLL";
		fehl_mll_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ml_Button = (Button) findViewById(R.id.fehl_ml);
		wurfecke="ML";
		fehl_ml_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mm_Button = (Button) findViewById(R.id.fehl_mm);		
        wurfecke="MM";
		fehl_mm_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mr_Button = (Button) findViewById(R.id.fehl_mr);
		wurfecke="MR";
		fehl_mr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_mrr_Button = (Button) findViewById(R.id.fehl_mrr);
		wurfecke="MRR";
		fehl_mrr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ull_Button = (Button) findViewById(R.id.fehl_ull);
		wurfecke="ULL";
		fehl_ull_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ul_Button = (Button) findViewById(R.id.fehl_ul);
		wurfecke="UL";
		fehl_ul_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_um_Button = (Button) findViewById(R.id.fehl_um);
		wurfecke="UM";
		fehl_um_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_ur_Button = (Button) findViewById(R.id.fehl_ur);
		wurfecke="UR";
		fehl_ur_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
        final Button fehl_urr_Button = (Button) findViewById(R.id.fehl_urr);
		wurfecke="URR";
		fehl_urr_Button.setText(beschriftungWurfeckeAlle(wurfecke, statWurfposition));
		
	}

	public String beschriftungWurfeckeAlle(String wurfecke, String statWurfposition) {
		positiv=0;
		negativ=0;
		if(spielerPosition.equals(getString(R.string.spielerPositionTorwart))){
			positiv = helper.countTickerGehaltenWurfeckeGesamt(spielId, spielerId, wurfecke, statWurfposition);
			negativ = helper.countTickerGegentorWurfeckeGesamt(spielId, spielerId, wurfecke, statWurfposition);
		} else {
			positiv = helper.countTickerTorWurfeckeGesamt(spielId, spielerId, wurfecke, statWurfposition);
			negativ = helper.countTickerFehlwurfWurfeckeGesamt(spielId, spielerId, wurfecke, statWurfposition);
		}
		gesamt = positiv + negativ;
		if(gesamt>0){
			prozent=positiv*100/gesamt;
			strButton=positiv + " / " + gesamt + "\n" + prozent + " %";
		} else {
			strButton="";
		}
		return(strButton);
	}
	
	public String beschriftungPositionAlle(String wurfposition, String statWurfecke) {
		positiv=0;
		negativ=0;
		if(spielerPosition.equals(getString(R.string.spielerPositionTorwart))){
			positiv = helper.countTickerGehaltenPositionGesamt(spielId, spielerId, wurfposition, statWurfecke);
			negativ = helper.countTickerGegentorPositionGesamt(spielId, spielerId, wurfposition, statWurfecke);
		} else {
			positiv = helper.countTickerTorPositionGesamt(spielId, spielerId, wurfposition, statWurfecke);
			negativ = helper.countTickerFehlwurfPositionGesamt(spielId, spielerId, wurfposition, statWurfecke);
		}
		gesamt = positiv + negativ;
		if(gesamt>0){
			prozent=positiv*100/gesamt;
			strButton=positiv + " / " + gesamt + "\n" + prozent + " %";
		} else {
			strButton="";
		}
		return(strButton);
	}
}
