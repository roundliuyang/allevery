import com.yly.crud.dataobject.User;
import com.yly.crud.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * 很多时候，在向数据库插入数据时，需要保留插入数据的id，以便进行后续的update操作或者将id存入其他表作为外键。
 * 但是，在默认情况下，insert操作返回的是一个int值，并且不是表示主键id，而是表示当前SQL语句影响的行数。。。
 * 接下来，我们看看MyBatis如何在使用MySQL和Oracle做insert插入操作时将返回的id绑定到对象中。
 *
 * 测试伪代码
 */
public class InsertReturnIdTest {
    @Resource
    UserMapper userMapper;
    public void test(){

        User user = new User();
        user.setUserName("chenzhou");
        user.setPassword("xxxx");
        user.setComment("测试插入数据返回主键功能");

        System.out.println("插入前主键为："+user.getUserId());
        userMapper.insertAndGetId(user);//插入操作
        System.out.println("插入后主键为："+user.getUserId());

        // 输出
        // 插入前主键为：0
        // 插入后主键为：15
    }
 }

