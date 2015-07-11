package com.praticalcoding.localstartups;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity  {

	private GoogleMap googleMap;
	Button button1;
	//Button button2;

	//List<ParseObject> objectList;
	ArrayList<MessageDetails> details;
	List<ParseObject> list = new ArrayList<ParseObject>();


	@SuppressWarnings("unchecked")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1=(Button)findViewById(R.id.button1);
		//button2=(Button)findViewById(R.id.button2);
		//button1.setOnClickListener(this);
		try { 
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().
						findFragmentById(R.id.map)).getMap();

			}
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

			googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(15.3617,75.0850) , 12.0f) );


		} catch (Exception e) {
			e.printStackTrace();
		}
		ParseAnalytics.trackAppOpenedInBackground(getIntent());


		/*ParseObject gameScore = new ParseObject("FirstParse");
			gameScore.put("name", "praticalcoding");
			gameScore.put("latlng1", "15.353694, 75.132432");
			gameScore.saveInBackground();*/
		ParseQuery<ParseObject> query = ParseQuery.getQuery("FirstParse");
		// query.whereEqualTo("playerName", "Dan Stemkoski");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> scoreList, ParseException e) {
				list = scoreList;
				if (e == null) {
					for (int i = 0; i < scoreList.size(); i++) {
						String  name=scoreList.get(i).getString("name");
						//	Toast.makeText(getApplicationContext(), name, 10).show();
						ParseGeoPoint pc=scoreList.get(i).getParseGeoPoint("latlng");
						String str=String.valueOf(pc.getLatitude());
						String str1=String.valueOf(pc.getLongitude());
						String str2=str+","+str1;

						//	Toast.makeText(getApplicationContext(),str2 , 10).show();
						LatLng ln=new LatLng(pc.getLatitude(),pc.getLongitude());
						if(scoreList.get(i).getString("type").contentEquals("IT")){
							Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).
									position(ln).title(name).snippet(scoreList.get(i).getString("url")));
						}
						if(scoreList.get(i).getString("type").contentEquals("BPO")){
							Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).
									position(ln).title(name).snippet(scoreList.get(i).getString("url")));
						}
						if(scoreList.get(i).getString("type").contentEquals("MECHANICAL")){
							Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
									position(ln).title(name).snippet(scoreList.get(i).getString("url")));
						}
						if(scoreList.get(i).getString("type").contentEquals("INCUBATION")){
							Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).
									position(ln).title(name).snippet(scoreList.get(i).getString("url")));
						}
						if(scoreList.get(i).getString("type").contentEquals("OTHERS")){
							Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).
									position(ln).title(name).snippet(scoreList.get(i).getString("url")));
						}


					}
					// your logic here
				} else {
					// handle Parse Exception here 
				}
			}
		});

	}

	public void startAbout(View v){
		Intent intent=new Intent(MainActivity.this,Activity_about.class);
		startActivity(intent);
	}

	public void startDailog(View v){
		Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.activity_message);
		ListView lv = (ListView ) dialog.findViewById(R.id.dialogList);
		details = new ArrayList<MessageDetails>();

		MessageDetails Detail;
		Detail = new MessageDetails();
		Detail.setType("IT");
		details.add(Detail);

		Detail = new MessageDetails();
		Detail.setType("MECHANICAL");
		details.add(Detail);
		Detail = new MessageDetails();
		Detail.setType("BPO");
		details.add(Detail);

		Detail = new MessageDetails();
		Detail.setType("INCUBATION");
		details.add(Detail);
		Detail = new MessageDetails();
		Detail.setType("OTHERS");
		details.add(Detail);

		lv.setAdapter(new CustomAdapter(details , this));

		//registerForContextMenu(msgList);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				System.out.println("Name: "+details.get(position).getType());
				String s =(String) ((TextView) v.findViewById(R.id.type_TV)).getText();
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();  
			}
		});	
		dialog.setTitle("Custom Dialog");

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	public class CustomAdapter extends BaseAdapter {

		private ArrayList<MessageDetails> _data;
		Context _c;

		CustomAdapter (ArrayList<MessageDetails> data, Context c){
			_data = data;
			_c = c;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return _data.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return _data.get(position);
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null) 
			{
				LayoutInflater vi = (LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}


			TextView typeText = (TextView)v.findViewById(R.id.type_TV);
			CheckBox check_CB = (CheckBox)v.findViewById(R.id.checktype_CB);
			check_CB.setChecked(true);

			MessageDetails msg = _data.get(position);
			typeText.setText(msg.getType());	
			if(msg.getType().equals("IT")){
				typeText.setTextColor(Color.BLUE);
				// parseColor(getResources().getColor(android.R.color.darker_gray)));
			}else if (msg.getType().equals("MECHANICAL")) {
				typeText.setTextColor(Color.GREEN);
			}else if (msg.getType().equals("BPO")) {
				typeText.setTextColor(Color.RED);
			}else if (msg.getType().equals("INCUBATION")) {
				typeText.setTextColor(Color.YELLOW);
			}else if (msg.getType().equals("OTHERS")) {
				typeText.setTextColor(Color.MAGENTA);
			}

			check_CB.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					//is chkIos checked?
					if (!((CheckBox) v).isChecked()) {
						Toast.makeText(getApplicationContext(),
								_data.get(position).getType(), Toast.LENGTH_LONG).show();

						String[] str = {"IT","MECHANICAL","BPO","INCUBATION","OTHERS"};
						clearMarkers(str);
					}

				}


			});

			return v; 
		}


	}
	
	

	private void clearMarkers(String[] types) {
		googleMap.clear();
		/*if(list!=null && list.size()>0 ){
			for (int i = 0; i < list.size(); i++) {
				String  name=list.get(i).getString("name");
				//	Toast.makeText(getApplicationContext(), name, 10).show();
				ParseGeoPoint pc=list.get(i).getParseGeoPoint("latlng");
				String str=String.valueOf(pc.getLatitude());
				String str1=String.valueOf(pc.getLongitude());
				String str2=str+","+str1;

				//	Toast.makeText(getApplicationContext(),str2 , 10).show();
				LatLng ln=new LatLng(pc.getLatitude(),pc.getLongitude());
				if(list.get(i).getString("type").contentEquals("IT")){
					Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).
							position(ln).title(name).snippet(list.get(i).getString("url")));
				}
				if(list.get(i).getString("type").contentEquals("BPO")){
					Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).
							position(ln).title(name).snippet(list.get(i).getString("url")));
				}
				if(list.get(i).getString("type").contentEquals("MECHANICAL")){
					Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).
							position(ln).title(name).snippet(list.get(i).getString("url")));
				}
				if(list.get(i).getString("type").contentEquals("INCUBATION")){
					Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).
							position(ln).title(name).snippet(list.get(i).getString("url")));
				}
				if(list.get(i).getString("type").contentEquals("OTHERS")){
					Marker TP5 = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)).
							position(ln).title(name).snippet(list.get(i).getString("url")));
				}


			}
		}*/


	}
}



