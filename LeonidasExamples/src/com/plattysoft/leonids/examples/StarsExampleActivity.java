package com.plattysoft.leonids.examples;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;

public class StarsExampleActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		new ParticleSystem(this, 10, R.drawable.star, 3000)		
		.setSpeedByComponentsRange(-0.3f, 0.3f, -0.3f, 0.1f)
//		.setVelocity(0.1f, 90)
		.setInitialRotationRange(0, 360)
		.setRotationSpeed(120)
		.setFadeOut(2000)
		.addModifier(new ScaleModifier(0f, 1.5f, 0, 1500))
		.oneShot(arg0, 10);
	}
/*
 * final OneShotParticleSystem particleSystem = new OneShotParticleSystem(
				new PointParticleEmitter(mBalls.get(0).mShape.getX(), 
						mBalls.get(0).mShape.getY()), 
				10, 10, 10, mGameActivity.mStarTexture);
		particleSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		particleSystem.setParticlesSpawnEnabled(false);
		
		particleSystem.addParticleInitializer(new VelocityInitializer(-150, 150, -150, 50));
		particleSystem.addParticleInitializer(new AccelerationInitializer(0, 50));
		particleSystem.addParticleInitializer(new RotationInitializer(0.0f, 360.0f));

		particleSystem.addParticleModifier(new ScaleModifier(0f, 1.5f, 0, 1.5f));
		particleSystem.addParticleModifier(new ExpireModifier(3f));
		particleSystem.addParticleModifier(new RotationModifier(0.0f, 360f, 0, 3));		
		particleSystem.addParticleModifier(new AlphaModifier(1.0f, 0.f, 1f, 3f));
		getLayer(BORDER_LAYER).addEntity(particleSystem);
		particleSystem.doOneSpawn();	
 */
}
