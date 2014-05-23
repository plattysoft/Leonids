package com.plattysoft.leonids;

import java.util.ArrayList;
import java.util.Random;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class ParticleSystem implements AnimatorUpdateListener, AnimatorListener {

	private ViewGroup mParentView;
	private int mMaxParticles;
	
	private float mSpeedMin;
	private float mSpeedMax;
	
	private int mMinAngle = 0;
	private int mMaxAngle = 360;
	private float mMinScale = 1.0f;
	private float mMaxScale = 1.0f;

	private ParticleField mDrawingView;

	private long mMilisecondsBeforeEnd = 0;
	private Interpolator mFadeOutInterpolator;

	private float mMinRotation = 0;
	private float mMaxRotation = 0;

	private float mVelocity = 0;
	private float mVelocityAngle = 0;

	private ArrayList<Particle> mParticles;
	private ArrayList<Particle> mActiveParticles;

	public ParticleSystem(Activity a, int maxParticles, Bitmap bitmap) {
		mParentView = (ViewGroup) a.findViewById(android.R.id.content);
		mMaxParticles = maxParticles;
		// Create the particles
		mActiveParticles = new ArrayList<Particle>(); 
		mParticles = new ArrayList<Particle> ();
		for (int i=0; i<mMaxParticles; i++) {
			mParticles.add (new Particle (bitmap));
		}
	}

	public void setSpeedRange(float speedMin, float speedMax) {
		mSpeedMin = speedMin;
		mSpeedMax = speedMax;
	}
	
	public void setAngleRange(int minAngle, int maxAngle) {
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
	}
	
	public void setScaleRange(float minScale, float maxScale) {
		mMinScale = minScale;
		mMaxScale = maxScale;
	}
	
	public void setRotationSpeed(float minRotationSpeed, float maxRotationSpeed) {
		mMinRotation = minRotationSpeed;
		mMaxRotation = maxRotationSpeed;
	}
	
	public void setVelocity(float velocity, float angle) {
		mVelocity = velocity;
		mVelocityAngle = angle;
	}
	
	public void setFadeOut(long milisecondsBeforeEnd, Interpolator interpolator) {
		mMilisecondsBeforeEnd = milisecondsBeforeEnd;
		mFadeOutInterpolator = interpolator;
	}
	
	public void setFadeOut(long milisecondsBeforeEnd) {
		setFadeOut(milisecondsBeforeEnd, new LinearInterpolator());
	}
	
	public void oneShot(View emiter, int numParticles, int timeToLive) {
		oneShot(emiter, numParticles, timeToLive, new LinearInterpolator());
	}
	
	public void oneShot(View emiter, int numParticles, int timeToLive, Interpolator interpolator) {
		int[] location = new int[2];
		int[] parentLocation = new int[2];
		emiter.getLocationInWindow(location);
		mParentView.getLocationInWindow(parentLocation);
		float emiterX = location[0] + emiter.getWidth()/2 - parentLocation[0];
		float emiterY = location[1] + emiter.getHeight()/2 - parentLocation[1];

		Random r = new Random();
		// We create particles based in the parameters
		for (int i=0; i<numParticles && i<mMaxParticles; i++) {
			float speed = r.nextFloat()*(mSpeedMax-mSpeedMin) + mSpeedMin;
			int angle = r.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
			float scale = r.nextFloat()*(mMaxScale-mMinScale) + mMinScale;
			float rotationSpeed = r.nextFloat()*(mMaxRotation-mMinRotation) + mMinRotation;			
			Particle p = mParticles.remove(i);
			p.configure(timeToLive, emiterX, emiterY, speed, angle, scale, rotationSpeed, mVelocity, mVelocityAngle, mMilisecondsBeforeEnd, mFadeOutInterpolator);
			mActiveParticles.add(p);
		}
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		mDrawingView.setParticles (mActiveParticles);
		// We start a property animator that will call us to do the update
		// Animate from 0 to timeToLiveMax
		ValueAnimator animator = ValueAnimator.ofInt(new int[] {0, timeToLive});
		animator.setDuration(timeToLive);
		animator.addUpdateListener(this);
		animator.addListener(this);
		animator.setInterpolator(interpolator);
		animator.start();
	}

	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		for (int i=0; i<mActiveParticles.size(); i++) {
			mActiveParticles.get(i).update((Integer)arg0.getAnimatedValue());
		}
		mDrawingView.postInvalidate();
	}

	@Override
	public void onAnimationCancel(Animator arg0) {
		mParentView.removeView(mDrawingView);
		mDrawingView = null;
		mParentView.postInvalidate();
	}

	@Override
	public void onAnimationEnd(Animator arg0) {
		// Remove the view from the parent
		mParentView.removeView(mDrawingView);
		mDrawingView = null;
		mParentView.postInvalidate();
		mParticles.addAll(mActiveParticles);
	}

	@Override
	public void onAnimationRepeat(Animator arg0) {
	}

	@Override
	public void onAnimationStart(Animator arg0) {
	}
}
