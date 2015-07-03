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
import date_base_book_contact.d_b_history.ServiseHistory;
import date_base_book_contact.d_b_history.impl.DateBaseHistory;
import date_base_book_contact.entity.ContactHistory;
import sipua.SipProfile;
import sipua.impl.DeviceImpl;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements ServiceParams{

    /**
     * The constant TAG.which is necessary for creating registration fragment
     */
    public static final String TAG ="HistoryFragment";

    private ListView listHistory;
//    private ArrayAdapter<String> adapter;
    private MyAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static HistoryFragment fragment;
    private View historyFragment;

    private ArrayList<String> names;
    private ServiseHistory history;


    /**
     * New instance.
     *
     * @return the end call fragment
     */
    public static HistoryFragment newInstance() {
        if (fragment == null) {
            fragment = new HistoryFragment();
        }
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Instantiates a new History fragment.
     */
    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        history = new DateBaseHistory(getActivity());
        names = new ArrayList<>();
        ArrayList<ContactHistory> contactHistories = history.getAllContactHistory();
        for (ContactHistory cont : contactHistories) {
            names.add(cont.getPhoneNumber() + " " + cont.getLastName() + " " + cont.getNikeName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);

        historyFragment = View.inflate(getActivity(), R.layout.fragment_history, null);
        listHistory = (ListView) historyFragment.findViewById(R.id.list_history);
        listHistory.setChoiceMode(AbsListView.CHOICE_MODE_NONE);
        listHistory.setOnItemClickListener(listener);
        adapter = new MyAdapter(getActivity(), names);
//        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, names);
        listHistory.setAdapter(adapter);

        return historyFragment;
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

    /**
     * The Listener.
     */
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            InputPlaceFragment.setTextInToTextView(parent.getItemAtPosition(position).toString());
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Call this contact ?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), CallActivity.class);
                    getActivity(). overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                    startActivity(intent);
                    makeCall();
                    InputPlaceFragment.setTextInToTextView("");
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();



        }
    };

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
            }
        }
    }

}
