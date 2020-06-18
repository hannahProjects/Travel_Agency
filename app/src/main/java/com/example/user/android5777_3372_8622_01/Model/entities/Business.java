package com.example.user.android5777_3372_8622_01.Model.entities;

/**
 * Created by user on 12/12/2016.
 *
 * class that represent a business
 */

public class Business
{
    int BusinessId;//the business number that with the name ht is the key
    String BusinessName;  // the business name the with the it is the key
    Address BusinessAddress;  // the business address
    String BusinessPhoneNumber;  // the business phone number
    String BusinessEMail;  // the business E-mail
    String BusinessWebsite; // the business website


    //constructors
    public Business()
    {
        BusinessId = 0;
        BusinessName = null;
        BusinessAddress = new Address();
        BusinessPhoneNumber = null;
        BusinessEMail = null;
        BusinessWebsite = null;
    }
    public Business(int _BusinessId, String _BusinessName, Address _BusinessAddress, String _BusinessPhoneNumber, String _BusinessEMail, String _BusinessWebsite)
    {
        BusinessId = _BusinessId;
        BusinessName = _BusinessName;
        BusinessAddress = _BusinessAddress;
        BusinessPhoneNumber = _BusinessPhoneNumber;
        BusinessEMail = _BusinessEMail;
        BusinessWebsite = _BusinessWebsite;
    }

    //setters and getters
    public int getBusinessId() {
        return BusinessId;
    }
    public void setBusinessId(int businessId) {
        BusinessId = businessId;
    }
    public String getBusinessName() {
        return BusinessName;
    }
    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }
    public Address getBusinessAddress() {
        return BusinessAddress;
    }
    public void setBusinessAddress(Address businessAddress) {
        BusinessAddress = businessAddress;
    }
    public void setFromStringToAddress(String businessAddress) //get a string and turn it to an address to set the address
    {
        String temp[] = businessAddress.split(" ");
        BusinessAddress.country = temp[0];
        BusinessAddress.city = temp[1];
        BusinessAddress.street = temp[2];
        BusinessAddress.number = Integer.parseInt(temp[3]);
    }
    public String getBusinessPhoneNumber() {
        return BusinessPhoneNumber;
    }
    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        BusinessPhoneNumber = businessPhoneNumber;
    }
    public String getBusinessEMail() {
        return BusinessEMail;
    }
    public void setBusinessEMail(String businessEMail) {
        BusinessEMail = businessEMail;
    }
    public String getBusinessWebsite() {
        return BusinessWebsite;
    }
    public void setBusinessWebsite(String businessWebsite) {
        BusinessWebsite = businessWebsite;
    }

}
