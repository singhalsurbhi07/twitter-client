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
		super.onCreate(savedInstanceState);
		populateData(null);

	}

	@Override
	void populateData(RequestParams params) {

		client = TwitterApp.getRestClient();
		client.getMentionsTimeLine(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray obj) {
				Log.d("JSON mentions obj", obj.toString());
				List<Tweet> newTweets = Tweet.fromJsonArray(obj);
				addAll(newTweets);
			}

			@Override
			public void onFailure(Throwable arg0, JSONArray arg1) {
				System.out.print(arg0);
				System.out.println(arg1);
				super.onFailure(arg0, arg1);
			}
		});

	}

}
