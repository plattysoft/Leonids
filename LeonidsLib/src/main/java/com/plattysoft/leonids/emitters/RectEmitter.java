package com.plattysoft.leonids.emitters;

/**
 * Specifies an arbitrary rectangular area where particles can be emitted from.
 * This will be relative to whatever parent view the emitter is attached to. So for example, if
 * you specify an "x" of 10px for this rectangle, and the parent view is offset 100px from the
 * edge of the screen, then the total offset will be 110px from the edge of the screen.
 */
public class RectEmitter implements Emitter {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public RectEmitter(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
