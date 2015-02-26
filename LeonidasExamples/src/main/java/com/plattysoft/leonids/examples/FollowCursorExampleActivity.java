package com.plattysoft.leonids.examples;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import com.plattysoft.leonids.ParticleSystem;

public class FollowCursorExampleActivity extends Activity  {

	private ParticleSystem ps;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Create a particle system and start emiting
			ps = new ParticleSystem(this, 100, R.drawable.star_pink, 800);
			ps.setScaleRange(0.7f, 1.3f);
			ps.setSpeedRange(0.05f, 0.1f);
			ps.setRotationSpeedRange(90, 180);
			ps.setFadeOut(200, new AccelerateInterpolator());
			ps.emit((int) event.getX(), (int) event.getY(), 40);
			break;
		case MotionEvent.ACTION_MOVE:
			ps.updateEmitPoint((int) event.getX(), (int) event.getY());
			break;
		case MotionEvent.ACTION_UP:
			ps.stopEmitting();
			break;
		}
		return true;
	}

	
	
}