package com.simx.riskiprojects.ui.main.pks;

import dagger.Module;
import dagger.Provides;

/**
 * User: simx Date: 07/08/18 16:51
 */
@Module
public class PuskesmasFragmentModule {
	@Provides PuskesmasPresenter presenter(PuskesmasFragment fragment){ return fragment;}

	@Provides PuskesmasPresenterImpl puskesmasPresenter(PuskesmasPresenter puskesmasPresenter){
		return new PuskesmasPresenterImpl(puskesmasPresenter);
	}
}
