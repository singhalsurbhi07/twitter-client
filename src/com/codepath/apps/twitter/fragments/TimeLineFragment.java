package com.codepath.apps.twitter.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.twitter.EndlessScrollListener;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TimeLineActivity;
import com.codepath.apps.twitter.TweetActivity;
import com.codepath.apps.twitter.adapters.TweetListAdapter;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.RequestParams;

public abstract class TimeLineFragment extends Fragment {
	private final static String TWEET_FORWARDING_KEY = "tweetKey";
	private List<Tweet> tweets;
	TweetListAdapter adapter;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		adapter = new TweetListAdapter(getActivity(), tweets);
		tweets.clear();
		adapter.clear();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_timeline, container,
				false);

		listView = (ListView) view.findViewById(R.id.lvTweets);
		listView.setAdapter(adapter);

		setScrollListener();
		itemClickListener();
		return view;
	}

	private void itemClickListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), TweetActivity.class);
				i.putExtra(TWEET_FORWARDING_KEY, tweets.get(position));
				startActivity(i);
			}

		});
	}

	private void setScrollListener() {
		listView.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				RequestParams params = new RequestParams();
				params.put("max_id",
						String.valueOf(TimeLineActivity.MIN_ELEMENT));
				populateData(params);
			}
		});
	}

	public void addAll(List<Tweet> newTweets) {
		adapter.addAll(newTweets);
	}

	abstract void populateData(RequestParams params);
}
