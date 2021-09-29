module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires com.google.gson;
    requires javafx.web;
    requires java.desktop;

    opens org.example to javafx.fxml;
    exports org.example;
}