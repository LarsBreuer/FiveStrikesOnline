package de.fivestrikes.online;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View gridView;
        
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	gridView = new View(mContext);
        	// get layout from mobile.xml
        	gridView = inflater.inflate(R.layout.uebersicht, null);
        	// set value into textview
        	TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        	switch(position) {
        		case 0:
        			textView.setText(R.string.uebersichtMannschaft);
        			break;
        		case 1:
        			textView.setText(R.string.uebersichtSpiel);
        			break;
        		case 2:
        			textView.setText(R.string.uebersichtStatistik);
        			break;
        		case 3:
        			textView.setText(R.string.uebersichtInfo);
        			break;
        		case 4:
        			textView.setText(R.string.uebersichtSchnellesSpiel);
        			break;
        		case 5:
        			textView.setText(R.string.uebersichtDownload);
        			break;
        	}
        	ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
            imageView.setImageResource(mThumbIds[position]);        	
        } else {
        	gridView = (View) convertView;
        }

        return gridView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.mannschaft, R.drawable.spiel,
            R.drawable.statistik, R.drawable.info,
            R.drawable.schnellesspiel, R.drawable.download,
    };
}
