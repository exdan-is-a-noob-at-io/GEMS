package sample.Model;

import java.util.Date;

//contains information for person object. Meant to be for the Trace TableView
public class PersonInfo {
    private String no;
    private String name;
    private String contact;
    private Event event;
    private Date entryDateTime;
    private Date exitDateTime;

    public PersonInfo(Person person, int no) {
        this.no = no + "";
        this.name = person.getEqrCode().getName();
        this.contact = person.getEqrCode().getContact_no();
        this.event = person.getEqrCode().getEvent();
        this.entryDateTime = person.getEntryDateTime();
        this.exitDateTime = person.getExitDateTime();
    }

    @Override
    public boolean equals(Object obj) {
        try{
            PersonInfo p = (PersonInfo)obj;
            return (name.equals(p.name) &&
                    contact.equals(p.contact) &&
                    event.equals(p.event) &&
                    entryDateTime.equals(p.entryDateTime) &&
                    exitDateTime.equals(p.exitDateTime));
        }
        catch (ClassCastException e){
            return false;
        }
    }

    //getter and setter
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
}
