package it.locandafienilicampiaro.app.view.swap;

import java.io.Serializable;


public class TopicList implements Serializable {

	private static final long serialVersionUID = 1L;

	String mSampleTopicText = "Topic text goes here.";

	public static TopicList sharedInstance = null;

	private TestTopics topics;
	
	static public TopicList getInstance() {
		if (sharedInstance == null)
			sharedInstance = new TopicList(null);
		return sharedInstance;
	}

	static public void setInstance(TopicList tlist) {
		sharedInstance = tlist;
	}

	public TopicList(String sampleText) {
		mSampleTopicText = sampleText;
	} 

	public int getNumTopics() {
		//return TestTopics.Titles.length;
		return this.topics.getImageids().length;
	} // end getNumTopics


	public String getTopicTitle(int index) {
		if (index >= this.topics.getTitles().length)
			return null;
		return this.topics.getTitles()[index];
	} // end getNumTopics


	public int getTopicImage(int index) {
		if (index >= this.topics.getImageids().length)
			return 0;
		return this.topics.getImageids()[index];
	} // end getTopicImage


	public String getTopicText(int index) {
		// All topics share some sample text in this demo.
		//if (index >= TestTopics.Titles.length)
		if (index >= this.topics.getTitles().length)
			return null;
		return mSampleTopicText;
	} // end getTopicText

	public TestTopics getTopics() {
		return this.topics;
	}
	public void setTopics(TestTopics topics) {
		this.topics = topics;
	}

}
