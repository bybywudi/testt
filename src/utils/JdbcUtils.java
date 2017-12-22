
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class JdbcUtils {
	/*
	 * private static Properties config = new Properties();
	 * 
	 * 
	 * static {
	 * 
	 * try { try { config.load(JdbcUtils.class.getClassLoader().getResourceAsStream(
	 * "db.properties")); } catch (IOException e) { e.printStackTrace(); }
	 * Class.forName(config.getProperty("driver")); }catch(ClassNotFoundException e)
	 * { throw new ExceptionInInitializerError(e); }
	 * 
	 * }
	 * 
	 */
	/*
	 * //private static BasicDataSource dbcp = null; private static DataSource dbcp;
	 * //private static ThreadLocal<Connection> tl; static { try { InputStream in =
	 * JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties")
	 * ; Properties prop = new Properties(); prop.load(in);
	 * 
	 * BasicDataSourceFactory factory = new BasicDataSourceFactory(); dbcp =
	 * factory.createDataSource(prop);
	 * 
	 * //一、初始化连接池 dbcp = new BasicDataSource();
	 * 
	 * /* //设置驱动 (Class.forName())
	 * dbcp.setDriverClassName(prop.getProperty("jdbc.driver")); //设置url
	 * dbcp.setUrl(prop.getProperty("jdbc.url")); //设置数据库用户名
	 * dbcp.setUsername(prop.getProperty("jdbc.user")); //设置数据库密码
	 * dbcp.setPassword(prop.getProperty("jdbc.password")); //初始连接数量
	 * dbcp.setInitialSize( Integer.parseInt( prop.getProperty("initsize") ) );
	 * //连接池允许的最大连接数 dbcp.setMaxIdle( Integer.parseInt(
	 * prop.getProperty("maxactive") ) ); //设置最大等待时间 dbcp.setMaxWaitMillis(
	 * Integer.parseInt( prop.getProperty("maxwait") ) ); //设置最小空闲数 dbcp.setMinIdle(
	 * Integer.parseInt( prop.getProperty("minidle") ) ); //设置最大空闲数 dbcp.setMaxIdle(
	 * Integer.parseInt( prop.getProperty("maxidle") ) ); //初始化线程本地 // tl = new
	 * ThreadLocal<Connection>(); }catch(Exception e) { throw new
	 * ExceptionInInitializerError(e); } }
	 * 
	 * public static Connection getConnection() throws SQLException {
	 * 
	 * 
	 * return dbcp.getConnection();
	 * 
	 * }
	 */

	/*
	 * public static Connection getConnection() throws SQLException {
	 * 
	 * 
	 * return DriverManager.getConnection(config.getProperty("url"),
	 * config.getProperty("username"), config.getProperty("password"));
	 * 
	 * }
	 */

	// 数据库连接池
	private static BasicDataSource dbcp;

	// 为不同线程管理连接
	private static ThreadLocal<Connection> tl;

	// 通过配置文件来获取数据库参数
	static {
		try {
			Properties prop = new Properties();

			InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");

			prop.load(is);
			is.close();

			// 一、初始化连接池
			dbcp = new BasicDataSource();

			// 设置驱动 (Class.forName())
			dbcp.setDriverClassName(prop.getProperty("jdbc.driver"));
			// 设置url
			dbcp.setUrl(prop.getProperty("jdbc.url"));
			// 设置数据库用户名
			dbcp.setUsername(prop.getProperty("jdbc.user"));
			// 设置数据库密码
			dbcp.setPassword(prop.getProperty("jdbc.password"));
			// 初始连接数量
			dbcp.setInitialSize(Integer.parseInt(prop.getProperty("initsize")));
			// 连接池允许的最大连接数
			dbcp.setMaxActive(Integer.parseInt(prop.getProperty("maxactive")));
			// 设置最大等待时间
			dbcp.setMaxWait(Integer.parseInt(prop.getProperty("maxwait")));
			// 设置最小空闲数
			dbcp.setMinIdle(Integer.parseInt(prop.getProperty("minidle")));
			// 设置最大空闲数
			dbcp.setMaxIdle(Integer.parseInt(prop.getProperty("maxidle")));
			// 初始化线程本地
			tl = new ThreadLocal<Connection>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		/*
		 * 通过连接池获取一个空闲连接
		 */
		Connection conn = dbcp.getConnection();
		tl.set(conn);
		return conn;
	}
	
	public static void startTransaction() {
		try {
			Connection conn = tl.get();
			if(conn==null) {
				conn = dbcp.getConnection();
				tl.set(conn);
			}
			conn.setAutoCommit(false);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void commitTransaction() {
		try {
			Connection conn = tl.get();
			if(conn!=null) {
				conn.commit();
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 关闭数据库连接
	 */
	/*
	 * public static void closeConnection(){ try{ Connection conn = tl.get();
	 * if(conn != null){
	 * 
	 * conn.close(); tl.remove(); } }catch(Exception e){ e.printStackTrace(); } }
	 */

	public static void release(Connection conn, Statement st, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
				tl.remove();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
 
	public static void update(String sql, Object params[]) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				st.setObject(i+1, params[i]);
			}
			
			st.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(conn, st, rs);
		}
	}
	
	public static Object querry(String sql,Object params[],ResultSetHandler handler) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++) {
				st.setObject(i+1, params[i]);
			}
			
			rs = st.executeQuery();
			return handler.handler(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(conn, st, rs);
		}

		
	}
	
	public static Object querry(String sql,ResultSetHandler handler) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			
			return handler.handler(rs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			release(conn, st, rs);
		}

		
	}

}

/*
interface ResultSetHandler{
	public Object handler(ResultSet rs) ;
}


class BeanHandler implements ResultSetHandler{
	
	private Class clazz;
	public BeanHandler(Class clazz) {
		this.clazz = clazz;
	}
	
	public Object handler(ResultSet rs) {
		
		try {
			if(!rs.next()) {
				return null;
			}
			//创建封装结果集的bean，bean的类型由用户传进来
			Object bean = clazz.newInstance();
			
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			for(int i=0;i<count;i++) {
				String name = meta.getColumnName(i+1);//获取结果集每一列的名称
				Object value = rs.getObject(name);//获取每一列的值
				//反射出bean上与列名相应的属性
				Field f = bean.getClass().getDeclaredField(name);
				f.setAccessible(true);
				f.set(bean, value);
			}
			
			return bean;
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
		
		
	}
	
}*/