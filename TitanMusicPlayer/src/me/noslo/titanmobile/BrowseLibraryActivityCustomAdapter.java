//package me.noslo.titanmobile;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import me.noslo.titanmobile.bll.Album;
//import com.example.titanmusicplayer.R;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.app.ActionBar;
//import android.app.LoaderManager;
//import android.content.Context;
//import android.content.CursorLoader;
//import android.content.Intent;
//import android.content.Loader;
//import android.database.Cursor;
//import android.support.v4.widget.SimpleCursorAdapter;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.CursorAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class BrowseLibraryActivity extends TitanPlayerActivity implements
//		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
//	public static final String EXTRA_ALBUM_ID = "me.noslo.titanmobile.extra.ALBUM";
//
//	private long selectedAlbumId;
//	private ListView list;
//	private SimpleCursorAdapter adapter;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_browse_albums);
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);
//
//		selectedAlbumId = getIntent().getLongExtra(EXTRA_ALBUM_ID, 0);
//
//		list = (ListView) findViewById(R.id.browseAlbumsListView);
//		list.setOnItemClickListener(this);
//
//		this.fillList();
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.browse_library, menu);
//		return true;
//	}
//
//	private void fillList() {
//
//		String[] from = new String[] { MediaStore.Audio.Media.TITLE,
//				MediaStore.Audio.Media.ALBUM };
//		int[] to = new int[] { R.id.song_title, R.id.song_meta };
//
//		getLoaderManager().initLoader(0, null, this);
//		adapter = new SimpleCursorAdapter(this, R.layout.song_list_item, null,
//				from, to, 0);
//
//		list.setAdapter(adapter);
//	}
//
//	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
//		// Album album = getListItemAlbum(position);
//		// Intent intent = new Intent(this, BrowseLibraryActivity.class);
//		// intent.putExtra(BrowseLibraryActivity.EXTRA_ALBUM, album.getId());
//		// startActivity(intent);
//	}
//
//	@Override
//	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//		CursorLoader cursorLoader;
//		String[] projection = { MediaStore.Audio.Media._ID,
//				MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
//				MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.TRACK };
//		Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//		if (selectedAlbumId > 0) {
//			cursorLoader = new CursorLoader(this, contentUri, projection, MediaStore.Audio.Media.ALBUM_ID+"=?",
//					new String[]{String.valueOf(selectedAlbumId)}, MediaStore.Audio.Media.TRACK);
//		} else {
//			cursorLoader = new CursorLoader(this, contentUri, projection, null,
//					null, MediaStore.Audio.Media.TITLE_KEY);
//		}
//		return cursorLoader;
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//		adapter.swapCursor(data);
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> loader) {
//		adapter.swapCursor(null);
//	}
//	
//	public class SongListCursorAdapter extends CursorAdapter {
//		
//		private LayoutInflater inflater;
//		private int idIndex;
//		private int titleIndex;
//		private int artistIndex;
//		private int albumIndex;
//		private int fileIndex;
//		private int trackIndex;
//		
//		public SongListCursorAdapter( Context context ) {
//			super( context, null, 0);
//			inflater = LayoutInflater.from(context);
//			
//		}
//		
//		private void  getColumnIndexes( Cursor cursor ) {
//			idIndex = cursor.getColumnIndex( MediaStore.Audio.Media._ID );
//			titleIndex = cursor.getColumnIndex( MediaStore.Audio.Media.TITLE );
//			artistIndex = cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST );
//			albumIndex = cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM );
//			fileIndex = cursor.getColumnIndex( MediaStore.Audio.Media.DATA );
//			trackIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TRACK);
//		}
//		
//		@Override
//		public Cursor swapCursor( Cursor cursor ) {
//			cursor = super.swapCursor( cursor );
//			getColumnIndexes( cursor );
//			return cursor;
//		}
//
//		@Override
//		public void bindView(View view, Context context, Cursor cursor) {
//
//			TextView songTitle = (TextView) view.findViewById(R.id.song_title);
//			TextView songMeta = (TextView) view.findViewById(R.id.song_meta);
//
////			songTitle.setText(cursor.getString(mActivityIndex));
////
////			long lTime = cursor.getLong(mTimeIndex);
////			Calendar cal = Calendar.getInstance();
////			cal.setTimeInMillis(lTime);
////			time.setText(cal.get(Calendar.HOUR_OF_DAY) + “:” + String.format(“%02d”, cal.get(Calendar.MINUTE)));
////
////			String amount = cursor.getString(mAmountIndex);
////			if ( amount.length() > 0){
////			actionAndAmount.setText(cursor.getString(mActionIndex) + ” (” + amount + “)”);
////			} else {
////			actionAndAmount.setText(cursor.getString(mActionIndex));
////			}
////			
//			
//		}
//
//		@Override
//		public View newView(Context context, Cursor cursor, ViewGroup parent) {
//			return inflater.inflate(R.layout.song_list_item, null);
//		}
//	}
//}
