package com.example.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ImageSettingsActivity extends Activity {

        Spinner spinnerImageSize;
        Spinner spinnerColorFilter;
        Spinner spinnerImageType;
        EditText etSiteFilterValue;
        String imageSize;
        String colorFilter;
        String imageType;
        String siteFilterValue;
        
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_image_settings);
                spinnerImageSize = setUpSpinner(R.id.spinnerImageSize, R.array.spinnerImageSizes);
                spinnerImageSize.setOnItemSelectedListener( new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                        int pos, long id) {
                                // TODO Auto-generated method stub
                                imageSize =  (String) parent.getItemAtPosition(pos);
                                Log.d("DEBUG", "Image size selected is " + imageSize);
                                
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                imageSize = "";
                        }
                        
                } );
                
                spinnerColorFilter = setUpSpinner(R.id.spinnerColorFilter, R.array.spinnerColorFilter);
                spinnerColorFilter .setOnItemSelectedListener( new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                        int pos, long id) {
                                // TODO Auto-generated method stub
                                colorFilter =  (String) parent.getItemAtPosition(pos);
                                Log.d("DEBUG", "Color selected is " + colorFilter);
                                
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                colorFilter = "";
                        }
                        
                } );
                
                
                spinnerImageType = setUpSpinner(R.id.spinnerImageType, R.array.spinnerImageType);
                spinnerImageType.setOnItemSelectedListener( new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                        int pos, long id) {
                                // TODO Auto-generated method stub
                                imageType =  (String) parent.getItemAtPosition(pos);
                                Log.d("DEBUG", "Image type is " + imageType);
                                
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                                imageType =  "";
                        }
                        
                } );
                
                etSiteFilterValue = (EditText) findViewById(R.id.etSiteFilterValue);
                
        }
        
        public String createParamString() {
                String siteFilter= etSiteFilterValue.getText().toString();
                String params = "";
                if(imageSize!=null && imageSize.length()>0) {
                        params += "&imgsz=" + imageSize;
                }
                if(colorFilter!=null && colorFilter.length()>0) {
                        params += "&imgcolor=" + colorFilter;
                }
                if(imageType!=null && imageType.length() >0) {
                        params +=  "&imgtype=" + imageType;
                }
                if(siteFilter!=null && siteFilter.length()>0) {
                        params += "&as_sitesearch=" + siteFilter;
                }
                return params;
        }
        
        public void onSaveBtnClicked(View v) {
                
                String params = createParamString();
                Log.d("DEBUG", "After save, " + params );
                Toast.makeText(this, params, Toast.LENGTH_SHORT).show();
                // Prepare data intent 
                  Intent data = new Intent();
                  data.putExtra("api_params",params);
               
                  setResult(RESULT_OK, data); // set result code and bundle data for response
                  finish(); // closes the activity, pass data to parent
        }
        
        public Spinner setUpSpinner(int spinnerId, int arrayId) {
                Spinner aSpinner = (Spinner) findViewById(spinnerId);
               
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                                arrayId, android.R.layout.simple_spinner_item);
               
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                aSpinner.setAdapter(adapter);
                return aSpinner;
                
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.image_settings, menu);
                return true;
        }

}