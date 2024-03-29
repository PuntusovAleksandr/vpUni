package com.example.dev2.faceforapplication.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dev2.faceforapplication.R;
import com.example.dev2.faceforapplication.otherActivity.CallActivity;

import java.util.ArrayList;

import date_base_book_contact.ServiceParams;
import date_base_book_contact.d_b_contacts_book.ServiceContactBook;
import date_base_book_contact.d_b_contacts_book.impl.DateBaseContact;
import date_base_book_contact.d_b_history.ServiseHistory;
import date_base_book_contact.d_b_history.impl.DateBaseHistory;
import date_base_book_contact.entity.ContactBook;
import sipua.SipProfile;
import sipua.impl.DeviceImpl;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment implements ServiceParams{

    /**
     * The constant TAG.which is necessary for creating registration fragment
     */
    public static final String TAG ="ContactsFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static ContactsFragment fragment;
    private View contactsFragment;

    private ListView listViewContacts;
//    private ArrayAdapter<String> adapter;
    private MyAdapter adapter;

//    private String[] names = {
//            "3001",
//            "3002",
//            "3003",
//            "3004",
//            "3005",
//            "3006"
//    };

    private ArrayList<String> names;
    private ArrayList<ContactBook> contactBookArrayList;
    private ServiceContactBook serviceContactBook;


    /**
     * New instance.
     *
     * @return the end call fragment
     */
    public static ContactsFragment newInstance() {
        if (fragment == null) {
            fragment = new ContactsFragment();
        }
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Instantiates a new Contacts fragment.
     */
    public ContactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);

        names  = new ArrayList<>();
        serviceContactBook = new DateBaseContact(getActivity());

        ArrayList tmp = serviceContactBook.getAllContact();
        if ( tmp.size() <= 0 ) {
            serviceContactBook.addContactToBase("3001", "Stas", "Bondar", "Stas","");
            serviceContactBook.addContactToBase("3002", "Valentin", "Valentin", "Done", "");
            serviceContactBook.addContactToBase( "3003", "Aleksandr", "Holodov", "", "null");
            serviceContactBook.addContactToBase("3004", "Aleksandr", "", "Alex", "");
            serviceContactBook.addContactToBase( "3005", "Aleksandr", "Puntusov", "zloj", "");
            serviceContactBook.addContactToBase( "3006", "op", "", "", "");
            serviceContactBook.addContactToBase( "*100", "Secretary", "", "", "");
        }
        contactBookArrayList = serviceContactBook.getAllContact();
        for (ContactBook con : contactBookArrayList) {
            names.add(con.getPhoneNumber() + " " + con.getName());
        }

        contactsFragment = View.inflate(getActivity(), R.layout.fragment_contacts, null);
        listViewContacts = (ListView) contactsFragment.findViewById(R.id.list_contacts);
        listViewContacts.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewContacts.setOnItemClickListener(listener);
        adapter = new MyAdapter(getActivity(), names);
        listViewContacts.setAdapter(adapter);

        return contactsFragment;
    }

    /**
     * On button pressed.
     *
     * @param uri the uri
     */
// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        /**
         * On fragment interaction.
         *
         * @param uri the uri
         */
// TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class MyAdapter extends ArrayAdapter {

        private Context mContext;
        private ArrayList<String> mStrings;

        public MyAdapter(Context context, ArrayList<String> strings) {
            super(context, 0, strings);
            mContext = context;
            mStrings = strings;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_to_list_view, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.news.setText(mStrings.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView news;

            public ViewHolder(View v){
                news =(TextView) v.findViewById(R.id.tex_label_in_list);
                news.setTextColor(0xFFFFFFFF);
            }
        }
    }
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
            final String[] mas = parent.getItemAtPosition(position).toString().split(" ");
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Call this contact ? ");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String phone = mas[0];
                    InputPlaceFragment.setTextInToTextView(phone);
                    saveToDBHistory(STATUS_TYPED);
                    makeCall();
                    Intent intent = new Intent(getActivity(), CallActivity.class);
                    getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                    startActivity(intent);
                    InputPlaceFragment.setTextInToTextView("");
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    InputPlaceFragment.setTextInToTextView("");
                    dialog.cancel();
                }
            });
            alert.show();
        }
    };

    private void saveToDBHistory(int status) {
        ServiseHistory history = new DateBaseHistory(getActivity());
        ServiceContactBook servCont = new DateBaseContact(getActivity());
        history.addContactToBase(servCont.getContactHistory(
                InputPlaceFragment.getTextFromTextView(),
                status
        ));
    }

    private void makeCall() {

        // // TODO: 30.06.15 необходимо создать IDevice inter = new DeviceImpl(); и переписать этот метод с добавлением
        // IDevice inter = new DeviceImpl();
        // inter.Call("sip:" + mCallAddress + "@" + mSipProfile.getRemoteIp() + ":" + mSipProfile.getRemotePort());

        // так же необходимо переименовать конструктор по умолчанию в классе DeviceImpl на public

        String mCallAddress = InputPlaceFragment.getTextFromTextView();
        SipProfile mSipProfile = new SipProfile();
        DeviceImpl.GetInstance().Call(
                "sip:" + mCallAddress +
                        "@" + mSipProfile.getRemoteIp() +
                        ":" + mSipProfile.getRemotePort());

    }
}
