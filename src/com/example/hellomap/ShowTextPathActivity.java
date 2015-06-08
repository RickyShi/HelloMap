package com.example.hellomap;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowTextPathActivity extends Activity {
	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_text_path);
		textView = (TextView) findViewById(R.id.pTxt);
		Intent intent = getIntent();
		List<String> txtDistances = intent.getStringArrayListExtra("distanceTextArrayLst");
		List<String> txtDirections = intent.getStringArrayListExtra("pathTextArrayLst");
		StringBuilder sb = new StringBuilder();
		if (txtDistances!=null){
			for (String s:txtDistances){
				sb.append(s);
			}
		}
		if (txtDirections!=null){
			for (String s:txtDirections){
				sb.append(s);
			}
		}
		textView.setText(Html.fromHtml(sb.toString()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_text_path, menu);
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
}
