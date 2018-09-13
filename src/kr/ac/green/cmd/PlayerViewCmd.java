package kr.ac.green.cmd;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.tribes.util.Arrays;

import kr.ac.green.dao.DaoFactory;
import kr.ac.green.dao.IDao;
import kr.ac.green.dto.Comment;
import kr.ac.green.dto.PlayerCard;

public class PlayerViewCmd implements ICmd {

	@Override
	public String action(HttpServletRequest request) {
		int player_code = Integer.parseInt(request.getParameter("player_code"));
		IDao dao = DaoFactory.getDao(); 
		Connection con = dao.connect();
		
		PlayerCard player = dao.getByPlayer_code(con, player_code);
		request.setAttribute("player", player);
		
		Comment[] comments = dao.getCommentsByPlayer_code(con, player_code);
		request.setAttribute("commentArray", comments);
		System.out.println("comments in CMD" + Arrays.toString(comments));
		
		return "player_view.jsp?player_code="+player_code;
	}
}