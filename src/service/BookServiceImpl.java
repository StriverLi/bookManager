package service;

import java.util.List;

import dao.BookDaoImpl;
import domain.Book;
import domain.PageBean;

public class BookServiceImpl {
	private BookDaoImpl dao = new BookDaoImpl();
	
	public List<Book> findAllBooks(){
		try {
			return dao.findAllBooks();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addBook(Book b){
		try {
			dao.addBook(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Book findBookByid(String id){
		try {
			return dao.findBookById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateBook(Book b) {
		try {
			dao.updateBook(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delBook(String id) {
		try {
			dao.delBook(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delAllBooks(String[] ids) {
		try {
			dao.delAllBooks(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Book> searchBooks(String id, String category, String name,
			String minprice, String maxprice) {

		try {
			return dao.searchBooks(id, category, name,minprice, maxprice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public PageBean findBooksPage(int currentPage, int pageSize) {
		
		try {
			int count = dao.count();
			int totalPage = (int) Math.ceil(count*1.0/pageSize);
			List<Book> list = dao.findBooksPage(currentPage,pageSize);
			
			PageBean pb = new PageBean();
			pb.setCurrentPage(currentPage);
			pb.setPageSize(pageSize);
			pb.setCount(count);
			pb.setTotalPage(totalPage);
			pb.setBooks(list);
			
			return pb;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	public List<Object> findBooksByName(String name) {
		try {
			return dao.findBooksByName(name);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
