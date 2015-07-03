package date_base_book_contact.d_b_history;

import java.util.ArrayList;

import date_base_book_contact.entity.ContactHistory;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public interface ServiseHistory {

    public ArrayList<ContactHistory> getAllContactHistory();

    public void addContactToBase(ContactHistory contactHistory);

    public void addContactToBase(String dateCall, String phoneNumber, String name,
                                 String lastName, String nikeName, String addressIcon,
                                 int statusIcon);

    public void deleteContactFromBook(ContactHistory contactHistory);

    public void deleteContact(String phone);

}
