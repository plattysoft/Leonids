package com.plattysoft.leonids.modifiers;

import android.view.animation.Interpolator;
import com.plattysoft.leonids.Particle;

import java.util.List;

public class GlobalAlphaModifier implements GlobalParticlesModifier {

    final float fromAlpha;
    final float toAlpha;
    final long duration;  //in ms
    final long startTime; //in ms
    final Interpolator interpolator;

    final long endTime;

    public GlobalAlphaModifier(float fromAlpha, float toAlpha, long duration, long startTime, Interpolator interpolator) {
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        this.duration = duration;
        this.startTime = startTime;
        this.interpolator = interpolator;

        endTime = startTime + duration;
    }

    @Override
    public void apply(List<Particle> particles, long milliseconds) {
        if (milliseconds < startTime || milliseconds > endTime) return;
        float currentAlpha = fromAlpha + (toAlpha - fromAlpha) * interpolator.getInterpolation((milliseconds - startTime) / (float)duration);

        for(int i=0, size=particles.size(); i<size; i++) {
            particles.get(i).mAlpha = (int)currentAlpha;
        }
    }
}
