package com.unochapeco.androidii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivityRunnable extends Activity implements Runnable {

	private final int DELAY = 5000;

	@Override
	public void onCreate(Bundle icicle) {

		super.onCreate(icicle);

		setContentView(R.layout.splash);

		Handler handler = new Handler();
		handler.postDelayed(this, DELAY);
	}

	public void run() {

		Intent intent = new Intent(getApplicationContext(), ListaFotosActivity.class);
		startActivity(intent);

		finish();

	}

}
