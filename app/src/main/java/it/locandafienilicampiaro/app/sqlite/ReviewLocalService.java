/**
 * Classe per la gestione delle recensioni.
 */
package it.locandafienilicampiaro.app.sqlite;

import android.app.Application;


/**
 * @author 
 */
public class ReviewLocalService {

	private LocalDatabaseHelper localHelper;

	
	protected void setHelper(LocalDatabaseHelper helper) {
		this.localHelper = helper;
	}
	
	protected LocalDatabaseHelper getHelper() {
		return this.localHelper;
	}
	
	protected LocalDataCursor customQuery(final String where,
			final String[] whereArgs, String tableName, String groupBy,
			String having, String orderBy, String[] columns) {
		
		return this.localHelper.customQuery(where, whereArgs, tableName, 
				null, null, null, null);
	}
	
	public void openHelper() {
		if(this.localHelper != null) {
			this.localHelper.open();
		}
	}
	
	public void closeHelper() {
		if(this.localHelper != null) {
			this.localHelper.close();
		}
	}
	
	public ReviewLocalService(Application context) {
		init(context);
	}

	private synchronized void init(Application context) {
		setHelper(new ReviewLocalHelper(context));
	}
	
	/**
	 * Metodo per l'inserimento di una nuova recensione.
	 * @return
	 */
	public boolean insertAlert(ReviewLocalModel alertModel, AbstractEntry entry) {
		long insertValue = localHelper.insert(alertModel, entry);
		return (insertValue == -1) ? false : true;
	}
}
