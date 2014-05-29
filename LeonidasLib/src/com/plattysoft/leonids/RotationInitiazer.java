package com.plattysoft.leonids;

import java.util.Random;

public class RotationInitiazer implements ParticleInitializer {

	private int mMinAngle;
	private int mMaxAngle;

	public RotationInitiazer(int minAngle, int maxAngle) {
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
	}

	@Override
	public void initParticle(Particle p, Random r) {
		int value = r.nextInt(mMaxAngle-mMinAngle)+mMinAngle;
		p.mInitialRotation = value;
	}

}
