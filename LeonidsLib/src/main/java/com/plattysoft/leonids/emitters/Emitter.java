package com.plattysoft.leonids.emitters;

/**
 * Specifies an area with which particles should be emitted from.
 */
public interface Emitter {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}
