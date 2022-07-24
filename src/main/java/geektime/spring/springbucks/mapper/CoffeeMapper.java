package geektime.spring.springbucks.mapper;

import geektime.spring.springbucks.model.Coffee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoffeeMapper {
    @Insert("insert into t_coffee (name, price, create_time, update_time) values (#{name}, #{price}, now(), now())")
    @Options(useGeneratedKeys = true)
    int save(Coffee coffee);

    @Select("select * from t_coffee where id = #{id}")
    Coffee findById(@Param("id") Long id);

    List<Coffee> findByIds(@Param("ids") List<String> ids);

    @Select("select * from t_coffee order by id")
    List<Coffee> queryCoffees(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    @Update("update t_coffee set price=#{price},name=#{name},update_time=current_timestamp where id = #{id}")
    void update(Coffee coffee);

    @Update("delete from  t_coffee where id = #{id}")
    void delete(@Param("id") Long id);
}
