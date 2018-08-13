package com.simx.riskiprojects.ui.Tracker;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 09/08/18 12:05
 */
@Module
public class TrackerMapModule {
	@Provides TrackerMapPresenter povidePresenter(TrackerMapsActivity trackerMapsActivity){
		return trackerMapsActivity;
	}
	@Provides TrackerMapPresenterImpl provideTrackerMapPresenterImpl(TrackerMapPresenter presenter){
		return new TrackerMapPresenterImpl(presenter);
	}
}
