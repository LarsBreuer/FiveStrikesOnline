package de.fivestrikes.online;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TickerInfoActivity extends Activity {
	SQLHelper helper=null;
	String spielId=null;
	EditText notiz=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ticker_info);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);

        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.info);

	    Button backButton = (Button) findViewById(R.id.back_button);
	    CheckBox chkSpieler = (CheckBox) findViewById(R.id.checkbox_spieler_eingeben);
	    CheckBox chkWurfecke = (CheckBox) findViewById(R.id.checkbox_wurfecke_eingeben);
	    notiz=(EditText)findViewById(R.id.notiz);

        spielId=getIntent().getStringExtra(TickerActivity.ID_SPIEL_EXTRA);
        helper=new SQLHelper(this);
		Cursor c=helper.getSpielCursor(spielId);
		c.moveToFirst();
		if(helper.getSpielSpielerEingabe(c)!=null){
			if(helper.getSpielSpielerEingabe(c).equals("1")){
				chkSpieler.setChecked(true);
			} else {
				chkSpieler.setChecked(false);
			}
		} else {
			chkSpieler.setChecked(true);
		}
		if(helper.getSpielSpielerWurfecke(c)!=null){
			if(helper.getSpielSpielerWurfecke(c).equals("1")){
				chkWurfecke.setChecked(true);
			} else {
				chkWurfecke.setChecked(false);
			}
		} else {
			chkWurfecke.setChecked(true);
		}
		notiz.setText(helper.getSpielNotiz(c));
		c.close();

        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					helper.updateSpielNotiz(spielId, notiz.getText().toString());
					finish();
				}
			});

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	    helper.close();
	}
	
	/*
	@Override
	public void onStop() {
		super.onStop();
		Log.v("TickerInfoActivity", "onStop Start");
	    helper.close();
	    Log.v("TickerInfoActivity", "onStop Ende");
	}
	*/
	
	public void onCheckboxClicked(View view) {
		boolean checked = ((CheckBox) view).isChecked();
	    CheckBox chkSpieler = (CheckBox) findViewById(R.id.checkbox_spieler_eingeben);
	    CheckBox chkWurfecke = (CheckBox) findViewById(R.id.checkbox_wurfecke_eingeben);
		switch(view.getId()) {
		case R.id.checkbox_spieler_eingeben:
			if (checked) {
				helper.updateSpielSpielerEingeben(spielId, 1);
			} else {
				helper.updateSpielSpielerEingeben(spielId, 0);
				helper.updateSpielSpielerWurfecke(spielId, 0);
				chkWurfecke.setChecked(false);
			}
				
			break;
		case R.id.checkbox_wurfecke_eingeben:
			if (checked) {
				helper.updateSpielSpielerWurfecke(spielId, 1);
				helper.updateSpielSpielerEingeben(spielId, 1);
				chkSpieler.setChecked(true);
			} else {
				helper.updateSpielSpielerWurfecke(spielId, 0);
				helper.updateSpielSpielerEingeben(spielId, 1);
				chkSpieler.setChecked(true);
			}
				
			break;
			
		}
	}
}
