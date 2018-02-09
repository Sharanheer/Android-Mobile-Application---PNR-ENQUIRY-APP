package com.indian.pnrinfo;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Pnr extends Activity implements OnClickListener{
	EditText edit;
	TextView text1;
	Button button;
	ProgressBar progress;
	HttpClient client;
	JSONObject json;
	int s;
	String URL;
	ViewFlipper flippy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pnr);
		edit = (EditText) findViewById(R.id.tv5);
		text1 = (TextView) findViewById(R.id.textView2);
		button=(Button)findViewById(R.id.button1);
		flippy=(ViewFlipper)findViewById(R.id.flipper1);
		client = new DefaultHttpClient();
		button.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
		flippy.showNext();
		flippy.setClickable(false);
		URL="http://pnrdekho.com/api_getPnrStatus.php?pnr=" +edit.getText().toString();
		new Read().execute();
	}
			
		

	

	public JSONObject pnrStatus(String key) throws ClientProtocolException,
			IOException, JSONException {
		
		
		HttpGet get = new HttpGet(key);
		HttpResponse r = client.execute(get);
		int status = r.getStatusLine().getStatusCode();
		JSONObject last = new JSONObject();
		if (status == 200) {
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			last = new JSONObject(data);
			return last;
		} else {
			return last;
		}

	}

	public class Read extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
				json = pnrStatus(URL);
				return json.getString(arg0[0]);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return s + "";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
		
			flippy.showNext();
            flippy.setClickable(true);
			text1.setText("               " + result);

		}

	}

	

}
