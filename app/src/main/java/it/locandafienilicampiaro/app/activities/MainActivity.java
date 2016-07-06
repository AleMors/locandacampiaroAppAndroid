package it.locandafienilicampiaro.app.activities;

import it.locandafienilicampiaro.app.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

	@Override
	protected void prepareView(Bundle savedInstanceState) {

		setContentView(R.layout.activity_main);
		
		Typeface florenceFont = getFlorenceFont();
		
		TextView headerNameText = (TextView)findViewById(R.id.header_name);
		headerNameText.setTypeface(florenceFont);
		TextView locationText = (TextView)findViewById(R.id.header_location);
		locationText.setTypeface(florenceFont);
		TextView contact1Text = (TextView)findViewById(R.id.header_contact_1);
		contact1Text.setTypeface(florenceFont);
		TextView contact2Text =(TextView)findViewById(R.id.header_contact_2);
		contact2Text.setTypeface(florenceFont);
		
	}




}
