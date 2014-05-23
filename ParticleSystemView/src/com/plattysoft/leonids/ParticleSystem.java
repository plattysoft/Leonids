package com.plattysoft.leonids;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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

public class ParticleSystem {

	private static final long TIMMERTASK_INTERVAL = 50;
	private ViewGroup mParentView;
	private int mMaxParticles;
	private Random mRandom;
	
	private float mSpeedMin;
	private float mSpeedMax;
	
	private int mMinAngle = 0;
	private int mMaxAngle = 360;
	private float mMinScale = 1.0f;
	private float mMaxScale = 1.0f;

	private ParticleField mDrawingView;

	private long mMilisecondsBeforeEnd = 0;
	private Interpolator mFadeOutInterpolator = new LinearInterpolator();

	private float mMinRotation = 0;
	private float mMaxRotation = 0;

	private float mVelocity = 0;
	private float mVelocityAngle = 0;

	private ArrayList<Particle> mParticles;
	private ArrayList<Particle> mActiveParticles;
	private int mEmiterX;
	private int mEmiterY;
	private int mTimeToLive;
	private int mCurrentTime;
	
	private int mDelayBetweenParticles;

	public ParticleSystem(Activity a, int maxParticles, Bitmap bitmap) {
		mRandom = new Random();
		mParentView = (ViewGroup) a.findViewById(android.R.id.content);
		mMaxParticles = maxParticles;
		// Create the particles
		mActiveParticles = new ArrayList<Particle>(); 
		mParticles = new ArrayList<Particle> ();
		for (int i=0; i<mMaxParticles; i++) {
			mParticles.add (new Particle (bitmap));
		}
	}

	public void setSpeed(float speed) {
		setSpeedRange(speed, speed);
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
	
	/**
	 * Configures a fade out for the particles when they disappear
	 * 
	 * @param duration fade out duration in miliseconds
	 * @param interpolator the interpolator for the fade out (default is linear)
	 */
	public void setFadeOut(long milisecondsBeforeEnd, Interpolator interpolator) {
		mMilisecondsBeforeEnd = milisecondsBeforeEnd;
		mFadeOutInterpolator = interpolator;
	}
	
	/**
	 * Configures a fade out for the particles when they disappear
	 * 
	 * @param duration fade out duration in miliseconds
	 */
	public void setFadeOut(long duration) {
		setFadeOut(duration, new LinearInterpolator());
	}
	
	/**
	 * Starts emiting particles from a specific view. If at some point the number goes over the amount of particles availabe on create
	 * no new particles will be created
	 * 
	 * @param emiter  View from which center the particles will be emited
	 * @param particlesPerSecond Number of particles per second that will be emited (evenly distributed)
	 * @param timeToLive miliseconds the particles will be displayed
	 */
	public void emit (View emiter, int particlesPerSecond, int timeToLive) {
		// Setup emiter
		configureEmiter(emiter);
		mTimeToLive = timeToLive;
		mDelayBetweenParticles = particlesPerSecond/1000;
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		
		mDrawingView.setParticles (mActiveParticles);
		mCurrentTime = 0;
		Timer t = new Timer();
		t.schedule(new TimerTask() {			
			@Override
			public void run() {
				onUpdate(mCurrentTime);
				mCurrentTime += TIMMERTASK_INTERVAL;
			}
		}, 0, TIMMERTASK_INTERVAL);
	}
	
	/**
	 * Launches particles in one Shot
	 * 
	 * @param emiter View from which center the particles will be emited
	 * @param numParticles number of particles launched on the one shot
	 * @param timeToLive miliseconds the particles will be displayed
	 */
	public void oneShot(View emiter, int numParticles, int timeToLive) {
		oneShot(emiter, numParticles, timeToLive, new LinearInterpolator());
	}
	
	/**
     * Launches particles in one Shot using a special Interpolator
	 * 
	 * @param emiter View from which center the particles will be emited
	 * @param numParticles number of particles launched on the one shot
	 * @param timeToLive miliseconds the particles will be displayed
	 * @param interpolator the interpolator for the time
	 */
	public void oneShot(View emiter, int numParticles, int timeToLive, Interpolator interpolator) {
		configureEmiter(emiter);
		
		mTimeToLive = timeToLive;
		// We create particles based in the parameters
		for (int i=0; i<numParticles && i<mMaxParticles; i++) {
			activateParticle(0);
		}
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		mDrawingView.setParticles (mActiveParticles);
		// We start a property animator that will call us to do the update
		// Animate from 0 to timeToLiveMax
		startAnimator(interpolator);
	}

	private void startAnimator(Interpolator interpolator) {
		ValueAnimator animator = ValueAnimator.ofInt(new int[] {0, mTimeToLive});
		animator.setDuration(mTimeToLive);
		animator.addUpdateListener(new AnimatorUpdateListener() {			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int miliseconds = (Integer) animation.getAnimatedValue();
				onUpdate(miliseconds);
			}
		});
		animator.addListener(new AnimatorListener() {			
			@Override
			public void onAnimationStart(Animator animation) {}
			
			@Override
			public void onAnimationRepeat(Animator animation) {}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				cleanupAnimation();
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				cleanupAnimation();				
			}
		});
		animator.setInterpolator(interpolator);
		animator.start();
	}

	private void configureEmiter(View emiter) {
		int[] location = new int[2];
		int[] parentLocation = new int[2];
		emiter.getLocationInWindow(location);
		mParentView.getLocationInWindow(parentLocation);
		mEmiterX = location[0] + emiter.getWidth()/2 - parentLocation[0];
		mEmiterY = location[1] + emiter.getHeight()/2 - parentLocation[1];
	}

	private void activateParticle(int delay) {
		Particle p = mParticles.remove(0);
		float speed = mRandom.nextFloat()*(mSpeedMax-mSpeedMin) + mSpeedMin;
		int angle = mRandom.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
		float scale = mRandom.nextFloat()*(mMaxScale-mMinScale) + mMinScale;
		float rotationSpeed = mRandom.nextFloat()*(mMaxRotation-mMinRotation) + mMinRotation;			
		p.configure(mTimeToLive, mEmiterX, mEmiterY, speed, angle, scale, rotationSpeed, mVelocity, mVelocityAngle, mMilisecondsBeforeEnd, mFadeOutInterpolator);
		p.activate(delay);
		mActiveParticles.add(p);
	}

	private void onUpdate(int miliseconds) {
		if (!mParticles.isEmpty() && mActiveParticles.size() * mDelayBetweenParticles < miliseconds) {
			// Activate a new particle
			activateParticle(miliseconds);			
		}
		for (int i=0; i<mActiveParticles.size(); i++) {
			boolean active = mActiveParticles.get(i).update(miliseconds);
			if (!active) {
				Particle p = mActiveParticles.remove(i);
				i--; // Needed to keep the index at the right position
				mParticles.add(p);
			}
		}
		mDrawingView.postInvalidate();
	}

	private void cleanupAnimation() {
		mParentView.removeView(mDrawingView);
		mDrawingView = null;
		mParentView.postInvalidate();
		mParticles.addAll(mActiveParticles);
	}
}
