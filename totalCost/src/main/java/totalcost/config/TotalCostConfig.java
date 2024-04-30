package totalcost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import totalcost.utils.CostCalculator;
import totalcost.utils.DateCalculator;
import totalcost.utils.DistanceCalculator;

@Configuration
public class TotalCostConfig {
    @Bean
    public CostCalculator costCalculator() {
        return new CostCalculator();
    }

    @Bean
    public DistanceCalculator distanceCalculator() {
        return new DistanceCalculator();
    }

    @Bean
    public DateCalculator dateCalculator() {
        return new DateCalculator();
    }
}
