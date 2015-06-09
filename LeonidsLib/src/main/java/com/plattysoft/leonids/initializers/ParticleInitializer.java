package com.plattysoft.leonids.initializers;

import java.util.Random;

import com.plattysoft.leonids.Particle;

public interface ParticleInitializer {

	void initParticle(Particle p, Random r);

}
