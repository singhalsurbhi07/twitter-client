package com.codepath.apps.twitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.twitterclient.datamodels.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {
	EditText etTweet;
	TwitterClient client;
	TextView tvLoginName;
	TextView tvLoginHandle;
	ImageView ivUserImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		setViews();
		client = TwitterApp.getRestClient();
		fillUserDetails();
	}

	private void setViews() {
		etTweet = (EditText) findViewById(R.id.etTweet);
		tvLoginName = (TextView) findViewById(R.id.tvLoginName);
		tvLoginHandle = (TextView) findViewById(R.id.tvLoginHandle);
		ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
	}

	public void postTweet(View view) {
		client.postTweet(etTweet.getText().toString(), null,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String out) {
						Log.d("JSON obj", out);
						Intent intent = new Intent(getApplicationContext(),
								TimeLineActivity.class);
						startActivity(intent);
					}
				});
	}

	public void fillUserDetails() {
		client.getUserCredentials(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, JSONObject response) {
				super.onSuccess(arg0, response);
				try {
					tvLoginName.setTypeface(null, Typeface.BOLD);
					tvLoginHandle.setTextColor(Color.GRAY);
					tvLoginHandle.setTextSize((float) 10.0);
					tvLoginName.setTextSize((float) 18.0);

					User user = User.fromJson(response);

					tvLoginName.setText(user.getName());
					tvLoginHandle.setText("@" + user.getScreenName());
					ImageLoader loader = ImageLoader.getInstance();
					loader.displayImage(user.getUserPic(), ivUserImage);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		});
	}
}
