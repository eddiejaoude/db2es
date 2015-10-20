package crossrail.db2es;

import crossrail.db2es.Repository.InstrumentRepository;
import crossrail.db2es.Repository.ReadingRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(
        		includeFilters = @ComponentScan.Filter(
        				type = FilterType.ASSIGNABLE_TYPE, classes = ReadingRepository.class))
@EnableElasticsearchRepositories(
        		includeFilters = @ComponentScan.Filter(
        				type = FilterType.ASSIGNABLE_TYPE, classes = InstrumentRepository.class))
public class CrossrailDb2EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrossrailDb2EsApplication.class, args);
    }
}
