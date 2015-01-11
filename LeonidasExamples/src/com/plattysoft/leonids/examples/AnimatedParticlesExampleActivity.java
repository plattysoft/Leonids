package com.plattysoft.leonids.examples;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

public class AnimatedParticlesExampleActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		new ParticleSystem(this, 100, R.drawable.animated_confetti, 5000)		
		.setSpeedRange(0.1f, 0.25f)
		.setRotationSpeedRange(90, 180)
		.setInitialRotationRange(0, 360)
		.oneShot(arg0, 100);

        Context context = getApplicationContext();
        String toastText = "Particle system example using 100 particles over 5000 milliseconds";

        Toast informUserToast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        informUserToast.show();
	}
}
