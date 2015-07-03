package com.example.dev2.faceforapplication.otherActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dev2.faceforapplication.R;
import com.example.dev2.faceforapplication.fragments.CallButtonsFragment;
import com.example.dev2.faceforapplication.fragments.EndCallFragment;
import com.example.dev2.faceforapplication.fragments.IconFragment;
import com.example.dev2.faceforapplication.fragments.InformationFragment;

/**
 * The type Window calling activity.
 */
public class WindowCallingActivity extends AppCompatActivity implements
        InformationFragment.OnFragmentInteractionListener,
        IconFragment.OnFragmentInteractionListener,
        CallButtonsFragment.OnFragmentInteractionListener,
        EndCallFragment.OnFragmentInteractionListener{

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private IconFragment iconFragment;
    private CallButtonsFragment callButtonsFragment;
    private EndCallFragment endCallFragment;
    private InformationFragment informationFragment;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_calling);

        manager = getSupportFragmentManager();
        iconFragment = IconFragment.newInstance();
        callButtonsFragment = CallButtonsFragment.newInstance();
        endCallFragment = EndCallFragment.newInstance();
        informationFragment = InformationFragment.newInstance();

        if (savedInstanceState == null) {
            transaction = manager.beginTransaction();
            transaction.add(R.id.ll_body_window_calling, iconFragment, IconFragment.TAG);
            transaction.add(R.id.ll_call_up, callButtonsFragment, CallButtonsFragment.TAG);
            transaction.add(R.id.ll_call_cancel, endCallFragment, EndCallFragment.TAG);
            transaction.add(R.id.ll_head_window_calling, informationFragment, InformationFragment.TAG);
            transaction.commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {    }

    /**
     * On click buttons call.
     *
     * @param view the view
     */
    public void onClickButtonsCall(View view) {
        transaction = manager.beginTransaction();
        Fragment callButtFragmentByTag = manager.findFragmentByTag(CallButtonsFragment.TAG);
        Fragment endCallFragmentByTag = manager.findFragmentByTag(EndCallFragment.TAG);

        switch (view.getId()) {
            case R.id.imBt_call:
                if (endCallFragmentByTag != null && callButtFragmentByTag != null) {
                    intent = new Intent(WindowCallingActivity.this, CallActivity.class);
                    startActivity(intent);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * On click from other activity.
     *
     * @param view the view
     */
    public void onClickFromOtherActivity(View view) {
        transaction = manager.beginTransaction();
        Fragment callButtFragmentByTag = manager.findFragmentByTag(CallButtonsFragment.TAG);
        Fragment endCallFragmentByTag = manager.findFragmentByTag(EndCallFragment.TAG);
        switch (view.getId()) {
            case R.id.imBt_end_call:
                if (endCallFragmentByTag != null && callButtFragmentByTag != null) {
                    onBackPressed();
                }
                break;
        }
        transaction.commit();
    }
}
