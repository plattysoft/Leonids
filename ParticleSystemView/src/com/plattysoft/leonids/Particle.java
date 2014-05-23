package com.plattysoft.leonids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class Particle {

	private Bitmap mImage;
	
	private float mCurrentX;
	private float mCurrentY;
	private float mScale;
	private float mSpeedX;
	private float mSpeedY;

	private Matrix mMatrix;

	private Paint mPaint;

	private float mInitialX;

	private float mInitialY;

	private float mRotationSpeed = 0;
	private float mInitialRotation = 0;

	private float mRotation;

	private float mVelocityX;
	private float mVelocityY;

	private long mMilisecondBeforeEndFade;
	private int mAlpha;

	private long mTimeToLive;

	public Particle(Bitmap image) {
		mImage = image;
	}

	public void configure(long timeToLive, float emiterX, float emiterY, float speed, int angle, float scale, float rotationSpeed, float velocity, float velocityAngle, long fadeOutMiliseconds) {
		float angleInRads = (float) (angle*Math.PI/180f);
		float velocityAngleInRads = (float) (velocityAngle*Math.PI/180f);
		
		mCurrentX = emiterX;
		mCurrentY = emiterY;
		mInitialX = emiterX - mImage.getWidth()/2*scale;
		mInitialY = emiterY - mImage.getHeight()/2*scale;
		mSpeedX = (float) (speed * Math.cos(angleInRads));
		mSpeedY = (float) (speed * Math.sin(angleInRads));
		mVelocityX = (float) (velocity * Math.cos(velocityAngleInRads));
		mVelocityY = (float) (velocity * Math.sin(velocityAngleInRads));
		mRotationSpeed = rotationSpeed;
		mMilisecondBeforeEndFade = fadeOutMiliseconds;
		mScale = scale;
		mTimeToLive = timeToLive;
		// Scale the bitmap if scale != 1
		mMatrix = new Matrix();
		mPaint = new Paint();
	}

	public void update (long miliseconds) {
		mCurrentX = mInitialX+mSpeedX*miliseconds+mVelocityX*miliseconds*miliseconds;
		mCurrentY = mInitialY+mSpeedY*miliseconds+mVelocityY*miliseconds*miliseconds;
		mRotation = mInitialRotation + mRotationSpeed*miliseconds/1000;
		// Alpha goes from 0 to 255
		if (mTimeToLive - miliseconds <= mMilisecondBeforeEndFade) {
			mAlpha = (int) (mMilisecondBeforeEndFade - (mTimeToLive - miliseconds))*255;
		}
		else {
			mAlpha = 255;
		}
	}
	
	public void draw (Canvas c) {
		mMatrix.reset();
		mMatrix.postScale(mScale, mScale);
		mMatrix.postRotate(mRotation);
		mMatrix.postTranslate(mCurrentX, mCurrentY);
		
		mPaint.setAlpha(mAlpha);
		
		c.drawBitmap(mImage, mMatrix, mPaint);
	}
}
