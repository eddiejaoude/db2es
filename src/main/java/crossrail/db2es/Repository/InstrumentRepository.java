package crossrail.db2es.Repository;

import java.util.List;

import crossrail.db2es.Entity.Instrument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface InstrumentRepository extends ElasticsearchRepository<Instrument, String> {
}
