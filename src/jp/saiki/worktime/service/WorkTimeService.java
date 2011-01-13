package jp.saiki.worktime.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Transaction;

import jp.saiki.worktime.meta.WorkTimeMeta;
import jp.saiki.worktime.model.WorkTime;


public class WorkTimeService {

    private static WorkTimeMeta meta = WorkTimeMeta.get();
    
    /**
     * 登録
     * @param input
     * @return
     * @throws ParseException 
     */
    public WorkTime save(Map<String, Object> input) throws ParseException {
    	WorkTime wt = new WorkTime();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	SimpleDateFormat fromToFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        input.put("from", fromToFormat.parse(input.get("date").toString() + " " + input.get("from").toString()));
        input.put("to", fromToFormat.parse(input.get("date").toString() + " " + input.get("to").toString()));
    	input.put("date", dateFormat.parse(input.get("date").toString()));
    	BeanUtil.copy(input, wt);
    	Transaction tx = Datastore.beginTransaction();
    	Datastore.put(wt);
    	tx.commit();
    	return wt;
    }
    
    /**
     * 範囲指定による検索
     * @param from
     * @param to
     * @return
     */
    public List<WorkTime> search(Date from, Date to) {
        List<WorkTime> ret = null;
        
        ret = Datastore.query(meta)
            .filter(meta.date.greaterThan(from))
            .filter(meta.date.lessThan(to))
            .sort(meta.date.asc)
            .asList();
        
        return ret;
    }
}
