package bev.and.owe;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class PhrazeTable {
	
	/** Phraze table in the database. **/
	public static final String DATABASE_TABLE_PHRAZE = "phraze_table";
	
	/** Phraze table column named and IDs for database access **/
	public static final String PHRAZE_KEY_ID = "_id";
	public static final int PHRAZE_COL_ID = 0;
	
	public static final String PHRAZE_KEY_TEXT = "phraze_text";
	public static final int PHRAZE_COL_TEXT = PHRAZE_COL_ID + 1;

	public static final String PHRAZE_KEY_ANSWER = "phraze_answer";
	public static final int PHRAZE_COL_ANSWER = PHRAZE_COL_ID + 2;
	
	public static final String PHRAZE_KEY_TIMES_SEEN = "phraze_seen";
	public static final int PHRAZE_COL_TIMES_SEEN = PHRAZE_COL_ID + 3;
	
	public static final String PHRAZE_KEY_COMPLETED = "phraze_completed";
	public static final int PHRAZE_COL_COMPLETED = PHRAZE_COL_ID + 4;

	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_PHRAZE + " (" +
			PHRAZE_KEY_ID + " integer primary key autoincrement, " +
			PHRAZE_KEY_TEXT + " text not null, " +
			PHRAZE_KEY_ANSWER + " text not null, " +
			PHRAZE_KEY_TIMES_SEEN + " integer default 0," +
			PHRAZE_KEY_COMPLETED + "integer default 0);";
	
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_PHRAZE;
	
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		ContentValues cv = new ContentValues();
		PhrazesAndAnswers paa = new PhrazesAndAnswers();
		ArrayList<PhrazePack> phrazePacks = paa.getAllPhrazes();
		Iterator<PhrazePack> iter = phrazePacks.iterator();
		while (iter.hasNext()) {
			PhrazePack pp = iter.next();
			cv.put(PHRAZE_KEY_TEXT, pp.getPhraze());
			cv.put(PHRAZE_KEY_ANSWER, pp.getAnswer());
			database.insert(DATABASE_TABLE_PHRAZE, null, cv);
			cv.clear();
		}
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(PhrazeTable.class.getName(), "You're upgrading the database dude");
		Log.w(PhrazeTable.class.getName(), "Upgrading from " + oldVersion + " to " + newVersion);
		database.setVersion(newVersion);
		database.execSQL(DATABASE_DROP);	
		onCreate(database);
	}
			
}

