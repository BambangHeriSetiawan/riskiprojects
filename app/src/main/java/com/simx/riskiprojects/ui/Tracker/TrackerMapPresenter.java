package com.simx.riskiprojects.ui.Tracker;

import com.simx.riskiprojects.data.model.waypoint.ResponseWaypoint; /**
 * User: simx Date: 09/08/18 12:04
 */
public interface TrackerMapPresenter {

	void initMapLineTracker(ResponseWaypoint responseWaypoint);

	void showError(String message);

	void showLoading(boolean isLoading);
}
