package com.example.hellomap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MapPaneActivity extends Activity {

	static final LatLng DISH = new LatLng(39.547622, -104.856219);
	static final LatLng ECHOSTAR = new LatLng(39.576325, -104.867355);
	static final String TAG = "MapPaneActivity";
	static final String ADD1 = "8722 Apache Plume Dr Parker, CO 80134";
	static final int ZOOM_LEVEL = 15;
	

	private GoogleMap mMap;
	LatLng currLatLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_pane);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		Marker mDish = mMap.addMarker(new MarkerOptions().position(ECHOSTAR)
				.title("Echostar").snippet("rickyTest"));

		// // Move the camera instantly to DISH with a zoom of 15.
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DISH, ZOOM_LEVEL));
		//Geocoder Log test:
		Geocoder geoCoder = new Geocoder(this,Locale.ENGLISH);
		try {
			List<Address> addrLst = geoCoder.getFromLocationName(ADD1, 1);
			for (Address a: addrLst){
				Log.d("ricky",a.getLatitude()+","+a.getLongitude());
				Log.d("ricky",a.getAdminArea()+","+a.getCountryName());
				Log.d("ricky",a.toString()+";"+a.getMaxAddressLineIndex());
				for (int i =0;i<a.getMaxAddressLineIndex();i++){
					Log.d("ricky",a.getAddressLine(i));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onClick(View v) {
		int id = v.getId();

		if (id == R.id.button1) {
//			Marker mEchostar = mMap.addMarker(new MarkerOptions().title("Dish")
//					.snippet("RickyTest").position(DISH));
			GPSTracker gpsTracker = new GPSTracker(this);
			if (gpsTracker.getIsGPSTrackingEnabled()){
				currLatLng = new LatLng(gpsTracker.latitude,gpsTracker.longitude);
				Marker currLoc = mMap.addMarker(new MarkerOptions().title("Current")
						.snippet("RickyTest").position(currLatLng));
	        } else
	        {
	            gpsTracker.showSettingsAlert();
	        }
			

//		GMapV2Direction md = new GMapV2Direction();
//		Document doc =  md.getDocument(DISH, ECHOSTAR, GMapV2Direction.MODE_DRIVING);
//		ArrayList<LatLng> directionPoint = md.getDirection(doc);
//		PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
//		for(int i = 0 ; i < directionPoint.size() ; i++) {          
//			rectLine.add(directionPoint.get(i));
//			}
//
//			mMap.addPolyline(rectLine);
		}
	}

	
}
