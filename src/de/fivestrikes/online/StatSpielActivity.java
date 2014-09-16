package de.fivestrikes.online;

// import de.fivestrikes.lite.R;
import android.app.ListActivity;
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

public class StatSpielActivity extends ListActivity {
	Cursor model=null;
	StatSpielAdapter adapter=null;
	SQLHelper helper=null;
	String spielId=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mannschaft);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.statSpielTitel);
		
		spielId=getIntent().getStringExtra(StatMenuActivity.ID_SPIEL_EXTRA);
		
        Button backButton = (Button) findViewById(R.id.back_button);
        
        helper=new SQLHelper(this);
        
        helper.createStatSpiel(spielId, this);
        
        model=helper.getAllStatSpiel();
        startManagingCursor(model);
        adapter=new StatSpielAdapter(model);
        setListAdapter(adapter);
        
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
	
	class StatSpielAdapter extends CursorAdapter {
		StatSpielAdapter(Cursor c) {
			super(StatSpielActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			StatSpielHolder holder=(StatSpielHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
			TextView teamHeim=(TextView)row.findViewById(R.id.txtTeamHeim);
			TextView bezeichnung=(TextView)row.findViewById(R.id.txtBezeichnung);
			TextView teamAusw=(TextView)row.findViewById(R.id.txtTeamAusw);
			if(helper.getStatSpielBezeichnung(c).equals("") ||
					helper.getStatSpielBezeichnung(c).equals("Spielstatistik") || 
					helper.getStatSpielBezeichnung(c).equals("Spielfilm") ||
					helper.getStatSpielBezeichnung(c).equals("Strafen")){
				row.setBackgroundResource(R.drawable.tablecell44blau);
				teamHeim.setTextColor(Color.WHITE);
				bezeichnung.setTextColor(Color.WHITE);
				teamAusw.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundResource(R.drawable.tablecell44none);
				teamHeim.setTextColor(getResources().getColor(R.color.standardfarbe));
				bezeichnung.setTextColor(getResources().getColor(R.color.standardfarbe));
				teamAusw.setTextColor(getResources().getColor(R.color.standardfarbe));
			}
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {			
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_stat_spiel, parent, false);
			StatSpielHolder holder=new StatSpielHolder(row);

			row.setTag(holder);
			TextView teamHeim=(TextView)row.findViewById(R.id.txtTeamHeim);
			TextView bezeichnung=(TextView)row.findViewById(R.id.txtBezeichnung);
			TextView teamAusw=(TextView)row.findViewById(R.id.txtTeamAusw);
			if(helper.getStatSpielBezeichnung(c).equals("") ||
					helper.getStatSpielBezeichnung(c).equals("Spielstatistik") || 
					helper.getStatSpielBezeichnung(c).equals("Spielfilm") ||
					helper.getStatSpielBezeichnung(c).equals("Strafen")){
				row.setBackgroundResource(R.drawable.tablecell44blau);
				teamHeim.setTextColor(Color.WHITE);
				bezeichnung.setTextColor(Color.WHITE);
				teamAusw.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundResource(R.drawable.tablecell44none);
				teamHeim.setTextColor(getResources().getColor(R.color.standardfarbe));
				bezeichnung.setTextColor(getResources().getColor(R.color.standardfarbe));
				teamAusw.setTextColor(getResources().getColor(R.color.standardfarbe));
			}

			return(row);
		}
	}
	
	static class StatSpielHolder {
	    private TextView teamHeim=null;
	    private TextView bezeichnung=null;
	    private TextView teamAusw=null;
	    
	    StatSpielHolder(View row) {
	    	teamHeim=(TextView)row.findViewById(R.id.txtTeamHeim);
	    	bezeichnung=(TextView)row.findViewById(R.id.txtBezeichnung);
	    	teamAusw=(TextView)row.findViewById(R.id.txtTeamAusw);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	    	teamHeim.setText(helper.getStatSpielTeamHeim(c));
	    	bezeichnung.setText(helper.getStatSpielBezeichnung(c));
	    	teamAusw.setText(helper.getStatSpielTeamAusw(c));
	    }
	}
}