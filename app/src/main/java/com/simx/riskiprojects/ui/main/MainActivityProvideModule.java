package com.simx.riskiprojects.ui.main;

import com.simx.riskiprojects.ui.main.home.HomeFragmentModule;
import com.simx.riskiprojects.ui.main.home.HomeFragment;
import com.simx.riskiprojects.ui.main.places.PlacesFragment;
import com.simx.riskiprojects.ui.main.places.PlacesFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * User: simx Date: 07/08/18 16:18
 */
@Module
public abstract class MainActivityProvideModule {
	@ContributesAndroidInjector(modules = HomeFragmentModule.class) abstract HomeFragment mapsFragment();
	@ContributesAndroidInjector(modules = PlacesFragmentModule.class) abstract PlacesFragment rsFragment();

}
