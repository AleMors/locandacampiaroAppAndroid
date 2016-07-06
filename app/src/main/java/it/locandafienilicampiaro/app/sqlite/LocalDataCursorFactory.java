package it.locandafienilicampiaro.app.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

/**
 * 
 * @author
 *
 */
public class LocalDataCursorFactory implements SQLiteDatabase.CursorFactory {

	public Cursor newCursor(SQLiteDatabase sqLiteDatabase, SQLiteCursorDriver sqLiteCursorDriver,
			String editTable, SQLiteQuery sqLiteQuery) {

		
		if(editTable.equalsIgnoreCase(IDatabaseMetadata.TABLE_NAME_REVIEWS)) {
			return new ReviewDataCursor(sqLiteDatabase, sqLiteCursorDriver, 
					IDatabaseMetadata.TABLE_NAME_REVIEWS, sqLiteQuery);
			
		} 
		return null;
	}


}
