package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import service.BookServiceImpl;
import util.UUIDUtil;
import domain.Book;

public class AddBookServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setHeaderEncoding("utf-8");
		List<FileItem> fileItems = new ArrayList<FileItem>();
		
		try {
			fileItems = sfu.parseRequest(request);
			Map<String, String[]> map = new HashMap<String, String[]>();
			
			for (FileItem fileItem : fileItems) {
				if(fileItem.isFormField()){  // 普通表单项
					String name = fileItem.getFieldName();  // 字段名
					String value = fileItem.getString("utf-8");   // 字段值
					map.put(name, new String[]{value});
					
				}else{  // 文件表单项
//					InputStream inputStream = fileItem.getInputStream();
					String filename = fileItem.getName();  // 得到的文件名
					
					String extension = FilenameUtils.getExtension(filename);
					if(!("jsp".equals(extension) || "exe".equals(extension))){
						
					}
					File storeDirectory = new File(this.getServletContext().getRealPath("/upload"));
					if(!storeDirectory.exists()){
						storeDirectory.mkdirs();
					}
				
					// 处理文件名
					if(filename != null){
						filename = FilenameUtils.getName(filename);  // 保证文件名没有绝对路径
					}
					
					String childDirectory = makeChildDirectory(storeDirectory, filename);
					filename = childDirectory + File.separator + filename;
					
					// 文件上传
					fileItem.write(new File(storeDirectory,filename));
					fileItem.delete();
					
					map.put("img_url", new String[]{filename});
					
				}
			}
			
			Book b = new Book();
			BeanUtils.populate(b, map);
			System.out.println(b.getImg_url());
			b.setId(UUIDUtil.getUUID());
			
			BookServiceImpl bsi = new BookServiceImpl();
			bsi.addBook(b);
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*request.setCharacterEncoding("utf-8");
		Book book = new Book();
		try {
			BeanUtils.populate(book, request.getParameterMap());
			book.setId(UUIDUtil.getUUID());  // 手动生成随机ID
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BookServiceImpl bsi = new BookServiceImpl();
		bsi.addBook(book);
		
		// 不写/代表相对路径，相对于本类的路径
		request.getRequestDispatcher("bookListServlet").forward(request, response);*/
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private String makeChildDirectory(File storeDirectory, String fileName) { // 目录打散
		int hashcode = fileName.hashCode();
		System.out.println(hashcode);
		String code = Integer.toHexString(hashcode);
		System.out.println(code);
		
		String childDirectory = code.charAt(0)+File.separator+code.charAt(1);
		
		File file = new File(storeDirectory,childDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		
		return childDirectory;
	}
}
