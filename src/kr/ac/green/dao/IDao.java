package kr.ac.green.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dto.Comment;
import kr.ac.green.dto.PlayerCard;

public interface IDao {
	public Connection connect();
	public PlayerCard[] getAll(Connection con);
	public int insert(Connection con, Comment co);
	public void close(Connection con);
	public int delete(Connection con, int number);
	public PlayerCard getByPlayer_code(Connection con, int number);
	public int getTotalCount(Connection con);
	public PlayerCard[] getList(
			Connection con, int pageNum, int perPage
			
	);
	public void loadPlayerDBFile(Connection con);
	public boolean renewPlayers(Connection con, HttpServletRequest request);
	public boolean hasTable(Connection con);
	public Comment[] getCommentsByPlayer_code(Connection con, int player_code);
}














