package com.codepath.apps.twitter.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.twitter.EndlessScrollListener;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.TimeLineActivity;
import com.codepath.apps.twitter.adapters.ListAdapter;
import com.codepath.twitterclient.datamodels.Tweet;
import com.loopj.android.http.RequestParams;

public abstract class TimeLineFragment extends Fragment {
	private List<Tweet> tweets;
	ListAdapter adapter;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		tweets = new ArrayList<Tweet>();
		adapter = new ListAdapter(getActivity(), tweets);
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
		return view;
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
