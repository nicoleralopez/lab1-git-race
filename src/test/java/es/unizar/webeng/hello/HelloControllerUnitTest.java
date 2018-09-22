package es.unizar.webeng.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.List;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerUnitTest {

    @Value("${app.message:Hello World}")
    private String message;

    @Autowired
    private HelloController controller;


    @Test
    public void testMessage() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String view = controller.welcome(map);
        assertThat(view, is("wellcome"));
        assertThat(map.containsKey("message"), is(true));
        assertThat(map.get("message"), is(message));
    }

    @Test
    public void primeTest() throws Exception {
        List<Integer> testList = new LinkedList<>();
        List<Integer> targetList = new LinkedList<>();
        targetList.add(2);
        targetList.add(3);
        targetList.add(5);
        targetList.add(7);
        testList = controller.sieveOfEratosthenes(10);

        assertThat(testList, is(targetList));
    }
}
