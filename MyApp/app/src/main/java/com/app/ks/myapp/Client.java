package com.app.ks.myapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ks on 09.04.2016.
 */
public class Client implements Parcelable, IDatabaseObject {

    private Long mId;
    @SerializedName("id")
    private Long mExternalId;
    @SerializedName("name")
    private String mName;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("phone")
    private String mPhone;

    public Client() {}

    protected  Client(Parcel in) {

        mName = in.readString();
        mAddress = in.readString();
        mEmail = in.readString();
        mPhone = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public Long getExternalId() {
        return mExternalId;
    }

    public void setExternalId(Long mExternalId) {
        this.mExternalId = mExternalId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mEmail);
        dest.writeString(mPhone);
    }

}
