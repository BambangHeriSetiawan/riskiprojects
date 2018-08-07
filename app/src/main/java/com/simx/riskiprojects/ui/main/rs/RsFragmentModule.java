package com.simx.riskiprojects.ui.main.rs;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:49
 */
@Module
public class RsFragmentModule {
	@Provides RsPresenter providePresenter (RSFragment fragment){
		return fragment;
	}

	@Provides RsPresenterImpl provideRsPresenterImpl(RsPresenter presenter){return new RsPresenterImpl(presenter);}
}
