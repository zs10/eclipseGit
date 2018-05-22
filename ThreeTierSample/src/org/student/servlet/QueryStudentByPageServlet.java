package org.student.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.student.entity.Page;
import org.student.entity.Student;
import org.student.service.IStudentService;
import org.student.service.impl.StudentServiceImpl;

public class QueryStudentByPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QueryStudentByPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IStudentService studentService  = new StudentServiceImpl();
		int count = studentService.getTotalCount() ;
		Page page = new Page();
		
		String cPage = request.getParameter("currentPage")  ;//
		if(cPage == null) {
			cPage = "1" ;
		}
		int currentPage = Integer.parseInt( cPage );
		page.setCurrentPage(currentPage);

		int totalCount = studentService.getTotalCount() ;//总数据数
		page.setTotalCount(totalCount);
		
		int pageSize = 3;
		page.setPageSize(pageSize);
		
		List<Student>  students  = studentService.queryStudentsByPage(page.getCurrentPage(),page.getPageSize()) ;
		System.out.println(students);
		System.out.println(count);
		page.setStudents(students);
		
		request.setAttribute("p", page);
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
