package com.app.ks.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ks on 09.04.2016.
 */
public class ClientDetailsFragment extends Fragment {

    @Bind(R.id.text_view_details_name)
    TextView mTextViewName;
    @Bind(R.id.text_view_details_address)
    TextView mTextViewAddress;
    @Bind(R.id.text_view_details_email)
    TextView mTextViewEmail;
    @Bind(R.id.text_view_details_phone)
    TextView mTextViewPhone;

    private Client mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mClient = bundle.getParcelable(IntentExtras.CLIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_client_details, container, false);

        ButterKnife.bind(this, fragmentView);
        initUI();

        return fragmentView;
    }

    public static ClientDetailsFragment getInstance(Client client) {
        ClientDetailsFragment fragment = new ClientDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentExtras.CLIENT, client);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUI() {
        mTextViewName.setText(mClient.getName());
        mTextViewAddress.setText(mClient.getAddress());
        mTextViewEmail.setText(mClient.getEmail());
        mTextViewPhone.setText(mClient.getPhone());
    }

}