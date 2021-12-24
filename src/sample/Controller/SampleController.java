package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import sample.Model.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SampleController {
    @FXML
    public ComboBox monitorEventComboBox;
    @FXML
    public ComboBox monitorLocationComboBox;
    @FXML
    public RadioButton monitorCheckInRadioButton;
    @FXML
    public RadioButton monitorCheckOutRadioButton;
    @FXML
    public Button monitorScanButton;
    @FXML
    public Button monitorMassCheckoutButton;
    @FXML
    public ImageView monitorQRCodeImageView;
    @FXML
    public Label monitorCurrentCapacityLabel;
    @FXML
    public Label monitorPercentFilledLabel;
    @FXML
    public Label monitorMaxCapacityLabel;
    @FXML
    public PieChart monitorPieChart;
    @FXML
    public ComboBox eventsEventComboBox;
    @FXML
    public ComboBox eventsVenueComboBox;
    @FXML
    public Button eventsVenuePlusButton;
    @FXML
    public Button eventsVenueMinusButton;
    @FXML
    public Label eventsDateLabel;
    @FXML
    public TableView eventsTableView;
    @FXML
    public TableColumn eventsVenueColumn;
    @FXML
    public TableColumn eventsVenueIDColumn;
    @FXML
    public TableColumn eventsRoomNumberColumn;
    @FXML
    public TableColumn eventsMaxCapacityColumn;
    @FXML
    public Button eventsCreateNewEventButton;
    @FXML
    public Button eventsDeleteEventButton;
    @FXML
    public Button eventsEditEventButton;
    @FXML
    public Button eventsSaveEventButton;
    @FXML
    public TextField eventsEventNameTextField;
    @FXML
    public DatePicker eventsDateDatePicker;
    @FXML
    public Button eventsCreateEditButton;
    @FXML
    public Spinner traceContactDurationSpinner;
    @FXML
    public PasswordField traceKeyTextField;
    @FXML
    public ComboBox traceVenueComboBox;
    @FXML
    public ComboBox traceTraceByComboBox;
    @FXML
    public Button traceTraceButton;
    @FXML
    public TableView traceTableView;
    @FXML
    public TableColumn traceNoTableColumn;
    @FXML
    public TableColumn traceNameTableColumn;
    @FXML
    public TableColumn traceContactTableColumn;
    @FXML
    public TableColumn traceEventTableColumn;
    @FXML
    public TableColumn traceStartTimeTableColumn;
    @FXML
    public TableColumn traceEndTimeTableColumn;
    @FXML
    public TextField traceIndentificationTextField;
    @FXML
    public TextField settingsVenueTextField;
    @FXML
    public TextField settingsRoomNumberTextField;
    @FXML
    public Spinner settingsMaxCapacitySpinner;
    @FXML
    public TableView settingsVenueTableView;
    @FXML
    public TableColumn settingsVenueIDColumn;
    @FXML
    public TableColumn settingsVenueColumn;
    @FXML
    public TableColumn settingsRoomNoColumn;
    @FXML
    public TableColumn settingsMaxPaxColumn;
    @FXML
    public Button settingsAddButton;
    @FXML
    public Button settingsEditButton;
    @FXML
    public Button settingsDeleteButton;
    @FXML
    public Button settingsSaveListButton;
    @FXML
    public PasswordField settingsEQRCodeKeyTextField;
    @FXML
    public RadioButton settingsSimpleEQRCodeRadioButton;
    @FXML
    public TextField settingsNameTextField;
    @FXML
    public TextField settingsContactNumberTextField;
    @FXML
    public ComboBox settingsVenuesDisallowedComboBox;
    @FXML
    public TableView settingsDisallowedVenuesTableView;
    @FXML
    public TableColumn settingsVenueIDQRCodeColumn;
    @FXML
    public TableColumn settingsVenueQRCodeColumn;
    @FXML
    public Button settingsVenuePlusButton;
    @FXML
    public Button settingsVenueMinusButton;
    @FXML
    public RadioButton settingsMultipleQRCodeRadioButton;
    @FXML
    public Button settingsBrowseQRCodeButton;
    @FXML
    public TextField settingsFilePathTextField;
    @FXML
    public TextField settingsSaveTextField;
    @FXML
    public Button settingsBrowseSaveLocationButton;
    @FXML
    public Button settingsGenerateQRCodeButton;
    @FXML
    public HBox eventsEventDetailsHBox;
    @FXML
    public ComboBox settingsEventComboBox;
    @FXML
    public Label monitorScanOutcomeLabel;

    public ToggleGroup QRCodeNumber, CheckInStatus;

    private static ObservableList<Venue> venues;
    private static ObservableList<Event> events;
    private static ObservableList<Venue> currentDisallowedVenues;
    private static ObservableList<Venue> monitorVenues;
    public static ArrayList<String> names;
    public static ArrayList<String> phoneNumbers;
    private Event eventsSelectedEvent;
    private Event monitorSelectedEvent;
    private Venue monitorSelectedVenue;
    static boolean fileSafeToLoad = true;

    private boolean singleQRCode;

    //run before program closes
    public static void onClose(){
        boolean append = false;
        for (Event i:events){
            i.saveInfo(append);
            append = true;
        }
        append = false;
        for (Venue i:venues){
            i.saveInfo(append);
            append = true;
        }
    }

    public void initialize() {
        venues = FXCollections.observableArrayList();
        events = FXCollections.observableArrayList();
        currentDisallowedVenues = FXCollections.observableArrayList();
        monitorVenues = FXCollections.observableArrayList();
        names = new ArrayList<String>();
        phoneNumbers = new ArrayList<String>();
        loadEventsVenues();

        settingsVenueColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("name"));
        settingsVenueIDColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("ID"));
        settingsRoomNoColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("room_no"));
        settingsMaxPaxColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("max_capacity"));
        settingsVenueTableView.setItems(venues);
        settingsMaxCapacitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000));
        traceContactDurationSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000));

        settingsVenueQRCodeColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("name"));
        settingsVenueIDQRCodeColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("ID"));
        settingsDisallowedVenuesTableView.setItems(currentDisallowedVenues);

        traceNoTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("no"));
        traceNameTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("name"));
        traceContactTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("contact"));
        traceEventTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("event"));
        traceStartTimeTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("entryDateTime"));
        traceEndTimeTableColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("exitDateTime"));

        eventsVenueComboBox.setItems(venues);
        eventsEventComboBox.setItems(events);
        monitorEventComboBox.setItems(events);
        settingsEventComboBox.setItems(events);
        traceVenueComboBox.setItems(venues);
        settingsVenuesDisallowedComboBox.setItems(venues);
        eventsEventDetailsHBox.setVisible(false);
        eventsTableView.setEditable(false);
        settingsVenueTableView.setEditable(false);

        setSingleQRCodeEnabled(false);
        settingsBrowseQRCodeButton.setDisable(true);
        settingsFilePathTextField.setEditable(false);
        settingsGenerateQRCodeButton.setDisable(true);
        settingsSaveTextField.setEditable(false);
        settingsFilePathTextField.setEditable(false);

        QRCodeNumber = new ToggleGroup();
        settingsSimpleEQRCodeRadioButton.setToggleGroup(QRCodeNumber);
        settingsMultipleQRCodeRadioButton.setToggleGroup(QRCodeNumber);
        CheckInStatus = new ToggleGroup();
        monitorCheckInRadioButton.setToggleGroup(CheckInStatus);
        monitorCheckOutRadioButton.setToggleGroup(CheckInStatus);
        //settingsMaxCapacitySpinner.setEditable(true);
        //traceContactDurationSpinner.setEditable(true);

        traceTraceByComboBox.setItems(FXCollections.observableArrayList("Contact Number", "Name"));
    }

    //loads events and venues, done on launch
    public void loadEventsVenues(){
        try{
            BufferedReader s_venues = new BufferedReader(new FileReader("src/sample/venues.csv"));
            BufferedReader s_events = new BufferedReader(new FileReader("src/sample/events.csv"));

            String line = "dummy";
            while (line != null){
                line = s_venues.readLine();
                if (line == null) continue;
                String[] tokens = line.split(",");
                venues.add(new Venue(tokens[0], tokens[1], tokens[2], Integer.parseInt(tokens[3])));
            }

            line = "dummy";
            while (line != null){
                line = s_events.readLine();
                if (line == null) continue;
                String[] tokens = line.split(",");

                Event eventToAdd = new Event(tokens[0], tokens[1], new SimpleDateFormat("dd/MM/yyyy").parse(tokens[2]));
                events.add(eventToAdd);
                for (int i = 3; i < tokens.length; ++i){
                    String ID = tokens[i];
                    for (Venue venue:venues){
                        if (ID.equals(venue.getID())){
                            if (venue == null) break;
                            eventToAdd.venueAdd(venue);
                            break;
                        }
                    }
                }
            }


            s_venues.close();
            s_events.close();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**monitor**/
    //Function to reload values when event changes
    private void monitorReloadVenues(){
        if (monitorEventComboBox.getValue() == null){
            monitorLocationComboBox.setItems(FXCollections.observableArrayList());
            monitorSelectedVenue = null;
            return;
        }
        monitorSelectedEvent = (Event) monitorEventComboBox.getValue();

        monitorVenues = FXCollections.observableArrayList( monitorSelectedEvent.getVenue() );

        monitorLocationComboBox.setItems(monitorVenues);
        monitorSetData();
    }

    //set data about amt of people filled
    private void monitorSetData(){
        if (monitorSelectedVenue == null || monitorSelectedEvent == null){
            monitorPieChart.setData(FXCollections.observableArrayList());
            monitorMaxCapacityLabel.setText("??");
            monitorCurrentCapacityLabel.setText("??");
            //System.out.println("idk");
            return;
        }
        double filled = ((double)monitorSelectedVenue.getCurr_capacity())/monitorSelectedVenue.getMax_capacity()*100;
        monitorMaxCapacityLabel.setText(monitorSelectedVenue.getMax_capacity() + "");
        monitorCurrentCapacityLabel.setText(monitorSelectedVenue.getCurr_capacity() + "");
        monitorPercentFilledLabel.setText(String.format("%.2f", filled) + "% filled");
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Current Capacity", filled),
                new PieChart.Data("Capacity Left", 100-filled)
        );

        //data.get(0).getNode().setStyle("-fx-pie-color: " + "#00FFFF" + ";");
        //data.get(1).getNode().setStyle("-fx-pie-color: " + "#FFFF00" + ";");

        monitorPieChart.setData(data);
        monitorPieChart.setClockwise(false);
    }

    @FXML
    public void monitorEventSelected(ActionEvent event){
        monitorReloadVenues();
    }

    @FXML
    public void monitorVenueSelected(ActionEvent event){
        if (monitorLocationComboBox.getValue() == null){
            monitorSelectedVenue = null;
            return;
        }

        monitorSelectedVenue = (Venue) monitorLocationComboBox.getValue();
        monitorSetData();
    }

    @FXML
    public void monitorScanOnAction(ActionEvent event){
        if (monitorEventComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No event selected!");
            a.setContentText("Please select an event");
            a.show();
            return;
        }
        if (monitorLocationComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No venue selected!");
            a.setContentText("Please select an venue to add");
            a.show();
            return;
        }
        try{
            monitorSelectedEvent = (Event)monitorEventComboBox.getValue();
            monitorSelectedVenue = (Venue)monitorLocationComboBox.getValue();

            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("."));
            File selectedFile = fc.showOpenDialog(null);
            if (selectedFile == null) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No file selected!");
                a.setContentText("Please select an file to read from");
                a.show();
                return;
            }
            String qrCodeString = new eQRCode().decodeQRCodeAES(selectedFile);
            System.out.println(qrCodeString);
            if (qrCodeString == null) return;

            eQRCode eqrCode = new eQRCode(qrCodeString, "NUSHigh2020");

            if (eqrCode.getEvent() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No such event exists!");
                a.setContentText("User is registered to an event which does not exist");
                a.show();
                return;
            }

            if (monitorCheckInRadioButton.isSelected()){
                if (eqrCode.getEvent() != monitorSelectedEvent){
                    monitorScanOutcomeLabel.setText("Access Denied");
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Person Cannot Enter This Event");
                    a.setContentText("Person is registered for event " + eqrCode.getEvent().getName());
                    a.show();
                    return;
                }
                for (String i:eqrCode.getDisallowedVenues()){
                    if (i.equals(monitorSelectedVenue.getID())){
                        monitorScanOutcomeLabel.setText("Access Denied");
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setHeaderText("Venue Disallowed");
                        a.setContentText("Person cannot enter this venue");
                        a.show();
                        return;
                    }
                }

                if (monitorSelectedVenue.getMax_capacity() <= monitorSelectedVenue.getCurr_capacity()){
                    monitorScanOutcomeLabel.setText("Full Capacity");
                    System.out.println(eqrCode.getDisallowedVenues());
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Full Capacity");
                    a.setContentText("This venue is at full capacity!");
                    a.show();
                    return;
                }

                Person person = new Person(eqrCode, monitorSelectedVenue);

                if (Monitor.checkRepeatPerson(person)){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Person already registered");
                    a.setContentText("This person has already registered to this venue!");
                    a.show();
                    return;
                }

                Date d = monitorSelectedEvent.getDate();
                LocalDate eventLocalDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentLocalDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (!eventLocalDate.isEqual(currentLocalDate)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Event Date is not today!");
                    alert.setContentText("Allow Person to Enter Event that is not today?");
                    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(okButton, noButton);
                    alert.showAndWait().ifPresent(responce -> {
                        if (responce == okButton) {
                            monitorSelectedVenue.setCurr_capacity(monitorSelectedVenue.getCurr_capacity() + 1);
                            Monitor.addPerson(person);
                            monitorSetData();

                            try {
                                monitorQRCodeImageView.setImage(new Image( new FileInputStream(selectedFile.getAbsolutePath()) ));
                                monitorScanOutcomeLabel.setText("Scan Successful");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    return;
                }

                monitorSelectedVenue.setCurr_capacity(monitorSelectedVenue.getCurr_capacity() + 1);
                Monitor.addPerson(person);
                monitorSetData();

                monitorQRCodeImageView.setImage(new Image( new FileInputStream(selectedFile.getAbsolutePath()) ));
                monitorScanOutcomeLabel.setText("Scan Successful");

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Successful Check In!");
                a.show();

            }
            else if (monitorCheckOutRadioButton.isSelected()){
                Person person = new Person(eqrCode, monitorSelectedVenue);
                if (!Monitor.checkoutPerson(person)){
                    monitorScanOutcomeLabel.setText("Checkout Unsuccessful");
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Person has not entered this Event and Venue");
                    a.setContentText("This person is not registered to this Event and Venue!");
                    a.show();
                    return;
                }
                monitorSelectedVenue.setCurr_capacity(monitorSelectedVenue.getCurr_capacity() - 1);
                monitorSetData();

                monitorQRCodeImageView.setImage(new Image( new FileInputStream(selectedFile.getAbsolutePath()) ));
                monitorScanOutcomeLabel.setText("Checkout Successful");

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Successful Checkout!");
                a.show();
            }
            else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Unselected Option");
                a.setContentText("Please select if you are checking in or out");
                a.show();
            }
        }
        catch (ClassCastException | IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    public void monitorMassCheckoutOnAction(ActionEvent event){
        if (monitorEventComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No event selected!");
            a.setContentText("Please select an event");
            a.show();
            return;
        }
        if (monitorLocationComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No venue selected!");
            a.setContentText("Please select an venue to add");
            a.show();
            return;
        }
        try{
            monitorSelectedEvent = (Event)monitorEventComboBox.getValue();
            monitorSelectedVenue = (Venue)monitorLocationComboBox.getValue();
            Monitor.MasscheckoutPeople(monitorSelectedEvent, monitorSelectedVenue);

            monitorScanOutcomeLabel.setText("Checkout Successful");
            monitorSetData();

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Successful Mass Checkout!");
            a.show();
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    /**events**/

    //function to reload the venue combo box, tableview, date label, and hide the bottom vbox when the event is changed
    private void eventsreloadVenues(){
        if (eventsEventComboBox.getValue() == null){
            eventsTableView.setItems(FXCollections.observableArrayList());
            return;
        };
        eventsSelectedEvent = (Event) eventsEventComboBox.getValue();
        eventsDateLabel.setText(eventsSelectedEvent.getDateInString());

        eventsVenueColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("name"));
        eventsVenueIDColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("ID"));
        eventsRoomNumberColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("room_no"));
        eventsMaxCapacityColumn.setCellValueFactory(new PropertyValueFactory<Venue, String>("max_capacity"));
        eventsTableView.setItems(FXCollections.observableArrayList( eventsSelectedEvent.getVenue() ));
        eventsTableView.refresh();
    }

    @FXML
    public void eventsEventSelected(ActionEvent event){
        try{
            eventsreloadVenues();

            eventsEventDetailsHBox.setVisible(false);
            eventsTableView.setEditable(false);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        catch(Exception e){ //just in case
            e.printStackTrace();
        }
    }
    @FXML
    public void eventsAddVenueOnAction(ActionEvent event){
        try{
            if (eventsSelectedEvent == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No event selected!");
                a.setContentText("Please select an event");
                a.show();
                return;
            }
            if (eventsVenueComboBox.getValue() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No venue selected!");
                a.setContentText("Please select an venue to add");
                a.show();
                return;
            }
            Venue eventsSelectedVenue = (Venue) eventsVenueComboBox.getValue();
            if (eventsSelectedEvent.inVenueArray(eventsSelectedVenue)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Venue is already selected in Event");
                a.setContentText("The venue you want to add is already selected in the event");
                a.show();
                return;
            }
            eventsSelectedEvent.venueAdd(eventsSelectedVenue);
            eventsreloadVenues();
            monitorReloadVenues();
            eventsTableView.refresh();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void eventsMinusVenueOnAction(ActionEvent event){
        try{
            if (eventsSelectedEvent == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No event selected!");
                a.setContentText("Please select an event");
                a.show();
                return;
            }
            if (eventsVenueComboBox.getValue() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No venue selected!");
                a.setContentText("Please select an venue to remove");
                a.show();
                return;
            }
            Venue eventsSelectedVenue = (Venue) eventsVenueComboBox.getValue();
            if (!eventsSelectedEvent.inVenueArray(eventsSelectedVenue)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Venue selected not in Event");
                a.setContentText("The venue you want to remove is not in the event");
                a.show();
                return;
            }
            eventsSelectedEvent.venueRemove(eventsSelectedVenue);
            eventsreloadVenues();
            monitorReloadVenues();
            eventsTableView.refresh();
        }
        catch(Exception e){ //should not have exception here, just in case
            System.out.println(e);
        }
    }
    @FXML
    public void eventsCreateNewEventOnAction(ActionEvent event){
        eventsEventDetailsHBox.setVisible(true);
        eventsTableView.setEditable(true);
        eventsCreateEditButton.setText("Create");
    }
    @FXML
    public void eventsDeleteEventOnAction(ActionEvent event){
        if (eventsSelectedEvent == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No event Selected!");
            a.setContentText("Please select an event to remove");
            a.show();
            return;
        }
        if (eventsSelectedEvent.getVenue().size() != 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Event could not be removed!");
            a.setContentText("Event could not be removed! Please try again!");
            a.show();
            return;
        }
        if (!events.remove(eventsSelectedEvent)){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Event could not be removed!");
            a.setContentText("Event could not be removed! Please try again!");
            a.show();
            return;
        }
        else{
            events.remove(eventsSelectedEvent);
            eventsreloadVenues();
            eventsSelectedEvent = null;
            eventsEventComboBox.setItems(events);
            monitorEventComboBox.setItems(events);
            settingsEventComboBox.setItems(events);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Event removed!");
            a.show();
            return;
        }
    }
    @FXML
    public void eventsEditEventOnAction(ActionEvent event){
        if (eventsSelectedEvent != null){
            eventsTableView.setEditable(true);
            eventsEventDetailsHBox.setVisible(true);
            eventsCreateEditButton.setText("Edit");
        }
        else{
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No event selected!");
            a.setContentText("Please make a section on the event that you want to edit!");
            a.show();
            return;
        }
    }
    @FXML
    public void eventsSaveEventOnAction(ActionEvent event){
        boolean append = false;
        for (Event i:events){
            i.saveInfo(append);
            append = true;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Information Written!");
        a.show();
        return;
    }
    @FXML
    public void eventsSetEventDetailsOnAction(ActionEvent event){
        if (eventsEventNameTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("No name inputted!");
            a.setContentText("Please fill in the required fields.");
            a.show();
            return;
        }
        for (Event i:events){
            if ( i.getName().equals(eventsEventNameTextField.getText()) ){
                if (eventsCreateEditButton.getText().equals("Edit")){
                    if (!i.getName().equals(eventsSelectedEvent.getName())){
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setHeaderText("Event Name Already Used");
                        a.setContentText("Please choose a unique event name.");
                        a.show();
                        return;
                    }
                }
                else{
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("Event Name Already Used");
                    a.setContentText("Please choose a unique event name.");
                    a.show();
                    return;
                }
            }
        }
        if (eventsDateDatePicker.getValue() == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("No date inputted!");
            a.setContentText("Please fill in the required fields.");
            a.show();
            return;
        }
        if (eventsCreateEditButton.getText().equals("Edit")){
            eventsSelectedEvent.setName(eventsEventNameTextField.getText());
            LocalDate localDate = eventsDateDatePicker.getValue();
            eventsSelectedEvent.setDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            eventsEventNameTextField.setText("");

            //reload combobox
            eventsEventComboBox.setItems(FXCollections.observableArrayList());
            eventsEventComboBox.setItems(events);
            eventsEventComboBox.setPromptText("Select Event");

            monitorEventComboBox.setItems(FXCollections.observableArrayList());
            monitorEventComboBox.setItems(events);
            monitorEventComboBox.setPromptText("Select Event");

            settingsEventComboBox.setItems(FXCollections.observableArrayList());
            settingsEventComboBox.setItems(events);
            settingsEventComboBox.setPromptText("Select Event");
            eventsEventDetailsHBox.setVisible(false);
            eventsDateLabel.setText("");

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Event Edited!");
            a.show();
            return;
        }
        else{
            LocalDate localDate = eventsDateDatePicker.getValue();
            Event event1 = new Event(eventsEventNameTextField.getText(), Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            events.add(event1);
            eventsEventNameTextField.setText("");
            eventsEventComboBox.setItems(events);
            monitorEventComboBox.setItems(events);
            settingsEventComboBox.setItems(events);
            eventsEventDetailsHBox.setVisible(false);
            eventsDateLabel.setText("");

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Event Created!");
            a.show();
            return;
        }
    }

    /**trace**/
    @FXML
    public void traceTraceOnAction(ActionEvent event){
        if (!traceKeyTextField.getText().equals("NUSHigh2020")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Incorrect Key");
            a.setContentText("Your AES Key is incorrect!");
            a.show();
            return;
        }
        if (traceVenueComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Venue ComboBox empty");
            a.setContentText("Please Select a Venue to Trace By!");
            a.show();
            return;
        }
        if (traceTraceByComboBox.getValue() == null){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Trace Method ComboBox Incomplete");
            a.setContentText("Please select a Trace Method!");
            a.show();
            return;
        }
        if (traceIndentificationTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("No Name/Contact Provided");
            a.setContentText("Please fill in Name/Contact!");
            a.show();
            return;
        }
        try{
            int hoursToTraceBy = ((Integer) traceContactDurationSpinner.getValue());
            Venue venue = (Venue) traceVenueComboBox.getValue();
            String traceMethod = (String) traceTraceByComboBox.getValue();
            Trace t = new Trace();

            ArrayList<Person> personArrayList;
            if (traceMethod.equals("Contact Number")){
                if (!eQRCode.checkPhoneValid(traceIndentificationTextField.getText())){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("Invalid Contact Number");
                    a.setContentText("Please enter a valid phone number!");
                    a.show();
                    return;
                }
                personArrayList = t.getPersonPhoneNumber(traceIndentificationTextField.getText(), venue);
            }
            else{
                if (!eQRCode.checkNameValid(traceIndentificationTextField.getText())){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("Invalid Name");
                    a.setContentText("Please enter a valid Name!");
                    a.show();
                    return;
                }
                personArrayList = t.getPersonName(traceIndentificationTextField.getText(), venue);
            }
            if (personArrayList.size() == 0){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No such Person Exists in Database");
                a.setContentText("Please Ensure Venue and identification are correct");
                a.show();
                return;
            }

            ObservableList<PersonInfo> peopleInfo = FXCollections.observableArrayList();

            for (Person p:personArrayList){
                ArrayList<Person> peopleInContact = t.getInContact(p, hoursToTraceBy);
                for (Person q:peopleInContact){
                    //if (q.getEqrCode().getName().equals(p.getEqrCode().getName())) continue;
                    if (!peopleInfo.contains(new PersonInfo(q, peopleInfo.size()))){
                        peopleInfo.add(new PersonInfo(q, peopleInfo.size() + 1));
                    }
                }
            }

            traceTableView.setItems(peopleInfo);

        }
        catch (ClassCastException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**settings - event creation**/
    @FXML
    public void settingsAddVenuesOnAction(ActionEvent event){
        try{
            if (settingsVenueTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Venue Text Field is empty!");
                a.setContentText("Please fill in the required fields for successful addition");
                a.show();
                return;
            }

            for (Venue i:venues){
                if ( i.getName().equals(settingsVenueTextField.getText()) ){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("Venue Name Already Used");
                    a.setContentText("Please choose a unique Venue name.");
                    a.show();
                    return;
                }
            }

            if (!(Character.isLetter(settingsRoomNumberTextField.getText().charAt(0)) &&
                    settingsRoomNumberTextField.getText().length() == 1)){
                for (int i = 0; i < settingsRoomNumberTextField.getText().length(); ++i){
                    if (!Character.isDigit(settingsRoomNumberTextField.getText().charAt(i))){
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setHeaderText("Field is in incorrect format!");
                        a.setContentText("Room number must be either an integer, or a character. ");
                        a.show();
                        return;
                    }
                }
            }
            Venue venue = new Venue(settingsVenueTextField.getText(),
                    settingsRoomNumberTextField.getText(),
                    (Integer) settingsMaxCapacitySpinner.getValue());

            if (venue.getName() == null) return;
            venues.add(venue);
        }
        catch(ClassCastException e){
            System.out.println(e);
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Fields are empty!");
            a.setContentText("Please fill in the required fields for successful addition");
            a.show();
            return;
        }
        catch(StringIndexOutOfBoundsException e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Fields are empty!");
            a.setContentText("Please fill in the required fields for successful addition");
            a.show();
            return;
        }
        clearValuesVenue();
    }
    @FXML
    public void settingsEditVenuesOnAction(ActionEvent event){
        try{
            if (settingsVenueTableView.getSelectionModel().getSelectedIndex() == -1){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No Row Selected!");
                a.setContentText("Please select a row for successful edit");
                a.show();
                return;
            }
            if (settingsVenueTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Venue Text Field is empty!");
                a.setContentText("Please fill in the required fields for successful edit");
                a.show();
                return;
            }

            for (Venue i:venues){
                if ( i.getName().equals(settingsVenueTextField.getText()) ){
                    if (i.getName().equals( venues.get(settingsVenueTableView.getSelectionModel().getSelectedIndex()).getName() )) continue;
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("Venue Name Already Used");
                    a.setContentText("Please choose a unique Venue name.");
                    a.show();
                    return;
                }
            }

            if (!(Character.isLetter(settingsRoomNumberTextField.getText().charAt(0)) &&
                    settingsRoomNumberTextField.getText().length() == 1)){
                for (int i = 0; i < settingsRoomNumberTextField.getText().length(); ++i){
                    if (!Character.isDigit(settingsRoomNumberTextField.getText().charAt(i))){
                        Alert a = new Alert(Alert.AlertType.WARNING);
                        a.setHeaderText("Field is in incorrect format!");
                        a.setContentText("Room number must be either an integer, or a character. ");
                        a.show();
                        return;
                    }
                }
            }

            Venue venue = venues.get(settingsVenueTableView.getSelectionModel().getSelectedIndex());
            if ((Integer) settingsMaxCapacitySpinner.getValue() < venue.getCurr_capacity()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Invalid Max Capacity!");
                a.setContentText("Please ensure that Max capacity of venue is larger than current capacity ("
                        + venue.getCurr_capacity() + ")");
                a.show();
                return;
            }
            venue.setName(settingsVenueTextField.getText());
            venue.setMax_capacity((Integer) settingsMaxCapacitySpinner.getValue());
            venue.setRoom_no(settingsRoomNumberTextField.getText());
            settingsVenueTableView.refresh();
            clearValuesVenue();
        }
        catch(ClassCastException e){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Fields are empty!");
            a.setContentText("Please fill in the required fields for successful edit");
            a.show();
            return;
        }

    }
    @FXML
    public void settingsDeleteVenuesOnAction(ActionEvent event){
        if (settingsVenueTableView.getSelectionModel().getSelectedIndex() == -1){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("No Row Selected!");
            a.setContentText("Please select a row for successful deletion");
            a.show();
            return;
        }
        for (Event i:events){
            if (i.inVenueArray(venues.get(settingsVenueTableView.getSelectionModel().getSelectedIndex()))){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Selected Venue is in Event");
                a.setContentText("Please delete the Venue from the running Events to proceed with the deletion");
                a.show();
                return;
            }
        }
        venues.remove(settingsVenueTableView.getSelectionModel().getSelectedIndex());
    }
    @FXML
    public void settingsSaveVenuesOnAction(ActionEvent event){
        boolean append = false;
        for (Venue i:venues){
            i.saveInfo(append);
            append = true;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Information Written!");
        a.show();
        return;
    }
    //resets all comboboxes, textfields, labels, and tables for when a venue is added, deleted, or edited
    private void clearValuesVenue(){
        settingsVenueTextField.setText("");
        settingsRoomNumberTextField.setText("");
        eventsVenueComboBox.setItems(FXCollections.observableArrayList());
        eventsVenueComboBox.setItems(venues);
        settingsVenuesDisallowedComboBox.setItems(FXCollections.observableArrayList());
        settingsVenuesDisallowedComboBox.setItems(venues);
        traceVenueComboBox.setItems(FXCollections.observableArrayList());
        traceVenueComboBox.setItems(venues);
        eventsreloadVenues();
        monitorReloadVenues();
    }

    /**settings - eqrcode**/

    // Method for making appropriate changes when the radio button is selected. Could have used a listener but this method also helps
    //in the initialise function
    public void setSingleQRCodeEnabled(boolean bool){
        settingsEventComboBox.setDisable(!bool);
        settingsNameTextField.setDisable(!bool);
        settingsContactNumberTextField.setDisable(!bool);
        settingsVenuesDisallowedComboBox.setDisable(!bool);
        settingsVenuePlusButton.setDisable(!bool);
        settingsVenueMinusButton.setDisable(!bool);
        settingsBrowseQRCodeButton.setDisable(bool);
        singleQRCode = bool;
        settingsGenerateQRCodeButton.setDisable(false);
    }

    @FXML
    public void settingsSingleQRCodeOnAction(ActionEvent event){
        setSingleQRCodeEnabled(true);
    }
    @FXML
    public void settingsMultipleQRCodeOnAction(ActionEvent event){
        setSingleQRCodeEnabled(false);
    }
    @FXML
    public void settingsGenerateQRCodeOnAction(ActionEvent event){
        if (settingsEQRCodeKeyTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("AES Key Text Field Empty");
            a.setContentText("Please enter your AES Key");
            a.show();
            return;
        }
        if (!settingsEQRCodeKeyTextField.getText().equals("NUSHigh2020")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Unsupported AES Key");
            a.setContentText("Your AES Key is not supported in this Beta version!");
            a.show();
            return;
        }
        String AES_Key = settingsEQRCodeKeyTextField.getText();
        if (singleQRCode){
            if (settingsEventComboBox.getValue() == null){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No Event Selected");
                a.setContentText("Please select which event this person is attending");
                a.show();
                return;
            }
            if (!eQRCode.checkNameValid(settingsNameTextField.getText())){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Invalid Name");
                a.setContentText("Name may only consist of letters and whitespace, and must be at least 3 letters long");
                a.show();
                return;
            }
            if (!eQRCode.checkPhoneValid(settingsContactNumberTextField.getText())){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Invalid Phone Number");
                a.setContentText("Your phone number is in an incorrect format");
                a.show();
                return;
            }
            if (settingsSaveTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No Save Location");
                a.setContentText("Please select a location to save your file");
                a.show();
                return;
            }
            /*if (nameContactUsed(settingsNameTextField.getText(), settingsContactNumberTextField.getText())){
                return;
            }*/

            ArrayList<String> disallowedVenuesID = new ArrayList<String>();
            for (Venue i: currentDisallowedVenues){
                disallowedVenuesID.add(i.getID());
            }
            try{
                eQRCode eqrCode = new eQRCode( (Event)settingsEventComboBox.getValue(), settingsNameTextField.getText(),
                        settingsContactNumberTextField.getText(), disallowedVenuesID, AES_Key);
                eqrCode.generateQRCode(settingsSaveTextField.getText());
                /*names.add(eqrCode.getName());
                phoneNumbers.add(eqrCode.getContact_no());*/

                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("eQRCode Generated!");
                a.show();
                return;
            }
            catch(ClassCastException e){
                e.printStackTrace();
            }

        }
        else{
            fileSafeToLoad = true;
            if (settingsFilePathTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No File Selected");
                a.setContentText("Please select a csv file to load your data from");
                a.show();
                return;
            }
            if (settingsSaveTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("No Save Location");
                a.setContentText("Please select a location to save your file");
                a.show();
                return;
            }
            try{
                Scanner s = new Scanner(new File(settingsFilePathTextField.getText()));
                ArrayList<eQRCode> eQRCodes = new ArrayList<eQRCode>();
                while (s.hasNext() && fileSafeToLoad){
                    String line = s.nextLine();
                    eQRCode eqrCode = new eQRCode( line, AES_Key);
                    eQRCodes.add(eqrCode);
                    /*if (nameContactUsed(eqrCode.getName(), eqrCode.getContact_no())){
                        return;
                    }
                    names.add(eqrCode.getName());
                    phoneNumbers.add(eqrCode.getContact_no());*/
                }
                if (fileSafeToLoad){
                    for (eQRCode i:eQRCodes){
                        i.generateQRCode(settingsSaveTextField.getText());
                    }
                }
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("eQRCode Generated!");
                a.show();
                return;
            }
            catch(IOException e ){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("File Failed to Load");
                a.setContentText("Input file failed to load");
                a.show();
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void settingsAddVenueOnAction(ActionEvent event){
        try {
            if (settingsVenuesDisallowedComboBox.getValue() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No venue selected!");
                a.setContentText("Please select an venue to add");
                a.show();
                return;
            }
            Venue eventsSelectedDisallowedVenue = (Venue) settingsVenuesDisallowedComboBox.getValue();
            if (inDisallowedVenueArray(eventsSelectedDisallowedVenue)) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Venue is already Disallowed");
                a.setContentText("The venue you want to add is already disallowed");
                a.show();
                return;
            }
            currentDisallowedVenues.add(eventsSelectedDisallowedVenue);
            settingsDisallowedVenuesTableView.refresh();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void settingsMinusVenueOnAction(ActionEvent event){
        try{
            if (settingsVenuesDisallowedComboBox.getValue() == null){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No venue selected!");
                a.setContentText("Please select an venue to remove");
                a.show();
                return;
            }
            Venue eventsSelectedDisallowedVenue = (Venue) settingsVenuesDisallowedComboBox.getValue();
            if (!inDisallowedVenueArray(eventsSelectedDisallowedVenue)){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Venue selected is not Disallowed");
                a.setContentText("The venue you want to remove is already disallowed");
                a.show();
                return;
            }
            currentDisallowedVenues.remove(eventsSelectedDisallowedVenue);
            eventsTableView.refresh();
        }
        catch(Exception e){ //should not have exception here, just in case
            System.out.println(e);
        }
    }
    @FXML
    public void settingsBrowseQRCodeOnAction(ActionEvent event){
        try{
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("."));
            File selectedFile = fc.showOpenDialog(null);
            if (selectedFile != null) {
                settingsFilePathTextField.setText(selectedFile.getAbsolutePath() + "");
                return;
            }
            settingsFilePathTextField.setText("");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    @FXML
    public void settingsBrowseSaveLocationOnAction(ActionEvent event){
        try{
            DirectoryChooser dc = new DirectoryChooser();
            dc.setInitialDirectory(new File("."));
            File selectedFile = dc.showDialog(null);
            if (selectedFile != null) {
                settingsSaveTextField.setText(selectedFile.getAbsolutePath() + "");
                return;
            }
            settingsSaveTextField.setText("");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**Alerts that may be called from View Classes**/
    public static void showAlertNameUsed(String name){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Name Already Used (" + name + ")");
        a.setContentText("Please choose another name!");
        a.show();
        return;
    }

    public static void showAlertPhoneNumberUsed(String number){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Phone Number Already Used (" + number + ")");
        a.setContentText("Please choose another phone number!");
        a.show();
        return;
    }

    public static void showAlertFileSaveFail(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("File Failed to Save");
        a.setContentText("QRCode Image failed to save");
        a.show();
        return;
    }

    public static void showAlertInputCSVInvalidFormat(){
        fileSafeToLoad = false;
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("File in Invalid format");
        a.setContentText("Selected csv file has invalid format. File failed to load");
        a.show();
        return;
    }

    public static void tooManyVenues(){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Too Many Venues Generated!");
        a.setContentText("Venue ID has surpassed 99999! Venue not created!");
        a.show();
        return;
    }

    public static void tooManyEvents(){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Too Many Events Generated!");
        a.setContentText("Event ID has surpassed 99999! Event not created!");
        a.show();
        return;
    }

    public static void showAlertCannotDecryptQRCode(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("QR Code cannot be Decrypted");
        a.setContentText("Please try again");
        a.show();
    }

    /**Random Checks that involves data stored in this class (eg all venues, all events)**/
    public boolean inDisallowedVenueArray(Venue venue){
        for (Venue v:currentDisallowedVenues){
            if (v.equals(venue)){
                return true;
            }
        }
        return false;
    }

    /** This, together with the 2 errors in this function and thr arraylists names and phoneNumbers,
     *  are now obselete after a bugfix. However, I am still going to leave it in here. **/
    public boolean nameContactUsed(String name, String contact){
        if (names.contains(name)){
            showAlertNameUsed(name);
            return true;
        }
        if (phoneNumbers.contains(contact)){
            showAlertPhoneNumberUsed(contact);
            return true;
        }
        return false;
    }

    public static Event getEvent(String event_ID){
        for (Event i: events){
            if (i.getID().equals(event_ID)){
                return i;
            }
        }
        return null;
    }

    public static Venue getVenue(String venue_ID){
        for (Venue i: venues){
            if (i.getID().equals(venue_ID)){
                return i;
            }
        }
        return null;
    }

}
