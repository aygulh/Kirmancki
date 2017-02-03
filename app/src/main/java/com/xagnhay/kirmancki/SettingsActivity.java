package com.xagnhay.kirmancki;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class SettingsActivity extends PreferenceActivity {
	public static final String LOGTAG = SettingsActivity.class.getSimpleName();

	private Tracker mTracker;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        KirmanckiApplication application = (KirmanckiApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        mTracker.setScreenName(SettingsActivity.class.getSimpleName());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
	}
}
