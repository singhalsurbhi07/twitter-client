package com.codepath.twitterclient.datamodels;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	private String name;
	private String screenName;
	private String userID;
	private String userPic;

	public User(String name, String screenName, String userId, String userPic) {
		this.name = name;
		this.screenName = screenName;
		this.userID = userId;
		this.userPic = userPic;
	}

	public User(JSONObject obj) {
		try {
			this.name = obj.getString("name");
			this.screenName = obj.getString("screen_name");
			this.userID = obj.getString("id_str");
			this.userPic = obj.getString("profile_image_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
