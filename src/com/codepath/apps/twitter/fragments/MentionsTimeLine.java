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

public class MentionsTimeLine extends TimeLineFragment {
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		populateData(null);

	}

	@Override
	void populateData(RequestParams params) {

		client = TwitterApp.getRestClient();
		client.getMentionsTimeLine(params, new JsonHttpResponseHandler() {
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
