package com.simx.riskiprojects.ui.main.places;

import android.content.Context;
import com.simx.riskiprojects.ui.main.AdapterPlace;
import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:49
 */
@Module
public class PlacesFragmentModule {
	@Provides
	PlacesPresenter providePresenter (PlacesFragment fragment){
		return fragment;
	}

	@Provides
	PlacesPresenterImpl provideRsPresenterImpl(PlacesPresenter presenter){return new PlacesPresenterImpl(presenter);}

	@Provides
	AdapterPlace provideAdapterPlace(Context context, PlacesPresenter presenter){
		return new AdapterPlace(context,presenter);
	}
}
