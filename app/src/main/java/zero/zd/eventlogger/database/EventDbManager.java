package zero.zd.eventlogger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import zero.zd.eventlogger.Event;
import zero.zd.eventlogger.database.EventDbSchema.EventTable;

public class EventDbManager {

    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private EventSQLiteOpenHelper mDbHelper;

    public EventDbManager(Context context) {
        mContext = context;
    }

    public void open() throws SQLException {
        mDbHelper = new EventSQLiteOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public List<Event> getEventList() {
        List<Event> eventList = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                EventTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        EventCursorWrapper cursorWrapper = new EventCursorWrapper(cursor);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                eventList.add(cursorWrapper.getEvent());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursor.close();
            cursorWrapper.close();
        }

        return eventList;
    }

    public void addEvent(Event event) {
        ContentValues values = getContentValues(event);
        mDatabase.insert(EventTable.NAME, null, values);
    }

    public void updateEvent(Event event) {
        ContentValues values = getContentValues(event);
        mDatabase.update(EventTable.NAME,
                values,
                "uuid = ?",
                new String[]{event.getId().toString()}
        );
    }

    public void deleteEvent(Event event) {
        mDatabase.delete(EventTable.NAME,
                "uuid = ?",
                new String[]{event.getId().toString()}
        );
    }

    private ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventTable.Cols.UUID, event.getId().toString());
        values.put(EventTable.Cols.EVENT, event.getEvent());
        values.put(EventTable.Cols.DATE, event.getDate().getTime());

        return values;
    }
}
