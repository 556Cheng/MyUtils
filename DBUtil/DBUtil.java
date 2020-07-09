package util;

//数据库连接工具类
import java.sql.*;

public class DBUtil {
	/* 静态加载 */
	static void open() {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 返回连接对象
	 * @return Connection对象
	 */
	public static Connection getConnection() {
		try {
			open();
			return DriverManager.getConnection ("jdbc:mysql://localhost:3306/数据库名?useSSL=false&serverTimezone=UTC","用户名","密码");
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/shop?characterEnconding=UTF-8", "root",
				//	"0000");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//关闭连接
	public static void closeall (ResultSet rs,PreparedStatement pre,Connection  con) {
		try {
				if(rs!=null)
					rs.close();
				if(pre!=null)
					pre.close();
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
