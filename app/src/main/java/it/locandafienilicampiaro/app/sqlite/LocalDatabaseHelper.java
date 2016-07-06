package it.locandafienilicampiaro.app.sqlite;



import it.locandafienilicampiaro.app.R;
import it.locandafienilicampiaro.app.exceptions.DataException;
import it.locandafienilicampiaro.app.utils.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class LocalDatabaseHelper extends SQLiteOpenHelper {


    private static final String TAG_LOG = LocalDatabaseHelper.class.getName();

	private Application _context = null;

	private SQLiteDatabase database;

	public LocalDatabaseHelper(Application context) {
		super(context, IDatabaseMetadata.DATABASE_NAME, null, IDatabaseMetadata.DATABASE_VERSION);
		this._context = context;
		
		open();
		
		System.out.println(this.database.getPath());
	}

	@Override
	public final void onCreate(SQLiteDatabase db) {

		final String createDbStatement;
		try {
			createDbStatement = ResourceUtils.getRawAsString(_context,
					R.raw.lfc_create_chema);
			
			DatabaseUtils.createDbFromSqlStatements(_context,
					IDatabaseMetadata.DATABASE_NAME, 
					IDatabaseMetadata.DATABASE_VERSION, createDbStatement);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG_LOG, "Error creating DB", e);
			return;
		}
		
	}

	@Override
	public final void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {

		switch (oldVersion) {
		case 1: {
			// Caricamento del dump per il popolamento delle tabelle di test.
			try {

				copyDataBase();

			} catch (DataException de) {
				System.err.println(de.getMessage());
			}
		}
		default: {
			// Caricamento del dump per il popolamento delle tabelle di test.
			try {

				copyDataBase();

			} catch (DataException de) {
				System.err.println(de.getMessage());
			}
		}
		}

	}

	public void open() {
		if (database == null || !database.isOpen()) {
			
			final File dbFile = _context.getDatabasePath(IDatabaseMetadata.DATABASE_NAME);
			final boolean wasExisting = dbFile.exists();
			
			if (!wasExisting) {
				final String createDbStatement;
				try {
					createDbStatement = ResourceUtils.getRawAsString(_context,
							R.raw.lfc_create_chema);
					// If it's not present we create it
					DatabaseUtils.createDbFromSqlStatements(_context,
							IDatabaseMetadata.DATABASE_NAME, 
							IDatabaseMetadata.DATABASE_VERSION, createDbStatement);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e(TAG_LOG, "Error creating DB", e);
					return;
				}
			}
			
			final SQLiteDatabase.CursorFactory cursorFactory = new LocalDataCursorFactory();
			
			database = _context.openOrCreateDatabase(IDatabaseMetadata.DATABASE_NAME,
					Context.MODE_PRIVATE, cursorFactory);
			
			int oldVersion = database.getVersion();
			if (oldVersion != IDatabaseMetadata.DATABASE_VERSION) {
				try {
					final String dropSchemaSql = ResourceUtils.getRawAsString(
							_context, R.raw.lfc_create_chema);
					database.execSQL(dropSchemaSql);
					final String createDbStatement = ResourceUtils
							.getRawAsString(_context, R.raw.lfc_create_chema);
					database.execSQL(createDbStatement);
				} catch (IOException e) {
					e.printStackTrace();
					Log.e(TAG_LOG, "Error updating DB", e);
				}
			}
		}
	}

	private final boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = "/data/data/" + this._context.getPackageName()
					+ "/databases/" + IDatabaseMetadata.DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);

		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Metodo per la copia del database per aggiornare la base dati con nuovi
	 * record in base alla successiva versione rilasciata.
	 * 
	 * @throws DataException
	 */
	private final void copyDataBase() throws DataException {

		InputStream myInput;
		try {

			myInput = _context.getAssets().open(IDatabaseMetadata.DATABASE_DUMP_VERSION_NAME);
			String outFileName = "/data/data/" + this._context.getPackageName()
					+ "/databases/" + IDatabaseMetadata.DATABASE_NAME;

			OutputStream myOutput = new FileOutputStream(outFileName);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();

			this.close();

		} catch (IOException e) {
			e.printStackTrace();
			throw new DataException("Errore durante la copia del database.");
		}

	}

	/**
	 * Metodo che di occupa della cancellazione della risorsa.
	 * 
	 * @param id
	 * @return
	 */
	protected final int deleteById(final long id, AbstractEntry entry) {
		int deleteResult = -1;
		if (database.isOpen()) {
			final String where = entry._ID + " = ?";
			final String[] whereArgs = new String[] { String.valueOf(id) };
			deleteResult = database.delete(entry.TABLE_NAME, where, whereArgs);
		}
		return deleteResult;
	}

	protected final long insert(final LocalDataModel data, AbstractEntry entry) {
		long newId = -1;
		if (database.isOpen()) {

			final ContentValues values = data.asValues();

			newId = database.insert(entry.getTableName(), null, values);
		}
		return newId;
	}

	protected final int update(final LocalDataModel data, AbstractEntry entry) {
		int updated = -1;
		if (database.isOpen()) {
			// We create the Values
			final ContentValues values = data.asValues();
			final String where = entry._ID + " = ?";
			final String[] whereArgs = new String[] { String.valueOf(data
					.getId()) };
			// We update the values into the DB
			updated = database.update(entry.TABLE_NAME, values, where,
					whereArgs);
		}
		return updated;
	}

	/**
	 * This method execute a query and return the result as an Iterator of
	 * LocalDataModel
	 * 
	 * @param where
	 *            The where clause
	 * @param whereArgs
	 *            The arguments for the where clause
	 * @return The Iterator over the result objects
	 */
	protected final Iterator<LocalDataModel> queryIterator(
			final String tableName, final String where,
			final String[] whereArgs, final LocalDataModel dataModel) {
		Iterator<LocalDataModel> resultIterator = null;
		// We execute the query
		if (database.isOpen()) {
			final Cursor cursor = database.query(tableName, null, where,
					whereArgs, null, null, null);
			resultIterator = new Iterator<LocalDataModel>() {

				public boolean hasNext() {
					boolean moreData = cursor.moveToNext();
					if (!moreData) {
						// We close the cursor if there are no data
						cursor.close();
					}
					return moreData;
				}

				public LocalDataModel next() {
					return dataModel.fromCursor(cursor);
				}

				public void remove() {
					// TODO Auto-generated method stub

				}

			};
		} else {
			// We create an empty iterator
			resultIterator = new Iterator<LocalDataModel>() {

				public boolean hasNext() {
					return false;
				}

				public LocalDataModel next() {
					return null;
				}

				public void remove() {

				}

			};
		}

		return resultIterator;
	}

	/**
	 * The standard version of query method. It returns the Cursor that should
	 * be managed by the caller object
	 * 
	 * @param where
	 *            The where clause
	 * @param whereArgs
	 *            The arguments for the where clause
	 * @return The Cursor implementations with the result
	 */
	public Cursor simpleQuery(final String where, final String[] whereArgs,
			String tableName) {
		Cursor cursor = null;
		// We execute the query
		if (database.isOpen()) {
			cursor = database.query(tableName, null, where, whereArgs, null,
					null, null);
		}
		return cursor;
	}

	/**
	 * The simpler version of query method. It returns the Cursor that should be
	 * managed by the caller object
	 * 
	 * @param where
	 *            The where clause
	 * @param whereArgs
	 *            The arguments for the where clause
	 * @return The Cursor implementations with the result
	 */
	public LocalDataCursor customQuery(final String where,
			final String[] whereArgs, String tableName, String groupBy, String having, String orderBy, String[] columns) {
		LocalDataCursor cursor = null;
		if (database.isOpen()) {
			cursor = (LocalDataCursor) database.query(tableName, columns, where, whereArgs, groupBy, having, orderBy);
		}
		return cursor;
	}
	
	protected String makePlaceholders(int len) {
	    if (len < 1) {
	        throw new RuntimeException("No placeholders");
	    } else {
	        StringBuilder sb = new StringBuilder(len * 2 - 1);
	        sb.append("?");
	        for (int i = 1; i < len; i++) {
	            sb.append(",?");
	        }
	        return sb.toString();
	    }
	}
	
	public void close() {
		if(this.database != null && this.database.isOpen()) {
			this.database.close();
		}
	}
}
