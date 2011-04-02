package jp.saiki.worktime.controller.worktime;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;

public class DeleteController extends Controller {

    @Override
    public Navigation run() throws Exception {
        Key key = asKey("key");
        Datastore.delete(key);
        return null;
    }
}
