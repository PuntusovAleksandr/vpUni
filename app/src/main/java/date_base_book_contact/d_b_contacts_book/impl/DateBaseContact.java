package date_base_book_contact.d_b_contacts_book.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import date_base_book_contact.ServiceParams;
import date_base_book_contact.d_b_contacts_book.ServiceContactBook;
import date_base_book_contact.entity.ContactBook;
import date_base_book_contact.entity.ContactHistory;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public class DateBaseContact implements ServiceContactBook, ServiceParams {

    private DbHelper dbHelper;
    private ContentValues contentValues;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ContactBook contactBook;

    public DateBaseContact(Context context) {
        dbHelper = new DbHelper(context);
    }

    @Override
    public ArrayList<ContactBook> getAllContact() {
        ArrayList<ContactBook> contactBooks = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
        cursor.moveToFirst()   ;
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getColumnIndex(COLUMN_ID);
                String phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String last = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String nike = cursor.getString(cursor.getColumnIndex(COLUMN_NIKE_NAME));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS_ICON));

                contactBook = new ContactBook(phone, name, last, nike, address);
                contactBooks.add(contactBook);
            } while (cursor.moveToNext());
        }else
        cursor.close();
        dbHelper.close();
        return contactBooks;
    }

    @Override
    public void addContactToBase(ContactBook contactBook) {
        addContactToBase(contactBook.getPhoneNumber(), contactBook.getName(),
                contactBook.getLastName(),contactBook.getNikeName(), contactBook.getAddressIcon());
    }

    @Override
    public void addContactToBase(String phone, String name, String lastName,
                                 String nikeName, String addressIcon) {
        contentValues = new ContentValues();
        db = dbHelper.getWritableDatabase();
        contentValues.put(COLUMN_PHONE_NUMBER, phone);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_NIKE_NAME, nikeName);
        contentValues.put(COLUMN_ADDRESS_ICON, addressIcon);
        long idCol = db.insert(TABLE_CONTACTS, null, contentValues);
        dbHelper.close();

        System.out.println("idCol = "+idCol);
    }

    @Override
    public void deleteContactFromBook(ContactBook contactBook) {

    }

    @Override
    public void renameContactBook(ContactBook contactDelete, ContactBook contactCreate) {

    }

    @Override
    public ContactHistory getContactHistory(String phone, int status) {
        ContactHistory contactHistory = null;
        ArrayList<ContactBook> listContBook = getAllContact();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM HH.mm.ss");
        for (ContactBook contactB : listContBook) {
            if (contactB.getPhoneNumber().equals(phone)) {
                contactHistory = new ContactHistory(
                        dateFormat.format(new Date()),
                        contactB.getPhoneNumber(),
                        contactB.getName(),
                        contactB.getLastName(),
                        contactB.getNikeName(),
                        contactB.getAddressIcon(),
                        status
                );
            }
        }
        if (contactHistory == null) {
                contactHistory = new ContactHistory(
                        dateFormat.format(new Date()),
                        phone, "","","", "", status
                        );
        }
        return contactHistory;
    }


    private class DbHelper extends SQLiteOpenHelper {
        public DbHelper(Context context) {
            super(context, TABLE_CONTACTS, null, VERSION_DB_CONTACT);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_CONTACTS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
