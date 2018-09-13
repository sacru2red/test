package kr.ac.green.cmd;

import java.sql.Connection;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dao.DaoFactory;
import kr.ac.green.dao.IDao;
import kr.ac.green.dto.PlayerCard;

public class GetListCmd implements ICmd {

	@Override
	public String action(HttpServletRequest request) {
		int perPage = 5;
		String strNum = request.getParameter("pageNum");
		
		int pageNum = 1;
		if(strNum != null) {
			pageNum = Integer.parseInt(strNum);
		}
		
		IDao dao = DaoFactory.getDao();
		Connection con = dao.connect();		
		int totalCount = dao.getTotalCount(con);
		PlayerCard[] list = dao.getList(con, pageNum, perPage);
		if(list==null || list.length==0) {
//			dao.loadPlayerDBFile(con);
			list = dao.getList(con, pageNum, perPage);
		}
		
		dao.close(con);
		
		int pageCount = totalCount / perPage;
		if(totalCount % perPage != 0) {
			pageCount++;
		}
		
		request.setAttribute("list", list);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageNum", pageNum);
		return "/list.jsp";
	}
}