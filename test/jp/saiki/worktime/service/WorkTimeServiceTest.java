package jp.saiki.worktime.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class WorkTimeServiceTest extends AppEngineTestCase {

    private WorkTimeService service = new WorkTimeService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
