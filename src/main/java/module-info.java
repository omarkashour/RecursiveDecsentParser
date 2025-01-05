module com.example.compilerprojectgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.compilerprojectgui to javafx.fxml;
    exports com.example.compilerprojectgui;
}