//Neu=> Neue Activity

package de.fivestrikes.online;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CursorAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MannschaftServerActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	String strTeamName=null;
	SQLHelper helper=null;
	String lastID=null;
	
	// url to make request
    private static String url = "http://calm-waters-7359.herokuapp.com/teams.json";
    private static String urlPlayer = "http://calm-waters-7359.herokuapp.com/players.json";
   
    // JSON Node names
    private static final String TAG_TEAMS = "teams";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "team_name";
    private static final String TAG_PLAYERS = "players";
    private static final String TAG_PLAYERS_ID = "id";
    private static final String TAG_PLAYERS_NAME = "player_name";
    private static final String TAG_PLAYERS_NUMBER = "player_number";
 
    // JSONArray
    JSONArray teams = null;
    JSONArray players = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mannschaft);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);
        
        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.mannschaftTitel);
        
        helper=new SQLHelper(this);
        
        url = "http://calm-waters-7359.herokuapp.com/teams.json";
        urlPlayer = "http://calm-waters-7359.herokuapp.com/players.json";
        strTeamName=getIntent().getStringExtra(MannschaftServerSearchActivity.TEAM_NAME);
        if(strTeamName!=null){
        	url=url+"?search="+strTeamName;
        }
        
        // Hashmap for ListView
        final ArrayList<HashMap<String, String>> teamList = new ArrayList<HashMap<String, String>>();
 
        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();
 
        // getting JSON string from URL
        JSONObject json = jParser.getJSONFromUrl(url);
 
        try {
            // Getting Array of Contacts
            teams = json.getJSONArray(TAG_TEAMS);
             
            // looping through All Contacts
            for(int i = 0; i < teams.length(); i++){
                JSONObject c = teams.getJSONObject(i);
                 
                // Storing each json item in variable
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                 
                // adding each child node to HashMap key => value
                map.put(TAG_ID, id);
                map.put(TAG_NAME, name);
                
                // adding HashList to ArrayList
                teamList.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * Updating parsed JSON data into ListView
         * */
        ListAdapter adapter = new SimpleAdapter(this, teamList,
                R.layout.row,
                new String[] { TAG_NAME }, new int[] {
                        R.id.rowMannschaftName });
 
        setListAdapter(adapter);
 
        // selecting single ListView item
        ListView lv = getListView();
 
        // Launching new screen on Selecting Single ListItem
        lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // getting values from selected ListItem
                String name = teamList.get(position).get(TAG_NAME);
                String strID = teamList.get(position).get(TAG_ID);
                
                helper.insertTeam(name, "TEST", strID);
                
                // Insert new team
                Cursor c=helper.getLastTeamId();
    	    	if (c.moveToFirst()!=false) {		// Bereits Mannschaften vorhanden...
    		    	c.moveToFirst();
    		    	lastID = String.valueOf(Integer.parseInt(helper.getTeamId(c)));
    	        }
    	        else {								// ... oder erste Mannschaft?
    	        	lastID = "1";
    	        }
    	    	c.close();
    			
    	    	
    	    	urlPlayer = "http://calm-waters-7359.herokuapp.com/players.json";
    	    	urlPlayer=urlPlayer+"?team_id="+strID;
    	        final ArrayList<HashMap<String, String>> playerList = new ArrayList<HashMap<String, String>>();
    	        JSONParser jParser = new JSONParser();
     	        JSONObject json = jParser.getJSONFromUrl(urlPlayer);
    	 
    	        try {
    	            // Getting Array of Contacts
    	            players = json.getJSONArray(TAG_PLAYERS);
    	             
    	            // looping through All Contacts
    	            for(int i = 0; i < players.length(); i++){
    	                JSONObject cPLayer = players.getJSONObject(i);
    	                 
    	                // Storing each json item in variable
    	                String player_id = cPLayer.getString(TAG_PLAYERS_ID);
    	                String player_name = cPLayer.getString(TAG_PLAYERS_NAME);
    	                String player_number = cPLayer.getString(TAG_PLAYERS_NUMBER);

    	                // creating new HashMap
    	                HashMap<String, String> map = new HashMap<String, String>();
    	                 
    	                // adding each child node to HashMap key => value
    	                map.put(TAG_PLAYERS_ID, player_id);
    	                map.put(TAG_PLAYERS_NAME, player_name);
    	                map.put(TAG_PLAYERS_NUMBER, player_number);
    	                
    	                // adding HashList to ArrayList
    	                playerList.add(map);
    	                
    	              
    	            }
    	        } catch (JSONException e) {
    	            e.printStackTrace();
    	        }
    	    	
    	        //To get size of Java ArrayList use int size() method
    	        int totalElements = playerList.size();
    	       
    	        System.out.println("ArrayList contains...");
    	        //loop through it
    	        for(int index=0; index < totalElements; index++){
    	        	String player_name = playerList.get(index).get(TAG_PLAYERS_NAME);
    	        	String player_number = playerList.get(index).get(TAG_PLAYERS_NUMBER);
    	        	String player_id = playerList.get(index).get(TAG_PLAYERS_ID);
    	        	helper.insertSpieler(player_name, player_number, lastID, "", player_id);
    	        	System.out.println(player_name);
    	        	System.out.println(lastID);
    	        }
                finish();
            }
        });
        
        Button backButton = (Button) findViewById(R.id.back_button);
        
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
