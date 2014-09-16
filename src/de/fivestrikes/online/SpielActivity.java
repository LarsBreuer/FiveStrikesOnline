package de.fivestrikes.online;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;


public class SpielActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	public final static String ID_EXTRA="de.fivestrikes.pro._ID";
	Cursor model=null;
	SpielAdapter adapter=null;
	SQLHelper helper=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spiel);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back_plus);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackPlusText);
        customTitleText.setText(R.string.spielTitel);
        
        helper=new SQLHelper(this);
        model=helper.getAllSpiel();
        startManagingCursor(model);
        adapter=new SpielAdapter(model);
        setListAdapter(adapter);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        Button plusButton = (Button) findViewById(R.id.plus_button);
        
        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
 
        /* Button Spiel hinzufügen */
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	startActivity(new Intent(SpielActivity.this, SpielEditActivity.class));
            }
        });
    }
	
	@Override
	public void onDestroy() {
	  super.onDestroy();
	    
	  helper.close();
	  model.close();
	}
	
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		Intent i=new Intent(SpielActivity.this, SpielEditActivity.class);
		
		i.putExtra(ID_EXTRA, String.valueOf(id));
		startActivity(i);
	}
	
	class SpielAdapter extends CursorAdapter {
		SpielAdapter(Cursor c) {
			super(SpielActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			SpielHolder holder=(SpielHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_spiel, parent, false);
			SpielHolder holder=new SpielHolder(row);

			row.setTag(holder);

			return(row);
		}
	}
	
	static class SpielHolder {
	    private TextView datum=null;
	    private TextView spielHeim=null;
	    private TextView spielAusw=null;
	    private int day;
	    private int month;
	    private int year;
	    
	    SpielHolder(View row) {
	    	datum=(TextView)row.findViewById(R.id.rowSpielDatum);
	    	spielHeim=(TextView)row.findViewById(R.id.rowSpielHeim);
	    	spielAusw=(TextView)row.findViewById(R.id.rowSpielAusw);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	    	String strSpielDatum = helper.getSpielDatum(c);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try
			{
				Date spielDatum = sdf.parse(strSpielDatum);
				day = spielDatum.getDate();    // getDate => Day of month; getDay => day of week
				month = spielDatum.getMonth();
				year = spielDatum.getYear()+1900;
			}
			catch (ParseException e) {}
			datum.setText(new StringBuilder()
			// Month is 0 based, just add 1
				.append(day).append(".").append(month + 1).append(".")
				.append(year).append(" "));
	    	spielHeim.setText(helper.getTeamHeimNameBySpielID(c) + " -");
	    	spielAusw.setText(helper.getTeamAuswNameBySpielID(c));
	    }
	}
}
