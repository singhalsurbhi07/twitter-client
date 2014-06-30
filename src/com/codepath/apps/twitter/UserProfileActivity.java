package com.codepath.apps.twitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends FragmentActivity {
	private TwitterClient client;
	TextView userName;
	TextView userTag;
	TextView usersFollowersCount;
	TextView usersFollowingCount;
	ImageView userPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		// setUpViews();
		// client = TwitterApp.getRestClient();
		// fillUserDetails();
	}

	// public void setUpViews() {
	// userName = (TextView) findViewById(R.id.tvUserProfName);
	// userTag = (TextView) findViewById(R.id.tvUserProfTag);
	// usersFollowersCount = (TextView)
	// findViewById(R.id.tvUserProfFollowersCount);
	// usersFollowingCount = (TextView)
	// findViewById(R.id.tvUserProfFollowingCount);
	// userPic = (ImageView) findViewById(R.id.ivUserProfPic);
	//
	// }

	// public void fillUserDetails() {
	// client.getUserCredentials(new JsonHttpResponseHandler() {
	// @Override
	// public void onSuccess(int arg0, JSONObject response) {
	// super.onSuccess(arg0, response);
	// try {
	// User user = User.fromJson(response);
	// userName.setText(user.getName());
	// userTag.setText(user.getTag());
	// usersFollowersCount.setText(String.valueOf(user
	// .getFollowersCount()));
	// usersFollowingCount.setText(String.valueOf(user
	// .getFollowingsCount()));
	// ImageLoader loader = ImageLoader.getInstance();
	// loader.displayImage(user.getUserPic(), userPic);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// });
	// }

}
