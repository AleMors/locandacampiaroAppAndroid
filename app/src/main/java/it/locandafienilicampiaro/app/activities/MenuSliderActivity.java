package it.locandafienilicampiaro.app.activities;

import it.locandafienilicampiaro.app.R;
import it.locandafienilicampiaro.app.utils.IAppUtils;
import it.locandafienilicampiaro.app.utils.ResourceUtils;
import it.locandafienilicampiaro.app.view.swap.GridViewPager.MyAdapter;
import it.locandafienilicampiaro.app.view.swap.TestTopics;
import it.locandafienilicampiaro.app.view.swap.TopicList;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MenuSliderActivity extends BaseActivity {

	private MyAdapter mAdapter;

	private ViewPager mPager;
	private int mNumFragments = 0; // total number of fragments
	private int mNumItems = 0; // number of items per fragment

	@Override
	protected void prepareView(Bundle savedInstanceState) {
		
		setContentView(R.layout.activity_menu_slider);

		Integer[] imgIds = null;
		String[] textLabels = null;

		Intent intent = getIntent();
		String sliderType = intent.getStringExtra("sliderType");

		int resourceImageId = 0;
		int resourceTextId = 0;
		int intem = Integer.valueOf(sliderType);
		switch (intem) {

		case IAppUtils.ACTIONBAR_MENU_MAIN: {
			resourceImageId = R.array.slider_image_main;
			resourceTextId = R.array.slider_text_main;
			break;
		}

		case IAppUtils.ACTIONBAR_MENU_SECOND: {
			resourceImageId = R.array.slider_image_second;
			resourceTextId = R.array.slider_text_second;
			break;
		}

		case IAppUtils.ACTIONBAR_MENU_DESSERT: {
			resourceImageId = R.array.slider_image_dessert;
			resourceTextId = R.array.slider_text_dessert;
			break;
		}

		case IAppUtils.ACTIONBAR_MENU_WINE: {
			resourceImageId = R.array.slider_image_wine;
			resourceTextId = R.array.slider_text_wine;
			break;
		}
		}

		// Recupero delle immagini dello slider.
		final String[] mainImages = getResources().getStringArray(resourceImageId);
		imgIds = new Integer[mainImages.length];
		for (int i = 0; i < mainImages.length; i++) {
			imgIds[i] = ResourceUtils.getDrowableIdentifier(getApplication(),
					mainImages[i]);
		}

		// Recupero delle didascalie dello slider.		
		final String[] mainTextLabels = getResources().getStringArray(resourceTextId);
		textLabels = new String[mainTextLabels.length];
		for (int i = 0; i < mainTextLabels.length; i++) {
			String text = ResourceUtils.
					getStringIdentifier(getApplication(), mainTextLabels[i]);
			textLabels[i] = text;
		}
		
		TestTopics topics = new TestTopics();
		topics.setImageIds(imgIds);
		topics.setTitles(textLabels);

		buildSliderAdapter(topics);
	}

	protected void buildSliderAdapter(TestTopics tt) {

		// Gestione dello switching delle immaigni
		Resources res = getResources();

		// Create a TopicList for this demo. Save it as the shared instance in
		// TopicList
		String sampleText = res.getString(R.string.sample_topic_text);
		TopicList tlist = new TopicList(sampleText);
		tlist.setTopics(tt);
		TopicList.setInstance(tlist);

		// Create an adapter object that creates the fragments that we need
		// to display the images and titles of all the topics.
		mAdapter = new MyAdapter(getSupportFragmentManager(), tlist, res);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		// Save information about how many fragments are needed and how many
		// items are on each page.
		int numTopics = tlist.getNumTopics();
		int numRows = res.getInteger(R.integer.grid_num_rows);
		int numCols = res.getInteger(R.integer.grid_num_cols);
		int numTopicsPerPage = numRows * numCols;
		int numFragments = numTopics / numTopicsPerPage;
		if (numTopics % numTopicsPerPage != 0)
			numFragments++; // Add one if there is a partial page
		mNumFragments = numFragments;
		mNumItems = numTopicsPerPage;
	}

}
