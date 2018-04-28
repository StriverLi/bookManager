package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.PageBean;
import service.BookServiceImpl;

public class PageServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize = 4;
		int currentPage = 1;
		
		String currPage = request.getParameter("currentPage");
		if(currPage != null){
			currentPage = Integer.parseInt(currPage);
		}
		
		BookServiceImpl bsi = new BookServiceImpl();
		
		PageBean pb = bsi.findBooksPage(currentPage,pageSize);
		
		request.setAttribute("pb", pb);
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
