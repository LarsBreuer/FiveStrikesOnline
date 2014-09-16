package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
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

public class StatToreActivity extends ListActivity {
	Cursor model=null;
	StatToreAdapter adapter=null;
	SQLHelper helper=null;
	String spielId=null;
	String idteamtore=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mannschaft);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        
		spielId=getIntent().getStringExtra(StatMenuActivity.ID_SPIEL_EXTRA);
		idteamtore=getIntent().getStringExtra(StatMenuActivity.ID_TEAMTORE_EXTRA);

        helper=new SQLHelper(this);
        
		Cursor c=helper.getTeamById(idteamtore);
		c.moveToFirst();    
		customTitleText.setText(helper.getTeamKuerzel(c));  
		c.close();
        
        Button backButton = (Button) findViewById(R.id.back_button);
        
        helper.createStatTore(spielId, idteamtore, this);
        
        model=helper.getAllStatTore();
        startManagingCursor(model);
        adapter=new StatToreAdapter(model);
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
	
	class StatToreAdapter extends CursorAdapter {
		StatToreAdapter(Cursor c) {
			super(StatToreActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			StatTorHolder holder=(StatTorHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
			TextView bezeichnung=(TextView)row.findViewById(R.id.torBezeichnung);
			TextView tore=(TextView)row.findViewById(R.id.torTore);
			TextView chancen=(TextView)row.findViewById(R.id.torChance);
			TextView quote=(TextView)row.findViewById(R.id.torQuote);
			if(helper.getStatTorBezeichnung(c).equals("Name")){
				row.setBackgroundResource(R.drawable.tablecell44blau);
				bezeichnung.setTextColor(Color.WHITE);
				tore.setTextColor(Color.WHITE);
				chancen.setTextColor(Color.WHITE);
				quote.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundResource(R.drawable.tablecell44none);
				bezeichnung.setTextColor(getResources().getColor(R.color.standardfarbe));
				tore.setTextColor(getResources().getColor(R.color.standardfarbe));
				chancen.setTextColor(getResources().getColor(R.color.standardfarbe));
				quote.setTextColor(getResources().getColor(R.color.standardfarbe));
			}
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {			
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_stat_tor, parent, false);
			StatTorHolder holder=new StatTorHolder(row);

			row.setTag(holder);
			TextView bezeichnung=(TextView)row.findViewById(R.id.torBezeichnung);
			TextView tore=(TextView)row.findViewById(R.id.torTore);
			TextView chancen=(TextView)row.findViewById(R.id.torChance);
			TextView quote=(TextView)row.findViewById(R.id.torQuote);
			if(helper.getStatSpielBezeichnung(c).equals("Name")){  // Hinweis: statt Name Dictionary verwenden
				row.setBackgroundResource(R.drawable.tablecell44blau);
				bezeichnung.setTextColor(Color.WHITE);
				tore.setTextColor(Color.WHITE);
				chancen.setTextColor(Color.WHITE);
				quote.setTextColor(Color.WHITE);
			}
			else{
				row.setBackgroundResource(R.drawable.tablecell44none);
				bezeichnung.setTextColor(getResources().getColor(R.color.standardfarbe));
				tore.setTextColor(getResources().getColor(R.color.standardfarbe));
				chancen.setTextColor(getResources().getColor(R.color.standardfarbe));
				quote.setTextColor(getResources().getColor(R.color.standardfarbe));
			}

			return(row);
		}
	}
	
	static class StatTorHolder {
	    private TextView bezeichnung=null;
	    private TextView tore=null;
	    private TextView chancen=null;
	    private TextView quote=null;
	    
	    StatTorHolder(View row) {
			bezeichnung=(TextView)row.findViewById(R.id.torBezeichnung);
			tore=(TextView)row.findViewById(R.id.torTore);
			chancen=(TextView)row.findViewById(R.id.torChance);
			quote=(TextView)row.findViewById(R.id.torQuote);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	    	bezeichnung.setText(helper.getStatTorBezeichnung(c));
	    	tore.setText(helper.getStatTorTore(c));
	    	chancen.setText(helper.getStatTorChancen(c));
	    	quote.setText(helper.getStatTorQuote(c));
	    }
	}
}