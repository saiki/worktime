package jp.saiki.worktime.controller.worktime;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

public class JsonController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("json.jsp");
    }
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.dateType("yyyy/MM/dd"));
        v.add("to", v.dateType("yyyy/MM/dd"));
        
        return v.validate();
    }
}
