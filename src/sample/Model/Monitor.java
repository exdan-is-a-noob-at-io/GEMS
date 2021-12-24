package sample.Model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Monitor {
    private static ArrayList<Person> people = new ArrayList<Person>();

    //add a person
    public static void addPerson(Person p){
        people.add(p);
    }

    //check if a person is going to the same venue in the same event
    public static boolean checkRepeatPerson(Person p){
        for (Person person:people){
            if (p.samePerson(person)){
                return true;
            }
        }
        return false;
    }

    //check out a person; also checks if person is in the people arraylist
    public static boolean checkoutPerson(Person p){
        try{
            for (int i = 0; i < people.size(); ++i){
                Person person = people.get(i);
                if (p.samePerson(person)){
                    people.remove(i);

                    p.setExitDateTime(new Date());
                    PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/visit.csv", true));
                    output.println(p);
                    output.close();
                    return true;
                }
            }

            return false;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //mass checkout people who are in an event and venue
    public static boolean MasscheckoutPeople(Event event, Venue venue){
        try{
            for (int i = people.size() - 1; i >= 0; --i){
                Person person = people.get(i);
                if (person.getVenue().equals(venue) && person.getEqrCode().getEvent().equals(event)){
                    person.setExitDateTime(new Date());
                    PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/visit.csv", true));
                    output.println(person);
                    output.close();
                    venue.setCurr_capacity(venue.getCurr_capacity() - 1);

                    people.remove(person);
                }
            }

            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //checkout everyone, done for when the application is shutting down
    public static boolean checkoutAll(){
        try{
            PrintWriter output = new PrintWriter(new FileOutputStream("src/sample/visit.csv", true));
            for (Person person:people){
                person.setExitDateTime(new Date());
                output.println(person);
            }
            output.close();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    //Getter method
    public static ArrayList<Person> getPeople() {
        return people;
    }
}
