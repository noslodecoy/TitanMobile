package me.noslo.titanmobile.bll;

import java.util.ArrayList;

import android.util.Log;

public interface MediaLibraryList {

	public void setName(String name);

	public String getName();
	
	public void setId(long id);

	public long getId();

	public void addReplaceAll( ArrayList<MediaLibraryItem> items );
	
}
