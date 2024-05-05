package ch.roester.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer>, JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    Set<Tag> findByNameIsIn(List<String> tagNames);
}
