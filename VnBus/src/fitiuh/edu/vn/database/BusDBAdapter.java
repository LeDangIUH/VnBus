package fitiuh.edu.vn.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BusDBAdapter {
	
	// For logging:
	public static final String TAG = "BusDBAdapter";
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "BusDB";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 2;	
	
	// Context of application who uses us.
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public BusDBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
		
	// Open the database connection.
	public BusDBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
			
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	//create table favourite 
	public static final String DATABASE_TABLE2="FavouriteBus";
		
	public static final String KEY_BUSNUMFavour="_id";
	public static final String KEY_BUSNAMEFavour = "TenTuyen";
		
	public static final int COL_BUSNUMFavour = 0;
	public static final int COL_BUSNAMEFavour = 1;
	public static final String[] ALL_KEYSFavour = new String[] {KEY_BUSNUMFavour, KEY_BUSNAMEFavour};

	private static final String DATABASE_CREATE_SQLFavour =
			"create table " + DATABASE_TABLE2 + " ("
			+ KEY_BUSNUMFavour + " integer primary key, "
			+ KEY_BUSNAMEFavour + " string not null "
			+ ");";
	// Add a new set of values to the database.
	public long insertRowFavour(int _id, String TenTuyen) {
					
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_BUSNUMFavour,_id);
		initialValues.put(KEY_BUSNAMEFavour, TenTuyen);	
		// Insert it into the database.
		return db.insert(DATABASE_TABLE2, null, initialValues);
	}
	
	//get row favourite
	public Cursor getAllRowsFavour() {
			
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE2, ALL_KEYSFavour, 
									where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	//create table maps co-ordinate
	public static final String DATABASE_TABLE3="OrdinateBus";
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
		
	public static final String KEY_BUSNUMORDI="MaTuyen";
	public static final String KEY_TYPE="Chieu";
	public static final String KEY_LAT="ViDo";
	public static final String KEY_LNG="KinhDo";
	public static final String KEY_ADD="DiaChi";
		
	public static final int COL_BUSNUMORDI=1;
	public static final int COL_TYPE=2;
	public static final int COL_LAT=3;
	public static final int COL_LNG=4;
	public static final int COL_EDD=5;
	
	public static final String[] ALL_KEYSOrdi = new String[] {KEY_ROWID,KEY_BUSNUMORDI,KEY_TYPE,KEY_LAT,KEY_LNG,KEY_ADD};
	
	private static final String DATABASE_CREATE_SQLOrdi =
			"create table " + DATABASE_TABLE3 + " ("
			+ KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_BUSNUMORDI + " integer not null, "
			+ KEY_TYPE + " string not null, "
			+ KEY_LAT + " string not null, "
			+ KEY_LNG + " string not null, "
			+ KEY_ADD + " string not null "
			+ ");";
	
	public long insertRowOrdi(int MaTuyen,String Chieu,String ViDo, String KinhDo, String DiaChi) {
		
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_BUSNUMORDI,MaTuyen);
		initialValues.put(KEY_TYPE, Chieu);
		initialValues.put(KEY_LAT,ViDo);
		initialValues.put(KEY_LNG, KinhDo);
		initialValues.put(KEY_ADD, DiaChi);
		
		// Insert it into the database.
		return db.insert(DATABASE_TABLE3, null, initialValues);
	}
		
	public Cursor getAllOrdi() {
			
		String where = null;
		return db.query(true, DATABASE_TABLE3, ALL_KEYSOrdi, 
								where, null, null, null, null, null);
			//if (c != null) {
				//c.moveToFirst();
			//}
			//return c;
	}
	
	public Cursor getAllRowsOrdi(int Num,String type) {
		
		String where = KEY_BUSNUMORDI+"="+Num+" AND "+KEY_TYPE+" LIKE "+"'"+type+"'";
		return db.query(true, DATABASE_TABLE3, ALL_KEYSOrdi, 
								where, null, null, null, null, null);
		//if (c != null) {
			//c.moveToFirst();
		//}
		//return c;
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowOrdi(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE3, where, null) != 0;
	}
			
	//delete all data in the database
	public void deleteAllOrdi() {
		Cursor c = getAllOrdi();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRowOrdi(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	//Create dabase BusInformation
	public static final String DATABASE_TABLE = "BusInformation";
	
	public static final String KEY_BUSNUMBER="_id";
	public static final String KEY_BUSNAME = "TenTuyen";
	public static final String KEY_BUSOUTJOURNEY = "LuotDi";
	public static final String KEY_BUSHOJOURNEY="LuotVe";
	public static final String KEY_BUSTACTIVITYTYPE="LoaiHinhHoatDong";
	public static final String KEY_BUSCOOLIE="CuLi";
	public static final String KEY_BUSCOUTTRIP="SoChuyen";
	public static final String KEY_BUSTIMETRIP="ThoiGianChuyen";
	public static final String KEY_BUSINTERMITTER="GianCach";
	public static final String KEY_BUSSTARTEND="ThoiGianHoatDong";
	public static final String KEY_BUSMONEY="GiaVe";
	public static final String KEY_BUSADMINISTRATE="DonViDamNhan";
	public static final String KEY_FORMBUS="LoaiXe";
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_BUSNUMBER=0;
	public static final int COL_BUSNAME= 1;
	public static final int COL_BUSOUTJOURNEY=2;
	public static final int COL_BUSHOJOURNEY=3;
	public static final int COL_BUSTACTIVITYTYPE=4;
	public static final int COL_BUSCOOLIE=5;
	public static final int COL_BUSCOUTTRIP=6;
	public static final int COL_BUSTIMETRIP=7;
	public static final int COL_BUSINTERMITTER=8;
	public static final int COL_BUSSTARTEND=9;
	public static final int COL_BUSMONEY=10;
	public static final int COL_BUSADMINISTRATE=11;
	public static final int COL_FORMBUS=12;
	
	public static final String[] ALL_KEYS = new String[] {KEY_BUSNUMBER, KEY_BUSNAME
		,KEY_BUSOUTJOURNEY,KEY_BUSHOJOURNEY
		,KEY_BUSTACTIVITYTYPE,KEY_BUSCOOLIE,KEY_BUSCOUTTRIP
		,KEY_BUSTIMETRIP,KEY_BUSINTERMITTER,KEY_BUSSTARTEND
		,KEY_BUSMONEY,KEY_BUSADMINISTRATE,KEY_FORMBUS};
	
	private static final String DATABASE_CREATE_SQL =
		"create table " + DATABASE_TABLE + " ("
		// + KEY_{...} + " {type} not null"
		//	- Key is the column name you created above.
		//	- {type} is one of: text, integer, real, blob
		//		(http://www.sqlite.org/datatype3.html)
		//  - "not null" means it is a required field (must be given a value).
		// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
		+ KEY_BUSNUMBER + " integer primary key, "
		+ KEY_BUSNAME + " string not null, "
		+ KEY_BUSOUTJOURNEY + " string not null, "
		+ KEY_BUSHOJOURNEY + " string not null, "
		+ KEY_BUSTACTIVITYTYPE + " string not null, "
		+ KEY_BUSCOOLIE + " string not null, "
		+ KEY_BUSCOUTTRIP + " string not null, "
		+ KEY_BUSTIMETRIP + " string not null, "
		+ KEY_BUSINTERMITTER + " string not null, "
		+ KEY_BUSSTARTEND + " string not null, "
		+ KEY_BUSMONEY + " integer not null, "
		+ KEY_BUSADMINISTRATE + " string not null, "
		+ KEY_FORMBUS +" string not null"
		+ ");";
	
	// Add a new set of values to the database.
	public long insertRow(int _id, String TenTuyen, String LuotDi, String LuotVe, String LoaiHinhHoatDong, String CuLi,String SoChuyen,String ThoiGianChuyen,String GianCach,String ThoiGianHoatDong,int GiaVe,String DonViDamNhan, String Loaixe) {
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_BUSNUMBER,_id);
		initialValues.put(KEY_BUSNAME, TenTuyen);
		initialValues.put(KEY_BUSOUTJOURNEY,LuotDi);
		initialValues.put(KEY_BUSHOJOURNEY, LuotVe);
		initialValues.put(KEY_BUSTACTIVITYTYPE, LoaiHinhHoatDong);
		initialValues.put(KEY_BUSCOOLIE, CuLi);
		initialValues.put(KEY_BUSCOUTTRIP, SoChuyen);
		initialValues.put(KEY_BUSTIMETRIP, ThoiGianChuyen);
		initialValues.put(KEY_BUSINTERMITTER, GianCach);
		initialValues.put(KEY_BUSSTARTEND, ThoiGianHoatDong);
		initialValues.put(KEY_BUSMONEY, GiaVe);
		initialValues.put(KEY_BUSADMINISTRATE, DonViDamNhan);
		initialValues.put(KEY_FORMBUS, Loaixe);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(long rowId) {
		String where = KEY_BUSNUMBER + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	//delete all data in the database
	public void deleteAll() {
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_BUSNUMBER);
		if (c.moveToFirst()) {
			do {
				deleteRow(c.getLong((int) rowId));				
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows() {
				
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
									where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Get a specific row (by rowId)
	public Cursor getRow(long rowId) {
		String where = KEY_BUSNUMBER + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
								where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	// Change an existing row to be equal to new data.
	public boolean updateRow(long rowId,int _id, String TenTuyen, String LuotDi
			, String LuotVe, String LoaiHinhHoatDong, String CuLi
			,String SoChuyen,String ThoiGianChuyen,String GianCach
			,String ThoiGianHoatDong,int GiaVe,String DonViDamNhan, String LoaiXe) {
			
		String where = KEY_BUSNUMBER + "=" + rowId;
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_BUSNUMBER,_id);
		newValues.put(KEY_BUSNAME, TenTuyen);
		newValues.put(KEY_BUSOUTJOURNEY,LuotDi);
		newValues.put(KEY_BUSHOJOURNEY, LuotVe);
		newValues.put(KEY_BUSTACTIVITYTYPE, LoaiHinhHoatDong);
		newValues.put(KEY_BUSCOOLIE, CuLi);
		newValues.put(KEY_BUSCOUTTRIP, SoChuyen);
		newValues.put(KEY_BUSTIMETRIP, ThoiGianChuyen);
		newValues.put(KEY_BUSINTERMITTER, GianCach);
		newValues.put(KEY_BUSSTARTEND, ThoiGianHoatDong);
		newValues.put(KEY_BUSMONEY, GiaVe);
		newValues.put(KEY_BUSADMINISTRATE, DonViDamNhan);
		newValues.put(KEY_FORMBUS, LoaiXe);
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);
			_db.execSQL(DATABASE_CREATE_SQLFavour);
			_db.execSQL(DATABASE_CREATE_SQLOrdi);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}
