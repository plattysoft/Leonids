package com.plattysoft.leonids.emitters;

import android.view.View;

/**
 * Emit particles within the area occupied by a particular {@link View}.
 */
public class ViewEmitter implements Emitter {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ViewEmitter(final View view) {
        // It works with an emission range
        int[] location = new int[2];
        view.getLocationInWindow(location);

        x = location[0];
        y = location[1];
        width = view.getWidth();
        height = view.getHeight();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
