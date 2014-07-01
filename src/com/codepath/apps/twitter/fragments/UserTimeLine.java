package com.codepath.apps.twitter.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApp;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserTimeLine extends TimeLineFragment {

	private TwitterClient client;
	String id;

	public static UserTimeLine newInstance(String userID) {
		UserTimeLine userTimeline = new UserTimeLine();
		Bundle args = new Bundle();
		args.putString("UserID", userID);
		userTimeline.setArguments(args);
		return userTimeline;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		id = getArguments().getString("UserID");
		RequestParams params = new RequestParams();
		params.put("user_id", id);
		populateData(params);

	}

	@Override
	void populateData(RequestParams params) {

		client = TwitterApp.getRestClient();
		params.put("user_id", id);
		client.getUserTimeLine(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray obj) {
				Log.d("JSON obj", obj.toString());
				List<Tweet> newTweets = Tweet.fromJsonArray(obj);
				addAll(newTweets);
				// adapter.addAll(newTweets);
			}
		});

	}

}
