package com.yly.rowbounds.mapper;

import com.yly.rowbounds.dataobject.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BookMapper {
	
	//添加数据
	int insert(Book book);
	
	//模糊查询
	List<Book> selectBookByName(Map<String, Object> map, RowBounds rowBounds);

}