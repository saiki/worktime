package jp.saiki.worktime.controller.worktime;

import java.util.HashMap;
import java.util.Map;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.BeanUtil;
import org.slim3.util.RequestMap;

import com.google.appengine.api.datastore.KeyFactory;

public class SaveController extends Controller {

    private WorkTimeService service = new WorkTimeService();
    
    @Override
    public Navigation run() throws Exception {
        WorkTime wt = service.save(new RequestMap(request));
        Map<String, Object> json = new HashMap<String, Object>();
        BeanUtil.copy(wt, json);
        json.put("key", KeyFactory.keyToString(wt.getKey()));
        requestScope("json", json);
        
        return forward("save.jsp");
    }
}
