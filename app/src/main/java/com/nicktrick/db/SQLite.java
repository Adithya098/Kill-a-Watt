package com.nicktrick.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLite extends SQLiteOpenHelper {

	Context context;

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "smartmeter"; // Databse Name

	private static final String USER_REG = "register"; // TableName

	/*--------------Table Columns-----------*/
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_METER = "meterid";
	private static final String KEY_THRESH = "threshval";


	/*--------------Table Columns Array-----------*/
	public static final String REG_COLUMN[] = {KEY_USERNAME,KEY_PASSWORD, KEY_METER,KEY_THRESH};

	public SQLite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String create_table = "CREATE TABLE " + USER_REG + "("
				+ KEY_USERNAME + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_METER
				+ " TEXT," + KEY_THRESH + " TEXT)";

		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + USER_REG);
		this.onCreate(db);
	}

	/*-----------------User Information register----------------*/
	public void user_register(String name, String pass, String meter, String threshval) {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cvalues = new ContentValues();

			cvalues.put(KEY_USERNAME, name);
			cvalues.put(KEY_PASSWORD, pass);
			cvalues.put(KEY_METER, meter);
			cvalues.put(KEY_THRESH, threshval);


			db.insert(USER_REG, null, cvalues);
			db.close();

			Toast.makeText(context, "User Registered Successfully !", Toast.LENGTH_SHORT)
					.show();

		} catch (Exception e) {
			Toast.makeText(context, "Check" + e, Toast.LENGTH_SHORT).show();
		}
	}

	/*---------User Login Check-----Query to retrieve------*/

	public String user_detail(String name,String password) {
		/*ArrayList list = new ArrayList();*/
		String resp="";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.query(USER_REG, REG_COLUMN, KEY_METER
					+ "=?", new String[] { name }, null, null, null, null);
			if (cursor != null) {
				
				
					if (cursor.moveToFirst()) {
						do {
							String pass = cursor.getString(1);
							if(pass.equals(password)){
								resp = "ok";
							}else{
								resp = "no";
							}
						} while (cursor.moveToNext());
					}
					cursor.close();

			
			}else{
				resp = "No";
			}
		} catch (Exception e) {

		}
		return resp;
	}
	
	/*-------Delete Query------------*/
	
	public boolean deleteTitle(String name) 
	{
		boolean b=false;
		try{
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(USER_REG, KEY_USERNAME +"= ?", new String[]{name});

		}catch(Exception e){
			
		}
		return b;
	}
}
