package de.fivestrikes.online;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.graphics.Color;

public class StatNotizActivity extends Activity {
	Cursor model=null;
	SQLHelper helper=null;
	String spielId=null;
	String notiz=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.stat_notiz);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.notiz);
		
		spielId=getIntent().getStringExtra(StatMenuActivity.ID_SPIEL_EXTRA);
		
        Button backButton = (Button) findViewById(R.id.back_button);
        
        helper=new SQLHelper(this);
        Cursor c=helper.getSpielCursor(spielId);
		c.moveToFirst();
		TextView text = (TextView) findViewById(R.id.notiz);
		text.setText(helper.getSpielNotiz(c));
		c.close();
        
        /* Button zurück */
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
}