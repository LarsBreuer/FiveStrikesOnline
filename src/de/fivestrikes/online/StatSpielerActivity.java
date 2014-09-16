package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
import android.app.ListActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
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
import android.util.Log;


public class StatSpielerActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	/** Hinweis: Nachschauen, ob man gleiche Variablen benutzen kann */
	public final static String ID_SPIEL_EXTRA="de.fivestrikes.pro.spiel_ID";
	public final static String ID_STATTEAM_EXTRA="de.fivestrikes.pro.statteam_ID";
	public final static String ID_SPIELER_EXTRA="de.fivestrikes.pro.spieler_ID";
	Cursor model=null;
	SpielerAdapter adapter=null;
	SQLHelper helper=null;
	String mannschaftId=null;
    String spielId=null;
    String spielerId=null;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spieler);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);

		spielId=getIntent().getStringExtra(StatMenuActivity.ID_SPIEL_EXTRA);
		mannschaftId=getIntent().getStringExtra(StatMenuActivity.ID_TEAMTORE_EXTRA);

        helper=new SQLHelper(this);
        
		Cursor c=helper.getTeamById(mannschaftId);
		c.moveToFirst();    
		customTitleText.setText(helper.getTeamKuerzel(c));  
		c.close();
        
		helper.createStatSpieler(spielId, mannschaftId);
		
        model=helper.getAllStatSpieler();
        startManagingCursor(model);
        adapter=new SpielerAdapter(model);
        setListAdapter(adapter);
        spielId=getIntent().getStringExtra(TickerAktionActivity.ID_SPIEL_EXTRA);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        
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
	
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		Intent i=new Intent(StatSpielerActivity.this, StatSpielerStatActivity.class);
		
		/** Hinweis: statTor (StatTorChancen) missbraucht für spieler Id */
        Cursor c=helper.getStatTorById(String.valueOf(id));
		c.moveToFirst();  
		i.putExtra(ID_SPIELER_EXTRA, helper.getStatTorChancen(c));
        c.close();  
		i.putExtra(ID_STATTEAM_EXTRA, mannschaftId);
		i.putExtra(ID_SPIEL_EXTRA, spielId);
		startActivity(i);
	}
	
	class SpielerAdapter extends CursorAdapter {
		SpielerAdapter(Cursor c) {
			super(StatSpielerActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			SpielerHolder holder=(SpielerHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row_player, parent, false);
			SpielerHolder holder=new SpielerHolder(row);

			row.setTag(holder);

			return(row);
		}
	}
	
	static class SpielerHolder {
	    private TextView name=null;
	    private TextView nummer=null;
	    
	    SpielerHolder(View row) {
	      name=(TextView)row.findViewById(R.id.rowMannschaftName);
	      nummer=(TextView)row.findViewById(R.id.rowMannschaftNummer);
	    }
	    
	    /** Hinweis: StatTore missbraucht für Spielerlist */
	    void populateFrom(Cursor c, SQLHelper helper) {
	      name.setText(helper.getStatTorBezeichnung(c));
	      nummer.setText(helper.getStatTorTore(c));
	  
	    }
	}
}
