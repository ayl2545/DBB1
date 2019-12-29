package com.example.dbb.Entities;

import android.app.DatePickerDialog;
import android.location.Location;

import java.util.Date;


public class Parcel {
    private TypeEnum typeEnum;
    private Boolean fragile;
    private WeightEnum weightEnum;
    private Location location;
    private String recipientN;
    private String address;
    private DatePickerDialog dateS;
    private DatePickerDialog dateR;
    private String phoneNumber;
    private String email;
    private StatusEnum statusEnum;
    private String deliveryN;
    private String key;

    public Parcel(TypeEnum typeEnum, Boolean fragile, WeightEnum weightEnum, Location location, String recipientN, String address, DatePickerDialog dateS, DatePickerDialog dateR, String phoneNumber, String email, StatusEnum statusEnum, String deliveryN) {
        this.typeEnum = typeEnum;
        this.fragile = fragile;
        this.weightEnum = weightEnum;
        this.location = location;
        this.recipientN = recipientN;
        this.address = address;
        this.dateS = dateS;
        this.dateR = dateR;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.statusEnum = statusEnum;
        this.deliveryN = deliveryN;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public Boolean getFragile() {
        return fragile;
    }

    public void setFragile(Boolean fragile) {
        this.fragile = fragile;
    }

    public WeightEnum getWeightEnum() {
        return weightEnum;
    }

    public void setWeightEnum(WeightEnum weightEnum) {
        this.weightEnum = weightEnum;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getRecipientN() {
        return recipientN;
    }

    public void setRecipientN(String recipientN) {
        this.recipientN = recipientN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DatePickerDialog getDateS() {
        return dateS;
    }

    public void setDateS(DatePickerDialog dateS) {
        this.dateS=dateS;
    }

    public DatePickerDialog getDateR() {
        return dateR;
    }

    public void setDateR(DatePickerDialog dateR) {
        this.dateR = dateR;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public String getDeliveryN() {
        return deliveryN;
    }

    public void setDeliveryN(String deliveryN) {
        this.deliveryN = deliveryN;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }


    public Parcel() {
        this.statusEnum=StatusEnum.sent;
        this.deliveryN="NO";
    }
}
