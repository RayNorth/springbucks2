package geektime.spring.springbucks.conf;

import geektime.spring.springbucks.service.CoffeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ray
 * @date 2022年07月{DAY}日21:21
 */

@Configuration
public class BeanConfig {
    @Bean
    public CoffeeService CoffeeService(){
        return new CoffeeService();
    }
}

