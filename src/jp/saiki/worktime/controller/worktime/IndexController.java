package jp.saiki.worktime.controller.worktime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class IndexController extends Controller {

    @Override
    public Navigation run() throws Exception {
        // 初期検索条件
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        Map<String, Object> json = new HashMap<String, Object>();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        json.put("from", sdf.format(cal.getTime()));
        cal.add(Calendar.DAY_OF_YEAR, 14);
        json.put("to", sdf.format(cal.getTime()));
        
        requestScope("json", json);
        
        return forward("json.jsp");
    }
}
