package jp.saiki.worktime.service;

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
     */
    public WorkTime save(Map<String, Object> input) {
    	WorkTime wt = new WorkTime();
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
