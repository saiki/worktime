package jp.saiki.worktime.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

public class IndexController extends Controller {

    private WorkTimeService service = new WorkTimeService();

    @Override
    public Navigation run() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        requestScope("from", sdf.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_YEAR, 7);
        requestScope("to", sdf.format(cal.getTime()));
        
        Date from = sdf.parse(requestScope("from").toString());
        Date to = sdf.parse(requestScope("to").toString());
        requestScope("list", service.search(from, to));
        return forward("index.jsp");
    }
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.dateType("yyyy/MM/dd"));
        v.add("to", v.dateType("yyyy/MM/dd"));
        return v.validate();
    }
}
