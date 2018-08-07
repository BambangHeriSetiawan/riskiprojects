package com.simx.riskiprojects.ui.main;

import com.simx.riskiprojects.data.model.UserModel;

/**
 * Created by simx on 14/02/18.
 */

public interface MainView {
    void initProfile(UserModel userModel);

    void gotoLogin();
}
