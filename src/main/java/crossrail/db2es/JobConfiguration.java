package crossrail.db2es;

import javax.sql.DataSource;

import crossrail.db2es.Entity.Instrument;
import crossrail.db2es.Entity.Reading;
import crossrail.db2es.Job.ReadingItemProcessor;
import crossrail.db2es.Repository.InstrumentRepository;
import crossrail.db2es.Repository.ReadingRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    private HashMap<String, Sort.Direction> sort = new HashMap<>();;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Bean
    public ItemReader<Reading> reader() {
        RepositoryItemReader<Reading> reader = new RepositoryItemReader<Reading>();
        reader.setRepository(this.readingRepository);
        reader.setMethodName("findAll");

        this.sort.put("id", Sort.Direction.ASC);
        reader.setSort(this.sort);
        reader.setPageSize(10);

        return reader;
    }

    @Bean
    public ItemProcessor<Reading, Instrument> processor() {
        return new ReadingItemProcessor();
    }

    @Bean
    public ItemWriter<Instrument> writer(DataSource dataSource) {
        RepositoryItemWriter<Instrument> writer = new RepositoryItemWriter<Instrument>();
        writer.setRepository(this.instrumentRepository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Job importReadingJob(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
        return jobs.get("importReadingJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(s1)
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Reading> reader,
                      ItemWriter<Instrument> writer, ItemProcessor<Reading, Instrument> processor) {
        return stepBuilderFactory.get("step1")
                .<Reading, Instrument> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
