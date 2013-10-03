package me.noslo.titanmobile.ui;

import me.noslo.titanmobile.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class StoreActivity extends TitanPlayerActivity {

	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);

		webView = (WebView) findViewById(R.id.store_webview);
		webView.loadUrl(getString(R.string.store_url));

	}

}