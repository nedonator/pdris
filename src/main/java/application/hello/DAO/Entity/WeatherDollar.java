package application.hello.DAO.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WeatherDollar")
public class WeatherDollar {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String dollar;
    private String date;
    private String temperature;

    protected WeatherDollar() {}

    public WeatherDollar(String dollar, Date date, String temperature) {
        this.dollar = dollar;
        this.temperature = temperature;
    }

    public String getDollar() {
        return dollar;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }
}