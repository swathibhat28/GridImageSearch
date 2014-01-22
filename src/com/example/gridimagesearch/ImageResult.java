package com.example.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6309945168105461554L;
	private String fullUrl;
	private String thumbUrl;
	
	public String getFullUrl() {
		return fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	@Override
	public String toString() {

		return super.toString();
	}
	
	public ImageResult(JSONObject json) {
		try{
			this.fullUrl =json.getString("url");
			this.thumbUrl=json.getString("tbUrl");
		}catch(JSONException e){
			this.fullUrl=null;
			this.thumbUrl=null;
		}
	}
	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < array.length(); i++) {
			 try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	

}
