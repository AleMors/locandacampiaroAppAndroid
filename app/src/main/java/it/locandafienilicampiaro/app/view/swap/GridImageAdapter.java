package it.locandafienilicampiaro.app.view.swap;

import it.locandafienilicampiaro.app.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GridImageAdapter extends BaseAdapter {

	public static final int DEFAULT_CELL_SIZE = 220; // default value to use for
														// image width and
														// height.

	private Context mContext;
	private TopicList mTopicList;
	private int mImageOffset = 0; // the index offset into the list of images
	private int mImageCount = -1; // -1 means that we display all images
	private int mNumTopics = 0; // the total number of topics in the topics
								// collection
	private int mCellWidth = DEFAULT_CELL_SIZE;
	private int mCellHeight = DEFAULT_CELL_SIZE;

	public GridImageAdapter(Context c, TopicList tlist, int imageOffset,
			int imageCount) {
		mContext = c;
		mImageOffset = imageOffset;
		mImageCount = imageCount;
		mTopicList = tlist;
		mNumTopics = (tlist == null) ? 0 : mTopicList.getNumTopics();
	}

	public GridImageAdapter(Context c, TopicList tlist, int imageOffset,
			int imageCount, int cellWidth, int cellHeight) {
		this(c, tlist, imageOffset, imageCount);
		mCellWidth = cellWidth;
		mCellHeight = cellHeight;
	}

	public int getCount() {
		// If we are on the last page and there are no more images, the count is
		// how many are being
		// displayed.
		int count = mImageCount;
		if (mImageOffset + mImageCount >= mNumTopics)
			count = mNumTopics - mImageOffset;
		return count;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return mImageOffset + position;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = null;
		TextView textView = null;
		View childView = null;
		int numTopics = mTopicList.getNumTopics();

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes

			int layoutId = R.layout.demo_pager_grid_item;
			LayoutInflater li = ((Activity) mContext).getLayoutInflater();
			childView = li.inflate(layoutId, null);
		} else {
			childView = convertView;
		}

		if (childView != null) {
			// Set the width and height of the child view.
			childView.setLayoutParams(new GridView.LayoutParams(mCellWidth,
					mCellHeight));

			int j = position + mImageOffset;
			if (j < 0)
				j = 0;
			if (j >= numTopics)
				j = numTopics - 1;

			imageView = (ImageView) childView.findViewById(R.id.image);
			if (imageView != null) {
				
				Resources res = mContext.getResources();
				int imagePadding = res
						.getDimensionPixelSize(R.dimen.grid_image_padding);

				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setBackgroundResource(R.color.background_grid1_cell);
				imageView.setPadding(imagePadding, imagePadding, imagePadding,
						imagePadding);
				imageView.setImageResource(mTopicList.getTopicImage(j));
				imageView.setTag(new Integer(j));

				// GESTIONE CROP IMMAGINI 16/01/2015
				//imageView.setImageBitmap(
					//    decodeSampledBitmapFromResource(res, R.id.image, 100, 100));
				// scaleImage(imageView);

				// imageView.setOnLongClickListener ((View.OnLongClickListener)
				// mContext);
				/*
				 * imageView.setOnLongClickListener(new OnLongClickListener() {
				 * public boolean onLongClick(View v) { showTopic ((Integer)
				 * v.getTag ()); return true; } });
				 */

			}
			textView = (TextView) childView.findViewById(R.id.title);
			if (textView != null) {
				textView.setText(mTopicList.getTopicTitle(j));
				textView.setTag(new Integer(j));
				
				textView.setTypeface(
						Typeface.createFromAsset(mContext.getAssets(),"fonts/florence.ttf"));
				/*
				 * textView.setOnLongClickListener(new OnLongClickListener() {
				 * public boolean onLongClick(View v) { showTopic ((Integer)
				 * v.getTag ()); return true; } });
				 */
			}
		}
		return childView;
	}

	private void scaleImage(ImageView view) {
		Drawable drawing = view.getDrawable();
		if (drawing == null) {
			return;
		}
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int bounding_x = 400;// ((View)view.getParent()).getWidth();//EXPECTED
								// WIDTH
		int bounding_y = 425;// ((View)view.getParent()).getHeight();//EXPECTED
								// HEIGHT

		int parent_x = ((View) view.getParent()).getLayoutParams().width;// EXPECTED
																			// WIDTH
		int parent_y = ((View) view.getParent()).getLayoutParams().height;

		bounding_x = parent_x;
		bounding_y = parent_y;

		float xScale = ((float) bounding_x) / width;
		float yScale = ((float) bounding_y) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(xScale, yScale);

		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		width = scaledBitmap.getWidth();
		height = scaledBitmap.getHeight();
		BitmapDrawable result = new BitmapDrawable(mContext.getResources(),
				scaledBitmap);

		view.setImageDrawable(result);

		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view
				.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}

	/**
	 * showTopic - start a GridImageActivity object to display the nth topic.
	 * 
	 * @param index
	 *            int - the index number of the topic to display
	 */

	public void showTopic(int index) {

		Toast.makeText(mContext, "" + index, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(mContext.getApplicationContext(), null);
		intent.putExtra("index", index);
		mContext.startActivity(intent);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}
}
