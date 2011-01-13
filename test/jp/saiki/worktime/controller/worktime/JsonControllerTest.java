package jp.saiki.worktime.controller.worktime;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class JsonControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/worktime/json");
        JsonController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/worktime/json.jsp"));
    }
}
