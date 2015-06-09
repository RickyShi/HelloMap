package com.example.hellomap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import android.util.Log;

public class HttpConnection {

	public String readUrl(String mapsApiDirectionsUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(mapsApiDirectionsUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			data = sb.toString();
			br.close();
		} catch (Exception e) {
			Log.d("Exception while reading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}
	
//	public String readUrl(String mapsApiDirectionsUrl){		
//	StringBuffer sb = new StringBuffer();
//	HttpUriRequest request = new HttpPost(URLEncoder.encode(mapsApiDirectionsUrl));  
//    HttpClient httpClient = HttpUtils.getHttpsClient();
//    try {
//		HttpResponse httpResponse = httpClient.execute(request);
//		if (httpResponse != null) {
//			StatusLine statusLine = httpResponse.getStatusLine();
//			if (statusLine != null
//					&& statusLine.getStatusCode() == HttpStatus.SC_OK) {
//				BufferedReader reader = null;
//				try {
//					reader = new BufferedReader(new InputStreamReader(
//							httpResponse.getEntity().getContent(),
//							"UTF-8"));
//					String line = null;
//					while ((line = reader.readLine()) != null) {
//						sb.append(line);
//					}
//
//				} catch (Exception e) {
//					Log.e("https", e.getMessage());
//				} finally {
//					if (reader != null) {
//						reader.close();
//						reader = null;
//					}
//				}
//			}
//		}
//
//	} catch (Exception e) {
//		Log.e("https", e.getMessage());
//	} finally {
//	}
//    Log.d("ricky","response: "+sb.toString());
//    return sb.toString();
//}
}
