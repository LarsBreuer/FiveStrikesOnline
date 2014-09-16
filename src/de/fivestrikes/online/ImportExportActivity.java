package de.fivestrikes.online;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import android.os.Environment;
import android.database.sqlite.SQLiteDatabase;
import au.com.bytecode.opencsv.CSVWriter;
import android.util.Log;


public class ImportExportActivity extends Activity {
	SQLHelper helper=null;
	
	//Neu=> Neue Activity
	private SharedPreferences mPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.importexport_menu);
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar_back);

        final TextView customTitleText = (TextView) findViewById(R.id.titleBackText);
        customTitleText.setText(R.string.importexportTitel);

		helper=new SQLHelper(this);

	    Button backButton = (Button) findViewById(R.id.back_button);
	    Button btnImportData = (Button) findViewById(R.id.importData);
	    Button btnImportDatabase = (Button) findViewById(R.id.importDatabase);
	    Button btnExportDatabase = (Button) findViewById(R.id.exportDatabase);
	    
	    //Neu=> Neue Button
	    Button btnLogin = (Button) findViewById(R.id.login);
	    Button btnRegister = (Button) findViewById(R.id.register);
	    
	    mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
	    Toast.makeText(this, "mPreferences" + mPreferences.getString("AuthToken", ""), Toast.LENGTH_LONG).show();
	    
	    //Neu=> Neue Button Ende

        /* Button zurück */
        backButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					finish();
				}
			});

        /* Button Import Data*/
        btnImportDatabase.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
				AlertDialog.Builder builderWebside = new AlertDialog.Builder(ImportExportActivity.this);
				builderWebside
	        		.setTitle(R.string.importMsgboxTitel)
	        		.setMessage(R.string.importDatabaseMsgboxText)
	        		.setIcon(android.R.drawable.ic_dialog_alert)
	        		.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
	        			public void onClick(DialogInterface dialog, int which) {
	        				importDB();
	        			}
	        		})
	        		.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
	        			public void onClick(DialogInterface dialog, int which) {			      	

	        			}
	        		})
	        		.show();
				}
		});

        /* Button Import Database */
        btnImportData.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					AlertDialog.Builder builderWebside = new AlertDialog.Builder(ImportExportActivity.this);
					builderWebside
        				.setTitle(R.string.importMsgboxTitel)
        				.setMessage(R.string.importMsgboxText)
        				.setIcon(android.R.drawable.ic_dialog_alert)
        				.setPositiveButton(R.string.tickerMSGBoxJa, new DialogInterface.OnClickListener() {
        					public void onClick(DialogInterface dialog, int which) {			      	

        						// File dbfile = new File("/sdcard/database_name.db" ); 
        						// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
        						if(new File("/sdcard/database_name.db").exists()){
        							String dbString = "/sdcard/database_name.db"; 
            						SQLiteDatabase db = SQLiteDatabase.openDatabase(dbString, null, SQLiteDatabase.OPEN_READONLY);
            						helper=new SQLHelper(ImportExportActivity.this);
        							if(db.isOpen()==true){
        								/* Alle Datenbanken löschen */
        								helper.deleteAllTeam();
        								helper.deleteAllSpiel();
        								helper.deleteAllTicker();
        								helper.deleteAllSpieler();

        								/* Datenbank Ticker einfügen */
        								Cursor cTicker= db.rawQuery("SELECT * FROM ticker", null);
        								while(cTicker.moveToNext())
        		                    	{
        									if(cTicker.getString(6)!=null && cTicker.getString(7)!=null && cTicker.getString(8)!=null){
        										helper.insertTickerImport(Integer.parseInt(cTicker.getString(1)), cTicker.getString(2), 
																		  Integer.parseInt(cTicker.getString(3)), 
																		  cTicker.getString(4), Integer.parseInt(cTicker.getString(5)), Integer.parseInt(cTicker.getString(6)), 
																		  Integer.parseInt(cTicker.getString(7)), cTicker.getString(8), Integer.parseInt(cTicker.getString(11)), 
																		  Integer.parseInt(cTicker.getString(13)));
        									} else {
        										helper.insertTickerImportOhneTore(Integer.parseInt(cTicker.getString(1)), cTicker.getString(2), 
																				  Integer.parseInt(cTicker.getString(3)), 
																				  cTicker.getString(4), Integer.parseInt(cTicker.getString(5)), Integer.parseInt(cTicker.getString(11)), 
																				  Integer.parseInt(cTicker.getString(13)));
        									}
        		                    	}
        								cTicker.close();

        								/* Datenbank Spiel einfügen */
        								Cursor cSpiel= db.rawQuery("SELECT * FROM spiel", null);
        								while(cSpiel.moveToNext())
        		                    	{
        									if(cSpiel.getString(7)!=null && cSpiel.getString(8)!=null){
        										helper.insertSpielImport(Integer.parseInt(cSpiel.getString(1)), Integer.parseInt(cSpiel.getString(2)), 
																		 Integer.parseInt(cSpiel.getString(3)), Integer.parseInt(cSpiel.getString(4)), 
																		 Integer.parseInt(cSpiel.getString(5)), cSpiel.getString(6), Integer.parseInt(cSpiel.getString(7)), 
																		 Integer.parseInt(cSpiel.getString(8)));
        									} else {
        										helper.insertSpielImportOhneTore(Integer.parseInt(cSpiel.getString(1)), Integer.parseInt(cSpiel.getString(2)), 
																				 Integer.parseInt(cSpiel.getString(3)), Integer.parseInt(cSpiel.getString(4)), 
																				 Integer.parseInt(cSpiel.getString(5)), cSpiel.getString(6));
        									}
        									String spielIdAlt=cSpiel.getString(0);
        									Cursor lastSpielC=helper.getLastSpielId();
            								lastSpielC.moveToFirst();
            								String spielIdNeu = helper.getTeamId(lastSpielC);
            								lastSpielC.close();
            								System.out.println("SpielID alt"+ spielIdAlt);
            								System.out.println("SpielID neu"+ spielIdNeu);
            								if(!spielIdAlt.equals(spielIdNeu)){
            									Cursor cTickerAlle=helper.getAllTickerAlle();
            									while(cTickerAlle.moveToNext())
                		                    	{
            										if(cTickerAlle.getString(13).equals(spielIdAlt)){
            											helper.updateTickerSpielId(cTickerAlle.getString(0), spielIdNeu);
            										}
                		                    	}
            								}
        		                    	}
        								cSpiel.close();

        								/* Datenbank Spieler einfügen */
        								Cursor cSpieler= db.rawQuery("SELECT * FROM spieler", null);
        								while(cSpieler.moveToNext())
        								{
        									helper.insertSpielerImport(Integer.parseInt(cSpieler.getString(1)), cSpieler.getString(2), 
																	   cSpieler.getString(3), "", Integer.parseInt(cSpieler.getString(5)));
            								String spielerIdAlt=cSpieler.getString(0);
        									Cursor lastSpielerC=helper.getLastSpielerId();
            								lastSpielerC.moveToFirst();
            								String spielerIdNeu = helper.getTeamId(lastSpielerC);
            								lastSpielerC.close();
            								System.out.println("SpielerID alt"+ spielerIdAlt);
            								System.out.println("SpielerID neu"+ spielerIdNeu);
            								if(!spielerIdAlt.equals(spielerIdNeu)){
            									Cursor cTickerAlle=helper.getAllTickerAlle();
            									while(cTickerAlle.moveToNext())
                		                    	{
            										if(cTickerAlle.getString(5).equals(spielerIdAlt)){
            											helper.updateTickerSpielerId(cTickerAlle.getString(0), spielerIdNeu);
            										}
                		                    	}
            								}
        								}
        								cSpieler.close();

        								/* Datenbank Team einfügen */
        								Cursor cTeam= db.rawQuery("SELECT * FROM team", null);
        								/* cTeam.moveToFirst();
										 helper.insertTeamImport(cTeam.getString(1), cTeam.getString(2), Integer.parseInt(cTeam.getString(3)));
										 Cursor lastTeamC=helper.getLastTeamId();
										 lastTeamC.moveToFirst();
										 Integer lastId = Integer.parseInt(helper.getTeamId(lastTeamC))-1;
										 lastTeamC.close(); */
        								while(cTeam.moveToNext())
        								{
        									helper.insertTeamImport(cTeam.getString(1), cTeam.getString(2), Integer.parseInt(cTeam.getString(3)));
            								String teamIdAlt=cTeam.getString(0);
        									Cursor lastTeamC=helper.getLastTeamId();
        									lastTeamC.moveToFirst();
            								String teamIdNeu = helper.getTeamId(lastTeamC);
            								lastTeamC.close();
            								System.out.println("SpielerID alt"+ teamIdAlt);
            								System.out.println("SpielerID neu"+ teamIdNeu);
            								if(!teamIdAlt.equals(teamIdNeu)){
            									Cursor cSpielAlle=helper.getAllSpielAlle();
            									while(cSpielAlle.moveToNext())
                		                    	{
            										if(cSpielAlle.getString(1).equals(teamIdAlt)){
            											helper.updateSpielHeimId(cSpielAlle.getString(0), teamIdNeu);
            										}
            										if(cSpielAlle.getString(2).equals(teamIdAlt)){
            											helper.updateSpielAuswId(cSpielAlle.getString(0), teamIdNeu);
            										}
                		                    	}
            									Cursor cSpielerAlle=helper.getAllSpielerAlle();
            									while(cSpielerAlle.moveToNext())
                		                    	{
            										if(cSpielerAlle.getString(1).equals(teamIdAlt)){
            											helper.updateSpielerId(cSpielerAlle.getString(0), teamIdNeu);
            										}
                		                    	}
            								}
        								}
        								cTeam.close();

        								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImportExportActivity.this);
        								alertDialogBuilder
        		    	    				.setTitle(R.string.importFertigMsgboxTitel)
        		    	    				.setMessage(R.string.importFertigMsgboxText)
        		    	    				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
        		    	    					public void onClick(DialogInterface dialog,int id) {

        		    	    					}
        		    	    				});
        								AlertDialog alertDialog = alertDialogBuilder.create();
        								alertDialog.show();
									}
								} else {
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImportExportActivity.this);
									alertDialogBuilder
										.setTitle(R.string.importFehlMsgboxTitel)
										.setMessage(R.string.importFehlMsgboxText)
										.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog,int id) {

											}
										});
									AlertDialog alertDialog = alertDialogBuilder.create();
									alertDialog.show();
								}
							}
						})
						.setNegativeButton(R.string.tickerMSGBoxNein, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {			      	

							}
						})
        				.show();
				}
			});

        /* Button Export Database */
        btnExportDatabase.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					exportDB();
				}
			});
        
        
        //Neu=> Definition neue Button
        /* Button Login */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(newIntent);
            }
        });
        
        /* Button Login */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				Intent newIntent = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(newIntent);
            }
        });
        //Neu=> Definition neue Button Ende

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	    helper.close();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
	    Toast.makeText(this, "mPreferences" + mPreferences.getString("AuthToken", ""), Toast.LENGTH_LONG).show();

	    helper.close();
	}
	
	private void importDB() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "de.fivestrikes.pro"
                        + "//databases//" + "fivestrikespro.db";
                String backupDBPath  = "/fivestrikespro.db";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), getString(R.string.importDatabaseComplete), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), getString(R.string.importDatabaseError), Toast.LENGTH_LONG)
                    .show();

        }
    }
	
	private void exportDB() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "de.fivestrikes.pro"
                        + "//databases//" + "fivestrikespro.db";
                String backupDBPath  = "/fivestrikespro.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), getString(R.string.exportDatabaseComplete), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), getString(R.string.exportDatabaseError), Toast.LENGTH_LONG)
                    .show();

        }
    }

}
