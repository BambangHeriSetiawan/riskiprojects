package com.simx.riskiprojects.ui.main.home;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:10
 */
@Module
public class HomeFragmentModule {
	@Provides
	HomePresenter providePresenter(HomeFragment homeFragment){
		return homeFragment;
	}

	@Provides
	HomePresenterImpl provideMapPresenterImpl(HomePresenter presenter){
		return new HomePresenterImpl(presenter);
	}
}
