package application.hello;

import application.hello.presentation.Example;
import com.fasterxml.jackson.databind.ObjectMapper;
//import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Component
public class WeatherDollarResponse {
    private static final String key = "0fa2b427096b8237f2388f62e847684d";
    private static final String latitude = "55.0000";
    private static final String longitude = "37.5000";

    private Double CoefA = 0.;
    private Double CoefB = 0.;
    private final ArrayList<Double> tempList;
    private final ArrayList<Double> rateList;

    private final RestTemplate restTemplate;
    private final RBKResponse ratesHandler;

    @Autowired
    public WeatherDollarResponse(RestTemplate restTemplate, RBKResponse ratesHandler) {
        this.restTemplate = restTemplate;
        this.ratesHandler = ratesHandler;
        this.tempList = new ArrayList<>(30);
        this.rateList = new ArrayList<>(30);
    }

    public Double predict(Double temperature) throws IOException, ParseException {
        getCoeffs();
        double answer = CoefA + temperature * CoefB;
        return answer > 0 ? answer : 0.0;
    }

    private void getCoeffs() throws IOException, ParseException {
        getData();
        CoefB = findB(tempList, rateList);
        CoefA = findA(tempList, rateList, CoefB);
    }

    private void getData() throws IOException, ParseException {
        ArrayList<AbstractMap.SimpleEntry<Double, Long>> pairsRateList = ratesHandler.getValues();
        for (AbstractMap.SimpleEntry<Double, Long> pair : pairsRateList) {
            String url = "https://api.darksky.net/forecast/" + key + "/" + latitude + "," +
                    longitude + "," + pair.getValue() +
                    "?units=auto&exclude=currently,hourly,flags";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            assert (response.getStatusCode().equals(HttpStatus.OK));
            Example example = new ObjectMapper().readValue(response.getBody(), Example.class);
            Double temperature = example.getDaily().getData().get(0).getTemperatureHigh();
            tempList.add(temperature);
            rateList.add(pair.getKey());
        }
    }

    private Double getMean(ArrayList<Double> arr) {
        double sumArr = 0.;
        double elementCount = 0.;
        for (Double elem : arr) {
            sumArr += elem;
            elementCount++;
        }
        return (Math.abs(elementCount - 0.00000001) < 0)? 99999.0: sumArr/(Math.abs(elementCount)+0.00000001);
    }

    private Double findB(ArrayList<Double> tempList, ArrayList<Double> rateList) {
        double up = 0.;
        double down = 0.;
        for (int i = 0; i < tempList.size(); i++) {
            up += (tempList.get(i) - getMean(tempList)) * (rateList.get(i) - getMean(rateList));
            down += Math.pow(tempList.get(i) - getMean(tempList), 2);
        }
        return (Math.abs(down - 0.00000001) < 0)? 99999.0: up / (Math.abs(down)+0.00000001);
    }

    private Double findA(ArrayList<Double> tempList, ArrayList<Double> rateList, Double b) {
        return getMean(rateList) - b * getMean(tempList);
    }
}