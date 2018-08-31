package com.karacraft.ribsncuts.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.karacraft.ribsncuts.model.Product;

import java.util.ArrayList;

public class ProductsDB
{
    /** Table Keys*/
    public static final String KEY_ROWID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CUTSOURCE = "cut_source";
    public static final String KEY_BESTFOR = "best_for";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICEPERKG = "price_per_kg";
    public static final String KEY_SLUG = "slug";
    public static final String KEY_CATEGORY = "category";
//    public static final String KEY_CREATEDATE = "created_at";
//    public static final String KEY_UPDATEDATE = "updated_at";

    /** Database Name (SQLITE)*/
    public static final String DATABASE_NAME = "ProductsDB";
    public static final String DATABASE_TABLE = "ProductsTable";
    public static final int DATABASE_VERSION = 3;

    /** Our own Sqlite Helper Class */
    private DBHelper ourHelper;
    /** Our Final Context */
    private final Context ourContext;
    /** Our Database */
    private SQLiteDatabase ourDatabase;

    /** Constructor */
    public ProductsDB(Context context)
    {
        ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION );
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            /** will only run , if there is no database*/

            /*
            * CREATE TABLE ProductTable (id INTEGER PRIMARY KEY AUTOINCREMENT )*/
             String sqlCode = "CREATE TABLE " + DATABASE_TABLE +
                     " (" +
                     KEY_ROWID + " INTEGER PRIMARY KEY, " +
                     KEY_TITLE + " TEXT NOT NULL, " +
                     KEY_CUTSOURCE + " TEXT NOT NULL, " +
                     KEY_BESTFOR + " TEXT NOT NULL, " +
                     KEY_DESCRIPTION + " TEXT NOT NULL, " +
                     KEY_IMAGE + " TEXT NOT NULL, " +
                     KEY_PRICEPERKG + " INTEGER , " +
                     KEY_SLUG + " TEXT NOT NULL, " +
                     KEY_CATEGORY + " TEXT NOT NULL); ";

             createTable(sqlCode,sqLiteDatabase);


        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
        {
            /** only called, when the database file is existed,
             * but the verison is lower then constructor*/
            //Save your Table - if you want
            //Drop the Table
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            //Recreate the Database with new Version
            onCreate(sqLiteDatabase);
        }

        public void createTable(String sql,SQLiteDatabase sqLiteDatabase)
        {
            sqLiteDatabase.execSQL(sql);
        }
    }


    public ProductsDB open() throws SQLException
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long createEntry(Integer rowid,
                            String title,
                            String cut_source,
                            String best_for,
                            String description,
                            String image,
                            String slug,
                            String category,
                            int price_per_kg)
    {
        //Key Value Pair
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,rowid);
        cv.put(KEY_TITLE,title);
        cv.put(KEY_CUTSOURCE,cut_source);
        cv.put(KEY_BESTFOR,best_for);
        cv.put(KEY_DESCRIPTION,description);
        cv.put(KEY_IMAGE,image);
        cv.put(KEY_SLUG,slug);
        cv.put(KEY_CATEGORY,category);
        cv.put(KEY_PRICEPERKG,price_per_kg);

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }


    public String readData()
    {
        String columns[] = new String[]{
                KEY_ROWID,KEY_TITLE,KEY_CUTSOURCE,
                KEY_BESTFOR,KEY_DESCRIPTION,KEY_IMAGE,
                KEY_SLUG,KEY_CATEGORY,KEY_PRICEPERKG
        };

        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,
                null,null,null,
                null, "Random()");

        String result = "";

        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iTitle = c.getColumnIndex(KEY_TITLE);
        int iCutSource = c.getColumnIndex(KEY_CUTSOURCE);
        int iBestFor = c.getColumnIndex(KEY_BESTFOR);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iImage = c.getColumnIndex(KEY_IMAGE);
        int iSlug = c.getColumnIndex(KEY_SLUG);
        int iCategory = c.getColumnIndex(KEY_CATEGORY);
        int iPricePerKg = c.getColumnIndex(KEY_PRICEPERKG);

        for( c.moveToFirst() ;  !c.isAfterLast(); c.moveToNext())
        {
            result = result + c.getInt(iRowID) + ": " +
                    c.getString(iTitle) + ": " +
                    c.getString(iCutSource) + ": " +
                    c.getString(iBestFor) + ": " +
                    c.getString(iDescription) + ": " +
                    c.getString(iImage) + ": " +
                    c.getString(iSlug) + ": " +
                    c.getString(iCategory) + ": " +
                    c.getInt(iPricePerKg) + "\n";
        }

        c.close();

        return result;
    }

    public ArrayList<Product> readDataInArrayList(String searchQuery)
    {

        ArrayList<Product> values = new ArrayList<Product>();
        Cursor c = null;

        String columns[] = new String[]{
                KEY_ROWID,KEY_TITLE,KEY_CUTSOURCE,
                KEY_BESTFOR,KEY_DESCRIPTION,KEY_IMAGE,
                KEY_SLUG,KEY_CATEGORY,KEY_PRICEPERKG
        };

        if(searchQuery.equals("All"))
        {
            c = ourDatabase.query(DATABASE_TABLE,columns,
                    null,null,null,
                    null,"Random()");
        }
        else
        {
            c = ourDatabase.query(DATABASE_TABLE,columns,
                    KEY_CATEGORY +" = '" + searchQuery + "'",null,null,
                    null,"Random()");
        }


        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iTitle = c.getColumnIndex(KEY_TITLE);
        int iCutSource = c.getColumnIndex(KEY_CUTSOURCE);
        int iBestFor = c.getColumnIndex(KEY_BESTFOR);
        int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
        int iImage = c.getColumnIndex(KEY_IMAGE);
        int iSlug = c.getColumnIndex(KEY_SLUG);
        int iCategory = c.getColumnIndex(KEY_CATEGORY);
        int iPricePerKg = c.getColumnIndex(KEY_PRICEPERKG);

        if(c != null)
        {
            for( c.moveToFirst() ;  !c.isAfterLast(); c.moveToNext())
            {

                Product p = new Product();
                p.setId(Integer.parseInt(c.getString(iRowID)));
                p.setTitle(c.getString(iTitle));
                p.setCutSource(c.getString(iCutSource));
                p.setBestFor(c.getString(iBestFor));
                p.setDescription(c.getString(iDescription));
                p.setImage(c.getString(iImage));
                p.setSlug(c.getString(iSlug));
                p.setCategory(c.getString(iCategory));
                p.setPrice(c.getInt(iPricePerKg));
                values.add(p);
            }
        }

        c.close();

        return values;
    }

    public long deleteEntry(String rowId)
    {
        return ourDatabase.delete(DATABASE_TABLE,
                KEY_ROWID + "=?", new String[]{rowId});
    }

    public long update(String rowId,
                       String title,
                       String cut_source,
                       String best_for,
                       String description,
                       String image,
                       String slug,
                       String category,
                       int price_per_kg)
    {
        //Key Value Pair
        ContentValues cv = new ContentValues();
        cv.put(KEY_TITLE,title);
        cv.put(KEY_CUTSOURCE,cut_source);
        cv.put(KEY_BESTFOR,best_for);
        cv.put(KEY_DESCRIPTION,description);
        cv.put(KEY_IMAGE,image);
        cv.put(KEY_SLUG,slug);
        cv.put(KEY_CATEGORY,category);
        cv.put(KEY_PRICEPERKG,price_per_kg);

        return ourDatabase.update(DATABASE_TABLE,
                cv,KEY_ROWID + "=?",
                new String[] {rowId});
    }
}
