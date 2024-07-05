package dashboard.config;

import dashboard.entity.DataEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;

public class DataProcessor implements ItemProcessor<DataEntity, DataEntity> {

    @Override
    public DataEntity process(DataEntity item) throws Exception {
        //logic
        return item;
    }

}
