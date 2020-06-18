package com.example.user.android5777_3372_8622_01.Model.entities;

/**
 * Created by user on 12/12/2016.
 *
 * class that represent entity of an address for the business.
 * the address have country, city, street and the house number.
 */

public class Address
{
    String country;
    String city;
    String street;
    int number;

    //constructors
    public Address()
    {
        country = null;
        city = null;
        street = null;
        number = 0;
    }
    public Address( String _country, String _city, String _street, int _number)
    {
        country = _country;
        city = _city;
        street = _street;
        number = _number;
    }


    @Override //running over the toString to do our own toString
    public String toString()
    {
        return  country + " " +
                city + " " +
                street + " " +
                number + " ";
    }

    //function that do the exact opposite form toString
    public void fromStringToAddress(String s)
    {
        String[] words = s.split(" ");
        country = words[0];
        city = words[1];
        street = words[2];
        number = Integer.parseInt(words[3]);
    }

    //getters and setters
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
