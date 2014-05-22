package com.plattysoft.leonids;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

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

//	private long mMilisecondsBeforeEnd;
//	private float mFinalAlpha;
//	private double mAlpha;

	private float mRotationSpeed = 0;
	private float mInitialRotation = 0;

	private float mRotation;

	private float mVelocityX;
	private float mVelocityY;

	public Particle(Bitmap image) {
		mImage = image;
	}

	public void configure(float emiterX, float emiterY, float speed, int angle,	float scale, float rotationSpeed, float velocity, float velocityAngle) {
		float angleInRads = (float) (angle*Math.PI/180f);
		float velocityAngleInRads = (float) (velocityAngle*Math.PI/180f);
		
		mCurrentX = emiterX;
		mCurrentY = emiterY;
		mInitialX = emiterX;
		mInitialY = emiterY;
		mSpeedX = (float) (speed * Math.cos(angleInRads));
		mSpeedY = (float) (speed * Math.sin(angleInRads));
		mVelocityX = (float) (velocity * Math.cos(velocityAngleInRads));
		mVelocityY = (float) (velocity * Math.sin(velocityAngleInRads));
		mRotationSpeed = rotationSpeed;
		mScale = scale;
		// Scale the bitmap if scale != 1
		mMatrix = new Matrix();
		mPaint = new Paint();
	}

	public void update (long miliseconds) {
		mCurrentX = mInitialX+mSpeedX*miliseconds+mVelocityX*miliseconds*miliseconds;
		mCurrentY = mInitialY+mSpeedY*miliseconds+mVelocityY*miliseconds*miliseconds;
		mRotation = mInitialRotation + mRotationSpeed*miliseconds/1000;
//		if (miliseconds > mMilisecondsBeforeEnd) {
//			mAlpha = 1.0 - (mFinalAlpha -1.0) * (miliseconds - mMilisecondsBeforeEnd);
//		}
	}
	
	public void draw (Canvas c) {
		mMatrix.reset();
		mMatrix.postScale(mScale, mScale);
		mMatrix.postRotate(mRotation);
		mMatrix.postTranslate(mCurrentX, mCurrentY);
		
		// TODO
//		mPaint.setAlpha(100);
		
		c.drawBitmap(mImage, mMatrix, mPaint);
	}

//	public void configureFadeOut(long milisecondsBeforeEnd, float finalAlpha) {
//		mMilisecondsBeforeEnd = milisecondsBeforeEnd;
//		mFinalAlpha = finalAlpha;
//	}
}
