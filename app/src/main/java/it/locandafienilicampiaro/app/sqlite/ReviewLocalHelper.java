package it.locandafienilicampiaro.app.sqlite;

import android.app.Application;


public class ReviewLocalHelper extends LocalDatabaseHelper {

	/**
	 * @param context
	 */
	public ReviewLocalHelper(Application context) {
		super(context);
	}

	
	public LocalDataCursor getReviewById(Long revId) {
		String where = ReviewContract.ReviewEntry._ID + " = ? ";
		String[] whereArgs = new String[1];
		whereArgs[0] = String.valueOf(revId);
		return customQuery(where, whereArgs,
				ReviewContract.ReviewEntry.TABLE_NAME, null, null, null, null);
	}
	
}
