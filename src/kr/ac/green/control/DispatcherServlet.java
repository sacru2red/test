package kr.ac.green.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ac.green.cmd.CmdFactory;
import kr.ac.green.dao.DaoFactory;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 2L;
   	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String path = config.getInitParameter("cmdsInfoPath");
		ServletContext application = config.getServletContext();
		String realPath = application.getRealPath(path);
		
		Properties prop = new Properties();
		
		File file = new File(realPath);
		application.setAttribute("realPath", realPath);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(realPath);
			prop.load(fis);
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch(Exception e) {}
		}		
		
		String db = config.getInitParameter("db");
		DaoFactory.init(db);
		CmdFactory.init(prop);
		super.init(config);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		doAction(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getRequestURI().substring(
			request.getContextPath().length()
		);
		request.setAttribute("cmd", cmd);
		String nextPage = CmdFactory.searchAndDo(request, cmd);
		System.out.println("doAction");
		if(request.getAttribute("isRedirect") != null) {
			response.sendRedirect(nextPage);
		} else {
			System.out.println("nextPage is " + nextPage);
			RequestDispatcher rd =request.getRequestDispatcher(nextPage);
			rd.forward(request, response);
		}
	}
}