package com.laozhao.webflux.config.event;

import java.io.Serializable;
import java.time.Instant;

/**
 * Created by viruser on 2018/11/1.
 */

public class InstanceEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private  long version;
    private  Instant timestamp;
    private  String type;
    private  String id;

    protected InstanceEvent(String id, long version, String type) {
        this.version = version;
        this.timestamp = Instant.now();
        this.type = type;
        this.id=id;
    }

    public long getVersion() {
        return version;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
