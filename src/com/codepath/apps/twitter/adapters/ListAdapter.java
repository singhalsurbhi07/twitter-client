package com.codepath.apps.twitter.adapters;

import java.text.ParseException;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.twitterclient.datamodels.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ListAdapter extends ArrayAdapter<Tweet> {

	private static class ViewHolder {
		ImageView userPic;
		TextView userName;
		TextView userScreenName;
		TextView tweetText;
		TextView relativeTime;

	}

	public ListAdapter(Context context, List<Tweet> tweets) {
		super(context, R.layout.tweet, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Tweet tweet = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder; // view lookup cache stored in tag

		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.tweet, parent, false);

			viewHolder.userPic = (ImageView) convertView
					.findViewById(R.id.ivUserPic);
			viewHolder.userName = (TextView) convertView
					.findViewById(R.id.tvUserName);
			viewHolder.userScreenName = (TextView) convertView
					.findViewById(R.id.tvScreenName);
			viewHolder.tweetText = (TextView) convertView
					.findViewById(R.id.tvTweetText);
			viewHolder.relativeTime = (TextView) convertView
					.findViewById(R.id.tvRelativeTime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// Populate the data into the template view using the data object
		viewHolder.userName.setText(tweet.getUser().getName());
		viewHolder.userScreenName
				.setText("@" + tweet.getUser().getScreenName());
		viewHolder.tweetText.setText(tweet.getText());
		viewHolder.userName.setTypeface(null, Typeface.BOLD);
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getUserPic(), viewHolder.userPic);
		try {
			viewHolder.relativeTime.setText(tweet.getRelativeTimeAgo(tweet
					.getCreatedAt()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Return the completed view to render on screen
		return convertView;
	}
}
