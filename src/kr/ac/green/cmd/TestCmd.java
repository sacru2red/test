package kr.ac.green.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dao.DaoFactory;
import kr.ac.green.dao.IDao;

public class TestCmd implements ICmd {
	@Override
	public String action(HttpServletRequest request) {
		IDao dao = DaoFactory.getDao(); 
		Connection con = dao.connect();
		
		if(dao.hasTable(con) && dao.renewPlayers(con, request)) {
			request.getSession().getServletContext().setAttribute("init", true);
		}
		
		dao.loadPlayerDBFile(con);
		return "done.jsp";
	}
}