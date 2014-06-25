package com.codepath.twitterclient.datamodels;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;

import com.codepath.apps.twitter.TimeLineActivity;

public class Tweet implements Serializable {
	private static final long serialVersionUID = -7740011131247133794L;
	private String text;
	private String tID;
	private String createdAt;
	private User user;
	private int favCount;
	private int retweetCount;

	public Tweet(String text, String tID, String createdAt, User user,
			int favCount, int retweetCount) {
		this.text = text;
		this.tID = tID;
		this.createdAt = createdAt;
		this.user = user;
		this.favCount = favCount;
		this.retweetCount = retweetCount;
	}

	public Tweet(JSONObject obj) throws JSONException {
		this(obj.getString("text"), obj.getString("id_str"), obj
				.getString("created_at"), new User(obj.getJSONObject("user")),
				obj.getInt("favorite_count"), obj.getInt("retweet_count"));
	}

	public static List<Tweet> fromJsonArray(JSONArray arrayOfTweets) {
		List<Tweet> result = new ArrayList<Tweet>(arrayOfTweets.length());
		for (int i = 0; i < arrayOfTweets.length(); i++) {
			try {
				TimeLineActivity.MIN_ELEMENT = Math.min(
						TimeLineActivity.MIN_ELEMENT, arrayOfTweets
								.getJSONObject(i).getLong("id"));
				result.add(new Tweet(arrayOfTweets.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<Tweet> RepliesFromJsonArray(String id,
			JSONArray arrayOfTweets) {
		List<Tweet> result = new ArrayList<Tweet>(arrayOfTweets.length());
		for (int i = 0; i < arrayOfTweets.length(); i++) {
			try {
				JSONObject obj = arrayOfTweets.getJSONObject(i);
				if (obj.getString("in_reply_to_status_id").equals(id)) {
					result.add(new Tweet(arrayOfTweets.getJSONObject(i)));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String getText() {
		return text;
	}

	public String gettID() {
		return tID;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public int getFavCount() {
		return favCount;
	}

	public int getRetweetCount() {
		return retweetCount;
	}
	public String getRelativeTimeAgo(String rawJsonDate)
			throws java.text.ParseException {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat,
				Locale.ENGLISH);
		sf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS)
					.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] relativeTimeArray = relativeDate.split(" ");
		return relativeTimeArray[0] + relativeTimeArray[1].charAt(0);
	}
}
