package org.techtown.findproject;

public class MemberInfo {
    private  String name;
    private  String phoneNumber;
    private  String birthDay;
    private  String address;
    private  String email;

    public MemberInfo(String name, String phoneNumber, String birthDay, String address, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.email = email;
    }

    public MemberInfo(String name, String phoneNumber, String birthDay, String address){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDay(){
        return this.birthDay;
    }

    public void setBirthDay(String birthDay){
        this.birthDay = birthDay;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
