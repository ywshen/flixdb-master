package com.streammovietv.utilities;

import android.support.design.widget.AppBarLayout;

public abstract class AppBarStateChangedListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public abstract void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset);
}
