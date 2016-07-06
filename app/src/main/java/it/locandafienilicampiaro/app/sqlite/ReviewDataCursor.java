package it.locandafienilicampiaro.app.sqlite;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;


/**
 * @author
 *
 */
public class ReviewDataCursor extends LocalDataCursor {


    /**
	 * @param sqLiteDatabase
	 * @param sqLiteCursorDriver
	 * @param editTable
	 * @param sqLiteQuery
	 */
	public ReviewDataCursor(SQLiteDatabase sqLiteDatabase,
			SQLiteCursorDriver sqLiteCursorDriver, String editTable,
			SQLiteQuery sqLiteQuery) {
		
		super(sqLiteDatabase, sqLiteCursorDriver, editTable, sqLiteQuery);
		
	}

    public String getIdReview() {
        return getString(getColumnIndex(
        		ReviewContract.ReviewEntry._ID));
    }

    public String getReviewCustomer() {
    	return getString(getColumnIndex((
    			ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_CUSTOMER)));
    }
    
    public String getReviewText() {
    	return getString(getColumnIndex((
    			ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_TEXT)));
    }
    
    
    @Override public ReviewLocalModel asLocalDataModel() {
        return ReviewLocalModel.create(getId(),
        			getReviewCustomer(), getReviewText());
    }
}
