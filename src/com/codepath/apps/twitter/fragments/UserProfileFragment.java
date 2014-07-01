package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TwitterApp;
import com.codepath.apps.twitter.TwitterClient;
import com.codepath.twitterclient.datamodels.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileFragment extends Fragment {

	private TwitterClient client;
	User newUser;
	TextView userName;
	TextView userTag;
	TextView usersFollowersCount;
	TextView usersFollowingCount;
	ImageView userPic;

	public static UserProfileFragment newInstance(User user) {
		UserProfileFragment profile = new UserProfileFragment();
		Bundle args = new Bundle();
		args.putSerializable("Profile_Key", user);
		profile.setArguments(args);
		return profile;
	}
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		newUser = (User) getArguments().getSerializable("Profile_Key");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_profile, container,
				false);
		userName = (TextView) view.findViewById(R.id.tvUserProfName);
		userTag = (TextView) view.findViewById(R.id.tvUserProfTag);
		usersFollowersCount = (TextView) view
				.findViewById(R.id.tvUserProfFollowersCount);
		usersFollowingCount = (TextView) view
				.findViewById(R.id.tvUserProfFollowingCount);
		userPic = (ImageView) view.findViewById(R.id.ivUserProfPic);

		client = TwitterApp.getRestClient();
		fillUserDetails();

		return view;
	}

	public void fillUserDetails() {
		// client.getUserCredentials(new JsonHttpResponseHandler() {
		// @Override
		// public void onSuccess(int arg0, JSONObject response) {
		// super.onSuccess(arg0, response);
		// Log.d("JsonUserProfile", response.toString());
		// try {
		// User user = User.fromJson(response);
		// Log.d("userNanme", user.getName());
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
		userName.setText(newUser.getName());
		userTag.setText(newUser.getTag());
		usersFollowersCount
				.setText(String.valueOf(newUser.getFollowersCount()));
		usersFollowingCount
				.setText(String.valueOf(newUser.getFollowingsCount()));
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(newUser.getUserPic(), userPic);

	}
}
