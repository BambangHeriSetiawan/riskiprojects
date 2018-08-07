package com.simx.riskiprojects.ui.main.maps;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:10
 */
@Module
public class MapFragmentModule {
	@Provides
	MapsPresenter providePresenter(MapsFragment mapsFragment){
		return mapsFragment;
	}

	@Provides
	MapPresenterImpl provideMapPresenterImpl(MapsPresenter presenter){
		return new MapPresenterImpl(presenter);
	}
}
