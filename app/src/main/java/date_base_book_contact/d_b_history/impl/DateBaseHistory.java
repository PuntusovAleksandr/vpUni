package date_base_book_contact.d_b_history.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import date_base_book_contact.ServiceParams;
import date_base_book_contact.d_b_history.ServiseHistory;
import date_base_book_contact.entity.ContactHistory;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public class DateBaseHistory implements ServiseHistory, ServiceParams {

    private DbHelper dbHelper;
    private ContentValues contentValues;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ContactHistory contactHistory;

    public DateBaseHistory(Context context) {
        dbHelper = new DbHelper(context);
    }

    @Override
    public ArrayList<ContactHistory> getAllContactHistory() {
        ArrayList<ContactHistory> contactHistorys = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        cursor = db.query(TABLE_HISTORY, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getColumnIndex(COLUMN_ID);
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CALL));
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String last = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String nike = cursor.getString(cursor.getColumnIndex(COLUMN_NIKE_NAME));
                int stat = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS_ICON));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ICON));
                contactHistory = new ContactHistory(date, phone, name, last, nike, address, stat);
                contactHistorys.add(contactHistory);
            } while (cursor.moveToNext());
        }else
            cursor.close();
        dbHelper.close();
        return contactHistorys;
    }

    @Override
    public void addContactToBase(ContactHistory contactHistory) {
        addContactToBase(contactHistory.getDateCall(), contactHistory.getPhoneNumber(),
                contactHistory.getName(), contactHistory.getLastName(),
                contactHistory.getNikeName(), contactHistory.getAddressIcon(),
                contactHistory.getStatusIcon());
    }

    @Override
    public void addContactToBase(String dateCall, String phoneNumber, String name, String lastName,
                                 String nikeName, String addressIcon, int statusIcon) {
        contentValues = new ContentValues();
        db = dbHelper.getWritableDatabase();
        contentValues.put(COLUMN_DATE_CALL, dateCall);
        contentValues.put(COLUMN_PHONE_NUMBER, phoneNumber);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_NIKE_NAME, nikeName);
        contentValues.put(COLUMN_ADDRESS_ICON, addressIcon);
        contentValues.put(COLUMN_STATUS_ICON, statusIcon);
        long idCol = db.insert(TABLE_HISTORY, null, contentValues);
        dbHelper.close();

        System.out.println("idCol = "+idCol);
    }

    @Override
    public void deleteContactFromBook(ContactHistory contactHistory) {

    }

    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, TABLE_HISTORY, null, VERSION_DB_hISTORY);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_HISTORY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
