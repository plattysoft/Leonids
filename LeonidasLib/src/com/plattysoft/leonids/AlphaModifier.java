package com.plattysoft.leonids;

public class AlphaModifier implements ParticleModifier {

	private int mInitialValue;
	private int mFinalValue;
	private long mStartTime;
	private long mEndTime;
	private float mValueIncrement;

	public AlphaModifier (int initialValue, int finalValue, long startMilis, long endMilis) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mStartTime = startMilis;
		mEndTime = endMilis;
		mValueIncrement = ((float)(mFinalValue-mInitialValue))/(endMilis - startMilis);
	}
	
	@Override
	public void apply(Particle particle, long miliseconds) {
		if (miliseconds > mStartTime && miliseconds < mEndTime) {			
			int newAlphaValue = (int) (mInitialValue + mValueIncrement*(miliseconds-mStartTime));
			particle.mAlpha = newAlphaValue;
		}
	}

}
