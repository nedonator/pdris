package application.hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class HelloController {
    private static final String response = "Greetings from Spring Boot!!";

    @RequestMapping(path="/", method = GET)
    public String index() {
        return response;
    }

}