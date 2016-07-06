package it.locandafienilicampiaro.app.activities;

import it.locandafienilicampiaro.app.R;
import android.app.AlertDialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends BaseActivity {

	private final LatLng STARTING_POINT = new LatLng(44.493728, 11.343072); // Bologna Centro

	private GoogleMap map;

	@Override
	protected void prepareView(Bundle savedInstanceState) {

		setContentView(R.layout.map_layout);

		setUpMapIfNeeded();

		if (map == null) {

			// Problemi nel rendering della mappa.
			new AlertDialog.Builder(this)
					.setTitle("Problemi nel rendering della mappa").create()
					.show();

		} else {

			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			boolean gps = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			if (!gps) {

				new AlertDialog.Builder(this).setTitle("GPS non disponibile")
						.create().show();

			}

			map.getUiSettings().setMyLocationButtonEnabled(true);
			map.setMyLocationEnabled(true);

			// Recupero dei POI da visualizzare.
			String[] poiData = getResources()
					.getStringArray(R.array.poi_values);
			StringBuffer sb = new StringBuffer();
			sb.append(poiData[1]);
			sb.append("\n");
			sb.append(poiData[2]);

			LatLng lL = new LatLng(Double.valueOf(poiData[3]),
					Double.valueOf(poiData[4]));

			map.addMarker(new MarkerOptions().position(lL).title(poiData[0])
					.snippet(sb.toString()));

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(lL).zoom(15).bearing(10).tilt(30).build();
			map.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

	private void setUpMapIfNeeded() {
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map_fragment)).getMap();
		if (map != null) {
			setUpMap();
		}
	}

	private void setUpMap() {
		map.getUiSettings().setZoomControlsEnabled(false);
	}

}
