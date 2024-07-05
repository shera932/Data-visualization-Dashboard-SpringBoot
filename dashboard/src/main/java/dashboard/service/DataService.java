package dashboard.service;

import dashboard.dto.DataDto;
import dashboard.entity.DataEntity;

import java.util.List;
import java.util.Optional;

public interface DataService {

    List<DataDto> getDataByFilters(Integer endYear, String topic, String sector, String region, String pestle, String source, String swot, String country, String city);
}
