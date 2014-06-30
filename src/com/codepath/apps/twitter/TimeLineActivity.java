package com.codepath.apps.twitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitter.fragments.HomeTimeLine;
import com.codepath.apps.twitter.fragments.MentionsTimeLine;
import com.codepath.apps.twitter.tabs.FragmentTabListener;

public class TimeLineActivity extends FragmentActivity {
	// private final static String TWEET_FORWARDING_KEY = "tweetKey";
	public static Long MIN_ELEMENT = Long.MAX_VALUE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		setupTabs();

		// listView.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		// Intent i = new Intent(getApplicationContext(),
		// TweetActivity.class);
		// i.putExtra(TWEET_FORWARDING_KEY, tweets.get(position));
		// startActivity(i);
		// }
		//
		// });
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
			case R.id.miProfile :
				showProfile();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}

	public void composeMessage() {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivity(i);
	}

	public void showProfile() {
		Intent i = new Intent(this, UserProfileActivity.class);
		startActivity(i);
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimeLine>(R.id.flContainer,
								this, "first", HomeTimeLine.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimeLine>(
								R.id.flContainer, this, "second",
								MentionsTimeLine.class));

		actionBar.addTab(tab2);
	}

	// public void getDataforTimeLine(RequestParams params) {
	// client.getTimeLine(params, new JsonHttpResponseHandler() {
	// @Override
	// public void onSuccess(JSONArray obj) {
	// Log.d("JSON obj", obj.toString());
	// List<Tweet> newTweets = Tweet.fromJsonArray(obj);
	// timelineFragment.addAll(newTweets);
	// // adapter.addAll(newTweets);
	// }
	// });
	// }
}
