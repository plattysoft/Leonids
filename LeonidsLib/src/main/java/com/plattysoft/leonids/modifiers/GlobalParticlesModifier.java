package com.plattysoft.leonids.modifiers;

import com.plattysoft.leonids.Particle;

import java.util.List;

public interface GlobalParticlesModifier {
    /**
     * modifies the specific value of a particle given the current miliseconds
     * @param particles
     * @param milliseconds
     */
    void apply(List<Particle> particles, long milliseconds);

}