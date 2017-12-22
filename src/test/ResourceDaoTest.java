package test;

import java.util.List;

import org.junit.Test;

import dao.impl.ResourceDaoImpl;
import dao.impl.RoleDaoImpl;

public class ResourceDaoTest {
	@Test
	public void testGetAll() {
		ResourceDaoImpl dao = new ResourceDaoImpl();
		List list = dao.getAll();
	}
}
