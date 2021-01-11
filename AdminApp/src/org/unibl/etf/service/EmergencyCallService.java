package org.unibl.etf.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.unibl.etf.dao.EmergencyCallDAO;
import org.unibl.etf.dto.EmergencyCall;

public class EmergencyCallService {
	
	private static final String getEmergencyCallURL = "http://localhost:8080/AssistanceSystem/api/emergencyCall";
	private static final String deleteEmergencyCallURL = "http://localhost:8080/AssistanceSystem/api/emergencyCall";
	
	public static ArrayList<EmergencyCall> getAll() {
		ArrayList<EmergencyCall> emergencyCallsArrayList = new ArrayList<>();
		try {
			URL url = new URL(getEmergencyCallURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			JSONArray result = new JSONArray();
			while ((output = bufferedReader.readLine()) != null) {
				try {
					result = new JSONArray(output);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				for (int i = 0; i < result.length(); i++) {
					JSONObject json = result.getJSONObject(i);
					EmergencyCall emergencyCall = new EmergencyCall();
					emergencyCall.setId(json.getInt("id"));
					emergencyCall.setName(json.getString("name"));
					emergencyCall.setLocation(json.getString("location"));
					emergencyCall.setEcName(EmergencyCallDAO.getCategoryName(json.getInt("emergencyCategoryId")));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					String getDate = json.getString("time");
					try {
						emergencyCall.setTime(simpleDateFormat.parse(getDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					simpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
					String getshareTime = json.getString("shareTime");
					try {
						emergencyCall.setShareTime(simpleDateFormat2.parse(getshareTime));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					emergencyCall.setDescription(json.getString("description"));
					emergencyCall.setPircutreURL(json.getString("pircutreURL"));
					emergencyCallsArrayList.add(emergencyCall);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emergencyCallsArrayList;
	}

	public static boolean deleteEmergencyCall(int id) {
		try {
			URL url = new URL(deleteEmergencyCallURL + "/" + id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("DELETE");

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
				return false;
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
