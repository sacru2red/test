package kr.ac.green.dao;

public class DaoFactory {
	private static IDao dao;
	public static void init(String db) {
		if(db.equals("mysql")) {
			dao = MySqlPlayerDao.getInstance();
		} else {
			dao = new OracleDummyDao();
		}
	}
	
	public static IDao getDao() {
		return dao;
	}
}	








