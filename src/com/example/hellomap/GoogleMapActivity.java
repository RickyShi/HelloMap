package com.example.hellomap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class GoogleMapActivity extends Activity implements OnMapReadyCallback{
	
	static final LatLng DISH = new LatLng(39.547622, -104.856219);
	static final LatLng ECOSTAR = new LatLng(39.576325, -104.867355);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
	
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.gMap);
	    mapFragment.getMapAsync(this);
	}
	
	@Override
	public void onMapReady(GoogleMap map) {
	
	    map.setMyLocationEnabled(true);
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(DISH, 13));
	
	    map.addMarker(new MarkerOptions()
	            .title("Dish")
	            .snippet("RickyTest.")
	            .position(DISH));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v){
		int id = v.getId();
		
		if (id==R.id.addMaker1){
//			map.addMarker(new MarkerOptions()
//            .title("Dish")
//            .snippet("RickyTest.")
//            .position(DISH));;
		}
	}
	
	/**
	 * Add direct line Comment here
	 * 
	 * @param listLocsToDraw
	 */
	/*
	private void drawPrimaryLinePath(ArrayList<Location> listLocsToDraw) {
		Log.d(TAG, "drawPrimaryLinePath()");
		if (map == null) {
			return;
		}

		if (listLocsToDraw.size() < 2) {
			return;
		}

		PolylineOptions options = new PolylineOptions();

		options.color(Color.parseColor("#CC0000FF"));
		options.width(5);
		options.visible(true);

		for (Location locRecorded : listLocsToDraw) {
			options.add(new LatLng(locRecorded.getLatitude(), locRecorded
					.getLongitude()));
		}

		map.addPolyline(options);

	}
	*/
}
