package it.locandafienilicampiaro.app.view.swap;

import java.io.Serializable;

public class TestTopics implements Serializable{


    // references to our images
    private Integer[] ImageIds = null;

    // references to the titles
    private String[] titles = null;
    
	public Integer[] getImageids() {
		return ImageIds;
	}
	public String[] getTitles() {
		return titles;
	}
	public Integer[] getImageIds() {
		return this.ImageIds;
	}
	public void setImageIds(Integer[] imageIds) {
		this.ImageIds = imageIds;
	}
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

}
