package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Book;
import service.BookServiceImpl;

public class AddCartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String id = request.getParameter("id");
		
		BookServiceImpl bsi = new BookServiceImpl();
		Book b = bsi.findBookByid(id);
		
		// 从session中把购物车取出
		HttpSession session = request.getSession();
		Map<Book, String> cart = (Map<Book, String>) session.getAttribute("cart");
		
		int num = 1;
		if(cart == null){
			cart = new HashMap<Book, String>();
		}
		if(cart.containsKey(b)){
			num = Integer.parseInt(cart.get(b))+1;
		}
		
		cart.put(b, num+"");
		
		session.setAttribute("cart", cart);
		
		out.write("<a href='"+request.getContextPath()+"/servlet/pageServlet'>继续购物</a>,<a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a>");
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
