package com.example.hellomap;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class PathTextJSONParser {

	public List<List<String>> parse(JSONObject jObject) {
		List<List<String>> textNavigations = new ArrayList<List<String>>();
		List<String> textDirections = new ArrayList<String>();
		List<String> textDistances = new ArrayList<String>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;
		try {
			jRoutes = jObject.getJSONArray("routes");
			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");

				/** Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

					/** Traversing all steps to get distance and html_instructions*/
					for (int k = 0; k < jSteps.length(); k++) {
						String textDirection = (String) ((JSONObject) jSteps
								.get(k)).get("html_instructions");
						String textDistance = (String) ((JSONObject)((JSONObject) jSteps
								.get(k)).get("distance")).get("text");
						Log.d("ricky", textDistance);
						textDirections.add(textDirection);			
						textDistances.add(textDistance);			
						}
					}
				}
			textNavigations.add(textDistances);
			textNavigations.add(textDirections);
			} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		return textNavigations;
	}
}
