package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;

public class ConfettiExampleActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confetti_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Drawable confetti2 = getResources().getDrawable(R.drawable.confeti2);
		ParticleSystem ps = new ParticleSystem(this, 80, ((BitmapDrawable) confetti2).getBitmap());
		ps.setAngleRange(180, 180);
		ps.setSpeedRange(0f, 0.3f);
		ps.setRotationSpeed(144, 144);
		ps.setVelocity(0.00005f, 90);		
		ps.emit(findViewById(R.id.emiter_top_right), 8, 10000);
		
		Drawable confetti3 = getResources().getDrawable(R.drawable.confeti3);
		ParticleSystem ps2 = new ParticleSystem(this, 80, ((BitmapDrawable) confetti3).getBitmap());
		ps2.setAngleRange(0, 0);
		ps2.setSpeedRange(0f, 0.3f);
		ps2.setRotationSpeed(144, 144);
		ps2.setVelocity(0.00005f, 90);		
		ps2.emit(findViewById(R.id.emiter_top_left), 8, 10000);
	}
	
//	private void launchConfettiParticleSystem() {
//		final ParticleSystem particleSystem = new ParticleSystem(
//				new PointParticleEmitter(0, -50), 
//				8, 12, 80, mGameActivity.mConfettiTexture);
//		particleSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		
//		particleSystem.addParticleInitializer(new VelocityInitializer(0, 120, 0, 50));
//		particleSystem.addParticleInitializer(new AccelerationInitializer(0, 15));
//		particleSystem.addParticleInitializer(new RotationInitializer(0.0f, 360.0f));
//		
//		particleSystem.addParticleModifier(new ExpireModifier (10f));
//		particleSystem.addParticleModifier(new RotationModifier(0.0f, 1440f, 0, 10));		
//		
//		final ParticleSystem particleSystem2 = new ParticleSystem(
//				new PointParticleEmitter(mCameraWidth,-50), 
//				8, 12, 80, mGameActivity.mConfettiTexture2);
//		particleSystem2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//		
//		particleSystem2.addParticleInitializer(new VelocityInitializer(-120, 0, 0, 50));
//		particleSystem2.addParticleInitializer(new AccelerationInitializer(0, 15));
//		particleSystem2.addParticleInitializer(new RotationInitializer(0.0f, 360.0f));
//		
//		particleSystem2.addParticleModifier(new ExpireModifier(10f));
//		particleSystem2.addParticleModifier(new RotationModifier(0.0f, 1440f, 0, 10));		
//		
//		getLayer(BORDER_LAYER).addEntity(particleSystem);
//		getLayer(BORDER_LAYER).addEntity(particleSystem2);
//
//	}
}
