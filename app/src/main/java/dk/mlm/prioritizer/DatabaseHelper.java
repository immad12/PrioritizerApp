package dk.mlm.prioritizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ml on 31/07/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PrioritizerDB";

    private static final String LIST_TABLE = "Lists";
    private static final String LIST_TABLE_ID = "ListID";
    private static final String LIST_TABLE_NAME = "ListName";
    private static final String TASK_TABLE = "Tasks";

    private static final String TASK_TABLE_ID = "TaskID";
    private static final String TASK_TABLE_NAME = "TaskName";
    private static final String TASK_TABLE_LIST = "TaskList";
    private static final String TASK_TABLE_PRIORITY = "TaskPriority";

    private static final String INT_KEY_INCREMENT = "INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String DATABASE_CREATE_LIST_TABLE = "CREATE TABLE " + LIST_TABLE + " (" + LIST_TABLE_ID + " " + INT_KEY_INCREMENT + ", " + LIST_TABLE_NAME + " TEXT)";
    private static final String DATABASE_CREATE_TASK_TABLE = "CREATE TABLE " + TASK_TABLE + " (" + TASK_TABLE_ID + " " + INT_KEY_INCREMENT + ", " + TASK_TABLE_NAME + " TEXT, " + TASK_TABLE_LIST + " TEXT, " + TASK_TABLE_PRIORITY + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_LIST_TABLE);
        db.execSQL(DATABASE_CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE);
        onCreate(db);
    }

    public ParentItem insertList(ParentItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(LIST_TABLE_NAME, item.getName());

        long id = db.insert(LIST_TABLE, null, contentValues);
        item.setId((int) id);

        db.close();
        Log.d("Insert list ", item.toString());
        return item;
    }

    public ChildItem insertTask(ChildItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_TABLE_NAME, item.getName());
        contentValues.put(TASK_TABLE_LIST, item.getListName());
        contentValues.put(TASK_TABLE_PRIORITY, item.getPriority());

        long id = db.insert(TASK_TABLE, null, contentValues);
        item.setId((int) id);

        db.close();
        Log.d("Insert task: ", item.toString());
        return item;
    }

    public void updateList(ParentItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LIST_TABLE_NAME, item.getName());

        db.update(LIST_TABLE, values, LIST_TABLE_ID + " = ?", new String[]{Integer.toString(item.getId())});

        db.close();
        Log.d("Update list: ", item.toString());
    }

    public void updateTask(ChildItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TASK_TABLE_NAME, item.getName());
        values.put(TASK_TABLE_LIST, item.getListName());
        values.put(TASK_TABLE_PRIORITY, item.getPriority());

        db.update(TASK_TABLE, values, TASK_TABLE_ID + " = ?", new String[]{Integer.toString(item.getId())});

        db.close();
        Log.d("Update task: ", item.toString());
    }

    public List<ParentItem> getAllLists() {
        List<ParentItem> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + LIST_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ParentItem item = new ParentItem();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setChildItems(getAllTasks(cursor.getString(1)));

                result.add(item);
            } while (cursor.moveToNext());
        }

        Log.d("Get all lists: ", result.toString());
        return result;
    }

    public List<ChildItem> getAllTasks(String listName) {
        List<ChildItem> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TASK_TABLE + " WHERE " + TASK_TABLE_LIST + " = '" + listName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    ChildItem item = new ChildItem();
                    item.setId(Integer.parseInt(cursor.getString(0)));
                    item.setName(cursor.getString(1));
                    item.setListName(listName);
                    item.setPriority(Integer.parseInt(cursor.getString(3)));

                    result.add(item);
                } while (cursor.moveToNext());
            }
        }

        Log.d("Get all tasks: ", result.toString());
        return result;
    }

    public ParentItem getList(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(LIST_TABLE, new String[]{LIST_TABLE_ID, LIST_TABLE_NAME}, LIST_TABLE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ParentItem item = new ParentItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), getAllTasks(cursor.getString(1)));

        Log.d("Get List: ", item.toString());
        return item;
    }

    public ChildItem getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TASK_TABLE, new String[]{TASK_TABLE_ID, TASK_TABLE_NAME, TASK_TABLE_LIST, TASK_TABLE_PRIORITY}, TASK_TABLE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        int taskId = Integer.parseInt(cursor.getString(0));
        String taskName = cursor.getString(1);
        String taskListName = cursor.getString(2);
        int taskPriority = Integer.parseInt(cursor.getString(3));

        ChildItem item = new ChildItem(taskId, taskName, taskListName, taskPriority);

        Log.d("Get Task: ", item.toString());
        return item;
    }

    public void deleteList(ParentItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LIST_TABLE, LIST_TABLE_ID + " = ?", new String[]{Integer.toString(item.getId())});
        db.close();

        Log.d("delete ParentItem: ", item.toString());
    }

    public void deleteTask(ChildItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TASK_TABLE, TASK_TABLE_ID + " = ?", new String[]{Integer.toString(item.getId())});
        db.close();

        Log.d("delete ChildItem: ", item.toString());
    }
}
