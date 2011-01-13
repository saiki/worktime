package jp.saiki.worktime.controller.worktime;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;

public class SaveController extends Controller {

    private WorkTimeService service = new WorkTimeService();
    
    @Override
    public Navigation run() throws Exception {
        WorkTime wt = service.save(new RequestMap(request));
        requestScope("json", wt);
        
        return forward("save.jsp");
    }
}
