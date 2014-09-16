package de.fivestrikes.online;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;



public class AktionenImageAdapter extends BaseAdapter {
    private Context mContext;

    public AktionenImageAdapter(Context c) {
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
        	gridView = inflater.inflate(R.layout.aktionen_uebersicht, null);
        	ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_aktion_image);
            imageView.setImageResource(mThumbIds[position]);
        } else {
        	gridView = (View) convertView;
        }

        return gridView;
    }

    // references to our images
    private Integer[] mThumbIds = {
    		R.drawable.aktion_torwurf, R.drawable.aktion_7m_tor, R.drawable.aktion_tg_tor, R.drawable.aktion_gelbekarte, R.drawable.aktion_fehlwurf, 
    		R.drawable.aktion_7m_fehlwurf, R.drawable.aktion_tg_fehlwurf, R.drawable.aktion_zweimin, R.drawable.aktion_techfehl, R.drawable.aktion_aufstellung_heim, 
    		R.drawable.aktion_auszeit_heim, R.drawable.aktion_zweipluszwei, R.drawable.aktion_wechsel, R.drawable.aktion_aufstellung_auswaerts, 
    		R.drawable.aktion_auszeit_auswaerts, R.drawable.aktion_rotekarte
    };
    
}
