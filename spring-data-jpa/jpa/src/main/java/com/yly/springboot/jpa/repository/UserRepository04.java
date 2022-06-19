package com.yly.springboot.jpa.repository;


import com.yly.springboot.jpa.dataobject.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository04 extends PagingAndSortingRepository<UserDO, Integer> {
    // 使用 @Query 自定义了一个 SQL 操作，并且参数使用占位符(?) + 参数位置的形式。
    @Query("SELECT u FROM UserDO u WHERE u.username = ?1")
    UserDO findByUsername01(String username);

    // 和 上面类似，差异在于使用占位符(:) + **参数名字(需要使用 @Param 声明)**的形式。
    @Query("SELECT u FROM UserDO u WHERE u.username = :username")
    UserDO findByUsername02(@Param("username") String username);

    /*
        和上面差别在于我们增加了 nativeQuery = true ，表示在 @Query 自定义的是原生 SQL，而非在上面自定义的是 JPQL 。进一步的说：
        上面FROM UserDO ，使用的是实体名。
        此处，使用的是表名。
     */
    @Query(value = "SELECT * FROM users u WHERE u.username = :username", nativeQuery = true)
    UserDO findByUsername03(@Param("username") String username);


    // 定义了更新操作，需要加上 @Modifying 注解。😈 另外，我们发可以现，使用参数名时，可以不用配合 @Param 注解。
    @Query("UPDATE UserDO  u SET u.username = :username WHERE u.id = :id")
    @Modifying
    int updateUsernameById(Integer id, String username);

}
