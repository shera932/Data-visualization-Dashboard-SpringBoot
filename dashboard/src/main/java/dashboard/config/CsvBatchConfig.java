package dashboard.config;

import dashboard.entity.DataEntity;
import dashboard.repo.DataRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class CsvBatchConfig {

    @Autowired
    private DataRepository dataRepo;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    public FlatFileItemReader<DataEntity> dataReader() {
        FlatFileItemReader<DataEntity> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/Data.csv"));
        itemReader.setName("csv-reader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public LineMapper<DataEntity> lineMapper() {
        DefaultLineMapper<DataEntity> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(
                "end_year", "citylng", "citylat", "intensity", "sector", "topic", "insight", "swot", "url",
                "region", "start_year", "impact", "added", "published", "city", "country", "relevance", "pestle",
                "source", "title", "likelihood"
        );

        BeanWrapperFieldSetMapper<DataEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DataEntity.class);

        Map<Class<?>, PropertyEditorSupport> customEditors = new HashMap<>();
        customEditors.put(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd yyyy HH:mm:ss");
                setValue(LocalDateTime.parse(text, formatter));
            }
        });

        customEditors.put(Integer.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(0);
                } else {
                    try {
                        setValue(Integer.parseInt(text));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid number format for impact field", e);
                    }
                }
            }
        });

        fieldSetMapper.setCustomEditors(customEditors);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public DataProcessor dataProcess() {
        return new DataProcessor();
    }

    @Bean
    public RepositoryItemWriter<DataEntity> dataWriter() {
        RepositoryItemWriter<DataEntity> repositoryWriter = new RepositoryItemWriter<>();
        repositoryWriter.setRepository(dataRepo);
        repositoryWriter.setMethodName("save");
        return repositoryWriter;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step-1").<DataEntity, DataEntity>chunk(10)
                .reader(dataReader())
                .processor(dataProcess())
                .writer(dataWriter())
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("data-job")
                .flow(step1())
                .end()
                .build();
    }
}
