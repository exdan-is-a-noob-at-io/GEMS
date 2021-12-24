module GEMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    opens sample.Controller;
    opens sample.View;
    opens sample.Model;
    opens sample;
}