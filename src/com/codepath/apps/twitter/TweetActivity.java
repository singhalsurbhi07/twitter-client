package com.codepath.apps.twitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitter.adapters.ListAdapter;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetActivity extends Activity {
	private final static String TWEET_FORWARDING_KEY = "tweetKey";
	TextView tvName;
	TextView tvHandle;
	TextView tvText;
	TextView tvTime;
	TextView tvTweetStat;
	ImageView userPic;
	ImageView reply;
	ImageView reTweet;
	ImageView ivFav;
	Tweet receivedTweet;

	long receivedID;
	long minID;
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet);
		tweets = new ArrayList<Tweet>();
		adapter = new ListAdapter(this, tweets);
		Intent i = getIntent();
		receivedTweet = (Tweet) i.getSerializableExtra(TWEET_FORWARDING_KEY);
		receivedID = Long.parseLong(receivedTweet.gettID());
		ListView listView = (ListView) findViewById(R.id.lvReplies);
		listView.setAdapter(adapter);
		// minID = receivedID - 1;
		Log.d("RetweetId", String.valueOf(receivedID));
		client = TwitterApp.getRestClient();
		setupViews();
		tvName.setText(receivedTweet.getUser().getName());
		tvHandle.setText("@" + receivedTweet.getUser().getScreenName());
		tvText.setText(receivedTweet.getText());
		tvTime.setText(receivedTweet.getCreatedAt());
		tvTweetStat.setText(receivedTweet.getFavCount() + " FAV   "
				+ receivedTweet.getRetweetCount() + " RETWEET");
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(receivedTweet.getUser().getUserPic(), userPic);
		getTweetData(String.valueOf(minID), String.valueOf(receivedID));
		reply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("TweetActivity", "Reply clicked");

				final Dialog d = new Dialog(TweetActivity.this);
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
									+ receivedTweet.getUser().getScreenName()
									+ " " + replyText.getText().toString(),
									receivedTweet.gettID(),
									new AsyncHttpResponseHandler() {

										@Override
										public void onSuccess(String out) {
											Log.d("JSON obj", out);
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
						// selectedType = "all";

						d.dismiss();
						// tvSelectedType.setText(selectedType);
					}
				});

				d.show();

			}

		});

		ivFav.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ivFav.setImageResource(R.drawable.ic_start_fav);
				client.postFavorite(receivedTweet.gettID(),
						new AsyncHttpResponseHandler() {

							@Override
							public void onSuccess(String out) {
								Log.d("JSON Favorite obj", out);
								Intent intent = new Intent(
										getApplicationContext(),
										TimeLineActivity.class);
								startActivity(intent);
							}
						});

			}
		});

		reTweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						TweetActivity.this);
				// Set a title
				builder.setTitle("Retweet");
				// Set a message
				builder.setMessage("Are you sure you want to retweet?");

				builder.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								client.postReTweet(receivedTweet.gettID(),
										new AsyncHttpResponseHandler() {

											@Override
											public void onSuccess(String out) {
												Log.d("JSON Retweet obj", out);
												Intent intent = new Intent(
														getApplicationContext(),
														TimeLineActivity.class);
												startActivity(intent);
											}
										});
								Toast.makeText(getApplicationContext(),
										"Retweeted", Toast.LENGTH_LONG).show();

							}
						});
				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
				// Create the dialog
				AlertDialog alertdialog = builder.create();

				// show the alertdia
				alertdialog.show();

			}

		});

	}
	private void setupViews() {
		tvName = (TextView) findViewById(R.id.tvUserName);
		tvHandle = (TextView) findViewById(R.id.tvHandle);
		tvText = (TextView) findViewById(R.id.tvText);
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTweetStat = (TextView) findViewById(R.id.tvTweetStat);
		reply = (ImageView) findViewById(R.id.ivReply);
		reTweet = (ImageView) findViewById(R.id.ivRetweet);
		userPic = (ImageView) findViewById(R.id.ivPic);
		ivFav = (ImageView) findViewById(R.id.ivFav);
		// tweetText = (EditText) findViewById(R.id.etReply);

	}

	public void getTweetData(String min_id, String max_id) {
		tweets.clear();
		Log.d("RetweetId", String.valueOf(receivedID));
		RequestParams params = new RequestParams();
		params.put("since_id", receivedTweet.gettID());

		client.getTimeLine(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray obj) {
				Log.d("JSON Retweets obj", obj.toString());
				System.out.println(obj.toString());
				tweets.addAll(Tweet.RepliesFromJsonArray(
						receivedTweet.gettID(), obj));
				System.out.println(tweets.size());
				adapter.notifyDataSetChanged();
			}
		});
	}
}
