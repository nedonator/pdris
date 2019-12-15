package application.hello;

import application.hello.presentation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class WeatherDollarController {
    private final WeatherDollarResponse response;

    @Autowired
    public WeatherDollarController(WeatherDollarResponse response) {
        this.response = response;
    }

    @RequestMapping(
        value = "/weather",
        params = {"temperature"},
        method = GET
    )
    public Response index(@RequestParam("temperature") Double temperature) throws IOException, ParseException {
        Response response = new Response();
        response.setValue(this.response.predict(temperature));
        return response;
    }
}