package dashboard.dto;

import lombok.Data;

@Data
public class DataDto {

    private Long id;
    private int intensity;
    private int likelihood;
    private int relevance;
    private Integer endYear;
    private String country;
    private String topic;
    private String region;
    private String city;
    private String addedAsString;
    private String publishedAsString;
}
