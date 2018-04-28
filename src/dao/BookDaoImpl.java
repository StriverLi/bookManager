package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import util.C3P0Util;
import domain.Book;

public class BookDaoImpl {
	/**
	 * 查找所有图书
	 * @return
	 * @throws Exception
	 */
	public List<Book> findAllBooks() throws Exception{
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		return qr.query("select * from book", new BeanListHandler<Book>(Book.class));
	}
	
	/**
	 * 添加图书
	 * @param b
	 * @throws Exception
	 */
	public void addBook(Book b) throws Exception{
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		qr.update("insert into book values(?,?,?,?,?,?,?)",b.getId(),b.getName(),b.getPrice(),
				b.getPnum(),b.getCategory(),b.getDescription(),b.getImg_url());
	}

	public Book findBookById(String id) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		return qr.query("select * from book where id=?", new BeanHandler<Book>(Book.class),id);
	}

	public void updateBook(Book b) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		qr.update("update book set name=?,price=?,pnum=?,category=?,description=? where id=?",
				b.getName(),b.getPrice(),b.getPnum(),b.getCategory(),b.getDescription(),b.getId());
		
	}

	public void delBook(String id) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		qr.update("delete from book where id=?", id);
	}

	public void delAllBooks(String[] ids) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		Object[][] params = new Object[ids.length][];
		for(int i = 0; i < ids.length; i++){
			params[i] = new Object[]{ids[i]};
		}
		qr.batch("delete from book where id=?", params);
	}

	/**
	 * 多条件查询图书
	 * @param id
	 * @param category
	 * @param name
	 * @param minprice
	 * @param maxprice
	 * @return
	 * @throws Exception 
	 */
	public List<Book> searchBooks(String id, String category, String name,
			String minprice, String maxprice) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		String sql = "select * from book where 1=1";
		List<String> list = new ArrayList<String>();
		if(!"".equals(id)){
			sql += " and id like ?";
			list.add("%"+id+"%");
		}
		
		if(!"".equals(category)){
			sql += " and category=?";
			list.add(category);
		}
		
		if(!"".equals(name)){
			sql += " and name like ?";
			list.add("%"+name+"%");
		}
		
		if(!"".equals(minprice)){
			sql += " and price>?";
			list.add(minprice);
		}
		
		if(!"".equals(maxprice)){
			sql += " and price<?";
			list.add(maxprice);
		}
		
		return qr.query(sql, new BeanListHandler<Book>(Book.class),list.toArray());
		
	}
	/**
	 * 得到总记录数
	 * @return
	 * @throws Exception
	 */
	public int count() throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		long query = (Long)qr.query("select count(*) from book", new ScalarHandler(1));
		return (int)query;
	}

	/**
	 * 查找分页数据
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	public List<Book> findBooksPage(int currentPage, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		return qr.query("select * from book limit ?,?", new BeanListHandler<Book>(Book.class),(currentPage-1)*pageSize,pageSize);
		
	}

	/**
	 * 根据书名模糊查询
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<Object> findBooksByName(String name) throws Exception {
		QueryRunner qr = new QueryRunner(C3P0Util.getCpds());
		return qr.query("select name from book where name like ?", new ColumnListHandler(),"%"+name+"%");
		
	}
}
