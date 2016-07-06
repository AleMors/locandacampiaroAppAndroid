package it.locandafienilicampiaro.app.activities;

import it.locandafienilicampiaro.app.R;
import it.locandafienilicampiaro.app.sqlite.ReviewContract;
import it.locandafienilicampiaro.app.sqlite.ReviewContract.ReviewEntry;
import it.locandafienilicampiaro.app.sqlite.ReviewLocalModel;
import it.locandafienilicampiaro.app.sqlite.ReviewLocalService;
import it.locandafienilicampiaro.app.utils.ResourceUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OpinionsActivity extends BaseActivity {

	@Override
	protected void prepareView(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_opinions);
		
		Button alarmButton = (Button) findViewById(R.id.sendReview);
		alarmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				// Gestione del salvataggio della recensione.
				try {
					EditText customerView = (EditText)findViewById(R.id.customer_review);
					EditText reviewView = (EditText)findViewById(R.id.text_review);
					
					String customerName = String.valueOf(customerView.getText());
					String reviewText = String.valueOf(reviewView.getText());
					
					// Inserimento del record in tabella.	
					ReviewLocalService reviewLocalService = new ReviewLocalService(getApplication());
					reviewLocalService.openHelper();
					
					String message = "";
					ReviewLocalModel alModel = new ReviewLocalModel(null,customerName, reviewText);
					
					ReviewEntry re = new ReviewContract.ReviewEntry();
					if(reviewLocalService.insertAlert(alModel, re)) {
						
						message = ResourceUtils.
								getStringIdentifier(getApplication(), "message_opinions_ok");
						
						customerView.setText("");
						reviewView.setText("");
						
					} else {
						message = ResourceUtils.
								getStringIdentifier(getApplication(), "message_opinions_ko");
					}
					
					
					Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG)
							.show();
					
				} catch(Exception ex) {
					Log.e(OpinionsActivity.class.getCanonicalName(), "Errore durante " +
							"l'inserimento della recensione", ex);
					
					String koMessage = ResourceUtils.
							getStringIdentifier(getApplication(), "message_opinions_ko");
					
					Toast.makeText(getApplicationContext(), koMessage, Toast.LENGTH_LONG)
							.show();
				}
			}
		});		
	}

}
