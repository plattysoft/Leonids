package com.plattysoft.leonids.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.plattysoft.leonids.Particle;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ParticleModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

public class DynamicEmiterActivity extends Activity implements OnClickListener {
	private ParticleSystem mParticleSystem;
	private boolean mStarted;
	private int mEmitCount = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
		List<Particle> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_1_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_2_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_3_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_4_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_5_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_6_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_7_normal)).getBitmap()));
			list.add(new Particle(((BitmapDrawable)getResources().getDrawable(R.drawable.icon_like_8_normal)).getBitmap()));
		}

		mParticleSystem = new ParticleSystem(this, list, 3000L,android.R.id.content)
				.setSpeedModuleAndAngleRange(0.05f, 0.1f, 260, 280)
				.setAcceleration(0.000017f, 270)
				.addModifier(new ScaleModifier(0.3f, 1f, 0, 1500))
				.addModifier(new AlphaModifier(100,200,0, 2000))
				.addModifier(new TrackModifier())
				.setFadeOut(1000, new AccelerateDecelerateInterpolator());
	}

	@Override
	public void onClick(View arg0) {
		if (!mStarted) {
			mStarted = true;
			mParticleSystem.emit(arg0, mEmitCount);
		} else {
			mParticleSystem.updateParticlesPerSecond(mEmitCount + 1,50);
		}
	}

	class TrackModifier implements ParticleModifier {
		HashMap<Particle,Float> mSignMap = new HashMap<>();

		public TrackModifier() {
		}

		public void apply(Particle particle, long miliseconds) {
			if (mSignMap.get(particle) == null) {
				final Random random = new Random();
				mSignMap.put(particle,random.nextInt(10) / 10f * (random.nextBoolean()? 1: -1));
			}
			double offset = Math.sin(particle.mCurrentY/100f) * mSignMap.get(particle);
			particle.mCurrentX += offset * 50 + particle.mCurrentY/1000f;
		}
	}
}
