package com.example.gridimagesearch;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	int REQUEST_CODE= 1234;
	String settings_params = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultsArrayAdapter(this,imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long arg3) {
				Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});	
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
		}
	
	

	public void customLoadMoreDataFromApi(int page) {
		String query = etQuery.getText().toString()+settings_params;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+page+"&v=1.0&q="+Uri.encode(query),
				new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				//	Log.d("DEBUG",imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	
});
		
	}

	public void setupViews() {
		etQuery = (EditText)findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
	}
	
	public void onSettingsClick(MenuItem mi) {
         Intent i = new Intent(getApplicationContext(), ImageSettingsActivity.class);
         startActivityForResult(i, REQUEST_CODE); // brings up the second activity
}

	
	public void onImageSearch(View v) {
		final String query = etQuery.getText().toString()+settings_params;
		Toast.makeText(this, "Searching for "+"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query), Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query),
				new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
							imageResults.clear();
							imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
							Log.d("DEBUG","Searching for: https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
			
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	    	 settings_params =  data.getExtras().getString("api_params");
	         final String query = etQuery.getText().toString()+settings_params;
	         Toast.makeText(this, "Re-searching for "+"https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query), Toast.LENGTH_SHORT).show();
	         AsyncHttpClient client = new AsyncHttpClient();
	 		 client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query),
	 				new JsonHttpResponseHandler(){

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					JSONArray imageJsonResults = null;
					try {
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
						imageResults.clear();
						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
						Log.d("DEBUG","Re-searching for: https://ajax.googleapis.com/ajax/services/search/images?rsz=8&"+"start="+0+"&v=1.0&q="+Uri.encode(query));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
		
	});
	      }
	      
	    } 

}
