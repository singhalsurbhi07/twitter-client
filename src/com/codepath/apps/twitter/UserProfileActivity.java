package com.codepath.apps.twitter;

import java.util.List;
import java.util.Vector;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.fragments.ProfileTagFragmant;
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
	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		user = (User) getIntent().getSerializableExtra("Profile_Key");
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.ic_bird);
		String title = user.getName();
		actionBar.setTitle(title);
		initializePager();
		FragmentTransaction timelineft = getSupportFragmentManager()
				.beginTransaction();
		UserTimeLine timelinefragment = UserTimeLine.newInstance(user
				.getUserID());
		timelineft.replace(R.id.fragmentUserTimeLine, timelinefragment);
		timelineft.commit();
	}

	public void initializePager() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(UserProfileFragment.newInstance(user));
		fragments.add(ProfileTagFragmant.newInstance(user));
		mPagerAdapter = new com.codepath.apps.twitter.adapters.PagerAdapter(
				this.getSupportFragmentManager(), fragments);
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(mPagerAdapter);

	}

}
