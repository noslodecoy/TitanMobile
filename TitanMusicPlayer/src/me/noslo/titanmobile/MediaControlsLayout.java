package me.noslo.titanmobile;

import me.noslo.titanmobile.bll.MediaPlayer;
import me.noslo.titanmobile.bll.SongList;

import com.example.titanmusicplayer.R;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MediaControlsLayout extends RelativeLayout {
	
	private MediaPlayer mediaPlayer;
	
	public MediaControlsLayout( Context context, AttributeSet attr ) {
		super(context, attr);
	}
	
	public void addQueue( SongList queue ) {
		this.mediaPlayer = new MediaPlayer(queue);
	}

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        

    }
    
    public void togglePlay() {
    	
    }
}
