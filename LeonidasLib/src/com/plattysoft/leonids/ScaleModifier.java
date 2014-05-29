package com.plattysoft.leonids;

public class ScaleModifier implements ParticleModifier {

	private float mInitialValue;
	private float mFinalValue;
	private long mEndTime;
	private long mStartTime;
	private long mDuration;

	public ScaleModifier (float initialValue, float finalValue, long startMilis, long endMilis) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mStartTime = startMilis;
		mEndTime = endMilis;
		mDuration = endMilis - startMilis;
	}
	
	@Override
	public void apply(Particle particle, long miliseconds, long timeToLive) {
		if (miliseconds > mStartTime && miliseconds < mEndTime) {			
			float newScale = mInitialValue + mFinalValue*(miliseconds-mStartTime)/mDuration;
			particle.mScale = newScale;
		}
	}

}
