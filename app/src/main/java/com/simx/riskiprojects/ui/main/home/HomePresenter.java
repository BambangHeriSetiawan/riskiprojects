package com.simx.riskiprojects.ui.main.home;

import com.simx.riskiprojects.data.model.ResultsItem;
import java.util.List; /**
 * User: simx Date: 07/08/18 16:09
 */
public interface HomePresenter {

	void initMarkerToMap(List<ResultsItem> results);

	void showError(String error_message);

	void showLoading(boolean isshow);
}
