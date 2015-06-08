package com.example.hellomap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    public void navigate(View view){
    	int id = view.getId();

		if (id == R.id.btn1) {
			Intent intent1 = new Intent(this, MapPaneActivity.class);
			this.startActivity(intent1);
		} else if (id == R.id.btn2) {
			Intent intent2 = new Intent(this, GoogleMapActivity.class);
			this.startActivity(intent2);
			Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
		} else if (id == R.id.btn3) {
	    	Intent intent2 = new Intent(this, PathGoogleMapActivity.class);
	    	this.startActivity(intent2);
	    }
		
    }
}
