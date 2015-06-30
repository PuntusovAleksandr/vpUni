package com.example.dev2.faceforapplication.otherActivity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.dev2.faceforapplication.MainActivity;
import com.example.dev2.faceforapplication.R;
import com.example.dev2.faceforapplication.fragments.EndCallFragment;
import com.example.dev2.faceforapplication.fragments.IconFragment;


/**
 * The type Call activity.
 */
public class CallActivity extends FragmentActivity  implements
        EndCallFragment.OnFragmentInteractionListener,
        IconFragment.OnFragmentInteractionListener{

    private FragmentManager manager;
    private FragmentTransaction transaction;

    private EndCallFragment endCallFragment;
    private IconFragment iconFragment;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        manager = getSupportFragmentManager();
        endCallFragment = new EndCallFragment().newInstance();
        iconFragment = new IconFragment().newInstance();

        if (savedInstanceState == null) {
        transaction = manager.beginTransaction();
        transaction.add(R.id.ll_body_other,iconFragment, IconFragment.TAG );
        transaction.add(R.id.ll_botton_other,endCallFragment, EndCallFragment.TAG );
        transaction.commit();
        }
    }

    /**
     * On click from other activity.
     *
     * @param view the view
     */
    public void onClickFromOtherActivity(View view) {
        transaction = manager.beginTransaction();
        Fragment endButtFragmentByTag = manager.findFragmentByTag(EndCallFragment.TAG);
        Fragment icFragmentByTag = manager.findFragmentByTag(IconFragment.TAG);
        switch (view.getId()) {
            case R.id.imBt_end_call:
                if (icFragmentByTag != null) {
                    onBackPressed();
                }
                break;
        }

        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
