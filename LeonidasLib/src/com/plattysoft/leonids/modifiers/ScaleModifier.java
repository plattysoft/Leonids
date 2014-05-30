package com.plattysoft.leonids.modifiers;

import com.plattysoft.leonids.Particle;

public class ScaleModifier implements ParticleModifier {

	private float mInitialValue;
	private float mFinalValue;
	private long mEndTime;
	private long mStartTime;
	private float mValueIncrement;

	public ScaleModifier (float initialValue, float finalValue, long startMilis, long endMilis) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mStartTime = startMilis;
		mEndTime = endMilis;
		mValueIncrement = (mFinalValue-mInitialValue)/(endMilis - startMilis);
	}
	
	@Override
	public void apply(Particle particle, long miliseconds) {
		if (miliseconds > mStartTime && miliseconds < mEndTime) {			
			float newScale = mInitialValue + mValueIncrement*(miliseconds-mStartTime);
			particle.mScale = newScale;
		}
	}

}
