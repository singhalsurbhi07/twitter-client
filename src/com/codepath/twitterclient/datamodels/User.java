package com.codepath.twitterclient.datamodels;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private String name;
	private String screenName;
	private String userID;
	private String userPic;
	private int followersCount;
	private int followingsCount;
	private String tag;

	public User(String name, String screenName, String userId, String userPic,
			int followersCount, int followingsCount, String tag) {
		this.name = name;
		this.screenName = screenName;
		this.userID = userId;
		this.userPic = userPic;
		this.followersCount = followersCount;
		this.followingsCount = followingsCount;
		this.tag = tag;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingsCount() {
		return followingsCount;
	}

	public String getTag() {
		return tag;
	}

	public User(JSONObject obj) throws JSONException {
		this.name = obj.getString("name");
		this.screenName = obj.getString("screen_name");
		this.userID = obj.getString("id_str");
		this.userPic = obj.getString("profile_image_url");
		this.followersCount = obj.getInt("followers_count");
		this.followingsCount = obj.getInt("friends_count");
		this.tag = obj.getString("description");
	}

	public static User fromJson(JSONObject obj) throws JSONException {
		return new User(obj);
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getUserID() {
		return userID;
	}

	public String getUserPic() {
		return userPic;
	}
}
