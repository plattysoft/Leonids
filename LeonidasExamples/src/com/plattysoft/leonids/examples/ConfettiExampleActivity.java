package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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
		ParticleSystem ps = new ParticleSystem(this, 80, confetti2);
		ps.setSpeedModuleAndAngleRange(0f, 0.3f, 180, 180);
		ps.setRotationSpeed(144, 144);
		ps.setVelocity(0.00005f, 90);		
		ps.emit(findViewById(R.id.emiter_top_right), 8, 10000);
		
		Drawable confetti3 = getResources().getDrawable(R.drawable.confeti3);
		ParticleSystem ps2 = new ParticleSystem(this, 80, confetti3);
		ps2.setSpeedModuleAndAngleRange(0f, 0.3f, 0, 0);
		ps2.setRotationSpeed(144, 144);
		ps2.setVelocity(0.00005f, 90);		
		ps2.emit(findViewById(R.id.emiter_top_left), 8, 10000);
	}
}
