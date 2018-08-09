package com.simx.riskiprojects.ui.main.places;

import com.simx.riskiprojects.data.model.ResultsItem;
import java.util.List; /**
 * User: simx Date: 07/08/18 16:48
 */
public interface PlacesPresenter {

	void initData(List<ResultsItem> results);

	void showError(String message);

	void showLoading(boolean isShow);

	void showDetail(String place_id);
}
