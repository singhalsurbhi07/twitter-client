package com.codepath.apps.twitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.fragments.UserProfileFragment;
import com.codepath.apps.twitter.fragments.UserTimeLine;
import com.codepath.twitterclient.datamodels.User;

public class UserProfileActivity extends FragmentActivity {
	private TwitterClient client;
	User user;
	TextView userName;
	TextView userTag;
	TextView usersFollowersCount;
	TextView usersFollowingCount;
	ImageView userPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		user = (User) getIntent().getSerializableExtra("Profile_Key");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		FragmentTransaction timelineft = getSupportFragmentManager()
				.beginTransaction();
		UserProfileFragment fragment = UserProfileFragment.newInstance(user);
		UserTimeLine timelinefragment = UserTimeLine.newInstance(user
				.getUserID());
		ft.replace(R.id.fragmentUserProfile, fragment);
		ft.commit();

		timelineft.replace(R.id.fragmentUserTimeLine, timelinefragment);
		timelineft.commit();
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
