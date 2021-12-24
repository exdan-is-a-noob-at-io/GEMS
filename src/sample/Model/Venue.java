package sample.Model;

import sample.Controller.SampleController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Venue {
    private String ID;
    private String name;
    private String room_no;
    private int max_capacity;
    private int curr_capacity;
    private ArrayList<eQRCode> visitors;

    //Constructor
    public Venue(String name, String room_no, int max_capacity) {
        if (!updateID()){
            //update unsuccessful;
            SampleController.tooManyVenues();
            return;
        }

        this.name = name;
        this.room_no = room_no;
        this.max_capacity = max_capacity;
        this.curr_capacity = 0;
    }

    public Venue(String ID, String name, String room_no, int max_capacity) {
        this.ID = ID;
        this.name = name;
        this.room_no = room_no;
        this.max_capacity = max_capacity;
    }

    //updates the ID in the Venue Object. Returns true if the update is successful, otherwise false
    private boolean updateID(){
        try{
            Scanner s = new Scanner(new File("src/sample/venueRunningNo.txt"));

            if (!s.hasNext()){ //if file empty
                this.ID = "00001";
                PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/venueRunningNo.txt"));
                output.print("00002");
                s.close();
                output.close();
                return true;
            }
            String num = s.next();
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/venueRunningNo.txt"));
            if (Integer.parseInt(num) <= 0){ //if the integer is invalid and less than 1
                this.ID = "00001";
                output.print("00002");
            }
            else if (Integer.parseInt(num) > 99999){ //if the integer is invalid as all ids from 1 to 99999 has been used.
                s.close();
                output.print("100000");
                output.close();
                return false;
            }
            else{ //if integer is valid
                this.ID = num;
                num = "" + (Integer.parseInt(num) + 1);
                while (num.length() < 5){
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
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/venues.csv", append));
            output.println(toStringToFile());
            output.close();
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }
    public void saveInfo(){
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/venues.csv", true));
            output.println(toStringToFile());
            output.close();
        }
        catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }

    //to String, when I am saving to a csv
    public String toStringToFile(){
        String out = ID + "," +
                name + "," +
                room_no + "," +
                max_capacity;
        return out;
    }

    //toString, for the comboBox display
    @Override
    public String toString() {
        return name;
    }


    //Getter Methods
    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getRoom_no() {
        return room_no;
    }
    public int getMax_capacity() {
        return max_capacity;
    }
    public int getCurr_capacity() {
        return curr_capacity;
    }
    public ArrayList<eQRCode> getVisitors() {
        return visitors;
    }

    //Setter Methods

    public void setName(String name) {
        this.name = name;
    }
    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }
    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }
    public void setCurr_capacity(int curr_capacity) {
        this.curr_capacity = curr_capacity;
    }

    //Methods to change the visitors array
    public void visitorsAdd(eQRCode v){
        visitors.add(v);
    }
    public void visitorsClear(){
        visitors.clear();
    }
    public void visitorsRemove(eQRCode eQRCode){
        visitors.remove(eQRCode);
    }
    public void visitorsClear(int index) {
        visitors.remove(index);
    }
    public boolean inVenueArray(eQRCode eQRCode){
        for (eQRCode e:this.visitors){
            if (e.equals(eQRCode)){
                return true;
            }
        }
        return false;
    }
}
