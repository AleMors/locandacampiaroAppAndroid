/**
 * 
 */
package it.locandafienilicampiaro.app.sqlite;

/**
 * @author 
 *
 */
public interface IDatabaseMetadata {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "lfc.db";
	public static final String DATABASE_DUMP_VERSION_NAME = "lfc_v1.db";
	
	public static final String TABLE_NAME_REVIEWS = "tb_reviews";
}
