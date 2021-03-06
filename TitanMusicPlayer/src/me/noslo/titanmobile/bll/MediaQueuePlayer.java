package me.noslo.titanmobile.bll;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaQueuePlayer {

	private Playlist mQueue;
	private int mPlaylistIndex;
	private boolean mIsPlaying;
	private long mStartTime;
	private Context mContext;

	private MediaPlayer mPlayer;

	public MediaQueuePlayer(Context context, Playlist queue) {
		this.mContext = context;
		mQueue = queue;
		mPlaylistIndex = 0;
		mIsPlaying = false;
		mStartTime = 0;
	}

	public Song getSong() {
		if (mQueue.size() > 0 && getPosition() < mQueue.size() && getPosition() >= 0) {
			return (Song) mQueue.get(mPlaylistIndex);
		}
		return null;
	}

	public Song getCurrentSong() {
		return getSong();
	}

	public String getSongTitleOfCurrentTrack() {
		String songTitle = getCurrentSong().getName();
		return songTitle;
	}

	public void play() {
		if (mQueue.size() > 0) {
			mIsPlaying = true;

			if (mPlayer != null) {
				mPlayer.stop();
			}

			try {
				mPlayer = android.media.MediaPlayer.create(mContext, getCurrentSong().getFileUri());
				mPlayer.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}

			return;
		}
		stop();
	}

	public long getStartTime() {
		return mStartTime;
	}

	public void stop() {
		if (mIsPlaying) {
			mPlayer.stop();
		}
		mStartTime = 0;
		mIsPlaying = false;
	}

	public boolean isPlaying() {
		return mIsPlaying;
	}

	public void skipForward() {
		boolean wasPlaying = isPlaying();
		stop();
		if ((mPlaylistIndex + 1) < mQueue.size()) {
			++mPlaylistIndex;
		}
		if (wasPlaying) {
			play();
		}
	}

	public void skipToEnd() {
		boolean wasPlaying = isPlaying();
		stop();
		mPlaylistIndex = mQueue.size() - 1;
		if (wasPlaying) {
			play();
		}
	}

	public void setPosition(int i) {
		mPlaylistIndex = i;
	}

	public int getPosition() {
		if (mPlaylistIndex < 0 || mPlaylistIndex >= mQueue.size()) {
			mPlaylistIndex = 0;
		}
		return mPlaylistIndex;
	}

	public void skipBackward() {

		boolean wasPlaying = isPlaying();
		stop();
		if ((mPlaylistIndex - 1) >= 0) {
			--mPlaylistIndex;
		}
		if (wasPlaying) {
			play();
		}
	}

	public Playlist getQueue() {
		return mQueue;
	}

	public void pause() {
		// long resumeTime = getTime();
		stop();
		// startTime = resumeTime;
	}

	public void seekToPosition(double percent) {
		double length = 0;
		long time = (long) (length * percent);
		seekToTime(time);
	}

	public void seekToTime(long time) {
		boolean wasPlaying = mIsPlaying;
		mStartTime = time;
		if (wasPlaying) {
			mIsPlaying = true;
		} else {
		}
	}

	public String getFormatedTime() {
		// long timeMillis = getTime();
		// long time = timeMillis / 1000;
		// String seconds = Integer.toString((int) (time % 60));
		// String minutes = Integer.toString((int) ((time % 3600) / 60));
		// String hours = Integer.toString((int) (time / 3600));
		// for (int i = 0; i < 2; i++) {
		// if (seconds.length() < 2) {
		// seconds = "0" + seconds;
		// }
		// if (minutes.length() < 2) {
		// minutes = "0" + minutes;
		// }
		// if (hours.length() < 2) {
		// hours = "0" + hours;
		// }
		// }
		// return hours + ":" + minutes + ":" + seconds;
		return null;
	}

	public int getProgressPercent() {
		if (mPlayer != null) {
			int percent = (mPlayer.getCurrentPosition() * 100 / mPlayer.getDuration());
			return percent;
		}
		return 0;
	}

	public void setProgressPercent(int progress) {
		if (mPlayer != null) {
			int currentLocation = (mPlayer.getDuration() * progress / 100);
			mPlayer.seekTo(currentLocation);
		}
	}

}
