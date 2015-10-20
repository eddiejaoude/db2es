package crossrail.db2es.Service;

import crossrail.db2es.Entity.Instrument;
import crossrail.db2es.Repository.InstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DestinationService {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    public Long getTotal() {
        return this.instrumentRepository.count();
    }

    public void save(Instrument instrument) {
        this.instrumentRepository.save(instrument);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendTotal() {
        this.messagingTemplate.convertAndSend(
                "/destination/total", this.getTotal());
    }
}
