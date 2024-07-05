package dashboard.repo;

import dashboard.entity.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataRepository extends JpaRepository<DataEntity, Long> {

    @Query("SELECT d FROM DataEntity d WHERE " +
            "(:endYear IS NULL OR d.endYear = :endYear) AND " +
            "(:topic IS NULL OR d.topic = :topic) AND " +
            "(:sector IS NULL OR d.sector = :sector) AND " +
            "(:region IS NULL OR d.region = :region) AND " +
            "(:pestle IS NULL OR d.pestle = :pestle) AND " +
            "(:source IS NULL OR d.source = :source) AND " +
            "(:swot IS NULL OR d.swot = :swot) AND " +
            "(:country IS NULL OR d.country = :country) AND " +
            "(:city IS NULL OR d.city = :city)")
    List<DataEntity> findByFilters(@Param("endYear") Integer endYear,
                                   @Param("topic") String topic,
                                   @Param("sector") String sector,
                                   @Param("region") String region,
                                   @Param("pestle") String pestle,
                                   @Param("source") String source,
                                   @Param("swot") String swot,
                                   @Param("country") String country,
                                   @Param("city") String city);
}
