package com.plattysoft.leonids.examples;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExampleListActivity extends ListActivity {

	private class Sample {
		public String name;
		public Class activityClass;

		public Sample(String name, Class activityClass) {
			this.name = name;
			this.activityClass = activityClass;
		}
	}

	/**
	 * List of samples to show.
	 * Make sure to add the activity to the manifest, too.
	 */
	private Sample[] samples = {
			new Sample("One Shot Simple", OneShotSimpleExampleActivity.class),
			new Sample("One Shot Advanced", OneShotAdvancedExampleActivity.class),

			new Sample("Emiter Simple", EmiterSimpleExampleActivity.class),
			new Sample("Emiting on background [NEW]", EmiterBackgroundSimpleExampleActivity.class),
			new Sample("Emiter Intermediate", EmiterIntermediateExampleActivity.class),
			new Sample("Emiter Time Limited", EmiterTimeLimitedExampleActivity.class),
			new Sample("Emit with Gravity [NEW]", EmiterWithGravityExampleActivity.class),

			new Sample("Follow touch [NEW]", FollowCursorExampleActivity.class),
			new Sample("Animated particles", AnimatedParticlesExampleActivity.class),
			new Sample("Fireworks", FireworksExampleActivity.class),
			new Sample("Confetti [Rabbit and Eggs]", ConfettiExampleActivity.class),
			new Sample("Dust [Rabbit and Eggs]", DustExampleActivity.class),
			new Sample("Stars [Rabbit and Eggs]", StarsExampleActivity.class)
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String sampleList[] = new String [samples.length];
		int n = 0;
		for (Sample sample : samples) {
			sampleList[n++] = sample.name;
		}
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleList));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (position >=0 && position < samples.length) {
			startActivity(new Intent(this, samples[position].activityClass));
		}
	}

}
