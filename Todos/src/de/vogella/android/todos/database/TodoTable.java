package de.vogella.android.todos.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TodoTable {

  // Database table
  public static final String TABLE_NAME = "todo";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_CATEGORY = "category";
  public static final String COLUMN_BRAND = "Brand";
  public static final String COLUMN_TYPE = "Type";
  public static final String COLUMN_WRAPPER = "Wrapper";
  public static final String COLUMN_VITOLA = "Vitola";
  public static final String COLUMN_QUANTITY = "Quantity";
  

  // Database creation SQL statement
  private static final String DATABASE_CREATE = "create table " 
      + TABLE_NAME
      + "(" 
      + COLUMN_ID + " integer primary key autoincrement, " 
      + COLUMN_CATEGORY + " text, " 
      + COLUMN_BRAND + " text not null," 
      + COLUMN_TYPE + " text not null," 
      + COLUMN_WRAPPER + " text," 
      + COLUMN_VITOLA + " text," 
      + COLUMN_QUANTITY + " Integer" 
      + ");";

  public static void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
    
	//Following code block will provide sample data
  //It should be deleted or commented out upon completion
  /**
  ContentValues values = new ContentValues();

  values.put(COLUMN_BRAND, "Perdomo");
  values.put(COLUMN_TYPE, "Lot 23");
  values.put(COLUMN_WRAPPER, "Connecticut");
  values.put(COLUMN_VITOLA, "Robusto");
  values.put(COLUMN_QUANTITY, 5);
  database.insert(TABLE_NAME, null, values);
  
  values.put(COLUMN_BRAND, "Alec Bradley");
  values.put(COLUMN_TYPE, "Prensado");
  values.put(COLUMN_WRAPPER, "Natural");
  values.put(COLUMN_VITOLA, "Churchill");
  values.put(COLUMN_QUANTITY, 5);
  database.insert(TABLE_NAME, null, values);
  
  values.put(COLUMN_BRAND, "Perdomo");
  values.put(COLUMN_TYPE, "Grand Cru 2004");
  values.put(COLUMN_WRAPPER, "Natural");
  values.put(COLUMN_VITOLA, "Robusto");
  values.put(COLUMN_QUANTITY, 9);
  database.insert(TABLE_NAME, null, values);
  
  values.put(COLUMN_BRAND, "Perdomo");
  values.put(COLUMN_TYPE, "Slow-Agedb 826");
  values.put(COLUMN_WRAPPER, "Maduro");
  values.put(COLUMN_VITOLA, "Glorioso");
  values.put(COLUMN_QUANTITY, 15);
  database.insert(TABLE_NAME, null, values);
  
  values.put(COLUMN_BRAND, "Oliva");
  values.put(COLUMN_TYPE, "Serie O");
  values.put(COLUMN_WRAPPER, "Natural");
  values.put(COLUMN_VITOLA, "Toro");
  values.put(COLUMN_QUANTITY, 3);
  database.insert(TABLE_NAME, null, values); **/
  }

  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
      int newVersion) {
    Log.w(TodoTable.class.getName(), "Upgrading database from version "
        + oldVersion + " to " + newVersion
        + ", which will destroy all old data");
    database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(database);
  }
}
