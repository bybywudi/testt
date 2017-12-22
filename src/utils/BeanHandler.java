package utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class BeanHandler implements ResultSetHandler{
	
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
	
}