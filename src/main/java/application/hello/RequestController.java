package application.hello;

import application.hello.presentation.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RequestController {
    private final RBKResponse response;

    @Autowired
    public RequestController(RBKResponse response) {
        this.response = response;
    }

    @RequestMapping(path="/request", method = GET)
    public Response index() {
        Response r = new Response();
        r.setValue(response.getMaxQuoteMonth());
        return r;
    }
}
