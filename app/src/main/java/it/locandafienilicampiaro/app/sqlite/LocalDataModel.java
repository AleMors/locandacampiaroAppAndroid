/**
 * 
 */
package it.locandafienilicampiaro.app.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * @author
 *
 */
public abstract class LocalDataModel {
	
	
	protected Long id;

	protected LocalDataModel(Long id) {
		this.id = id;
	}
	
	public abstract ContentValues asValues();
	
	public abstract LocalDataModel fromCursor(final Cursor cursor);

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
