package com.simx.riskiprojects.ui.main;

import com.simx.riskiprojects.ui.main.klinik.KlinikFragment;
import com.simx.riskiprojects.ui.main.klinik.KlinikFragmentModule;
import com.simx.riskiprojects.ui.main.home.HomeFragmentModule;
import com.simx.riskiprojects.ui.main.home.HomeFragment;
import com.simx.riskiprojects.ui.main.pks.PuskesmasFragment;
import com.simx.riskiprojects.ui.main.pks.PuskesmasFragmentModule;
import com.simx.riskiprojects.ui.main.rs.RSFragment;
import com.simx.riskiprojects.ui.main.rs.RsFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * User: simx Date: 07/08/18 16:18
 */
@Module
public abstract class MainActivityProvideModule {
	@ContributesAndroidInjector(modules = HomeFragmentModule.class) abstract HomeFragment mapsFragment();
	@ContributesAndroidInjector(modules = RsFragmentModule.class) abstract RSFragment rsFragment();
	@ContributesAndroidInjector(modules = KlinikFragmentModule.class) abstract KlinikFragment klinikFragment();
	@ContributesAndroidInjector(modules = PuskesmasFragmentModule.class) abstract PuskesmasFragment puskesmasFragment();
}
