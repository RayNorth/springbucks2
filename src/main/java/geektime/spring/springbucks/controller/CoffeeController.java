package geektime.spring.springbucks.controller;


import geektime.spring.springbucks.model.Coffee;
import geektime.spring.springbucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Ray
 * @date 2022年07月{DAY}日21:28
 */
@Slf4j
@RestController
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping(value = "/coffee/one/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Coffee> getCoffeeById(@PathVariable String name) {
        Optional<Coffee> coffee = coffeeService.findOneCoffee(name);
        log.info("Coffee {}:", coffee);
        return coffee;
    }

    @GetMapping(value = "/coffee/search", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public List<Coffee> queryCoffees(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        return coffeeService.queryCoffees(pageNum,pageSize);
    }
}
