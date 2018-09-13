package kr.ac.green.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import kr.ac.green.dto.CAM;
import kr.ac.green.dto.CB;
import kr.ac.green.dto.CDM;
import kr.ac.green.dto.CF;
import kr.ac.green.dto.CM;
import kr.ac.green.dto.Comment;
import kr.ac.green.dto.GK;
import kr.ac.green.dto.LM;
import kr.ac.green.dto.LW;
import kr.ac.green.dto.LWB;
import kr.ac.green.dto.PlayerCard;
import kr.ac.green.dto.Position;
import kr.ac.green.dto.RB;
import kr.ac.green.dto.RM;
import kr.ac.green.dto.RW;
import kr.ac.green.dto.RWB;
import kr.ac.green.dto.ST;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MySqlPlayerDao implements IDao {

	private static final MySqlPlayerDao instance = new MySqlPlayerDao();

	private MySqlPlayerDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	static MySqlPlayerDao getInstance() {
		return instance;
	}

	@Override
	public Connection connect() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/test", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public boolean hasTable(Connection con) {
		boolean hasTable = false;
		PreparedStatement pStmt = null;
		ResultSet resultSet = null;
		boolean result = false;

		String sql = "SELECT EXISTS (SELECT 1"
				+ " FROM Information_schema.tables"
				+ " WHERE table_name = 'players'" + ") AS flag";
		try {
			pStmt = con.prepareStatement(sql);
			resultSet = pStmt.executeQuery(sql);
			if (resultSet.next()) {
				hasTable = resultSet.getBoolean("flag");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(resultSet);
			close(pStmt);
		}
		if (!hasTable) {
			sql = "CREATE TABLE players ("
					+"player_code		INT				PRIMARY KEY		NOT NULL	AUTO_INCREMENT,"
					+"srcThumb			CHAR(100),"
					+"player_name		CHAR(20),"
					+"player_price_bp1	INT(15)	,"
					+"player_price_bp2	INT(15)	,"
					+"player_price_bp3	INT(15)	,"
					+"player_price_bp4	INT(15)	,"
					+"player_price_bp5	BIGINT	,"
					+"player_price_bp6	BIGINT	,"
					+"player_price_bp7	BIGINT	,"
					+"player_price_bp8	BIGINT	,"
					+"player_price_bp9	BIGINT	,"
					+"player_price_bp10	BIGINT	);";
			try {
				pStmt = con.prepareStatement(sql);
				pStmt.executeUpdate();
				result = true;
			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				close(pStmt);
			}
		}
		sql = "SELECT EXISTS (SELECT 1"
				+ " FROM Information_schema.tables"
				+ " WHERE table_name = 'positions'" + ") AS flag";
		try {
			pStmt = con.prepareStatement(sql);
			resultSet = pStmt.executeQuery(sql);
			if (resultSet.next()) {
				hasTable = resultSet.getBoolean("flag");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			close(resultSet);
			close(pStmt);
		}
		
		if (!hasTable) {
			sql = "CREATE TABLE positions ("
					+"player_code		INT				NOT NULL,"
					+"position			CHAR(10)		NOT NULL,"
					+"stat				INT				NOT NULL );";
			try {
				pStmt = con.prepareStatement(sql);
				pStmt.executeUpdate();
				result = true;
			} catch (SQLException se) {
				result = false;
				se.printStackTrace();
			} finally {
				close(pStmt);
			}
		}
		return result;
	}

	@Override
	public PlayerCard[] getAll(Connection con) {
		String sqlFromPlayers = "SELECT * FROM players;";
		String sqlFromPositions = "SELECT * FROM positions;";
		//pl INNER JOIN positions po ON pl.player_code=po.player_code ORDER BY po.stat DESC;
		PlayerCard[] list = null;
		PreparedStatement pStmtPlayers = null;
		PreparedStatement pStmtPositions = null;
		ResultSet rsPlayers = null;
		ResultSet rsPositions = null;

		try {
			pStmtPlayers = con.prepareStatement(sqlFromPlayers);
			rsPlayers = pStmtPlayers.executeQuery();
			pStmtPositions = con.prepareStatement(sqlFromPositions);
			rsPositions = pStmtPositions.executeQuery();
			
			rsPlayers.last();
			int count = rsPlayers.getRow();
			list = new PlayerCard[count];
			rsPlayers.beforeFirst();
			int idx = 0;
			
			while (rsPlayers.next()) {
				list[idx++] = getPlayer(rsPlayers, rsPositions);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rsPlayers);
			close(pStmtPlayers);
			close(rsPositions);
			close(pStmtPositions);
		}
		return list;
	}

	@Override
	public boolean renewPlayers(Connection con, HttpServletRequest request) {
		// to false
		boolean result = true;

		ServletContext application = request.getSession().getServletContext();
		Long lastLoadPlayers = (Long) application
				.getAttribute("lastLoadPlayers");

		if (lastLoadPlayers != null) {
			long differ = Calendar.getInstance().getTimeInMillis()
					- lastLoadPlayers;
			if (differ > 3600000) {
				// parse method
			}
		} else {
			loadPlayerDBFile(con);
		}
		return result;
	}

	@Override
	public int insert(Connection con, Comment comment) {
		String sql = "INSERT INTO comments (player_code, comment, edit_time) VALUES (?, ?, ?)";
		int result = 0;
		PreparedStatement pStmt = null;
		
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, comment.getPlayer_code());
			pStmt.setString(2, comment.getComment());
			pStmt.setDate(3, comment.getEdit_time());
			result = pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	@Override
	public Comment[] getCommentsByPlayer_code(Connection con, int player_code) {
		String sql = "SELECT * FROM comments WHERE player_code="+player_code+";";
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			Vector<Comment> vector = new Vector<Comment>();
			while(rs.next()) {
				String comment = rs.getString("comment");
				Date date = rs.getDate("edit_time");
				vector.add(new Comment(player_code, comment, date));
			}
			
			return vector.toArray(new Comment[0]);
		} catch(SQLException se) {
			se.printStackTrace();
		}
		return null;
	}

	@Override
	public int delete(Connection con, int number) {
		String sql = "DELETE FROM players WHERE player_code = ?";
		PreparedStatement pStmt = null;
		int result = 0;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, number);
			result = pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}

	@Override
	public PlayerCard getByPlayer_code(Connection con, int player_code) {
		String sqlPlayers = "SELECT * FROM players WHERE player_code = ?";
		String sqlPositions = "SELECT * FROM positions WHERE player_code = ?";
		PlayerCard player = null;
		PreparedStatement pStmtPlayers = null;
		ResultSet rsPlayers = null;
		PreparedStatement pStmtPositions = null;
		ResultSet rsPositions = null;

		try {
			pStmtPlayers = con.prepareStatement(sqlPlayers);
			pStmtPlayers.setInt(1, player_code);
			rsPlayers = pStmtPlayers.executeQuery();
			
			pStmtPositions = con.prepareStatement(sqlPositions);
			pStmtPositions.setInt(1, player_code);
			rsPositions = pStmtPositions.executeQuery();
			if (rsPlayers.next()) {
				player = getPlayer(rsPlayers, rsPositions);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return player;
	}

	@Override
	public int getTotalCount(Connection con) {
		String sql = "SELECT count(*) FROM players";
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		int rowCount = 0;
		try {
			pStmt = con.prepareStatement(sql);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				rowCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}
		return rowCount;
	}

	public void loadPlayerDBFile(Connection con) {
		try{
			WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getCookieManager().setCookiesEnabled(false);
			System.out.println("loadPlayerDBFile start");
			HtmlPage page = (HtmlPage) webClient
			.getPage("http://fifaonline4.nexon.com/datacenter/index?strSeason=%2C298%2C");
			webClient.waitForBackgroundJavaScript (140000);
			String toParse = page.asXml();
			
			FileWriter fw = new FileWriter("2.txt");
			fw.write(toParse);
			fw.close();

			Document doc = Jsoup.parse(toParse);
			
			// 선수들을 감싸고있는 div#divPlayerList
			Elements playerList = 
					doc.select(
							"body div#wrapper main#middle div.datacenter div.wrap "
							+ "div.player_list div.content div.player_list_wrap div.tbody div#divPlayerList");
			// 개인별 player_info
			Elements player_info =  playerList.select("div.tr div div.player_info");
			Iterator<Element> player_infoItr = player_info.iterator();
			
			Vector<PlayerCard> vector = new Vector<PlayerCard>();

			while(player_infoItr.hasNext()) {
				Element playerE = player_infoItr.next();
				
				// playerCode 구하기 :: div class "player_info" 아래 div의 클래스 이름가져오기
				// playerE ==> div#divPlayerList div.tr div div.player_info
				// playerE select("div") ==> 자기자신
				String className = playerE.select("div div div").first().className();
				String playerCode = className.substring(className.lastIndexOf("_")+1);
				int player_code = Integer.parseInt(playerCode);
				
				//썸네일 주소 가져오기
				String srcThumb = playerE.select("div.thumb img").first().attr("src");
				
				//시즌썸네일
				//http://s.nx.com/s2/game/fo4/obt/externalAssets/season/KFA.png
				
				//이름가져오기
				String player_name = playerE.select("div.info_top div.name").text();
				
				//포지션s
				String[] positions = playerE.select("div.info_middle span.txt").text().split(" ");
				String[] statOfpositions =playerE.select("div.info_middle span.skillData_"+player_code).text().split(" ");
				
				Position[] positionArray = new Position[positions.length];
				for(int i=0; i<positions.length; i++) {
					Position p = getPosition(positions[i]);
						
					p.setStatus(Integer.parseInt(statOfpositions[i]));
					positionArray[i] = p;
				}
				
				Elements divs = playerList.select("div.tr div");
				Iterator<Element> tempItr = divs.iterator();
				Long[] bps = new Long[10];
				while(tempItr.hasNext()) {
					Element tempE = tempItr.next();
					// playerCode로 끝나는 Element를 goalE에 저장함
					if(tempE.className().endsWith("bp_"+player_code)) {
						String fullTest = tempE.text();
						String[] split = fullTest.split(" ");
						for(int i=0; i<split.length; i++) {
							bps[i] = (Long) Long.parseLong(split[i].replace(",", ""));
						}
						break;
					}
				}
				
				PlayerCard player = new PlayerCard(player_code, player_name, positionArray, bps, srcThumb);
				vector.add(player);
			}
			
			PlayerCard[] playerArray = vector.toArray(new PlayerCard[vector.size()]);
			
			StringBuffer sqlPlayersTable = new StringBuffer("INSERT INTO players (player_code, srcThumb, player_name, player_price_bp1, player_price_bp2, player_price_bp3,"
					+"player_price_bp4, player_price_bp5, player_price_bp6, player_price_bp7, player_price_bp8, player_price_bp9, player_price_bp10)"
					+"VALUES ");
			StringBuffer sqlPositionsTable = new StringBuffer("INSERT INTO positions (player_code, position, stat) VALUES ");
			
			for(int i=0; i<playerArray.length; i++) {
				PlayerCard player = playerArray[i];
				Long[] bps = player.getBps();
				sqlPlayersTable.append("(" +player.getPlayer_code() + ", '" + player.getSrcThumb() + "', '" + player.getPlayer_name()+ "', ");
				for(int j=0; j<bps.length; j++) {
					sqlPlayersTable.append(bps[j]);
					if(j != (bps.length-1)) {
						sqlPlayersTable.append(", ");
					} else {
						sqlPlayersTable.append(")");
						if(i != playerArray.length-1) {
							sqlPlayersTable.append(",");
						}
					}
				}
				if(i == playerArray.length-1) {
					sqlPlayersTable.append(";");
				}
				Position[] positions = player.getPositions();
				for(int k=0; k<positions.length; k++) {
					String className = positions[k].getClass().getName();
					className = className.substring(className.lastIndexOf(".")+1);
					sqlPositionsTable.append("(" +player.getPlayer_code() + ", '" +className + "', " +positions[k].getStatus()+ ")");
					if(i != (playerArray.length-1)) {
						sqlPositionsTable.append(", ");
					}
				}
			}
			String sql = sqlPlayersTable.toString();
			PreparedStatement pSmtm = con.prepareStatement(sql);
			pSmtm.executeUpdate(sql);
			String sql2 = sqlPositionsTable.toString();
			PreparedStatement pSmtm2 = con.prepareStatement(sql2);
			pSmtm2.executeUpdate(sql2);
		} catch (SQLException e1) {
			loadPlayerDBFile(con);
//			System.out.println("loadPlayerDBFile is fail");
//			e1.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("loadPlayerDBFile is done");
	}

	@Override
	public PlayerCard[] getList(Connection con, int pageNum, int perPage) {
//		String sql = "SELECT * FROM players pl INNER JOIN positions po ON pl.player_code=po.player_code ORDER BY po.stat DESC LIMIT ?, ?";
		String sqlPlayers = "SELECT * FROM players ORDER BY player_name DESC LIMIT ?, ?";
		StringBuffer sqlPositions = new StringBuffer("SELECT * FROM positions WHERE player_code=");
		PreparedStatement pStmtPlayers = null;
		ResultSet rsPlayers = null;
		PreparedStatement pStmtPositions = null;
		ResultSet rsPositions = null;

		PlayerCard[] list = null;
		try {
			pStmtPlayers = con.prepareStatement(sqlPlayers);
			pStmtPlayers.setInt(1, (pageNum - 1) * perPage);
			pStmtPlayers.setInt(2, perPage);
			rsPlayers = pStmtPlayers.executeQuery();
			while(rsPlayers.next()) {
				sqlPositions.append(rsPlayers.getInt("player_code"));
				if(!rsPlayers.isLast()) {
					sqlPositions.append("||");
				} else {
					sqlPositions.append(";");
				}
			}
			rsPlayers.beforeFirst();
			
			pStmtPositions = con.prepareStatement(sqlPositions.toString());
			rsPositions = pStmtPositions.executeQuery();
			
			Vector<PlayerCard> vec = new Vector<PlayerCard>();
			while (rsPlayers.next()) {
				vec.add(getPlayer(rsPlayers, rsPositions));
			}
			list = vec.toArray(new PlayerCard[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rsPlayers);
			close(pStmtPlayers);
			close(rsPositions);
			close(pStmtPositions);
		}
		return list;
	}

	// bind DTO
	private PlayerCard getPlayer(ResultSet rsPlayers, ResultSet rsPositions) throws SQLException{
		int player_code = rsPlayers.getInt("player_code");
		String player_name = rsPlayers.getString("player_name");
		String srcThumb = rsPlayers.getString("srcThumb");
		Long[] bps = new Long[10];
		for(int i=0; i<bps.length; i++) {
			bps[i] = rsPlayers.getLong("player_price_bp"+(i+1));
		}
		
		Vector<Position> positions = new Vector<Position>();
		while(rsPositions.next()) {
			if(player_code == rsPositions.getInt("player_code")) {
				int stat = rsPositions.getInt("stat");
				Position p = getPosition(rsPositions.getString("position"));
				p.setStatus(stat);
				positions.add(p);
			}
		}
		rsPositions.first();
		Position[] positionArray = positions.toArray(new Position[positions.size()]);
		PlayerCard p = new PlayerCard(player_code, player_name, positionArray, bps, srcThumb);
		return p;
	}
	
	private Position getPosition(String position) {
		switch(position) {
			case "CAM":
				return new CAM();
			case "CB":
				return new CB();
			case "CDM":
				return new CDM();
			case "CF":
				return new CF();
			case "CM":
				return new CM();
			case "RB":
				return new RB();
			case "LB":
				return new LM();
			case "LM":
				return new LM();
			case "LW":
				return new LW();
			case "LWB":
				return new LWB();
			case "RM":
				return new RM();
			case "RW":
				return new RW();
			case "RWB":
				return new RWB();
			case "ST":
				return new ST();
			case "GK":
				return new GK();
			default:
				return null;
		}
	}

	private void close(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
	}

	private void close(Statement stmt) {
		try {
			if(stmt != null) {
				stmt.close();
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void close(Connection con) {
		try {
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
		}
	}
}