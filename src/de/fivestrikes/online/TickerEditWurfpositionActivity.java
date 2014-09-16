package de.fivestrikes.online;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Button;


public class TickerEditWurfpositionActivity extends Activity {
    /** Called when the activity is first created. */

	Cursor model=null;
	SQLHelper helper=null;
	String tickerId=null;
    String torwartTickerId=null;
    String wurfecke=null;
    String wurfposition=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.ticker_edit_position);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        getWindow().setWindowAnimations(0);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.tickerAktionWurfecke);
        
        /** Hinweis: ID_TEAM_EXTRA bei Ticker Aktion löschen, wenn Übertragung von TickerActivity funktioniert. 
         *           Genauso bei andren Activitys verfahren*/
        
        helper=new SQLHelper(this);
        wurfposition=getIntent().getStringExtra(TickerEditActivity.ID_TICKEREDITPOSITION_EXTRA);
        
        Button backButton = (Button) findViewById(R.id.back_button);
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
        
        if(wurfposition.equals("1_1")){
        	feld_1_1_Button.setText("X");
        }
        if(wurfposition.equals("2_1")){
        	feld_2_1_Button.setText("X");
        }
        if(wurfposition.equals("3_1")){
        	feld_3_1_Button.setText("X");
        }
        if(wurfposition.equals("4_1")){
        	feld_4_1_Button.setText("X");
        }
        if(wurfposition.equals("5_1")){
        	feld_5_1_Button.setText("X");
        }
        if(wurfposition.equals("1_2")){
        	feld_1_2_Button.setText("X");
        }
        if(wurfposition.equals("2_2")){
        	feld_2_2_Button.setText("X");
        }
        if(wurfposition.equals("3_2")){
        	feld_3_2_Button.setText("X");
        }
        if(wurfposition.equals("4_2")){
        	feld_4_2_Button.setText("X");
        }
        if(wurfposition.equals("5_2")){
        	feld_5_2_Button.setText("X");
        }
        if(wurfposition.equals("1_3")){
        	feld_1_3_Button.setText("X");
        }
        if(wurfposition.equals("2_3")){
        	feld_2_3_Button.setText("X");
        }
        if(wurfposition.equals("3_3")){
        	feld_3_3_Button.setText("X");
        }
        if(wurfposition.equals("4_3")){
        	feld_4_3_Button.setText("X");
        }
        if(wurfposition.equals("5_3")){
        	feld_5_3_Button.setText("X");
        }
        if(wurfposition.equals("1_4")){
        	feld_1_4_Button.setText("X");
        }
        if(wurfposition.equals("2_4")){
        	feld_2_4_Button.setText("X");
        }
        if(wurfposition.equals("3_4")){
        	feld_3_4_Button.setText("X");
        }
        if(wurfposition.equals("4_4")){
        	feld_4_4_Button.setText("X");
        }
        if(wurfposition.equals("5_4")){
        	feld_5_4_Button.setText("X");
        }

    	
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
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
		
		Intent i=new Intent();
		i.putExtra("Activity", "Wurfposition");
		i.putExtra("Wurfposition", wurfposition);
		setResult(RESULT_OK, i);
		finish();
		
	}
}
