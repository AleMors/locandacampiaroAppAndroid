package it.locandafienilicampiaro.app.sqlite;

import android.content.ContentValues;
import android.database.Cursor;

public class ReviewLocalModel extends LocalDataModel {

	private Long id;
	private String customer;
	private String review;

	public ReviewLocalModel(Long id, String customerName, String revirew) {

		super(id);
		this.customer = customerName;
		this.review = revirew;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String idGame) {
		this.customer = idGame;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String dateTime) {
		this.review = dateTime;
	}


	@Override
	public ContentValues asValues() {

		final ContentValues values = new ContentValues();
		values.put(ReviewContract.ReviewEntry._ID, this.id);
		values.put(ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_CUSTOMER, this.customer);
		values.put(ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_TEXT,
				this.review);

		return values;
	}

	public static ReviewLocalModel create(Long id, String custName, String review) {
		
		return new ReviewLocalModel(id, custName, review);
	}

	@Override
	public LocalDataModel fromCursor(Cursor cursor) {
		
		Long id = cursor.getLong(cursor.getColumnIndex(ReviewContract.ReviewEntry._ID));
		String custName = cursor.getString(cursor
				.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_CUSTOMER));
		String review = cursor.getString(cursor
				.getColumnIndex(ReviewContract.ReviewEntry.COLUMN_NAME_REVIEW_TEXT));
		LocalDataModel item = this.create(id, custName, review);
		return item;
	}

}
