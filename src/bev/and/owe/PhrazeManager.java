package bev.and.owe;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class PhrazeManager implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

	public static final String UNSEEN = "0";
	public static final String SEEN = "1";
	private String auth = "bev.and.owe.contentprovider";
	private String base = "phraze_table";
	private Context context;
	
	public PhrazeManager(Context context) {
		this.context = context;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String auth = "beva.and.owe.contentprovider";
		String base = "phraze_table";
		String[] projection = {PhrazeTable.PHRAZE_KEY_ID, PhrazeTable.PHRAZE_KEY_TEXT, PhrazeTable.PHRAZE_KEY_ANSWER, PhrazeTable.PHRAZE_KEY_SEEN};
        
		Uri uri = Uri.parse("content://" + auth + "/" + base + "/" + "seen/" +  UNSEEN);
		CursorLoader cursor = new CursorLoader(context, uri, projection, null, projection, null);

		return cursor;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}