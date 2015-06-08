package com.example.hellomap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapUtility {	
	/**
	 * get Url sent to get Direction API response
	 * 
	 * @param srcLat
	 * @param srcLng
	 * @param dstLat
	 * @param dstLng
	 * @return
	 */
	/*
	public String makeURL(double srcLat, double srcLng, double dstLat,
			double dstLng) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(srcLat));
		urlString.append(",");
		urlString.append(Double.toString(srcLng));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(dstLat));
		urlString.append(",");
		urlString.append(Double.toString(dstLng));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	public void drawPath(String result) {

		try {
			// Tranform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				Polyline line = map.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).geodesic(true));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}
	
	public class JSONParser {

	    InputStream is = null;
	    JSONObject jObj = null;
	    String json = "";
	    // constructor
	    public JSONParser() {
	    }
	    public String getJSONFromUrl(String url) {

	        // Making HTTP request
	        try {
	            // defaultHttpClient
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            HttpPost httpPost = new HttpPost(url);

	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();           

	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    is, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }

	            json = sb.toString();
	            is.close();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting result " + e.toString());
	        }
	        return json;

	    }
	}
	
	private class ConnectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		ConnectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(MapPaneActivity.this);
			progressDialog.setMessage("Fetching route, Please wait...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}
	

	private Location convertLatLngToLoc(LatLng latLng) {
		Location location = new Location("dish");
		location.setLatitude(latLng.latitude);
		location.setLongitude(latLng.longitude);
		location.setTime(new Date().getTime());
		return location;
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
