package bev.and.owe;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PhrazeDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "phrazedatabase.db";
	
	public static final int DATABASE_VERSION = 1;

	public PhrazeDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		PhrazeTable.onCreate(db);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		PhrazeTable.onUpgrade(db, oldVersion, newVersion);
	}
	
	
	
}