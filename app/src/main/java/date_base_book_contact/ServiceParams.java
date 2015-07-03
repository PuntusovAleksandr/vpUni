package date_base_book_contact;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public interface ServiceParams {

    public String TABLE_CONTACTS = "contacts";
    public String TABLE_HISTORY = "history";
    public String COLUMN_ID = "id";
    public String COLUMN_NIKE_NAME = "nike_name";
    public String COLUMN_NAME = "name";
    public String COLUMN_LAST_NAME = "last_name";
    public String COLUMN_PHONE_NUMBER = "phone_number";
    public String COLUMN_ADDRESS_ICON = "address_icon";
    public String COLUMN_DATE_CALL = "date_call";
    public String COLUMN_STATUS_ICON = "status_icon";

    public int VERSION_DB_CONTACT = 1;
    public int VERSION_DB_hISTORY = 1;

    public int STATUS_SKIPPED = 0;
    public int STATUS_TYPED = 1;
    public int STATUS_ANSWERED = 2;



    public String CREATE_TABLE_CONTACTS = "CREATE table "+TABLE_CONTACTS + "(\n" +
            COLUMN_ID + " integer primary key autoincrement, \n" +
            COLUMN_NAME + " text not null,\n" +
            COLUMN_LAST_NAME + " text,\n" +
            COLUMN_NIKE_NAME + " text,\n" +
            COLUMN_PHONE_NUMBER + " text not null,\n" +
            COLUMN_ADDRESS_ICON + " text);";

    public String CREATE_TABLE_HISTORY = "CREATE table "+TABLE_HISTORY+ "(\n" +
            COLUMN_ID + " integer primary key autoincrement,\n" +
            COLUMN_DATE_CALL + " text not null,\n" +
            COLUMN_NAME + " text not null,\n" +
            COLUMN_LAST_NAME + " text,\n" +
            COLUMN_NIKE_NAME + " text,\n" +
            COLUMN_PHONE_NUMBER + " text not null,\n" +
            COLUMN_STATUS_ICON + " integer not null,\n" +
            COLUMN_ADDRESS_ICON + " text);";

}
