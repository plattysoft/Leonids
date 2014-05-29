package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.AlphaModifier;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.ScaleModifier;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
			Drawable confetti2 = getResources().getDrawable(R.drawable.dust);
			ParticleSystem ps = new ParticleSystem(this, 4, ((BitmapDrawable) confetti2).getBitmap());
			ps.setSpeedRange(0.2f, 0.3f);
			ps.setSpeedAngleRange(-70, -110);
			ps.setRotationSpeed(144, 144);
			ps.setVelocity(0.00003f, 330);
			ps.setInitialRotationRange(0, 360);
			ps.addModifier(new AlphaModifier(255, 0, 1000, 3000));
			ps.addModifier(new ScaleModifier(0.5f, 2f, 0, 1000));
			ps.oneShot(findViewById(R.id.emiter_bottom), 4, 3000);			
		}

//	private void launchDustPartileSystem() {
//		particleSystem.addParticleInitializer(new VelocityInitializer(-35, 35, -90, -120));
//		particleSystem.addParticleInitializer(new AccelerationInitializer(10, 20));
//		particleSystem.addParticleInitializer(new RotationInitializer(0.0f, 360.0f));
//
//		particleSystem.addParticleModifier(new ScaleModifier(0.5f, 2.0f, 0, 1));
//		particleSystem.addParticleModifier(new ExpireModifier(3f));
//		particleSystem.addParticleModifier(new AlphaModifier(1.0f, 0.f, 1f, 3f));
//		getLayer(BORDER_LAYER).addEntity(particleSystem);
//		particleSystem.doOneSpawn();
//	}
}
