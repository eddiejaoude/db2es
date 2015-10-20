package crossrail.db2es.Job;

import crossrail.db2es.Entity.Instrument;
import crossrail.db2es.Entity.Reading;

import org.springframework.batch.item.ItemProcessor;

public class ReadingItemProcessor implements ItemProcessor<Reading, Instrument> {

    @Override
    public Instrument process(final Reading reading) throws Exception {
        return new Instrument(reading.getId().toString(), reading.getDate());
    }
}
