package bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class AndroidDataBase implements GameDataBase {

    private BDOpenHelper openHelper;

    public AndroidDataBase(Context c){
        openHelper=new BDOpenHelper(c,1);
    }

    @Override
    public int cargar() {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor c = db.query("userScore", null,null,null,null,null,null);
        if(c.moveToFirst()){
            return c.getInt(c.getColumnIndex("score"));
        }else{
            return 0;
        }
    }

    @Override
    public void saveScore(int newScore) {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        Cursor c=db.query("userScore", null,null,null, null,null,null);

        ContentValues cv=new ContentValues();
        cv.put("score", newScore);

        if(c.moveToFirst()){
            db.update("userScore",cv,null,
                    null);
        }else{
            db.insert("userScore",null,cv);
        }
        c.close();
        db.close();
    }


    public ArrayList allScore() {
        SQLiteDatabase db=openHelper.getReadableDatabase();
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = db.rawQuery( "select * from userScore", null );
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("score")));
            res.moveToNext();
        }
        return array_list;
    }

}
