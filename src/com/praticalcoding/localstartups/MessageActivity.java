package com.praticalcoding.localstartups;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
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

public class MessageActivity extends Activity {
	ListView msgList;
	ArrayList<MessageDetails> details;
	AdapterView.AdapterContextMenuInfo info;
	Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		button=(Button)findViewById(R.id.button1);
		button=(Button)findViewById(R.id.button2);
		ShowDialog();
	}

	private void ShowDialog() {
		// TODO Auto-generated method stub
		Dialog dialog = new Dialog(MessageActivity.this);
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
				Toast.makeText(MessageActivity.this, s, Toast.LENGTH_LONG).show();  
			}
		});	
		dialog.setTitle("Custom Dialog");

		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);

		dialog.show();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();			
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
		// clear all markers


	}

}
