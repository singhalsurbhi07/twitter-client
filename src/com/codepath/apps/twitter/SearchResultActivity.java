package com.codepath.apps.twitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.codepath.apps.twitter.fragments.SearchResultFragment;

public class SearchResultActivity extends FragmentActivity {
	String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		query = getIntent().getStringExtra("Query");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		SearchResultFragment fragment = SearchResultFragment.newInstance(query);
		ft.replace(R.id.flSearchContainer, fragment);
		ft.commit();
	}

}
