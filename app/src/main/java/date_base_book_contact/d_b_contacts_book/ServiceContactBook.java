package date_base_book_contact.d_b_contacts_book;


import java.util.ArrayList;

import date_base_book_contact.entity.ContactBook;
import date_base_book_contact.entity.ContactHistory;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public interface ServiceContactBook {

    public void addContactToBase(ContactBook contactBook);

    public void addContactToBase(String phone, String name, String lastName, String nikeName, String addressIcon);

    public ArrayList<ContactBook> getAllContact();

    public void deleteContactFromBook(ContactBook contactBook);

    public void renameContactBook(ContactBook contactDelete, ContactBook contactCreate);

    public ContactHistory getContactHistory(String phone, int status);
}
