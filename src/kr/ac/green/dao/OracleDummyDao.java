package kr.ac.green.dao;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dto.Comment;
import kr.ac.green.dto.PlayerCard;

public class OracleDummyDao implements IDao {
	OracleDummyDao() {}

	@Override
	public Connection connect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerCard[] getAll(Connection con) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Comment[] getCommentsByPlayer_code(Connection con, int player_code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Connection con, Comment co) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close(Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(Connection con, int number) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PlayerCard getByPlayer_code(Connection con, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCount(Connection con) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PlayerCard[] getList(Connection con, int pageNum, int perPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadPlayerDBFile(Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean renewPlayers(Connection con, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean hasTable(Connection con) {
		// TODO Auto-generated method stub
		return false;
	}
}