package com.yunus.dao;

import com.yunus.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_COMMENT;

/**
 * @Author: gaoyunfeng
 * @date: 2019/2/12
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * 根据 firstName 和 lastName 查询customer
     * 根据方法名查询参考  Query creation  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
     *
     * @param firstName
     * @param lastName
     * @return
     */
    Customer findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * 用预定义查询  直接调用在 customer 实体类中定义的查询
     *
     * @param firstName
     * @return
     */
    Customer findByFirstName(String firstName);

    /**
     * 模糊匹配
     *
     * @param bauer
     * @return
     */
    @Query("select c from Customer c where c.firstName=?1")
    Customer findByFirstName2(String bauer);


    @Query("select c from Customer c where c.lastName=?1 order by c.id desc")
    List<Customer> findByLastName2(String lastName);

    /**
     * 一个参数，匹配两个字段
     *
     * @param name2
     * @return 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     */
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name  order by c.id desc")
    List<Customer> findByName(@Param("name") String name2);

    /**
     * 一个参数，匹配两个字段
     *
     * @param name
     * @return 这里的%只能放在占位的前面，后面不行
     */
    @Query("select c from Customer c where c.firstName like %?1")
    List<Customer> findByName2(@Param("name") String name);

    /**
     * 一个参数，匹配两个字段
     *
     * @param name
     * @return 开启nativeQuery=true，在value里可以用原生SQL语句完成查询
     */
    @Query(nativeQuery = true, value = "select * from Customer c where c.first_name like concat('%' ,?1,'%') ")
    List<Customer> findByName3(@Param("name") String name);

    /**
     * 一个参数，匹配两个字段
     *
     * @param name2
     * @param sort  指定排序的参数，可以根据需要进行调整
     * @return 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     */
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    List<Customer> findByName4(@Param("name") String name2, Sort sort);

    /**
     * 根据lastName去更新firstName，返回结果是更改数据的行数
     *
     * @param firstName
     * @param lastName
     * @return
     */
    @Modifying//更新查询
    @Transactional//开启事务
    @Query("update Customer c set c.firstName = ?1 where c.lastName = ?2")
    int setFixedFirstnameFor(String firstName, String lastName);

    /**
     * 一个参数，匹配两个字段
     *
     * @param name2
     * @return 这里Param的值和=:后面的参数匹配，但不需要和方法名对应的参数值对应
     * 这里增加了@QueryHints注解，是给查询添加一些额外的提示
     * 比如当前的name值为HINT_COMMENT是在查询的时候带上一些备注信息
     * @Param pageable 分页参数
     */
    @QueryHints(value = {@QueryHint(name = HINT_COMMENT, value = "a query for pageable")})
    @Query("select c from Customer c where c.firstName=:name or c.lastName=:name")
    Page<Customer> findByName(@Param("name") String name2, Pageable pageable);
}
