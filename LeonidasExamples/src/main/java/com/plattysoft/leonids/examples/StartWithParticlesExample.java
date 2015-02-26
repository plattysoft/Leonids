package com.plattysoft.leonids.examples;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.plattysoft.leonids.ParticleSystem;

/**
 * Sometimes you might want to start with a set a particles already on screen.
 *
 * This also shows how you can manipulate the view the particles are drawn on.  This one shows
 * a quick scale effect to simulate them exploding into view then falling down with the rest.
 *
 */
public class StartWithParticlesExample extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_with_particles_example);

        final View emitter = findViewById(R.id.emitter);

        emitter.postDelayed(new Runnable() {
            @Override
            public void run() {

                //important to call initialParticles after configuring acceleration/speed/etc
                new ParticleSystem(StartWithParticlesExample.this, 80, R.drawable.confeti2, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.1f, 180, 180)
                        .setRotationSpeed(144)
                        .setAcceleration(0.00017f, 90)
                        .setParentViewGroup((ViewGroup) findViewById(R.id.particle_view))
                        .initialParticles(40)
                        .emitWithGravity(findViewById(R.id.emitter), Gravity.BOTTOM, 8, 3000);

                new ParticleSystem(StartWithParticlesExample.this, 80, R.drawable.confeti3, 10000)
                        .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                        .setRotationSpeed(144)
                        .setParentViewGroup((ViewGroup) findViewById(R.id.particle_view))
                        .setAcceleration(0.00017f, 90)
                        .initialParticles(40)
                        .emitWithGravity(findViewById(R.id.emitter), Gravity.BOTTOM, 8, 3000);

                ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(
                        findViewById(R.id.particle_view),
                        PropertyValuesHolder.ofFloat("scaleX", .8f, 1.2f, 1),
                        PropertyValuesHolder.ofFloat("scaleY", .8f, 1.2f, 1));
                scale.setDuration(500);
                scale.start();

            }
        }, 500);



    }
}
