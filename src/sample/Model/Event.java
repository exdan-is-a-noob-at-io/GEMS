package sample.Model;

import sample.Controller.SampleController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Event {
    private String ID;
    private String name;
    private Date date;
    private ArrayList<Venue> venue;

    //Constructor
    public Event(String name, Date date) {
        if (!updateID()){
            //update unsuccessful;
            SampleController.tooManyEvents();
        }
        this.name = name;
        this.date = date;
        venue = new ArrayList<Venue>();
    }
    public Event(String ID, String name, Date date) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        venue = new ArrayList<Venue>();
    }

    //updates the ID in the Event Object. Returns true if the update is successful, otherwise false
    private boolean updateID(){
        try{
            Scanner s = new Scanner(new File("src/sample/eventRunningNo.txt"));

            if (!s.hasNext()){ //if file empty
                this.ID = "00001";
                PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/eventRunningNo.txt"));
                output.print("00002");
                System.out.println("Case 1");
                s.close();
                output.close();
                return true;
            }
            String num = s.next();
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/eventRunningNo.txt"));
            if (Integer.parseInt(num) <= 0){ //if the integer is invalid and less than 1
                this.ID = "00001";
                output.print("00002");
                System.out.println("Case 2");
            }
            else if (Integer.parseInt(num) > 99999){ //if the integer is invalid as all ids from 1 to 99999 has been used.
                s.close();
                output.close();
                return false;
            }
            else{ //if integer is valid
                this.ID = num;
                num = "" + (Integer.parseInt(num) + 1);
                while (num.length() != 5){
                    num = "0" + num;
                }
                output.print(num);
            }
            s.close();
            output.close();
            return true;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    //Appends the info from the event to the csv. Parameter defaults to true
    public void saveInfo(boolean append){
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/events.csv", append));
            output.println(toStringToFile());
            output.close();
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }
    public void saveInfo(){
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/events.csv", true));
            output.println(toStringToFile());
            output.close();
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }

    //toString, for ComboBox info
    @Override
    public String toString() {
        return this.name;
    }

    //to String, used when program saves the info to the csv
    public String toStringToFile(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String out = ID + "," +
                name + "," +
                simpleDateFormat.format(date);
        for (Venue i:venue){
            out += "," + i.getID();
        }
        return out;
    }


    //Setter methods
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    //Getter methods
    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public Date getDate() {
        return date;
    }
    public String getDateInString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
    public ArrayList<Venue> getVenue() {
        return venue;
    }

    //Methods to change and check for things in the venue array
    public void venueAdd(Venue v){
        venue.add(v);
    }
    public void venueClear(){
        venue.clear();
    }
    public void venueRemove(Venue v){
        venue.remove(v);
    }
    public void venueClear(int index) {
        venue.remove(index);
    }
    public boolean inVenueArray(Venue venue){
        for (Venue v:this.venue){
            if (v.equals(venue)){
                return true;
            }
        }
        return false;
    }
    public boolean venueHasPeople(){
        for (Venue v:this.venue){
            if (v.getVisitors().size() > 0){
                return true;
            }
        }
        return false;
    }


}
