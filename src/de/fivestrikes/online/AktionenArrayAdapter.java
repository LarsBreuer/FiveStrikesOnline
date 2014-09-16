package de.fivestrikes.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.res.Resources;

public class AktionenArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	
	public AktionenArrayAdapter(Context context, String[] values) {
		super(context, R.layout.row_ticker_aktion, values);
		this.context = context;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_ticker_aktion, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.rowtickerAktion);
		textView.setText(values[position]);

		// Hintergrund nach Art der Aktion setzen
		String s = values[position];
		Resources res = context.getResources();
		
		if (s.equals(res.getString(R.string.tickerAktionTor)) || 
			s.equals(res.getString(R.string.tickerAktionFehlwurf)) || 
			s.equals(res.getString(R.string.tickerAktionTechnischerFehler)) || 
			s.equals(res.getString(R.string.tickerAktion7mTor)) || 
			s.equals(res.getString(R.string.tickerAktion7mFehlwurf)) || 
			s.equals(res.getString(R.string.tickerAktionTempogegenstossTor)) || 
			s.equals(res.getString(R.string.tickerAktionTempogegenstossFehlwurf))) {
			
				rowView.setBackgroundResource(R.drawable.tablecell44none);
		}
		if (s.equals(res.getString(R.string.tickerAktionWechsel)) || 
			s.equals(res.getString(R.string.tickerAktionStartaufstellung)) || 
			s.equals(res.getString(R.string.tickerAktionStartaufstellung2Halbzeit))) {
				
				rowView.setBackgroundResource(R.drawable.tablecell44gruen);
		}
		if (s.equals(res.getString(R.string.tickerAktionZweiMinuten)) || 
			s.equals(res.getString(R.string.tickerAktionGelbeKarte)) || 
			s.equals(res.getString(R.string.tickerAktionZweiMalZwei)) || 
			s.equals(res.getString(R.string.tickerAktionRoteKarte))) {
			
				rowView.setBackgroundResource(R.drawable.tablecell44rot);
		}
		if (s.equals(res.getString(R.string.tickerAktionAuszeit))) {
				
				rowView.setBackgroundResource(R.drawable.tablecell44gelb);
		}

		return rowView;
	}
}