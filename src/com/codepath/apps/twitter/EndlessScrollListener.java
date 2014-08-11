package com.codepath.apps.twitter;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {
	private int visibleThreshold = 5;
	private int currentPage = 0;
	private int previousTotalItemCount = 0;
	private boolean loading = true;
	private int startingPageIndex = 0;

	public EndlessScrollListener() {
	}

	public EndlessScrollListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}

	public EndlessScrollListener(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.startingPageIndex = startPage;
		this.currentPage = startPage;
	}

	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// If the total item count is zero and the previous isn't, assume the
		// list is invalidated and should be reset back to initial state
		if (totalItemCount < previousTotalItemCount) {
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.loading = true;
			}
		}

		// If it�s still loading, we check to see if the dataset count has
		// changed, if so we conclude it has finished loading and update the
		// current page
		// number and total item count.
		if (loading && (totalItemCount > previousTotalItemCount)) {
			loading = false;
			previousTotalItemCount = totalItemCount;
			currentPage++;
		}

		// If it isn�t currently loading, we check to see if we have breached
		// the visibleThreshold and need to reload more data.
		// If we do need to reload some more data, we execute onLoadMore to
		// fetch the data.
		if (!loading
				&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			onLoadMore(currentPage + 1, totalItemCount);
			loading = true;
		}
	}

	// Defines the process for actually loading more data based on page
	public abstract void onLoadMore(int page, int totalItemsCount);

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Don't take any action on changed
	}
}
