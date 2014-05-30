package com.plattysoft.leonids.examples;

import com.plattysoft.leonids.ParticleSystem;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;

public class EmiterSimpleExampleActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_particle_system_example);
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Drawable d = getResources().getDrawable(R.drawable.star_pink);
		ParticleSystem ps = new ParticleSystem(this, 50, d);
		ps.setSpeedRange(0.2f, 0.5f);
		ps.emit(arg0, 100, 1000);		
	}

}
