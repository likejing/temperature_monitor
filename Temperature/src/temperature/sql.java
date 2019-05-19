package temperature;


import java.sql.*; 

public class sql {
	public static Connection getConn() {
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://127.0.0.1:2019;databaseName=smart_platform;";
		String username="sa";
		String password="666666";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 添加数据命令
	public static int insert(String tablename, String keys, String values) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into "+tablename+" ("+keys+") value ("+values+");";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try{
				pstmt.close();
				conn.close();
			}
			catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		return i;
	}
	//增加新表命令
	public static int createtable(String tablename,String tablecondition) {
		Connection conn = getConn();
		int i = 0;
		String sql = "create table  if not exists " + tablename+" "+tablecondition+";";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	//删除数据命令
	public static int delete(String tablename,String condition) {
		Connection conn = getConn();
		int i = 0;
		String sql = "delete from " + tablename+" "+condition+";";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	//删除表格命令
	public static int droptable(String tablename) {
		Connection conn = getConn();
		int i = 0;
		String sql = "drop table " + tablename+ ";";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	// 修改数据命令
	public static int update(String tablename, String key_values,String condition) {
				Connection conn = getConn();
				int i = 0;
				String sql = "UPDATE " + tablename + " SET "+key_values+" "+condition;
				PreparedStatement pstmt = null;
				try {
					pstmt = (PreparedStatement) conn.prepareStatement(sql);
					i = pstmt.executeUpdate();
					pstmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return i;
			}
			//查询数据命令
	public static String[][] select (String keys,String tablename,String condition,int rows,int cols) {
		String[][] data= new String[rows][cols];
		Connection conn = getConn();
		Statement st;
		ResultSet rs;
		String sql = "select "+keys+" from " + tablename +" "+condition +";";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			int col = rs.getMetaData().getColumnCount();
			// 将读取的数据存入data中
			int i = 0;
			while (rs.next()) {
				for(int coltemp=1;coltemp<=col;coltemp++){
					data[i][coltemp-1] = rs.getString(coltemp);//firstindex=1
				}
				i++;
				if(i>=rows){
					break;
				}
			}
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
