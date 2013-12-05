package bev.and.owe;

import java.util.Arrays;
import java.util.HashSet;

import bev.and.owe.PhrazeTable;
import bev.and.owe.PhrazeDatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class PhrazeContentProvider extends ContentProvider {
	
	/** Our database to be initialized on onCreate **/
	private PhrazeDatabaseHelper database;

	/** Values for the URIMatcher. */
	private static final int PHRAZE_ID = 1;
	private static final int PHRAZE_SEEN = 2;
	private static final int PHRAZE_HAS_BEEN_SEEN = 1;
	private static final int PHRAZE_HAS_NOT_BEEN_SEEN = 0;
	
	/** The authority for this content provider. */
	private static final String AUTHORITY = "bev.and.owe.contentprovider";
	
	/** The database table to read from and write to, and also the root path for use in the URI matcher.
	 * This is essentially a label to a two-dimensional array in the database filled with rows of phrazes
	 * whose columns contain phraze data. */
	private static final String BASE_PATH = "joke_table";
	
	/** This provider's content location. Used by accessing applications to
	 * interact with this provider. */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	
	/** Matches content URIs requested by accessing applications with possible
	 * expected content URI formats to take specific actions in this provider. */
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/phrazes/#", PHRAZE_ID);
	}
	
	@Override
	public boolean onCreate() {
		database = new PhrazeDatabaseHelper(getContext(), PhrazeDatabaseHelper.DATABASE_NAME, null, PhrazeDatabaseHelper.DATABASE_VERSION);
		return true;
	}
	
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		/** Use a helper class to perform a query for us. */
		 SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		 
		 /** Make sure the projection is proper before querying. */
		 checkColumns(projection);
		
	    /** Set up helper to query our jokes table. */
		queryBuilder.setTables(PhrazeTable.DATABASE_TABLE_PHRAZE);
		
		SQLiteDatabase db = this.database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, null, null, null, PhrazeTable.PHRAZE_KEY_TIMES_SEEN);

		/** Set the cursor to automatically alert listeners for content/view refreshing. */
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		 
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		/** Open the database for writing. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		
		/** Will contain the id of the inserted Phraze **/
		long id = 0;
		
		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURIMatcher.match(uri);
		
		switch(uriType) {
		/** Expects a Phraze ID, but we will do nothing with the passed-in ID since
		 * the database will automatically handle ID assignment and incrementation.
		 * IMPORTANT: phraze ID cannot be set to -1 in passed-in URI; -1 is not interpreted
		 * as a numerical value by the URIMatcher. */
		case PHRAZE_ID:
			/** Perform the database insert, placing the joke at the bottom of the table. */
			id = sqlDB.insert(PhrazeTable.DATABASE_TABLE_PHRAZE, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	    
		/** Alert any watchers of an underlying data change for content/view refreshing. */
		getContext().getContentResolver().notifyChange(uri, null);
		
		return Uri.parse(BASE_PATH + "/" + id);
	}


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		int numDelRows = 0;
		
		int uriType = sURIMatcher.match(uri);

	    switch(uriType) {
	    case PHRAZE_ID:
			 String id = uri.getLastPathSegment();
			 String whereClause = PhrazeTable.PHRAZE_KEY_ID + "=" + id;
			 numDelRows = sqlDB.delete(PhrazeTable.DATABASE_TABLE_PHRAZE, whereClause, null);
             break;
        default:
        	throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
		
		return 0;
	}
	
	
	/**
	 * Updates a row in the phraze table. Given a specific URI containing a phraze ID and the
	 * new phraze values (I think it's going to be the seen/answered), 
	 * updates the values in the row with the matching ID in the table. 
	 * Since IDs are automatically incremented on insertion, this will only ever update
	 * a single phraze in the phraze table.<br><br>
	 * */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();

		int numUpdateRows = 0;

		int uriType = sURIMatcher.match(uri);

		switch(uriType) {
		case PHRAZE_ID:
			String id = uri.getLastPathSegment();
			String whereClause = PhrazeTable.PHRAZE_KEY_ID + "=" + id;
			
			numUpdateRows = sqlDB.update(PhrazeTable.DATABASE_TABLE_PHRAZE, values, whereClause, null);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		return numUpdateRows;
	}
	
	
	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	/**
	 * Verifies the correct set of columns to return data from when performing a query.
	 * 
	 * @param projection
	 * 						The set of columns about to be queried.
	 */
	private void checkColumns(String[] projection) {
		String[] available = { PhrazeTable.PHRAZE_KEY_ID, PhrazeTable.PHRAZE_KEY_TEXT, PhrazeTable.PHRAZE_KEY_ANSWER, PhrazeTable.PHRAZE_KEY_TIMES_SEEN, PhrazeTable.PHRAZE_KEY_COMPLETED };
		
		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			
			if(!availableColumns.containsAll(requestedColumns))	{
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
	
}
