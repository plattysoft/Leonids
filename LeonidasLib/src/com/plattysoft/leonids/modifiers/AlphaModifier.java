package com.plattysoft.leonids.modifiers;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.plattysoft.leonids.Particle;

public class AlphaModifier implements ParticleModifier {

	private int mInitialValue;
	private int mFinalValue;
	private long mStartTime;
	private long mEndTime;
	private Interpolator mInterpolator;
	private long mDuration;

	public AlphaModifier(int initialValue, int finalValue, long startMilis, long endMilis, Interpolator interpolator) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mStartTime = startMilis;		
		mEndTime = endMilis;
		mDuration = mEndTime - mStartTime;
		mInterpolator = interpolator;
	}
	
	public AlphaModifier (int initialValue, int finalValue, long startMilis, long endMilis) {
		this(initialValue, finalValue, startMilis, endMilis, new LinearInterpolator());
	}

	@Override
	public void apply(Particle particle, long miliseconds) {
		if (miliseconds > mStartTime && miliseconds < mEndTime) {			
			float interpolaterdValue = mInterpolator.getInterpolation((mEndTime - miliseconds)*1f/mDuration);
			int newAlphaValue = (int) (mInitialValue + mFinalValue*interpolaterdValue);
			particle.mAlpha = newAlphaValue;
		}
	}

}
