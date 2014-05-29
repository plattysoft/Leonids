package com.plattysoft.leonids;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 * @param mTimeToLive
	 */
	void apply(Particle particle, long miliseconds, long timeToLive);

}
