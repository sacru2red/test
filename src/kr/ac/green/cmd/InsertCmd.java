package kr.ac.green.cmd;

import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dao.DaoFactory;
import kr.ac.green.dao.IDao;
import kr.ac.green.dto.Comment;
import kr.ac.green.dto.PlayerCard;

public class InsertCmd implements ICmd {

	@Override
	public String action(HttpServletRequest request) {
		System.out.println("do insertCmd");
		String comment = request.getParameter("comment");
		int player_code = Integer.parseInt(request.getParameter("player_code"));
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		
		Comment co = new Comment(player_code, comment, date);
		
		IDao dao = DaoFactory.getDao();
		Connection con = dao.connect();
		dao.insert(con, co);
		dao.close(con);
		request.setAttribute("isRedirect", true);
		return "list.html";
	}	
}