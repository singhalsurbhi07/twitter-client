package com.codepath.apps.twitter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.twitterclient.datamodels.User;

public class ProfileTagFragmant extends Fragment {

	private User newUser;
	TextView userTag;
	TextView userlocation;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		newUser = (User) getArguments().getSerializable("Profile_Key");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.profile_tag_fragment, container,
				false);
		userTag = (TextView) view.findViewById(R.id.tvUserTag);
		userlocation = (TextView) view.findViewById(R.id.tvUserLocation);
		fillUserDetails();

		return view;
	}

	public static ProfileTagFragmant newInstance(User user) {
		ProfileTagFragmant profileTag = new ProfileTagFragmant();
		Bundle args = new Bundle();
		args.putSerializable("Profile_Key", user);
		profileTag.setArguments(args);
		return profileTag;
	}

	public void fillUserDetails() {
		userTag.setText(newUser.getTag());
		userlocation.setText(newUser.getLocation());
	}

}
