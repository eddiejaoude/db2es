package crossrail.db2es.Service;

import crossrail.db2es.Repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SourceService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private MessageSendingOperations<String> messagingTemplate;

    public Integer getTotal() {
        Integer pageSize = 10;
        // @TODO: not happy with this :(
        return this.readingRepository.findAll(new PageRequest(0, pageSize)).getTotalPages() * pageSize;
    }

    @Scheduled(fixedDelay = 1000)
    public void sendTotal() {
        this.messagingTemplate.convertAndSend(
                "/source/total", this.getTotal());
    }
}
