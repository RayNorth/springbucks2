package geektime.spring.springbucks.service;

import com.alibaba.druid.util.StringUtils;
import geektime.spring.springbucks.mapper.CoffeeMapper;
import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public Optional<Coffee> findOneCoffee(String name) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", exact().ignoreCase());
        String price =(String)redisTemplate.opsForValue().get(name);
        Optional<Coffee> coffee;
        if(StringUtils.isEmpty(price)){
             coffee = coffeeRepository.findOne(Example.of(Coffee.builder().name(name).build(), matcher));
            redisTemplate.opsForValue().set(coffee.get().getName(),coffee.get().getPrice().toString(),10, TimeUnit.MINUTES);
        }else {
             coffee= Optional.of(new Coffee(name,Long.parseLong(price)));
        }
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addCoffee(Coffee coffee) {
        coffeeMapper.save(coffee);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void updateCoffee(Coffee coffee) {
        coffeeMapper.update(coffee);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteCoffee(Long id) {
        coffeeMapper.delete(id);
    }

    public List<Coffee> findByIds(List<String> ids) {
        List<Coffee> coffees = coffeeMapper.findByIds(ids);
        return coffees;
    }

    public List<Coffee> queryCoffees(int pageNum, int pageSize) {
        List<Coffee> coffees = coffeeMapper.queryCoffees(pageNum,pageSize);
        return coffees;
    }
}
