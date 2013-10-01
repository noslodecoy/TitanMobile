package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.R;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class PlayerControlsFragment extends Fragment implements OnSeekBarChangeListener {

	private SeekBar mProgressBar;
	private TitanPlayerActivity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mActivity = (TitanPlayerActivity) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must extend TitanPlayerActivity");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.music_controls, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mProgressBar = (SeekBar) mActivity.findViewById(R.id.progress_bar);
		mProgressBar.setOnSeekBarChangeListener(this);

		ImageButton playButton = (ImageButton) mActivity.findViewById(R.id.play_button);
		ImageButton skipForwardButton = (ImageButton) mActivity
				.findViewById(R.id.skip_forward_button);
		ImageButton skipBackwardButton = (ImageButton) mActivity
				.findViewById(R.id.skip_backward_button);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				togglePlay();
			}
		});

		skipForwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				skipForward();
			}
		});

		skipBackwardButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				skipBackward();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		drawPlayBtn();
	}

	public void play() {
		mActivity.app.mediaPlayer.play();
		Log.d("Button pushed", "Play");
		updateProgressBar();
		updateCurrentlyPlaying();
		showNowPlayingDialog();
	}

	public void pause() {
		mActivity.app.mediaPlayer.pause();
		finishPlaying();
		updateCurrentlyPlaying();
	}

	public void finishPlaying() {
		mProgressBar.setProgress(0);
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	public void drawPlayBtn() {
		if (mActivity.app.mediaPlayer.isPlaying()) {
			ImageButton btn = (ImageButton) mActivity.findViewById(R.id.play_button);
			btn.setImageResource(R.drawable.pause);
		} else {
			ImageButton btn = (ImageButton) mActivity.findViewById(R.id.play_button);
			btn.setImageResource(R.drawable.play);
		}
	}

	public void togglePlay() {
		if (mActivity.app.mediaPlayer.isPlaying()) {
			pause();
		} else {
			play();
		}
	}

	public void updateCurrentlyPlaying() {
		drawPlayBtn();
		String txt = "";
		if (mActivity.app.mediaPlayer.isPlaying()) {
			updateProgressBar();
			txt = mActivity.app.mediaPlayer.getSong().getName();
		}
		TextView txtCurrentlyPlaying = (TextView) mActivity
				.findViewById(R.id.currently_playing_text);
		txtCurrentlyPlaying.setText(txt);
	}

	public void showNowPlayingDialog() {
//		AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
//		alertDialog.setTitle("Play Song");
//		alertDialog.setMessage("Playing " + mActivity.app.mediaPlayer.getSong().getName());
//		alertDialog.show();
	}

	public void skipForward() {
		finishPlaying();
		mActivity.app.mediaPlayer.skipForward();
		updateCurrentlyPlaying();
	}

	public void skipBackward() {
		finishPlaying();
		mActivity.app.mediaPlayer.skipBackward();
		updateCurrentlyPlaying();
	}

	private Handler mHandler = new Handler();

	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		mActivity.app.mediaPlayer.setProgressPercent(seekBar.getProgress());
		updateProgressBar();
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			int progress = (int) (mActivity.app.mediaPlayer.getProgressPercent());
			mProgressBar.setProgress(progress);
			mHandler.postDelayed(this, 100);
		}
	};

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

}
