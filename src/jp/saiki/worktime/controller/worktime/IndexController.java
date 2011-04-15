package jp.saiki.worktime.controller.worktime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        // From
        json.put("from", sdf.format(getFirstDay(cal)));
        // To
        json.put("to", sdf.format(getLastDay(cal)));
        
        requestScope("json", json);
        
        return forward("json.jsp");
    }
    
    private Date getFirstDay(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    
    private Date getLastDay(Calendar cal) {
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal.getTime();
    }
}
