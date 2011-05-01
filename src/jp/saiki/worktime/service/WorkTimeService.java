package jp.saiki.worktime.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
        
        // 日付用
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 時間用
        SimpleDateFormat fromToFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        // 文字列をオブジェクトに変換
        input.put("from", fromToFormat.parse(input.get("date").toString() + " " + input.get("from").toString()));
        input.put("to", fromToFormat.parse(input.get("date").toString() + " " + input.get("to").toString()));
        input.put("date", dateFormat.parse(input.get("date").toString()));

    	if (input.containsKey("key")) {
            Key key = KeyFactory.stringToKey((String) input.get("key"));
            return update(input, key);
    	} else {
    	    return insert(input);
    	}
    }
    
    public WorkTime insert(Map<String, Object> input) throws ParseException {
        WorkTime wt = new WorkTime();
        BeanUtil.copy(input, wt);
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(wt);
        tx.commit();
        return wt;
    }
    
    public WorkTime update(Map<String, Object> input, Key key) throws ParseException {
        
        WorkTime wt = Datastore.get(WorkTime.class, key);
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
            .filter(meta.date.greaterThanOrEqual(from))
            .filter(meta.date.lessThanOrEqual(to))
            .sort(meta.date.asc)
            .sort(meta.from.asc)
            .asList();
        
        return ret;
    }
}
