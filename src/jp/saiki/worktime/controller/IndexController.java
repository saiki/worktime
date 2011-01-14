package jp.saiki.worktime.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

public class IndexController extends Controller {

    private WorkTimeService service = new WorkTimeService();

    @Override
    public Navigation run() throws Exception {
        // 初期検索条件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        requestScope("from", sdf.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_YEAR, 14);
        requestScope("to", sdf.format(cal.getTime()));
        
        Date from = sdf.parse(requestScope("from").toString());
        Date to = sdf.parse(requestScope("to").toString());
        List<WorkTime> list = service.search(from, to);
        // 新規登録ようにからのデータを追加
        list.add(new WorkTime());
        requestScope("list", list);
        return forward("index.jsp");
    }
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.dateType("yyyy/MM/dd"));
        v.add("to", v.dateType("yyyy/MM/dd"));
        return v.validate();
    }
}
