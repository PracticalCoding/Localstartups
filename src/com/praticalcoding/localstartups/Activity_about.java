package com.praticalcoding.localstartups;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_about extends Activity {
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		TeamObject weather_data[] = new TeamObject[]
		        {
		            new TeamObject(R.drawable.sir1, "Basavaraj"),
		           // new TeamObject(R.drawable.rashmi, "Rashmi"),
		        };
		TeamListAdapter adapter = new TeamListAdapter(this, 
                R.layout.listview_team_row, weather_data);
        
        
        listView = (ListView)findViewById(R.id.listView1);
       
        
	       
	        
	        listView.setAdapter(adapter);
	    
	}
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle item selection
	        switch (item.getItemId()) {
	            case R.id.share:
	            	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
	                sharingIntent.setType("text/plain");
	                String shareBody = "Hey check localstartups app at playstore https://play.google.com/store/apps/details?id=practicalcoding.localstartups.main it was built by student";
	                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Swatch bharat app");
	                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
	            startActivity(Intent.createChooser(sharingIntent, "Tell your friends"));
	                return true;
	            case R.id.like:
	            	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/PracticalCoding"));
	            	startActivity(browserIntent);
	                return true;
	            case R.id.rate:
	            	Intent intent = new Intent(Intent.ACTION_VIEW);
	            	intent.setData(Uri.parse("market://details?id=practicalcoding.swatchbharat.main"));
	            	startActivity(intent);
	                return true;
	            case R.id.feedback:
	            	String[] TO = {"basuhampali@gmail.com"};
	                String[] CC = {"rashmi.kulkarni19192@gmail.com"};
	                Intent emailIntent = new Intent(Intent.ACTION_SEND);
	                emailIntent.setData(Uri.parse("mailto:"));
	                emailIntent.setType("text/plain");
	                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	                emailIntent.putExtra(Intent.EXTRA_CC, CC);
	                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback of Swatch Hubli app");
	                emailIntent.putExtra(Intent.EXTRA_TEXT, "");

	                try {
	                   startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	                   finish();
	                   Log.i("Finished sending email...", "");
	                } catch (android.content.ActivityNotFoundException ex) {
	                   Toast.makeText(getApplicationContext(), 
	                   "There is no email client installed.", Toast.LENGTH_SHORT).show();
	                }
	            	
	                return true;
	            default:
	                return super.onOptionsItemSelected(item);
	        }
	    }


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_about, menu);
		return true;
	}

}
