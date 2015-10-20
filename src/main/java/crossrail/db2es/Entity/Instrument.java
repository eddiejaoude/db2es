package crossrail.db2es.Entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Document(indexName = "instrument", type = "pri", shards = 1, replicas = 0, refreshInterval = "-1")
public class Instrument implements Serializable {

    @Id
    private String id;

    @Column(nullable = false)
    private Date date;

    Instrument() {} // for JPA

    public Instrument(String id, Date date) {
        this.id = id;
        this.date= date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
