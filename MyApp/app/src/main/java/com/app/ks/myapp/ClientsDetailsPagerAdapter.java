package com.app.ks.myapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ks on 09.04.2016.
 */
public class ClientsDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 2;
    private final int CLIENT_DETAILS_PAGE  = 0;
    private final int CONTACTS_LIST_PAGE  = 1;

    private final Context context;
    private final Client client;

    public ClientsDetailsPagerAdapter(FragmentManager fm, Context context, Client client) {
        super(fm);
        this.context = context;
        this.client = client;
    }

    @Override
    public Fragment getItem(int position) {
        ClientDetailsFragment fragment;
        switch(position) {
            case CLIENT_DETAILS_PAGE:
                return ClientDetailsFragment.getInstance(client);
            case CONTACTS_LIST_PAGE:
                return ClientDetailsFragment.getInstance(client);

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ClientDetailsFragment fragment;
        switch(position) {
            case CLIENT_DETAILS_PAGE:
                return context.getString(R.string.details_page_title);
            case CONTACTS_LIST_PAGE:
                return context.getString(R.string.list_page_title);

            default: return null;
        }
    }
}
