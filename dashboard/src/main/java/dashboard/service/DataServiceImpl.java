package dashboard.service;

import dashboard.dto.DataDto;
import dashboard.entity.DataEntity;
import dashboard.repo.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;

    @Override
    public List<DataDto> getDataByFilters(Integer endYear, String topic, String sector, String region, String pestle, String source, String swot, String country, String city) {
        List<DataEntity> dataEntities = dataRepository.findByFilters(endYear, topic, sector, region, pestle, source, swot, country, city);
        return dataEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private DataDto convertToDto(DataEntity dataEntity) {
        DataDto dataDto = new DataDto();
        dataDto.setId(dataEntity.getId());
        dataDto.setIntensity(dataEntity.getIntensity());
        dataDto.setLikelihood(dataEntity.getLikelihood());
        dataDto.setRelevance(dataEntity.getRelevance());
        dataDto.setEndYear(dataEntity.getEndYear());
        dataDto.setCountry(dataEntity.getCountry());
        dataDto.setTopic(dataEntity.getTopic());
        dataDto.setRegion(dataEntity.getRegion());
        dataDto.setCity(dataEntity.getCity());
        dataDto.setAddedAsString(dataEntity.getAddedAsString());
        dataDto.setPublishedAsString(dataEntity.getPublishedAsString());
        return dataDto;
    }
}
