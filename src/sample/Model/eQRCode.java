package sample.Model;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import sample.Controller.SampleController;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class eQRCode extends QRCode{
    private String eQRCodeID;
    private String name;
    private String contact_no;
    private ArrayList<String> disallowedVenues;
    private Event event;
    private String AESKey;

    //Constructor
    protected eQRCode(int width, int height) {
        super(width, height);
    }
    public eQRCode(){
        super(15,15);
    }
    //Generic function which aids the Controller method
    public void setInfo(String eQRCodeID, String name, String contact_no, ArrayList<String> disallowedVenues, String AESKey){
        this.AESKey = AESKey;
        if (!checkeQRCodeIDValid(eQRCodeID)){
            SampleController.showAlertInputCSVInvalidFormat();
            return;
        }
        this.eQRCodeID = eQRCodeID;
        if (checkPhoneValid(contact_no)){
            this.contact_no = contact_no;
        }
        if (checkNameValid(name)){
            this.name = name;
        }

        this.disallowedVenues = new ArrayList<String>();
        for (String i: disallowedVenues){
            if (!checkVenueIDValid(i)){
                SampleController.showAlertInputCSVInvalidFormat();
                return;
            }
            this.disallowedVenues.add(i);
        }
        if (disallowedVenues.size() == 0){
            disallowedVenues.add("00000");
        }

        String event_ID = eQRCodeID.substring(10, 15);
        this.event = SampleController.getEvent(event_ID);
    }
    public eQRCode(String line, String AESKey){
        super(15, 15);
        String[] tokens = line.split("[,]+");

        if (tokens.length < 3){
            SampleController.showAlertInputCSVInvalidFormat();
        }

        ArrayList<String> disallowed = new ArrayList<String>();
        for (int i = 3; i < tokens.length; ++i){
            disallowed.add(tokens[i]);
        }
        setInfo(tokens[0], tokens[1], tokens[2], disallowed, AESKey);
    }
    public eQRCode(Event event, String name, String contact_no, ArrayList<String> disallowedVenues, String AESKey){
        super(15, 15);

        this.event = event;

        eQRCodeID = new SimpleDateFormat("ddMMyy").format(new Date());
        Random random = new Random();
        for (int i = 0; i < 4; ++i){
            eQRCodeID = eQRCodeID + (char) ('A' + random.nextInt(26));
        }
        eQRCodeID += event.getID();

        setInfo(eQRCodeID, name, contact_no, disallowedVenues, AESKey);
    }


    //Regex to check if input are valid
    public static boolean checkPhoneValid(String phone_number){
        String regx = "[89]\\d{7}";
        if (!phone_number.matches(regx)){
            return false;
        }
        return true;
    }
    public static boolean checkVenueIDValid(String ID){
        String regx = "\\d{5}";
        if (!ID.matches(regx)){
            return false;
        }
        return true;
    }
    public static boolean checkNameValid(String name){
        String regx = "[a-zA-Z\\s]+";
        if (!name.matches(regx) || name.length() < 3){
            return false;
        }
        return true;
    }
    public static boolean checkeQRCodeIDValid(String eQRCodeID){
        String regx = "\\d{6}[A-Z]{4}\\d{5}";
        if (!eQRCodeID.matches(regx)){
            return false;
        }
        return true;
    }

    //Getter Methods
    public String geteQRCodeID() {
        return eQRCodeID;
    }
    public String getName() {
        return name;
    }
    public String getContact_no() {
        return contact_no;
    }
    public ArrayList<String> getDisallowedVenues() {
        return disallowedVenues;
    }
    public Event getEvent() {
        return event;
    }

    //To String
    @Override
    public String toString() {
        String out =  eQRCodeID + ',' + name + "," + contact_no;
        for (String i:disallowedVenues){
            out += "," + i;
        }
        return out;
    }

    //Method to generate QR Code
    public void generateQRCode(String directory){
        String encrypted = new AES().encrypt(toString(), AESKey);
        try{
            generateQRCodeImage(encrypted, directory + "/" + eQRCodeID + ".png");
        }
        catch (IOException e) {
            SampleController.showAlertFileSaveFail();
            e.printStackTrace();
        }
        catch (WriterException e) {
            SampleController.showAlertFileSaveFail();
            e.printStackTrace();
        }
    }

    //Method to decode the QR Code and AES
    public String decodeQRCodeAES(File qrCodeImage) {
        try{
            String s = decodeQRCode(qrCodeImage);
            if (s == null){
                SampleController.showAlertCannotDecryptQRCode();
            }
            s = new AES().decrypt(s, "NUSHigh2020");
            return s;
        }
        catch(IOException | NullPointerException e){
            SampleController.showAlertCannotDecryptQRCode();
            return null;
        }
    }
}