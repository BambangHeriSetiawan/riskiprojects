package com.simx.riskiprojects.ui.main.klinik;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:52
 */
@Module
public class KlinikFragmentModule {
	@Provides KlinikPresenter presenter(KlinikFragment fragment){
		return fragment;
	}
	@Provides KlinikPresenterImpl klinikPresenter(KlinikPresenter presenter){
		return new  KlinikPresenterImpl(presenter);
	}
}
