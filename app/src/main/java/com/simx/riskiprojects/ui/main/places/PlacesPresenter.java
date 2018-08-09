package com.simx.riskiprojects.ui.main.places;

import com.simx.riskiprojects.data.model.ResponseSample;
import java.util.List; /**
 * User: simx Date: 07/08/18 16:48
 */
public interface PlacesPresenter {

	void initData(List<ResponseSample> results);

	void showError(String message);

	void showLoading(boolean isShow);

	void showDetail(ResponseSample responseSample);
}
