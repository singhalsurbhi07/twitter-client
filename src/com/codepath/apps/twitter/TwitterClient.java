package com.codepath.apps.twitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change
																				// this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change
																			// this,
																			// base
																			// API
																			// URL
	public static final String REST_CONSUMER_KEY = "m7aKQuNoZCogh3x9bYRkXOnXo"; // Change
																				// this
	public static final String REST_CONSUMER_SECRET = "mngi6gOnaTLfKOoLLaIBQLVD37Z0cznjHun4fB3CQAzdinUjYq"; // Change
																											// this
	public static final String REST_CALLBACK_URL = "oauth://cpbasicTweets"; // Change
																			// this
																			// (here
																			// and
																			// in
																			// manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeLine(RequestParams params,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/home_timeline.json");
		client.get(url, params, handler);
	}

	public void getMentionsTimeLine(RequestParams params,
			AsyncHttpResponseHandler handler) {
		System.out.println("calling user mentions");
		String url = getApiUrl("statuses/mentions_timeline.json");
		client.get(url, params, handler);
	}

	public void getUserTimeLine(RequestParams params,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/user_timeline.json");
		client.get(url, params, handler);
	}

	public void postTweet(String tweet, String originalTweetID,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		params.put("in_reply_to_status_id", originalTweetID);
		client.post(url, params, handler);
	}

	public void postReTweet(String originalTweetID,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/retweet/" + originalTweetID + ".json");
		// RequestParams params = new RequestParams();
		client.post(url, null, handler);
	}

	public void postFavorite(String originalTweetID,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("favorites/create.json");
		RequestParams params = new RequestParams();
		params.put("id", originalTweetID);
		client.post(url, params, handler);
	}

	public void getTweetDetails(String min_id, String max_id,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("since_id", min_id);
		params.put("max_id", max_id);
		params.put("exclude_replies", "false");
		Log.d("RetweetURL", url);

		client.get(url, params, handler);
		// String additional_url="/statuses/home_timeline.json";
	}

	public void getUserCredentials(AsyncHttpResponseHandler handler) {
		String url = getApiUrl("account/verify_credentials.json");
		client.get(url, handler);
	}

	public void getSearchTimeLine(RequestParams params,
			AsyncHttpResponseHandler handler) {
		String url = getApiUrl("search/tweets.json");
		client.get(url, params, handler);

	}

	
}