package jp.saiki.worktime.model;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class WorkTimeTest extends AppEngineTestCase {

    private WorkTime model = new WorkTime();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
