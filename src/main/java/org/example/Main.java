package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class Main
        extends Application {

    public static void main(String[] args) throws Exception {

        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();

        System.out.println(rate1);
        System.out.println(rate2);
        System.out.println(weatherJson);
        launch();
    }
    // ------------------------------------------------GUI------------------------------------------------------------

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Launch");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField country;
    @FXML
    private TextField currency;
    @FXML
    private TextField city;

    public void openNewStage(ActionEvent actionEvent) throws IOException {
        try {
            String countryGetter = country.getText();
            String currencyGetter = currency.getText();
            String cityGetter = city.getText();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));
            Service service = new Service(countryGetter);
            ((Label) root.lookup("#weatherText")).setText(service.getWeather(cityGetter));
            ((Label) root.lookup("#currencyText")).setText(String.valueOf(service.getRateFor(currencyGetter)));
            ((Label) root.lookup("#plnText")).setText(String.valueOf(service.getNBPRate()));
            ((WebView) root.lookup("#webView")).getEngine().load("https://en.wikipedia.org/wiki/" + cityGetter);
            Scene scene = new Scene(root);
            stage.setTitle("Application");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }
}
