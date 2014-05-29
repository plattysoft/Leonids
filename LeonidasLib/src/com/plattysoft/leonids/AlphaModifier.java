package com.plattysoft.leonids;

public class AlphaModifier implements ParticleModifier {

	private int mInitialValue;
	private int mFinalValue;
	private long mStartTime;
	private long mEndTime;
	private long mDuration;

	public AlphaModifier (int initialValue, int finalValue, long startMilis, long endMilis) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mStartTime = startMilis;
		mEndTime = endMilis;
		mDuration = endMilis - startMilis;
	}
	
	@Override
	public void apply(Particle particle, long miliseconds, long timeToLive) {
		if (miliseconds > mStartTime && miliseconds < mEndTime) {			
			int newAlphaValue = (int) (mInitialValue + mFinalValue*(miliseconds-mStartTime)/mDuration);
			particle.mAlpha = newAlphaValue;
		}
	}

}
