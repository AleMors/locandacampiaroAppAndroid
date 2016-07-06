package it.locandafienilicampiaro.app.sqlite;

import android.provider.BaseColumns;

public final class ReviewContract {

	public ReviewContract() {
		
	}
	
	public static ReviewEntry getEntry () {
		
		return new ReviewEntry() {
		};
	}
	
	public static class ReviewEntry extends AbstractEntry 
	implements BaseColumns {
		
		public static final String TABLE_NAME = IDatabaseMetadata.TABLE_NAME_REVIEWS;
		public static final String _ID 					= "_id";
		public static final String COLUMN_NAME_REVIEW_CUSTOMER 		= "customer";
		public static final String COLUMN_NAME_REVIEW_TEXT 		= "review";
		
		@Override
		public String getTableName() {
			return TABLE_NAME;
		}
		
	}

	
	public static final String SQL_SELECT_ALL_ALERTS_BY_RETENTION = " select " + AbstractEntry._ID +
			", " + ReviewEntry.COLUMN_NAME_REVIEW_CUSTOMER +
			", " + ReviewEntry.COLUMN_NAME_REVIEW_TEXT  + " from " + AbstractEntry.TABLE_NAME;
}
