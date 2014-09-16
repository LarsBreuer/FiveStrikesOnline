package de.fivestrikes.online;

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


public class SpielerActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	public final static String ID_SPIELER_EXTRA="de.fivestrikes.pro.spieler_ID";
	public final static String ID_MANNSCHAFT_EXTRA="de.fivestrikes.pro.mannschaft_ID";
	int mannschaft_ID=0;
	Cursor model=null;
	SpielerAdapter adapter=null;
	SQLHelper helper=null;
	String mannschaftId=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.spieler);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back_plus);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackPlusText);
        customTitleText.setText(R.string.spielerTitel);
        
        helper=new SQLHelper(this);
        mannschaftId=getIntent().getStringExtra(MannschaftEditActivity.ID_MANNSCHAFT_EXTRA);
        model=helper.getAllSpieler(mannschaftId);
        startManagingCursor(model);
        adapter=new SpielerAdapter(model);
        setListAdapter(adapter);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        Button plusButton = (Button) findViewById(R.id.plus_button);
        
        /** Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
        });
 
        /** Button Spieler hinzufügen */
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent newIntent = new Intent(SpielerActivity.this, SpielerEditActivity.class);
            	newIntent.putExtra(ID_MANNSCHAFT_EXTRA, mannschaftId);
            	startActivity(newIntent);
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
		Intent i=new Intent(SpielerActivity.this, SpielerEditActivity.class);
		
		i.putExtra(ID_SPIELER_EXTRA, String.valueOf(id));
		i.putExtra(ID_MANNSCHAFT_EXTRA, mannschaftId);
		startActivity(i);
	}
	
	class SpielerAdapter extends CursorAdapter {
		SpielerAdapter(Cursor c) {
			super(SpielerActivity.this, c);
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
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	      name.setText(helper.getSpielerName(c));
	      nummer.setText(helper.getSpielerNummer(c));
	  
	    }
	}
}
