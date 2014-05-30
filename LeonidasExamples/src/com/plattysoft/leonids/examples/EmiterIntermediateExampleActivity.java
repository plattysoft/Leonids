package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
		Drawable d = getResources().getDrawable(R.drawable.star_pink);
		ParticleSystem ps = new ParticleSystem(this, 100, ((BitmapDrawable) d).getBitmap());
		ps.setScaleRange(0.7f, 1.3f);
		ps.setSpeedModuleAndAngleRange(0.2f, 0.5f, 0, 180);
		ps.setRotationSpeed(90, 180);
		ps.setVelocity(0.0004f, 90);
		ps.setFadeOut(200, new AccelerateInterpolator());
		ps.emit(arg0, 100, 1000);		
	}

}
