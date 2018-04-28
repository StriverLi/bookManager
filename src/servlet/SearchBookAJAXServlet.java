package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Book;
import service.BookServiceImpl;

public class SearchBookAJAXServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		
		BookServiceImpl bsi = new BookServiceImpl();
		
		List<Object> list = bsi.findBooksByName(name);
		
		String str = "";
		for(int i = 0; i < list.size(); i++){
			if(i > 0)
				str += ",";
			str += list.get(i);
		}
		
		out.write(str);
		//request.setAttribute("str", str);
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
