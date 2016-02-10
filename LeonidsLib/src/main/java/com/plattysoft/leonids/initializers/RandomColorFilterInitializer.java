package com.plattysoft.leonids.initializers;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import com.plattysoft.leonids.Particle;

import java.util.List;
import java.util.Random;

public class RandomColorFilterInitializer implements ParticleInitializer {

    // list of color integers in the form 0xAARRGGBB
    // (ex. getResources().getColor(R.color.someColor) or Color.WHITE
    List<Integer> colorList;

    public RandomColorFilterInitializer(List<Integer> colorList) {
        this.colorList = colorList;
    }

    @Override
    public void initParticle(Particle p, Random r) {
        int index = r.nextInt(colorList.size());
        int color = colorList.get(index);
        PorterDuffColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        p.getPaint().setColorFilter(filter);
    }
}
