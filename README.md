Leonids
==========================

Leonids is a particle system library that works with the standard Android UI.

The library is extremely lightweight, it is just a jar file of 18Kb you can add to your project.

# Why this library?

Particle systems are often used in games for a wide range of purposes: Explosions, fire, smoke, etc. This effects can also be used on normal apps to add an element of "juiciness" or Playful Design.

Precisely because its main use is games, all engines have support for particle systems, but there is no such thing for standard Android UI.

This means that if you are building an Android app and you want a particle system, you have to include a graphics engine and use OpenGL -which is quite an overkill- or you have to implement it yourself.

Leonids is made to fill this gap, bringing particle sytems to developers that use the standard Android UI.

# Basic usage

Creating and firing a one-shot particle system is very easy, just 3 lines of code.

```java
new ParticleSystem(this, numParticles, drawableResId, timeToLive)
.setSpeedRange(0.2f, 0.5f)
.oneShot(anchorView, numParticles);
```

When you create the particle system, you tell how many particles will it use as a maximum, the resourceId of the drawable you want to use for the particles and for how long the particles will live.

Then you configure the particle system. In this case we specify that the particles will have a speed between 0.2 and 0.5 pixels per milisecond (support for dips will be included in the future). Since we did not provide an angle range, it will be considered as "any angle".

Finally, we call oneShot, passing the view from which the particles will be launched and saying how many particles we want to be shot.

Check the wiki for more details.
