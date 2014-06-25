package com.codepath.apps.twitter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.twitter.adapters.ListAdapter;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TimeLineActivity extends Activity {
	private final static String TWEET_FORWARDING_KEY = "tweetKey";
	private TwitterClient client;
	private List<Tweet> tweets;
	ListAdapter adapter;

	public static Long MIN_ELEMENT = Long.MAX_VALUE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		client = TwitterApp.getRestClient();

		tweets = new ArrayList<Tweet>();
		adapter = new ListAdapter(this, tweets);
		MIN_ELEMENT = Long.MAX_VALUE;

		// Attach the adapter to a ListView
		ListView listView = (ListView) findViewById(R.id.lvTweets);
		listView.setAdapter(adapter);

		tweets.clear();
		adapter.clear();

		getDataforTimeLine(null);
		listView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				RequestParams params = new RequestParams();
				Log.d("MINID", String.valueOf(MIN_ELEMENT));
				params.put("max_id", String.valueOf(MIN_ELEMENT));
				getDataforTimeLine(params);
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),
						TweetActivity.class);
				i.putExtra(TWEET_FORWARDING_KEY, tweets.get(position));
				startActivity(i);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline_action_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
			case R.id.miCompose :
				composeMessage();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}

	public void composeMessage() {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivity(i);
	}

	public void getDataforTimeLine(RequestParams params) {
		client.getTimeLine(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray obj) {
				Log.d("JSON obj", obj.toString());
				List<Tweet> newTweets = Tweet.fromJsonArray(obj);
				adapter.addAll(newTweets);
			}
		});
	}
}
