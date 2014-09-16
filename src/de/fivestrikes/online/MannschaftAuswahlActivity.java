package de.fivestrikes.online;

//import de.fivestrikes.lite.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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


public class MannschaftAuswahlActivity extends ListActivity {
    /** Called when the activity is first created. */
    
	public final static String ID_EXTRA="de.fivestrikes.pro._ID";
	Cursor model=null;
	MannschaftAdapter adapter=null;
	SQLHelper helper=null;
	String haTeam=null;
	String gegnerID=null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mannschaft);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.mannschaftAuswahl);
        
        helper=new SQLHelper(this);
        model=helper.getAllTeam();
        startManagingCursor(model);
        adapter=new MannschaftAdapter(model);
        setListAdapter(adapter);
        
        Button backButton = (Button) findViewById(R.id.back_button);
        haTeam=getIntent().getStringExtra(SpielEditActivity.ID_HATEAM_EXTRA);
        gegnerID=getIntent().getStringExtra(SpielEditActivity.ID_GEGNER_EXTRA);
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
	
	@Override
	public void onListItemClick(ListView list, View view,
            int position, long id) {
		if(String.valueOf(id).equals(gegnerID)){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MannschaftAuswahlActivity.this);
			alertDialogBuilder
    			.setTitle(R.string.spielTeamauswahlMsgboxTitel)
    			.setMessage(R.string.spielGegnerauswahlMsgboxText)
    			.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog,int id) {
    					
    				}
    			});
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
		}else{
			Intent i=new Intent();
			i.putExtra("Mannschaft", String.valueOf(id));
			i.putExtra("haTeam", haTeam);
			setResult(RESULT_OK, i);
			finish();
		}
		
		
	}
	
	class MannschaftAdapter extends CursorAdapter {
		MannschaftAdapter(Cursor c) {
			super(MannschaftAuswahlActivity.this, c);
		}
    
		@Override
		public void bindView(View row, Context ctxt,
				Cursor c) {
			MannschaftHolder holder=(MannschaftHolder)row.getTag();
			      
			holder.populateFrom(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c,
				ViewGroup parent) {
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row, parent, false);
			MannschaftHolder holder=new MannschaftHolder(row);

			row.setTag(holder);

			return(row);
		}
	}
	
	static class MannschaftHolder {
	    private TextView name=null;
	    
	    MannschaftHolder(View row) {
	      name=(TextView)row.findViewById(R.id.rowMannschaftName);
	    }
	    
	    void populateFrom(Cursor c, SQLHelper helper) {
	      name.setText(helper.getTeamName(c));
	  
	    }
	}
}
