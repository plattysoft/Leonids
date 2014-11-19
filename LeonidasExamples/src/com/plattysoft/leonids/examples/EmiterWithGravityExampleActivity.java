package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;

public class EmiterWithGravityExampleActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		new ParticleSystem(this, 50, R.drawable.star_pink, 1000)
		.setSpeedRange(0.1f, 0.25f)
		.setRotationSpeedRange(90, 180)
		.setAcceleration(0.00013f, 90)
		.setFadeOut(200, new AccelerateInterpolator())
		.emitWithGravity(arg0, Gravity.BOTTOM, 100);
	}
}
