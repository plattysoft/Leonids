package com.plattysoft.leonids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.plattysoft.leonids.initializers.ParticleInitializer;
import com.plattysoft.leonids.initializers.RotationInitiazer;
import com.plattysoft.leonids.initializers.RotationSpeedInitializer;
import com.plattysoft.leonids.initializers.ScaleInitializer;
import com.plattysoft.leonids.modifiers.ParticleModifier;

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

	private ParticleField mDrawingView;

	private long mMilisecondsBeforeEnd = 0;
	private Interpolator mFadeOutInterpolator = new LinearInterpolator();

	private float mVelocity = 0;
	private float mVelocityAngle = 0;

	private ArrayList<Particle> mParticles;
	private ArrayList<Particle> mActiveParticles;
	private int mEmiterX;
	private int mEmiterY;
	private int mTimeToLive;
	private int mCurrentTime;
	
	private float mParticlesPerMilisecond;
	private int mActivatedParticles;
	private int mEmitingTime;
	
	private List<ParticleModifier> mModifiers;
	private List<ParticleInitializer> mInitializers;

	public ParticleSystem(Activity a, int maxParticles, Bitmap bitmap) {
		mRandom = new Random();
		mParentView = (ViewGroup) a.findViewById(android.R.id.content);
		
		mModifiers = new ArrayList<ParticleModifier>();
		mInitializers = new ArrayList<ParticleInitializer>();
		
		mMaxParticles = maxParticles;
		// Create the particles
		mActiveParticles = new ArrayList<Particle>(); 
		mParticles = new ArrayList<Particle> ();
		for (int i=0; i<mMaxParticles; i++) {
			mParticles.add (new Particle (bitmap));
		}
	}

	/**
	 * Adds a modifier to the Particle system, it will be executed on each update.
	 * 
	 * @param modifier
	 * @return
	 */
	public ParticleSystem addModifier(ParticleModifier modifier) {
		mModifiers.add(modifier);
		return this;
	}
	
	public ParticleSystem setSpeed(float speed) {
		return setSpeedRange(speed, speed);
	}
	
	public ParticleSystem setSpeedRange(float speedMin, float speedMax) {		
		mSpeedMin = speedMin;
		mSpeedMax = speedMax;
		return this;
	}
	
	public ParticleSystem setSpeedAngleRange(int minAngle, int maxAngle) {
		mMinAngle = minAngle;
		mMaxAngle = maxAngle;
		// Make sure the angles are in the [0-360) range
		while (mMinAngle < 0) {
			mMinAngle+=360;
		}
		mMinAngle = mMinAngle%360;
		while (mMaxAngle < 0) {
			mMaxAngle+=360;
		}
		mMaxAngle = mMaxAngle%360;
		// Also make sure that mMinAngle is the smaller
		if (mMinAngle > mMaxAngle) {
			int tmp = mMinAngle;
			mMinAngle = mMaxAngle;
			mMaxAngle = tmp;
		}
		return this;
	}
	
	public ParticleSystem setInitialRotationRange (int minAngle, int maxAngle) {
		mInitializers.add(new RotationInitiazer(minAngle, maxAngle));
		return this;
	}
	
	public ParticleSystem setScaleRange(float minScale, float maxScale) {
		mInitializers.add(new ScaleInitializer(minScale, maxScale));
		return this;
	}
	
	public ParticleSystem setRotationSpeed(float minRotationSpeed, float maxRotationSpeed) {
		mInitializers.add(new RotationSpeedInitializer(minRotationSpeed, maxRotationSpeed));
		return this;
	}
	
	public ParticleSystem setVelocity(float velocity, float angle) {
		mVelocity = velocity;
		mVelocityAngle = angle;
		return this;
	}
	
	/**
	 * Configures a fade out for the particles when they disappear
	 * 
	 * @param duration fade out duration in miliseconds
	 * @param interpolator the interpolator for the fade out (default is linear)
	 */
	public ParticleSystem setFadeOut(long milisecondsBeforeEnd, Interpolator interpolator) {
		mMilisecondsBeforeEnd = milisecondsBeforeEnd;
		mFadeOutInterpolator = interpolator;
		return this;
	}
	
	/**
	 * Configures a fade out for the particles when they disappear
	 * 
	 * @param duration fade out duration in miliseconds
	 */
	public ParticleSystem setFadeOut(long duration) {
		return setFadeOut(duration, new LinearInterpolator());
	}
	
	/**
	 * Starts emiting particles from a specific view. If at some point the number goes over the amount of particles availabe on create
	 * no new particles will be created
	 * 
	 * @param emiter  View from which center the particles will be emited
	 * @param particlesPerSecond Number of particles per second that will be emited (evenly distributed)
	 * @param timeToLive miliseconds the particles will be displayed
	 * @param emitingTime time the emiter will be emiting particles
	 */
	public void emit (View emiter, int particlesPerSecond, int timeToLive, int emitingTime) {
		// Setup emiter
		configureEmiter(emiter);
		mActivatedParticles = 0;
		mTimeToLive = timeToLive;
		mParticlesPerMilisecond = particlesPerSecond/1000f;
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		
		mDrawingView.setParticles (mActiveParticles);
		mCurrentTime = 0;
		mEmitingTime = emitingTime;
		startAnimator(new LinearInterpolator(), emitingTime+timeToLive);
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
		mActivatedParticles = 0;
		mTimeToLive = timeToLive;
		mParticlesPerMilisecond = particlesPerSecond/1000f;
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		mEmitingTime = -1; // Meaning infinite
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
		mActivatedParticles = 0;
		mTimeToLive = timeToLive;
		mEmitingTime = timeToLive;
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
		startAnimator(interpolator, mTimeToLive);
	}

	private void startAnimator(Interpolator interpolator, int animnationTime) {
		ValueAnimator animator = ValueAnimator.ofInt(new int[] {0, animnationTime});
		animator.setDuration(animnationTime);
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
		int angle;
		if (mMaxAngle == mMinAngle) {
			angle = mMinAngle;
		}
		else {
			angle = mRandom.nextInt(mMaxAngle - mMinAngle) + mMinAngle;
		}
		// Initialization goes before configuration, scale is required before can be configured properly
		for (int i=0; i<mInitializers.size(); i++) {
			mInitializers.get(i).initParticle(p, mRandom);
		}
		p.configure(mTimeToLive, mEmiterX, mEmiterY, speed, angle, mVelocity, mVelocityAngle, mMilisecondsBeforeEnd, mFadeOutInterpolator);
		p.activate(delay, mModifiers);
		mActiveParticles.add(p);
		mActivatedParticles++;
	}

	private void onUpdate(int miliseconds) {
		while (((mEmitingTime > 0 && miliseconds < mEmitingTime)|| mEmitingTime == -1) && // This point should emit
				 !mParticles.isEmpty() && // We have particles in the pool 
				 mActivatedParticles < mParticlesPerMilisecond*miliseconds) { // and we are under the number of particles that should be launched
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
