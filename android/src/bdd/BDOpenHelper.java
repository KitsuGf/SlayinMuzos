package bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @Author Kitsu ( Juan Miguel )
 *
 * OpenHelper, this class create the database in Android
 *
 */
public class BDOpenHelper extends SQLiteOpenHelper {

    //Constructor to set the context and the name of the Database
    public BDOpenHelper(Context c, int version){
        super(c,"baseDatosMuzos",null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a query to insert the table with the value will be in
        db.execSQL("create table userScore(score int(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
