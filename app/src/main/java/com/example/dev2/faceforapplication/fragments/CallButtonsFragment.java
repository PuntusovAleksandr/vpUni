package com.example.dev2.faceforapplication.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dev2.faceforapplication.R;
import com.example.dev2.faceforapplication.otherActivity.CallActivity;

import date_base_book_contact.ServiceParams;
import date_base_book_contact.d_b_contacts_book.ServiceContactBook;
import date_base_book_contact.d_b_contacts_book.impl.DateBaseContact;
import date_base_book_contact.d_b_history.ServiseHistory;
import date_base_book_contact.d_b_history.impl.DateBaseHistory;
import sipua.SipProfile;
import sipua.impl.DeviceImpl;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CallButtonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CallButtonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallButtonsFragment extends Fragment implements ServiceParams{

    /**
     * The constant TAG.which is necessary for creating registration fragment
     */
    public static final String TAG ="CallButtonsFragment";
    private ImageButton btCall;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static CallButtonsFragment fragment;


    private View callButtonsFragmetnt;
    private ServiseHistory history;
    private ServiceContactBook servCont;

    //Sip profile settings
    private SipProfile mSipProfile;

    /**
     * New instance.
     *
     * @return the call buttons fragment
     */
    public static CallButtonsFragment newInstance() {
        if (fragment == null) {
            fragment = new CallButtonsFragment();
        }
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallButtonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallButtonsFragment newInstance(String param1, String param2) {
        CallButtonsFragment fragment = new CallButtonsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Instantiates a new Call buttons fragment.
     */
    public CallButtonsFragment() {
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
        setRetainInstance(true);

        mSipProfile = new SipProfile();

        callButtonsFragmetnt = View.inflate(getActivity(), R.layout.fragment_call_buttons, null);
        // Inflate the layout for this fragment
        btCall = (ImageButton) callButtonsFragmetnt.findViewById(R.id.imBt_call);
        btCall.setOnClickListener(listener);

        return callButtonsFragmetnt;
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
    View.OnClickListener  listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager manager = getFragmentManager();
            switch (v.getId()) {
                case R.id.imBt_call:
                    Fragment buttFragmentByTag = manager.findFragmentByTag(ButtonsFragment.TAG);
                    Fragment callButtFragmentByTag = manager.findFragmentByTag(CallButtonsFragment.TAG);
                    Fragment inputPlaceFragment = manager.findFragmentByTag(InputPlaceFragment.TAG);
                    Fragment icon = manager.findFragmentByTag(IconFragment.TAG);
                    Fragment endCall = manager.findFragmentByTag(EndCallFragment.TAG);

                    if (buttFragmentByTag != null && callButtFragmentByTag != null && inputPlaceFragment != null) {
                        if (InputPlaceFragment.getTextFromTextView().equals("")) {
                            Toast.makeText(getActivity(), "Input number phone", Toast.LENGTH_SHORT).show();
                        } else {
                            makeCall();
                            saveToDB(STATUS_TYPED);
                            Intent intent = new Intent(getActivity(), CallActivity.class);
                            getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                            startActivity(intent);
                            InputPlaceFragment.setTextInToTextView("");
                        }
                    }
                    if (icon != null && endCall != null) {
                        makeCall();
                        saveToDB(STATUS_ANSWERED);
                        Intent intent = new Intent(getActivity(), CallActivity.class);
                        getActivity().overridePendingTransition(R.anim.righttoleft, R.anim.stable);
                        startActivity(intent);
                        getActivity().finish();
                        InputPlaceFragment.setTextInToTextView("");
                    }
                    break;
            }
        }
    };

    private void saveToDB(int status) {

        history = new DateBaseHistory(getActivity());
        servCont = new DateBaseContact(getActivity());
        history.addContactToBase(servCont.getContactHistory(
                InputPlaceFragment.getTextFromTextView(),
                status
        ));

    }

    private void makeCall() {

        //// TODO: 30.06.15 необходимо создать IDevice inter = new DeviceImpl(); и переписать этот метод с добавлением
        // inter.Call("sip:" + mCallAddress +"@" + mSipProfile.getRemoteIp() +":" + mSipProfile.getRemotePort());
        // так же необходимо переименовать конструктор по умолчанию в классе DeviceImpl на public

        String mCallAddress = InputPlaceFragment.getTextFromTextView();

        //globalData.setOutCallNumber(mCallAddress);
        DeviceImpl.GetInstance().Call(
                "sip:" + mCallAddress +
                        "@" + mSipProfile.getRemoteIp() +
                        ":" + mSipProfile.getRemotePort());
    }

}
