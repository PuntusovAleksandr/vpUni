package date_base_book_contact.entity;

import java.text.SimpleDateFormat;

/**
 * Created by Aleksandr on 01.07.2015.
 */
public class ContactHistory {

    private String dateCall;
    private String phoneNumber;
    private String name;
    private String lastName;
    private String nikeName;
    private String addressIcon;
    private int statusIcon;

    private SimpleDateFormat dateFormat;


    public ContactHistory(String dateCall, String phoneNumber, String name, String lastName,
                          String nikeName, String addressIcon, int statusIcon) {
        this.dateCall = dateCall;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastName = lastName;
        this.nikeName = nikeName;
        this.addressIcon = addressIcon;
        this.statusIcon = statusIcon;
    }

    public String getDateCall() {
        return dateCall;
    }

    public void setDateCall(String dateCall) {
        this.dateCall = dateCall;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(int statusIcon) {
        this.statusIcon = statusIcon;
    }

    public String getAddressIcon() {
        return addressIcon;
    }

    public void setAddressIcon(String addressIcon) {
        this.addressIcon = addressIcon;
    }
}
