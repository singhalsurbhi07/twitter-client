package com.codepath.apps.twitter.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.twitter.TwitterApp;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SearchResultFragment extends TimeLineFragment {
	private TwitterClient client;
	private String query;

	public static SearchResultFragment newInstance(String receivedQuery) {
		SearchResultFragment searchTimeline = new SearchResultFragment();
		Bundle args = new Bundle();
		args.putString("Query", receivedQuery);
		searchTimeline.setArguments(args);
		return searchTimeline;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		query = (getArguments().getString("Query"));
		RequestParams params = new RequestParams();
		params.put("q", Uri.encode(query));
		populateData(params);

	}

	@Override
	void populateData(RequestParams params) {

		client = TwitterApp.getRestClient();
		Log.d("searchresultActivity query", query);
		params.put("q", Uri.encode(query));
		client.getSearchTimeLine(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject obj) {
				Log.d("JSON searchresult obj", obj.toString());
				JSONArray arrOfObj;
				try {
					arrOfObj = obj.getJSONArray("statuses");
					List<Tweet> newTweets = Tweet.fromJsonArray(arrOfObj);
					addAll(newTweets);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

		});

	}

}
