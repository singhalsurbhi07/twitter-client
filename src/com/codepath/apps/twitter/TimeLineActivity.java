package com.codepath.apps.twitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.codepath.apps.twitter.fragments.HomeTimeLine;
import com.codepath.apps.twitter.fragments.MentionsTimeLine;
import com.codepath.apps.twitter.tabs.FragmentTabListener;
import com.codepath.twitterclient.datamodels.Tweet;
import com.codepath.twitterclient.datamodels.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends FragmentActivity {
	// private final static String TWEET_FORWARDING_KEY = "tweetKey";
	public static Long MIN_ELEMENT = Long.MAX_VALUE;
	TwitterClient client;
	MenuItem searchItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		client = TwitterApp.getRestClient();
		setupTabs();
		ActionBar actionBar = getActionBar();
		String title = (String) actionBar.getTitle();
		actionBar.setIcon(R.drawable.ic_bird);
		actionBar.setDisplayShowTitleEnabled(false);

		
	}

	public void onImageClicked(View v) {
		System.out.println(((User) v.getTag()).getScreenName());
		User u = (User) v.getTag();
		Intent i = new Intent(this, UserProfileActivity.class);
		i.putExtra("Profile_Key", u);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline_action_bar, menu);
		searchItem = menu.findItem(R.id.miSearch);
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
			case R.id.miSearch :
				Log.d("searchView", "clicked");
				searchViewClicked();
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}
	}

	public void searchViewClicked() {
		Log.d("searchViewClicked", "entered");
		SearchView searchView = (SearchView) searchItem.getActionView();
		if (searchView == null) {
			Log.d("serachView", "null");

		} else {
			Log.d("searchview", "not null");
		}
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent i = new Intent(getApplicationContext(),
						SearchResultActivity.class);
				i.putExtra("Query", query);
				startActivity(i);

				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
	}
	public void composeMessage() {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivity(i);
	}

	public void showProfile() {
		client.getUserCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject response) {
				super.onSuccess(arg0, response);
				Log.d("JsonUserProfile", response.toString());

				User user;
				try {
					user = User.fromJson(response);
					Log.d("userNanme", user.getName());

					Intent i = new Intent(getApplicationContext(),
							UserProfileActivity.class);
					i.putExtra("Profile_Key", user);

					startActivity(i);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	public void sendReply(View v) {
		final Tweet receivedTweet = (Tweet) v.getTag();

		final Dialog d = new Dialog(TimeLineActivity.this);
		d.setTitle("Enter the tweet");
		d.setContentView(R.layout.reply_dialog);
		Button b1 = (Button) d.findViewById(R.id.btnTweet);
		Button b2 = (Button) d.findViewById(R.id.btnCancel);

		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Log.d("TweetActivity",
				// tweetText.getText().toString());
				EditText replyText = (EditText) d
						.findViewById(R.id.etReplyText);
				Log.d("TweetActivity", replyText.getText().toString());
				if (replyText.getText().toString().length() > 0) {
					client.postTweet("@"
							+ receivedTweet.getUser().getScreenName() + " "
							+ replyText.getText().toString(),
							receivedTweet.gettID(),
							new AsyncHttpResponseHandler() {

								@Override
								public void onSuccess(String out) {

									Log.d("JSON reply obj", out);
									Intent intent = new Intent(
											getApplicationContext(),
											TimeLineActivity.class);
									startActivity(intent);
								}
							});
				}
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				d.dismiss();
				
			}
		});

		d.show();

	}

	public void sendRetweet(View v) {
		final Tweet receivedTweet = (Tweet) v.getTag();

		AlertDialog.Builder builder = new AlertDialog.Builder(
				TimeLineActivity.this);
		// Set a title
		builder.setTitle("Retweet");
		// Set a message
		builder.setMessage("Are you sure you want to retweet?");

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				client.postReTweet(receivedTweet.gettID(),
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(int arg0, String out) {
								Log.d("JSON Retweet obj", out);
								Intent intent = new Intent(
										getApplicationContext(),
										TimeLineActivity.class);
								startActivity(intent);
							}
						});
				Toast.makeText(getApplicationContext(), "Retweeted",
						Toast.LENGTH_LONG).show();

			}
		});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		// Create the dialog
		AlertDialog alertdialog = builder.create();
		alertdialog.show();

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

	
}
