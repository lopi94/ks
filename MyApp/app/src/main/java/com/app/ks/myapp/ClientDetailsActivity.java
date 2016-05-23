package com.app.ks.myapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientDetailsActivity extends AppCompatActivity {

    @Bind(R.id.view_pager_client_details)
    ViewPager mViewPagerClientDetails;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    Client mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        getSupportActionBar().setTitle(R.string.title_activity_details_client);

        ButterKnife.bind(this);

        getData();

        ClientsDetailsPagerAdapter clientsDetailsPagerAdapter = new ClientsDetailsPagerAdapter(getSupportFragmentManager(), ClientDetailsActivity.this, mClient);
        mViewPagerClientDetails.setAdapter(clientsDetailsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPagerClientDetails);

        getSupportActionBar().setSubtitle(mClient.getName());

    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mClient = bundle.getParcelable(IntentExtras.CLIENT);
        }
    }

    @OnClick(R.id.floating_action_button_call)
    protected void makeCall() {
        String url = String.format("tel:.%s", "123456789");
        Intent callIntent = new Intent("android.intent.action.CALL", Uri.parse(url));
        startActivity(callIntent);
    }

    @OnClick(R.id.floating_action_button_mail)
    protected void sendMail() {
        String url = String.format("mailto:%s", "test@test.pl");
        Intent emailIntent= new Intent("android.intent.action.VIEW", Uri.parse(url));
        startActivity(emailIntent);
    }

    @OnClick(R.id.floating_action_button_maps)
    protected void makeMaps() {
        String uri = String.format("geo:0,0?q=%s","1 Maja 133 Katowice");
        Intent mapsIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        mapsIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(mapsIntent);
    }

}
