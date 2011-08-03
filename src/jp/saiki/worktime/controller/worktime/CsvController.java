package jp.saiki.worktime.controller.worktime;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.saiki.worktime.model.WorkTime;
import jp.saiki.worktime.service.WorkTimeService;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.KeyFactory;

public class CsvController extends Controller {

    private WorkTimeService service = new WorkTimeService();
    
    @Override
    public Navigation run() throws Exception {
        
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment; filename=worktime.csv");
        if (!validate()) {
            requestScope("json", errors);
            return forward("error.jsp");
        }
        // データ取得
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date from = sdf.parse(asString("from"));
        Date to = sdf.parse(asString("to"));
        List<WorkTime> list = service.search(from, to);
        PrintWriter out = response.getWriter();
        out.println("\"日付\",\"開始\",\"終了\",\"休憩\",\"コード\",\"内容\",\"備考\"");
        // keyの値がうまくわたらないので変換
        for (WorkTime wt : list) {
            HashMap<String, Object> in = new HashMap<String, Object>();
            BeanUtil.copy(wt, in);
            if (wt.getKey() != null) {
                in.put("key", KeyFactory.keyToString(wt.getKey()));
            }
            out.print("\"" + sdf.format(wt.getDate()) + "\",");
            out.print("\"" + sdf.format(wt.getFrom()) + "\",");
            out.print("\"" + sdf.format(wt.getTo()) + "\",");
            out.print("\"" + wt.getRest() + "\",");
            out.print("\"" + wt.getCode() + "\",");
            out.print("\"" + wt.getWork() + "\",");
            out.print("\"" + wt.getRemark() + "\"");
            out.print("\n");
        }
        response.flushBuffer();
        return null;
    }
    
    
    protected boolean validate() {
        Validators v = new Validators(request);
        v.add("from", v.required(), v.dateType("yyyy/MM/dd"));
        v.add("to", v.required(), v.dateType("yyyy/MM/dd"));
        
        return v.validate();
    }
}
