package kr.ac.green.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dao.DaoFactory;
import kr.ac.green.dao.IDao;

public class InitCmd implements ICmd {

	@Override
	public String action(HttpServletRequest request) {
		IDao dao = DaoFactory.getDao(); 
		Connection con = dao.connect();
		
		if(dao.hasTable(con) && dao.renewPlayers(con, request)) {
			request.getSession().getServletContext().setAttribute("init", true);
		}
		request.setAttribute("cmd", "/list.html");
		return CmdFactory.searchAndDo(request, "/list.html");
	}
}