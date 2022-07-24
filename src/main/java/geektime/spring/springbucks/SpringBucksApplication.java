package geektime.spring.springbucks;

import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.repository.CoffeeRepository;
import geektime.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@EnableJpaRepositories
public class SpringBucksApplication implements ApplicationRunner {
	@Autowired
	private CoffeeRepository coffeeRepository;
	@Autowired
	private CoffeeService coffeeService;


	public static void main(String[] args) {
		SpringApplication.run(SpringBucksApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//新增
		Coffee coffee1 = new Coffee("bk", 600L);
		coffeeService.addCoffee(coffee1);
		//查询修改
		List<String> ids = new ArrayList<>(Arrays.asList("1","2","3")) ;
		List<Coffee> coffees=coffeeService.findByIds(ids);
		coffees.forEach(coffee -> {
			log.info("coffee:{}",coffee);
			coffeeService.updateCoffee(coffee);
		});
		//分页查询 删除
		List<Coffee> coffeeList=coffeeService.queryCoffees(2,3);
		coffeeList.forEach(coffee -> {
			if(coffee.getId().compareTo(Long.parseLong("6"))>0){
				log.info("coffee:{}",coffee);
				coffeeService.deleteCoffee(coffee.getId());
			}
		});

		//redis缓存
		Optional<Coffee> coffee=coffeeService.findOneCoffee("bk");
		log.info("price:{}",coffee.get().getPrice());


	}
}

