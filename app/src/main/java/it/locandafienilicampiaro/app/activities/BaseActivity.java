package it.locandafienilicampiaro.app.activities;

import it.locandafienilicampiaro.app.R;
import it.locandafienilicampiaro.app.utils.IAppUtils;
import it.locandafienilicampiaro.app.utils.ResourceUtils;
import it.locandafienilicampiaro.app.view.SpinnerNavItem;
import it.locandafienilicampiaro.app.view.TitleNavigationAdapter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity  extends FragmentActivity implements ActionBar.OnNavigationListener {

    // action bar
    private ActionBar actionBar;
 
    // Title navigation Spinner data
    private ArrayList<SpinnerNavItem> navSpinner;
     
    // Navigation adapter
    private TitleNavigationAdapter adapter;
  
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Preparazione della vire della Activity corrente.
		prepareView(savedInstanceState);
		
		actionBar = getActionBar();
        
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
 
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        actionBar.setDisplayShowHomeEnabled(false);
       
		// Recupero dei POI da visualizzare.
		String[] navMenuData = getResources()
				.getStringArray(R.array.nav_menu_items);
        
		navSpinner = new ArrayList<SpinnerNavItem>();
		for(String navMenu : navMenuData) {
			String text = ResourceUtils.
					getStringIdentifier(getApplication(), navMenu);
			navSpinner.add(new SpinnerNavItem(text));
		}
		
        // title drop down adapter
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
 
        // assigning the spinner navigation    
        actionBar.setListNavigationCallbacks(adapter, this);
	}
    
	
	/**
	 * Metodo per la gestione dell'inizializzazione delle Activities figlie.
	 * @param savedInstanceState
	 */
	protected abstract void prepareView(Bundle savedInstanceState);
	
	/**
	 * Metodo che restituisce il font Florence florence.ttf.
	 * @return
	 */
	protected Typeface getFlorenceFont() {
		return Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/florence.ttf");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar, menu);
		return true;
	}
	
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
    	
    	String sliderType = null;
    	
    	switch(itemPosition) {
    	
	    	case IAppUtils.ACTIONBAR_MENU_MAIN :{
	    		sliderType = String.valueOf(IAppUtils.ACTIONBAR_MENU_MAIN);
	    		Intent i = new Intent(this, MenuSliderActivity.class);
	    		i.putExtra("sliderType", sliderType);
	    		startActivity(i);
	    		break;
	    	}
	    	
	    	case IAppUtils.ACTIONBAR_MENU_SECOND :{
	    		sliderType = String.valueOf(IAppUtils.ACTIONBAR_MENU_SECOND);
	    		Intent i = new Intent(this, MenuSliderActivity.class);
	    		i.putExtra("sliderType", sliderType);
	    		startActivity(i);    		
	    		sliderType = "second";
	    		break;
	    	}
	    	
	    	case IAppUtils.ACTIONBAR_MENU_DESSERT :{
	    		sliderType = String.valueOf(IAppUtils.ACTIONBAR_MENU_DESSERT);
	    		Intent i = new Intent(this, MenuSliderActivity.class);
	    		i.putExtra("sliderType", sliderType);
	    		startActivity(i);		
	    		sliderType = "dessert";
	    		break;
	    	}
	    	
	    	case IAppUtils.ACTIONBAR_MENU_WINE :{
	    		sliderType = String.valueOf(IAppUtils.ACTIONBAR_MENU_WINE);
	    		Intent i = new Intent(this, MenuSliderActivity.class);
	    		i.putExtra("sliderType", sliderType);
	    		startActivity(i);		
	    		sliderType = "wine";
	    		break;
	    	}
    	}
    	
    	return false;	    		
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

    	switch (item.getItemId()) {
        
        case R.id.action_map:
    		Intent intent = new Intent(this, MapActivity.class);
    		startActivity(intent);
        	return true;
        case R.id.action_opinion:
    		Intent i = new Intent(this, OpinionsActivity.class);
    		startActivity(i);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
