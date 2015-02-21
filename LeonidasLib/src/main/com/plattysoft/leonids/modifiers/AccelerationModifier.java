package com.plattysoft.leonids.modifiers;

import com.plattysoft.leonids.Particle;

public class AccelerationModifier implements ParticleModifier {

	private float mVelocityX;
	private float mVelocityY;

	public AccelerationModifier(float velocity, float angle) {
		float velocityAngleInRads = (float) (angle*Math.PI/180f);
		mVelocityX = (float) (velocity * Math.cos(velocityAngleInRads));
		mVelocityY = (float) (velocity * Math.sin(velocityAngleInRads));
	}

	@Override
	public void apply(Particle particle, long miliseconds) {
		particle.mCurrentX += mVelocityX*miliseconds*miliseconds;
		particle.mCurrentY += mVelocityY*miliseconds*miliseconds;
	}

}
