package ch.roester.tag;

import ch.roester.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer>, JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    Set<Tag> findByNameIsIn(List<String> tagNames);
    @Query("select t from Tag t " +
            "where t.name like concat('%', :searchQuery, '%') ")
    Page<Tag> findAllByName(String searchQuery, Pageable pageable);

}
