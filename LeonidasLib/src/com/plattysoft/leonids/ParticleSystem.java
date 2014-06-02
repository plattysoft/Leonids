package com.plattysoft.leonids;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.plattysoft.leonids.initializers.AccelerationInitializer;
import com.plattysoft.leonids.initializers.ParticleInitializer;
import com.plattysoft.leonids.initializers.RotationInitiazer;
import com.plattysoft.leonids.initializers.RotationSpeedInitializer;
import com.plattysoft.leonids.initializers.ScaleInitializer;
import com.plattysoft.leonids.initializers.SpeeddByComponentsInitializer;
import com.plattysoft.leonids.initializers.SpeeddModuleAndRangeInitializer;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ParticleModifier;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class ParticleSystem {

	private static final long TIMMERTASK_INTERVAL = 50;
	private ViewGroup mParentView;
	private int mMaxParticles;
	private Random mRandom;

	private ParticleField mDrawingView;
	
	private ArrayList<Particle> mParticles;
	private ArrayList<Particle> mActiveParticles;
	private int mEmiterX;
	private int mEmiterY;
	private long mTimeToLive;
	private long mCurrentTime;
	
	private float mParticlesPerMilisecond;
	private int mActivatedParticles;
	private long mEmitingTime;
	
	private List<ParticleModifier> mModifiers;
	private List<ParticleInitializer> mInitializers;
	private ValueAnimator mAnimator;
	private Timer mTimer;

	private ParticleSystem(Activity a, int maxParticles, long timeToLive) {
		mRandom = new Random();
		mParentView = (ViewGroup) a.findViewById(android.R.id.content);
		
		mModifiers = new ArrayList<ParticleModifier>();
		mInitializers = new ArrayList<ParticleInitializer>();
		
		mMaxParticles = maxParticles;
		// Create the particles
		mActiveParticles = new ArrayList<Particle>(); 
		mParticles = new ArrayList<Particle> ();
		mTimeToLive = timeToLive;
	}
	
	/**
	 * Creates a particle system with the given parameters
	 * 
	 * @param a The parent activity
	 * @param maxParticles The maximum number of particles
	 * @param drawableRedId The drawable resource to use as particle (supports Bitmaps and Animations)
	 * @param timeToLive The time to live for the particles
	 */
	public ParticleSystem(Activity a, int maxParticles, int drawableRedId, long timeToLive) {
		this(a, maxParticles, a.getResources().getDrawable(drawableRedId), timeToLive);
	}
	
	/**
	 * Utility constructor that receives a Drawable
	 * 
	 * @param a The parent activity
	 * @param maxParticles The maximum number of particles
	 * @param drawable The drawable to use as particle (supports Bitmaps and Animations)
	 * @param timeToLive The time to live for the particles
	 */
	public ParticleSystem(Activity a, int maxParticles, Drawable drawable, long timeToLive) {
		this(a, maxParticles, timeToLive);
		if (drawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			for (int i=0; i<mMaxParticles; i++) {
				mParticles.add (new Particle (bitmap));
			}
		}
		else if (drawable instanceof AnimationDrawable) {
			AnimationDrawable animation = (AnimationDrawable) drawable;
			for (int i=0; i<mMaxParticles; i++) {
				mParticles.add (new AnimatedParticle (animation));
			}
		}
		else {
			// Not supported, no particles are being created
		}
	}
	
	/**
	 * Utility constructor that receives a Bitmap
	 * 
	 * @param a The parent activity
	 * @param maxParticles The maximum number of particles
	 * @param bitmap The bitmap to use as particle
	 * @param timeToLive The time to live for the particles
	 */
	public ParticleSystem(Activity a, int maxParticles, Bitmap bitmap, long timeToLive) {
		this(a, maxParticles, timeToLive);		
		for (int i=0; i<mMaxParticles; i++) {
			mParticles.add (new Particle (bitmap));
		}
	}

	/**
	 * Utility constructor that receives an AnimationDrawble
	 * 
	 * @param a The parent activity
	 * @param maxParticles The maximum number of particles
	 * @param animation The animation to use as particle
	 * @param timeToLive The time to live for the particles
	 */
	public ParticleSystem(Activity a, int maxParticles, AnimationDrawable animation, long timeToLive) {
		this(a, maxParticles, timeToLive);
		// Create the particles
		for (int i=0; i<mMaxParticles; i++) {
			mParticles.add (new AnimatedParticle (animation));
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
	
	public ParticleSystem setSpeedRange(float speedMin, float speedMax) { 
		mInitializers.add(new SpeeddModuleAndRangeInitializer(speedMin, speedMax, 0, 360));		
		return this;
	}
	
	public ParticleSystem setSpeedModuleAndAngleRange(float speedMin, float speedMax, int minAngle, int maxAngle) {
		mInitializers.add(new SpeeddModuleAndRangeInitializer(speedMin, speedMax, minAngle, maxAngle));		
		return this;
	}
	
	public ParticleSystem setSpeedByComponentsRange(float speedMinX, float speedMaxX, float speedMinY, float speedMaxY) {
		mInitializers.add(new SpeeddByComponentsInitializer(speedMinX, speedMaxX, speedMinY, speedMaxY));		
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
	
	public ParticleSystem setRotationSpeed(float rotationSpeed) {
		mInitializers.add(new RotationSpeedInitializer(rotationSpeed, rotationSpeed));
		return this;
	}
	
	public ParticleSystem setRotationSpeedRange(float minRotationSpeed, float maxRotationSpeed) {
		mInitializers.add(new RotationSpeedInitializer(minRotationSpeed, maxRotationSpeed));
		return this;
	}
	
	public ParticleSystem setAccelerationModuleAndAndAngleRange(float minAcceleration, float maxAcceleration, int minAngle, int maxAngle) {
		mInitializers.add(new AccelerationInitializer(minAcceleration, maxAcceleration, minAngle, maxAngle));
		return this;
	}
	
	public ParticleSystem setAcceleration(float acceleration, int angle) {
		mInitializers.add(new AccelerationInitializer(acceleration, acceleration, angle, angle));
		return this;
	}
	
	/**
	 * Configures a fade out for the particles when they disappear
	 * 
	 * @param duration fade out duration in miliseconds
	 * @param interpolator the interpolator for the fade out (default is linear)
	 */
	public ParticleSystem setFadeOut(long milisecondsBeforeEnd, Interpolator interpolator) {
		mModifiers.add(new AlphaModifier(255, 0, mTimeToLive-milisecondsBeforeEnd, mTimeToLive, interpolator));
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
	public void emit (View emiter, int particlesPerSecond, int emitingTime) {
		// Setup emiter
		configureEmiter(emiter);
		mActivatedParticles = 0;
		mParticlesPerMilisecond = particlesPerSecond/1000f;
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		
		mDrawingView.setParticles (mActiveParticles);
		mCurrentTime = 0;
		mEmitingTime = emitingTime;
		startAnimator(new LinearInterpolator(), emitingTime+mTimeToLive);
	}
	
	/**
	 * Starts emiting particles from a specific view. If at some point the number goes over the amount of particles availabe on create
	 * no new particles will be created
	 * 
	 * @param emiter  View from which center the particles will be emited
	 * @param particlesPerSecond Number of particles per second that will be emited (evenly distributed)
	 */
	public void emit (View emiter, int particlesPerSecond) {
		// Setup emiter
		configureEmiter(emiter);
		mActivatedParticles = 0;
		mParticlesPerMilisecond = particlesPerSecond/1000f;
		// Add a full size view to the parent view		
		mDrawingView = new ParticleField(mParentView.getContext());
		mParentView.addView(mDrawingView);
		mEmitingTime = -1; // Meaning infinite
		mDrawingView.setParticles (mActiveParticles);
		mCurrentTime = 0;
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {			
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
	 */
	public void oneShot(View emiter, int numParticles) {
		oneShot(emiter, numParticles, new LinearInterpolator());
	}
	
	/**
     * Launches particles in one Shot using a special Interpolator
	 * 
	 * @param emiter View from which center the particles will be emited
	 * @param numParticles number of particles launched on the one shot
	 * @param interpolator the interpolator for the time
	 */
	public void oneShot(View emiter, int numParticles, Interpolator interpolator) {
		configureEmiter(emiter);
		mActivatedParticles = 0;
		mEmitingTime = mTimeToLive;
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

	private void startAnimator(Interpolator interpolator, long animnationTime) {
		mAnimator = ValueAnimator.ofInt(new int[] {0, (int) animnationTime});
		mAnimator.setDuration(animnationTime);
		mAnimator.addUpdateListener(new AnimatorUpdateListener() {			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int miliseconds = (Integer) animation.getAnimatedValue();
				onUpdate(miliseconds);
			}
		});
		mAnimator.addListener(new AnimatorListener() {			
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
		mAnimator.setInterpolator(interpolator);
		mAnimator.start();
	}

	private void configureEmiter(View emiter) {
		int[] location = new int[2];
		int[] parentLocation = new int[2];
		emiter.getLocationInWindow(location);
		mParentView.getLocationInWindow(parentLocation);
		mEmiterX = location[0] + emiter.getWidth()/2 - parentLocation[0];
		mEmiterY = location[1] + emiter.getHeight()/2 - parentLocation[1];
	}

	private void activateParticle(long delay) {
		Particle p = mParticles.remove(0);		
		// Initialization goes before configuration, scale is required before can be configured properly
		for (int i=0; i<mInitializers.size(); i++) {
			mInitializers.get(i).initParticle(p, mRandom);
		}
		p.configure(mTimeToLive, mEmiterX, mEmiterY);
		p.activate(delay, mModifiers);
		mActiveParticles.add(p);
		mActivatedParticles++;
	}

	private void onUpdate(long miliseconds) {
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
	
	public void cancel() {
		if (mAnimator != null && mAnimator.isRunning()) {
			mAnimator.cancel();
		}
		if (mTimer != null) {
			mTimer.cancel();
			mTimer.purge();
			cleanupAnimation();
		}
	}
}
