/**
 * 
 */
package it.locandafienilicampiaro.app.sqlite;

import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;

/**
 * @author
 *
 */
public abstract class LocalDataCursor extends SQLiteCursor {

	/**
     * Creates a LocalDataCursor from the information needed
     *
     * @param sqLiteDatabase     The current DB instance
     * @param sqLiteCursorDriver The Driver of the current Db
     * @param editTable          The table used but this cursor
     * @param sqLiteQuery        The current query executed
     */
    public LocalDataCursor(SQLiteDatabase sqLiteDatabase, SQLiteCursorDriver sqLiteCursorDriver,
                           String editTable, SQLiteQuery sqLiteQuery) {
        super(sqLiteDatabase, sqLiteCursorDriver, editTable, sqLiteQuery);
    }

	public Long getId() {
	    return getLong(getColumnIndex(AbstractEntry._ID));
	}
	
	public abstract LocalDataModel asLocalDataModel();
}
