package sample.Model;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Trace {
    private ArrayList<Person> people = new ArrayList<Person>();

    //constructor
    public Trace() throws ParseException, IOException {
        Scanner s = new Scanner(new File("src/sample/visit.csv"));
        while (s.hasNext()){
            people.add(new Person(s.nextLine()));
        }
    }


    //Returns an arraylist of person. It takes in the person's venue, and phone number/name.
    //Returns all the times where the person with that phone number visits the venue
    public ArrayList<Person> getPersonPhoneNumber(String phoneNumber, Venue v){
        ArrayList<Person> personArrayList = new ArrayList<Person>();
        for (Person p:people){
            //System.out.println(p);
            if (p.getEqrCode().getContact_no().equals(phoneNumber) && p.getVenue().equals(v)){
                personArrayList.add(p);
            }
        }
        return personArrayList;
    }
    public ArrayList<Person> getPersonName(String name, Venue v){
        ArrayList<Person> personArrayList = new ArrayList<Person>();
        for (Person p:people){
            //System.out.println(p);
            if (p.getEqrCode().getName().equals(name) && p.getVenue().equals(v)){
                personArrayList.add(p);
            }
        }
        return personArrayList;
    }

    //returns all the people in contact with that person in the selected time period
    public ArrayList<Person> getInContact(Person person, int hoursToTraceBy){
        ArrayList<Person> peopleInContact = new ArrayList<Person>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(person.getEntryDateTime());
        calendar.add(Calendar.HOUR_OF_DAY, hoursToTraceBy);
        Date traceEndTime = calendar.getTime();

        for (Person p:people){
            if (!p.getVenue().equals(person.getVenue())) continue;
            if (p.getExitDateTime().before(person.getEntryDateTime())) continue;
            if (p.getEntryDateTime().after(traceEndTime)) continue;
            peopleInContact.add(p);
        }

        return peopleInContact;
    }
}
