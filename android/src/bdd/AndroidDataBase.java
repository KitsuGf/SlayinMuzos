package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

/**
 * @Author Kitsu ( Juan Miguel )
 *
 * Class what define the databases implementing the interface
 * from core to set the methods
 *
 */
public class AndroidDataBase implements GameDataBase {

    private BDOpenHelper openHelper;

    public AndroidDataBase(Context c){
        openHelper=new BDOpenHelper(c,1);
    }



    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * Sqlite Query to save the score in the database
     *
     */
    @Override
    public void saveScore(int newScore) {
        //Create a database OpenHelper
        SQLiteDatabase db=openHelper.getWritableDatabase();
        //Create a cursor with the table
        Cursor c=db.query("userScore", null,null,null, null,null,null);
        //Create a contentvalues
        ContentValues cv=new ContentValues();
        //put the row and the new score in the content values.
        cv.put("score", newScore);
        //insert the new score in the table
        db.insert("userScore",null,cv);
        //close the cursor
        c.close();
        //close the db
        db.close();
    }

    /**
     * @Author Kitsu ( Juan Miguel )
     *
     * Sqlite Query to show all the data from score in a list.
     *
     *
     */
    public ArrayList allScore() {
        //Create a database OpenHelper
        SQLiteDatabase db=openHelper.getReadableDatabase();
        //Create an arraylist to get the user score added to this list
        ArrayList<String> array_list = new ArrayList<String>();
        //Create a cursor with the table
        Cursor res = db.rawQuery( "select score from userScore", null );
        //cursor is move to first.
        res.moveToFirst();
        //loop to add info until get the last one
        while(res.isAfterLast() == false) {
            //add the info to the array list
            array_list.add(res.getString(res.getColumnIndex("score")));
            //move to next
            res.moveToNext();
        }
        //return the arraylist
        return array_list;
    }

}
