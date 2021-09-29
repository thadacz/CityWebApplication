package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

public class Service {

    private String country;
    private String currency;

    public Service(String country) {
        this.country = country;
    }

    public String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getWeather(String city) throws Exception {
        String weatherUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=5ecba12819880eb1c447731b37194d03";
        URL weatherURL = new URL(weatherUrl);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(weatherURL.openStream()));
        String inputLine = readAllLinesWithStream(in);
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(inputLine);

        return "Weather: " + jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString() +
                "\nTemperature: " + jsonObject.getAsJsonObject("main").get("temp").toString() +
                "\nPressure: " + jsonObject.getAsJsonObject("main").get("pressure").toString() +
                "\nHumidity: " + jsonObject.getAsJsonObject("main").get("humidity").toString() +
                "\nWind deg: " + jsonObject.getAsJsonObject("wind").get("deg").toString() +
                "\nWind speed: " + jsonObject.getAsJsonObject("wind").get("speed").toString();
    }

    public Double getRateFor(String cur) throws Exception {
        currency = cur;
        String currencyUrl = "https://api.exchangerate.host/latest?base=PLN&symbols=" + currency;
        URL weatherURL = new URL(currencyUrl);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(weatherURL.openStream()));
        String inputLine = readAllLinesWithStream(in);
        JsonObject jsonObject = (JsonObject) JsonParser.parseString(inputLine);
        double rate = jsonObject.getAsJsonObject("rates").get(currency).getAsDouble();
        return rate;
    }

    public Double getNBPRate() {
        if (currency != null && !currency.equals("")) {
            try {
                Document doc = Jsoup.connect("http://www.nbp.pl/kursy/kursya.html").timeout(6000).get();
                for (Element row : doc.select(
                        "table.pad5 tr")) {
                    if (row.select("td:nth-of-type(1)").text().equals("")) {
                        continue;
                    } else if (currency.contains("")) {
                        final String currency2 =
                                row.select("td:nth-of-type(3)").text();
                        final String rate1 = currency2.replace(",", ".");
                        final double rate = Double.parseDouble(rate1);
                        return rate;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1.0;
    }
}
