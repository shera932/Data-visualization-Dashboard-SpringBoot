package dashboard.rest;

import dashboard.dto.DataDto;
import dashboard.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
public class DataRestController {

    @Autowired
    private DataService dataService;

    @GetMapping("/api/data/filter")
    public List<DataDto> getDataByFilters(
            @RequestParam(required = false) Integer endYear,
            @RequestParam(required = false) String topic,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String pestle,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String swot,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city) {
        return dataService.getDataByFilters(endYear, topic, sector, region, pestle, source, swot, country, city);
    }
}
