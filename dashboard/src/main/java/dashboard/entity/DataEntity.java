package dashboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DATA_INFO")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "END_YEAR", nullable = true)
    private Integer endYear;

    @Column(name = "CITYLNG", nullable = true)
    private String cityLng;

    @Column(name = "CITYLAT", nullable = true)
    private String cityLat;

    @Column(name = "INTENSITY", nullable = true)
    private int intensity;

    @Column(name = "SECTOR", nullable = true)
    private String sector;

    @Column(name = "TOPIC", nullable = true)
    private String topic;

    @Column(name = "INSIGHT", nullable = true)
    private String insight;

    @Column(name = "SWOT", nullable = true)
    private String swot;

    @Column(name = "URL", nullable = true)
    private String url;

    @Column(name = "REGION", nullable = true)
    private String region;

    @Column(name = "START_YEAR", nullable = true)
    private Integer startYear;

    @Column(name = "IMPACT", nullable = true)
    private Integer impact;

    @Column(name = "ADDED", nullable = true)
    private LocalDateTime added;

    @Column(name = "PUBLISHED", nullable = true)
    private LocalDateTime published;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "COUNTRY", nullable = true)
    private String country;

    @Column(name = "RELEVANCE", nullable = true)
    private int relevance;

    @Column(name = "PESTLE", nullable = true)
    private String pestle;

    @Column(name = "SOURCE", nullable = true)
    private String source;

    @Column(name = "TITLE", nullable = true)
    private String title;

    @Column(name = "LIKELIHOOD", nullable = true)
    private int likelihood;

    // Getter and Setter methods for added and published with custom format
    @JsonIgnore
    public void setAdded(String added) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm:ss");
        this.added = LocalDateTime.parse(added, formatter);
    }

    @JsonIgnore
    public void setPublished(String published) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm:ss");
        this.published = LocalDateTime.parse(published, formatter);
    }

    @JsonProperty("addedAsString")
    public String getAddedAsString() {
        if (added == null) {
            return null;
        }
        return added.format(DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm:ss"));
    }

    @JsonProperty("publishedAsString")
    public String getPublishedAsString() {
        if (published == null) {
            return null;
        }
        return published.format(DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm:ss"));
    }
}
