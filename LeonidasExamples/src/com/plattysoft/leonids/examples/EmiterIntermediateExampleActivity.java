package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;

public class EmiterIntermediateExampleActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {		
		ParticleSystem ps = new ParticleSystem(this, 100, R.drawable.star_pink, 1000);
		ps.setScaleRange(0.7f, 1.3f);
		ps.setSpeedModuleAndAngleRange(0.07f, 0.16f, 0, 180);
		ps.setRotationSpeedRange(90, 180);
		ps.setAcceleration(0.00013f, 90);
		ps.setFadeOut(200, new AccelerateInterpolator());
		ps.emit(arg0, 100);
	}

}
