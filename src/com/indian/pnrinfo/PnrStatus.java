package com.indian.pnrinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;

import android.graphics.Color;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class PnrStatus extends Activity implements OnClickListener {

	static String URL;
	static String status = "";
	static String trainNum = "";
	static String trainName = "";
	static String boarding = "";
	static String reserved = "";
	static String trainJ = "";
	static String booking0 = "";
	static String booking1 = "";
	static String booking2 = "";
	static String booking3 = "";
	static String booking4 = "";
	static String booking5 = "";
	static String current0 = "";
	static String current1 = "";
	static String current2 = "";
	static String current3 = "";
	static String current4 = "";
	static String current5 = "";
	static String clas = "";
	EditText edit;
	Button button;
	ViewFlipper flippy;
	int count;
	int error;
	TextView line1;
	TextView line2;
	TextView line3;
	TextView line4;
	TextView line5;
	TextView line6;
	TextView line7;
	TextView line8;
	TextView line9;
	TextView line10;
	TextView line11;
	TextView line12;
	TextView line13;
	TextView line14;
	TextView line15;
	TextView line16;
	TextView line17;
	TextView line18;
	TextView line19;
	TextView line20;
	InputMethodManager iim;
	AlertDialog.Builder builder1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pnr);

		error = 0;
		builder1 = new AlertDialog.Builder(this);
		flippy = (ViewFlipper) findViewById(R.id.flipper1);
		edit = (EditText) findViewById(R.id.tv1);
		edit.setBackgroundColor(Color.WHITE);
		edit.invalidate();
		iim = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		button = (Button) findViewById(R.id.button1);

		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cManager.getActiveNetworkInfo();
		if (nInfo != null && nInfo.isConnected()) {
			button.setOnClickListener(this);
		} else {
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("          INTERNET ERROR");
			builder1.setMessage("INTERNET CONNECTION IS NOT AVAILABLE");
			builder1.setCancelable(true);
			builder1.setNeutralButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();
			button.setOnClickListener(this);

		}

	}

	private class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			HttpPost httppost = new HttpPost(URL);
			httppost.setHeader("content-type", "application/json");
			InputStream inputStream = null;
			String result = null;
			DefaultHttpClient httpClient = new DefaultHttpClient();
			try {
				HttpResponse response = httpClient.execute(httppost);

				HttpEntity entity = response.getEntity();
				inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "utf-8"), 8);
				StringBuilder theStringBuilder = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					theStringBuilder.append(line + "\n");
				}
				result = theStringBuilder.toString();
			} catch (Exception e) {
				e.printStackTrace();
				error = 2;
				return null;

			} finally {
				try {
					if (inputStream != null)
						inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
					error = 3;
					return null;
				}
			}

			JSONObject jsonObject, book;
			try {
				jsonObject = new JSONObject(result);

				status = jsonObject.getString("chartStat");
				trainNum = jsonObject.getString("trainNo");
				trainName = jsonObject.getString("trainName");
				boarding = jsonObject.getString("trainBoard");
				reserved = jsonObject.getString("trainEmbark");
				trainJ = jsonObject.getString("trainJourney");
				clas = jsonObject.getString("trainFareClass");

				JSONArray array = jsonObject.getJSONArray("passengers");
				count = array.length();
				for (int i = 0; i < count; i++) {

					switch (i) {
					case 0: {
						book = array.getJSONObject(i);
						booking0 = book.getString("trainBookingBerth");
						current0 = book.getString("trainCurrentStatus");

						break;
					}
					case 1: {
						book = array.getJSONObject(i);
						booking1 = book.getString("trainBookingBerth");
						current1 = book.getString("trainCurrentStatus");

						break;
					}
					case 2: {
						book = array.getJSONObject(i);
						booking2 = book.getString("trainBookingBerth");
						current2 = book.getString("trainCurrentStatus");

						break;
					}
					case 3: {
						book = array.getJSONObject(i);
						booking3 = book.getString("trainBookingBerth");
						current3 = book.getString("trainCurrentStatus");

						break;
					}
					case 4: {
						book = array.getJSONObject(i);
						booking4 = book.getString("trainBookingBerth");
						current4 = book.getString("trainCurrentStatus");

						break;
					}
					case 5: {
						book = array.getJSONObject(i);
						booking5 = book.getString("trainBookingBerth");
						current5 = book.getString("trainCurrentStatus");

						break;
					}
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
				error = 1;
				return null;
			}

			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (error == 0) {
				initialize();
				flippy.showNext();
				settext();

				settingUp();
				edit.setEnabled(true);
			} else if (error == 1) {

				builder1.setTitle("   Error fetching ticket status");
				builder1.setMessage("Ticket status could not be obtained either due to invalid/expired pnr or a problem with Indian railways servers please try again later");
				builder1.setCancelable(true);
				builder1.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
				flippy.showNext();
				edit.setEnabled(true);
			} else if (error == 2) {
				builder1.setTitle("                   Connectivity Error");
				builder1.setMessage("please check your internet connection");
				builder1.setCancelable(true);
				builder1.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
				flippy.showNext();
				edit.setEnabled(true);

			}
		}

	}

	@Override
	public void onClick(View v) {

		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cManager.getActiveNetworkInfo();
		if (nInfo != null && nInfo.isConnected()) {
			if (edit.length() == 10) {

				String data = edit.getText().toString();
				URL = "http://pnrdekho.com/api_getPnrStatus.php?pnr=" + data;
				// to hide the keyboard
				iim.hideSoftInputFromWindow(edit.getWindowToken(), 0);
				flippy.showNext();
				edit.setEnabled(false);
				new MyAsyncTask().execute();
				error = 0;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
				builder1.setTitle("               Invalid PNR");
				builder1.setMessage("Please enter a valid 10 digit pnr number");
				builder1.setCancelable(true);
				builder1.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
		} else {
			// ALERT DIALOG BOX CREATED
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("         INTERNET ERROR");
			builder1.setMessage("INTERNET CONNECTION IS NOT AVAILABLE");
			builder1.setCancelable(true);
			builder1.setNeutralButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();

		}

	}

	private void initialize() {
		// TODO Auto-generated method stub
		line1 = (TextView) findViewById(R.id.textView2);
		line2 = (TextView) findViewById(R.id.textView3);
		line3 = (TextView) findViewById(R.id.textView4);
		line4 = (TextView) findViewById(R.id.textView5);
		line5 = (TextView) findViewById(R.id.textView6);
		line6 = (TextView) findViewById(R.id.textView7);
		line7 = (TextView) findViewById(R.id.textView8);
		line8 = (TextView) findViewById(R.id.textView9);
		line9 = (TextView) findViewById(R.id.textView10);
		line10 = (TextView) findViewById(R.id.textView11);
		line11 = (TextView) findViewById(R.id.textView12);
		line12 = (TextView) findViewById(R.id.textView13);
		line13 = (TextView) findViewById(R.id.textView14);
		line14 = (TextView) findViewById(R.id.textView15);
		line15 = (TextView) findViewById(R.id.textView16);
		line16 = (TextView) findViewById(R.id.textView17);
		line17 = (TextView) findViewById(R.id.textView18);
		line18 = (TextView) findViewById(R.id.textView19);
		line19 = (TextView) findViewById(R.id.textView20);
		line20 = (TextView) findViewById(R.id.textView21);
		line15.setText(" ");
		line9.setText("");
		line16.setText(" ");
		line10.setText("");
		line17.setText(" ");
		line11.setText("");
		line18.setText(" ");
		line12.setText("");
		line19.setText("");
		line13.setText("");
		line20.setText("");
		line14.setText("");

	}

	private void settingUp() {
		for (int i = 0; i < count; i++) {
			switch (i) {
			case 0: {
				line15.setText("  1]");
				line15.setTextColor(Color.DKGRAY);
				line9.setText("    " + booking0 + "        " + current0);
				line9.setTextColor(Color.BLUE);
				break;
			}
			case 1: {
				line16.setText("  2]");
				line16.setTextColor(Color.DKGRAY);
				line10.setText("    " + booking1 + "        " + current1);
				line10.setTextColor(Color.BLUE);

				break;
			}
			case 2: {
				line17.setText("  3]");
				line17.setTextColor(Color.DKGRAY);
				line11.setText("    " + booking2 + "        " + current2);
				line11.setTextColor(Color.BLUE);

				break;
			}
			case 3: {
				line18.setText("  4]");
				line18.setTextColor(Color.DKGRAY);
				line12.setText("    " + booking3 + "        " + current3);
				line12.setTextColor(Color.BLUE);
				break;
			}
			case 4: {
				line19.setText("  5]");
				line19.setTextColor(Color.DKGRAY);
				line13.setText("    " + booking4 + "        " + current4);
				line13.setTextColor(Color.BLUE);
				break;
			}
			case 5: {
				line20.setText("  6]");
				line20.setTextColor(Color.DKGRAY);
				line14.setText("    " + booking5 + "        " + current5);
				line14.setTextColor(Color.BLUE);
				break;
			}
			}
		}
	}

	public void settext() {
		line1.setText(status);
		line1.setTextColor(Color.RED);
		line2.setText("Train no." + "          : " + trainNum);
		line2.setTextColor(Color.BLUE);
		line3.setText("Train name" + "      : " + trainName);
		line3.setTextColor(Color.BLUE);
		line4.setText("Boarding from" + " : " + boarding);
		line4.setTextColor(Color.BLUE);
		line5.setText("Reserved upto" + " : " + reserved);
		line5.setTextColor(Color.BLUE);
		line6.setText("Journey date" + "    : " + trainJ);
		line6.setTextColor(Color.BLUE);
		line7.setText("class" + "                 : " + clas);
		line7.setTextColor(Color.BLUE);
		line8.setText("S/No. " + " " + "booking status" + "     "
				+ "current status");
		line8.setTextColor(Color.DKGRAY);
	}

}
