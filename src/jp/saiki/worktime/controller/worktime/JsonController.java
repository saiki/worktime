package jp.saiki.worktime.controller.worktime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class JsonController extends Controller {
    
    private WorkTimeService service = new WorkTimeService();

    @Override
    public Navigation run() throws Exception {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        response.setContentType("application/json; charset=" + encoding);
        if (asKey("key") != null ) {
            // 単一オブジェクトの取得
            WorkTime wt = Datastore.get(WorkTime.class, asKey("key"));
            Map<String, Object> json = new HashMap<String, Object>();
            BeanUtil.copy(wt, json);
            json.put("key", KeyFactory.keyToString(wt.getKey()));
            requestScope("json", json);
        } else {
            if (!validate()) {
                requestScope("json", errors);
                return forward("error.jsp");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date from = sdf.parse(requestScope("from").toString());
            Date to = sdf.parse(requestScope("to").toString());
            UserService userService = UserServiceFactory.getUserService();
            List<WorkTime> list = service.search(from, to, userService.getCurrentUser());
            // 新規登録ようにからのデータを追加
            list.add(new WorkTime());

            // keyの値がうまくわたらないので変換
            List<Map<String, Object>> json = new ArrayList<Map<String,Object>>();
            for (WorkTime wt : list) {
                HashMap<String, Object> in = new HashMap<String, Object>();
                BeanUtil.copy(wt, in);
                if (wt.getKey() != null) {
                    in.put("key", KeyFactory.keyToString(wt.getKey()));
                }
                json.add(in);
            }
            requestScope("json", json);
        }
        return forward("json.jsp");
    }
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.required(), v.dateType("yyyy/MM/dd"));
        v.add("to", v.required(), v.dateType("yyyy/MM/dd"));
        
        return v.validate();
    }
}
