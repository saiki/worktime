package jp.saiki.worktime.controller.worktime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

public class JsonController extends Controller {
    
    private WorkTimeService service = new WorkTimeService();

    @Override
    public Navigation run() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date from = sdf.parse(requestScope("from").toString());
        Date to = sdf.parse(requestScope("to").toString());
        List<WorkTime> list = service.search(from, to);
        // 新規登録ようにからのデータを追加
        list.add(new WorkTime());

        requestScope("json", list);

        return forward("json.jsp");
    }
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.dateType("yyyy/MM/dd"));
        v.add("to", v.dateType("yyyy/MM/dd"));
        
        return v.validate();
    }
}
