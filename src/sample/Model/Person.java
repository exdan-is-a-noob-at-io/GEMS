package sample.Model;

import sample.Controller.SampleController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Person {
    private eQRCode eqrCode;
    private Venue venue;
    private Date entryDateTime;
    private Date exitDateTime;

    //constructor
    public Person(eQRCode eqrCode, Venue venue) {
        this.eqrCode = eqrCode;
        this.venue = venue;
        entryDateTime = new Date();
        exitDateTime = null;
    }
    public Person(String line) throws ParseException {
        String[] tokens = line.split("[,]+");

        eqrCode = new eQRCode(SampleController.getEvent(tokens[2]), tokens[0], tokens[1], new ArrayList<String>(), "NUSHigh2020");
        venue = SampleController.getVenue(tokens[3]);
        entryDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(tokens[4]);
        exitDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(tokens[5]);
    }

    //checks if two people are the same; if its eqrcode and venue are the same
    public boolean samePerson(Person p){
        if (p == null){
            return false;
        }
        if (this.eqrCode.geteQRCodeID().equals(p.getEqrCode().geteQRCodeID())){
            if (this.venue.getID().equals(p.getVenue().getID())){
                return true;
            }
        }
        return false;
    }

    public eQRCode getEqrCode() {
        return eqrCode;
    }

    public void setEqrCode(eQRCode eqrCode) {
        this.eqrCode = eqrCode;
    }

    public Date getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(Date entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public Date getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(Date exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    //for printing person to visit.csv
    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return eqrCode.getName() + "," +
                eqrCode.getContact_no() + "," +
                eqrCode.getEvent().getID() + "," +
                venue.getID() + "," +
                simpleDateFormat.format(entryDateTime) + "," +
                simpleDateFormat.format(exitDateTime);
    }
}
