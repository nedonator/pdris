package application.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RBKResponseTest {
    @Mock
    private RestTemplate template;

    @Mock
    private ResponseEntity<String> entity;

    @Test
    public void test() {
        when(entity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(entity.getBody()).thenReturn("Hallo,2.28");
        when(template.getForEntity(anyString(), eq(String.class))).thenReturn(entity);
    }
}