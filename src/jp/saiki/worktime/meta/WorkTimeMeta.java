package jp.saiki.worktime.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-07-09 00:00:06")
/** */
public final class WorkTimeMeta extends org.slim3.datastore.ModelMeta<jp.saiki.worktime.model.WorkTime> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.lang.Integer> code = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.lang.Integer>(this, "code", "code", java.lang.Integer.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date> date = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date>(this, "date", "date", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date> from = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date>(this, "from", "from", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<jp.saiki.worktime.model.WorkTime> remark = new org.slim3.datastore.StringAttributeMeta<jp.saiki.worktime.model.WorkTime>(this, "remark", "remark");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date> to = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.util.Date>(this, "to", "to", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<jp.saiki.worktime.model.WorkTime, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<jp.saiki.worktime.model.WorkTime> work = new org.slim3.datastore.StringAttributeMeta<jp.saiki.worktime.model.WorkTime>(this, "work", "work");

    private static final WorkTimeMeta slim3_singleton = new WorkTimeMeta();

    /**
     * @return the singleton
     */
    public static WorkTimeMeta get() {
       return slim3_singleton;
    }

    /** */
    public WorkTimeMeta() {
        super("WorkTime", jp.saiki.worktime.model.WorkTime.class);
    }

    @Override
    public jp.saiki.worktime.model.WorkTime entityToModel(com.google.appengine.api.datastore.Entity entity) {
        jp.saiki.worktime.model.WorkTime model = new jp.saiki.worktime.model.WorkTime();
        model.setCode(longToInteger((java.lang.Long) entity.getProperty("code")));
        model.setDate((java.util.Date) entity.getProperty("date"));
        model.setFrom((java.util.Date) entity.getProperty("from"));
        model.setKey(entity.getKey());
        model.setRemark((java.lang.String) entity.getProperty("remark"));
        model.setTo((java.util.Date) entity.getProperty("to"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        model.setWork((java.lang.String) entity.getProperty("work"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("code", m.getCode());
        entity.setProperty("date", m.getDate());
        entity.setProperty("from", m.getFrom());
        entity.setProperty("remark", m.getRemark());
        entity.setProperty("to", m.getTo());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("work", m.getWork());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        jp.saiki.worktime.model.WorkTime m = (jp.saiki.worktime.model.WorkTime) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getCode() != null){
            writer.setNextPropertyName("code");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCode());
        }
        if(m.getDate() != null){
            writer.setNextPropertyName("date");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getDate());
        }
        if(m.getFrom() != null){
            writer.setNextPropertyName("from");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFrom());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getRemark() != null){
            writer.setNextPropertyName("remark");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getRemark());
        }
        if(m.getTo() != null){
            writer.setNextPropertyName("to");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getTo());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        if(m.getWork() != null){
            writer.setNextPropertyName("work");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getWork());
        }
        writer.endObject();
    }

    @Override
    public jp.saiki.worktime.model.WorkTime jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        jp.saiki.worktime.model.WorkTime m = new jp.saiki.worktime.model.WorkTime();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("code");
        decoder = new org.slim3.datastore.json.Default();
        m.setCode(decoder.decode(reader, m.getCode()));
        reader = rootReader.newObjectReader("date");
        decoder = new org.slim3.datastore.json.Default();
        m.setDate(decoder.decode(reader, m.getDate()));
        reader = rootReader.newObjectReader("from");
        decoder = new org.slim3.datastore.json.Default();
        m.setFrom(decoder.decode(reader, m.getFrom()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("remark");
        decoder = new org.slim3.datastore.json.Default();
        m.setRemark(decoder.decode(reader, m.getRemark()));
        reader = rootReader.newObjectReader("to");
        decoder = new org.slim3.datastore.json.Default();
        m.setTo(decoder.decode(reader, m.getTo()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        reader = rootReader.newObjectReader("work");
        decoder = new org.slim3.datastore.json.Default();
        m.setWork(decoder.decode(reader, m.getWork()));
    return m;
}
}