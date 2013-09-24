package me.noslo.titanmobile.bll;

import java.io.IOException;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaQueuePlayer {

	private SongList queue;
	private int playlistIndex;
	private boolean isPlaying;
	private long startTime;
	private Context context;

	private MediaPlayer mPlayer;

	public MediaQueuePlayer(Context context, SongList queue) {
		this.context = context;
		this.queue = queue;
		playlistIndex = 0;
		isPlaying = false;
		startTime = 0;
	}

	public Song getSong() {
		if (queue.size() > 0 && getPosition() < queue.size()
				&& getPosition() >= 0) {
			return queue.get(playlistIndex);
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
		if (queue.size() > 0) {
			isPlaying = true;

			if (mPlayer != null) {
				mPlayer.stop();
			}

			mPlayer = android.media.MediaPlayer.create(context,
					getCurrentSong().getFileUri());
			try {
				mPlayer.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mPlayer.start();

			return;
		}
		stop();
	}

	public long getStartTime() {
		return startTime;
	}

	public void stop() {
		if (isPlaying) {
			mPlayer.stop();
		}
		startTime = 0;
		isPlaying = false;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void skipForward() {
		boolean wasPlaying = isPlaying();
		stop();
		if ((playlistIndex + 1) < queue.size()) {
			++playlistIndex;
		}
		if (wasPlaying) {
			play();
		}
	}

	public void skipToEnd() {
		boolean wasPlaying = isPlaying();
		stop();
		playlistIndex = queue.size() - 1;
		if (wasPlaying) {
			play();
		}
	}

	public void setPosition(int i) {
		playlistIndex = i;
	}

	public int getPosition() {
		if (playlistIndex < 0 || playlistIndex >= queue.size()) {
			playlistIndex = 0;
		}
		return playlistIndex;
	}

	public void skipBackward() {

		boolean wasPlaying = isPlaying();
		stop();
		if ((playlistIndex - 1) >= 0) {
			--playlistIndex;
		}
		if (wasPlaying) {
			play();
		}
	}

	public SongList getQueue() {
		return queue;
	}

	public void pause() {
		//long resumeTime = getTime();
		stop();
		//startTime = resumeTime;
	}

	public void seekToPosition(double percent) {
			double length = 0;
			long time = (long) (length * percent);
			seekToTime(time);
	}

	public void seekToTime(long time) {
			boolean wasPlaying = isPlaying;
			startTime = time;
			if (wasPlaying) {
				isPlaying = true;
			} else {
			}
	}


	public String getFormatedTime() {
//		long timeMillis = getTime();
//		long time = timeMillis / 1000;
//		String seconds = Integer.toString((int) (time % 60));
//		String minutes = Integer.toString((int) ((time % 3600) / 60));
//		String hours = Integer.toString((int) (time / 3600));
//		for (int i = 0; i < 2; i++) {
//			if (seconds.length() < 2) {
//				seconds = "0" + seconds;
//			}
//			if (minutes.length() < 2) {
//				minutes = "0" + minutes;
//			}
//			if (hours.length() < 2) {
//				hours = "0" + hours;
//			}
//		}
//		return hours + ":" + minutes + ":" + seconds;
		return null;
	}

	public int getProgressPercent() {
		int percent = (mPlayer.getCurrentPosition() * 100 / mPlayer.getDuration());
		return percent;
	}

	public void setProgressPercent(int progress) {
		int currentLocation = (mPlayer.getDuration()*progress / 100 );
        mPlayer.seekTo(currentLocation);
	}

}
