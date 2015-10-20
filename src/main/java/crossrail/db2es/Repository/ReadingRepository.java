package crossrail.db2es.Repository;

import crossrail.db2es.Entity.Reading;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends PagingAndSortingRepository<Reading, Reading> {

}
