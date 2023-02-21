module com.example.kasvipaivakirja_ohj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.kasvipaivakirja_ohj2 to javafx.fxml;
    exports com.example.kasvipaivakirja_ohj2;
}