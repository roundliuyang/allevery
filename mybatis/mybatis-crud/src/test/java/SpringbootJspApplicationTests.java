import com.yly.rowbounds.dataobject.Book;
import com.yly.rowbounds.mapper.BookMapper;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在 mybatis 中，使用 RowBounds 进行分页，非常方便，不需要在 sql 语句中写 limit，即可完成分页功能。但是由于它是在 sql 查询出所有结果的基础上截取数据的，所以在数据量大的sql中并不适用，它更适合在返回数据结果较少的查询中使用
 * 最核心的是在 mapper 接口层，传参时传入 RowBounds(int offset, int limit) 对象，即可完成分页
 * 注意：由于 java 允许的最大整数为 2147483647，所以 limit 能使用的最大整数也是 2147483647，一次性取出大量数据可能引起内存溢出，所以在大数据查询场合慎重使用
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJspApplicationTests {

//    @Autowired
//    private BookMapper bookMapper;
    static BookMapper bookMapper;   //伪代码

    @Test
    public void contextLoads() {
        Book book = new Book();
        book.setBookName("隋唐演义");
        book.setBookAuthor("褚人获");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        book.setCreateDate(sdf.format(new Date()));
        book.setUpdateDate(sdf.format(new Date()));
        bookMapper.insert(book);
        System.out.println("返回的主键:   "+book.getId());
    }

    @Test
    public void query() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bookName", "");
        map.put("bookAuthor", "");
        List<Book> list = bookMapper.selectBookByName(map, new RowBounds(0, 5));

        for(Book b : list) {
            System.out.println(b.getBookName());
        }
    }

}
