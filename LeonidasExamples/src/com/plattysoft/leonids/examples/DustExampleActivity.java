package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class DustExampleActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dust_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		ParticleSystem ps = new ParticleSystem(this, 4, R.drawable.dust);
		ps.setSpeedModuleAndAngleRange(0.2f, 0.3f, -70, -110);
		ps.setRotationSpeed(144, 144);
		ps.setVelocity(0.00003f, 330);
		ps.setInitialRotationRange(0, 360);
		ps.addModifier(new AlphaModifier(255, 0, 1000, 3000));
		ps.addModifier(new ScaleModifier(0.5f, 2f, 0, 1000));
		ps.oneShot(findViewById(R.id.emiter_bottom), 4, 3000);			
	}
}
