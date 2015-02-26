package com.plattysoft.leonids.initializers;

import java.util.Random;

import com.plattysoft.leonids.Particle;

public class SpeeddModuleAndRangeInitializer implements ParticleInitializer {

	private float mSpeedMin;
	private float mSpeedMax;
	private int mMinAngle;
	private int mMaxAngle;

	public SpeeddModuleAndRangeInitializer(float speedMin, float speedMax, int minAngle, int maxAngle) {
		mSpeedMin = speedMin;
		mSpeedMax = speedMax;
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
		// Make sure the angles are in the [0-360) range
		while (mMinAngle < 0) {
			mMinAngle+=360;
		}
		while (mMaxAngle < 0) {
			mMaxAngle+=360;
		}
		// Also make sure that mMinAngle is the smaller
		if (mMinAngle > mMaxAngle) {
			int tmp = mMinAngle;
			mMinAngle = mMaxAngle;
			mMaxAngle = tmp;
		}
	}

	@Override
	public void initParticle(Particle p, Random r) {
		float speed = r.nextFloat()*(mSpeedMax-mSpeedMin) + mSpeedMin;
		int angle;
		if (mMaxAngle == mMinAngle) {
			angle = mMinAngle;
		}
		else {
			angle = r.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
		}
		float angleInRads = (float) (angle*Math.PI/180f);
		p.mSpeedX = (float) (speed * Math.cos(angleInRads));
		p.mSpeedY = (float) (speed * Math.sin(angleInRads));
	}

}
