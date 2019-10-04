import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import yun.bean.Dept;
import yun.bean.Emp;
import yun.dao.DeptMapper;
import yun.dao.EmpMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class MapperTest {
	
	@Autowired
	DeptMapper deptMapper;
	
	@Autowired
	EmpMapper empMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD() {
		System.out.println(deptMapper);
		System.out.println(sqlSession);
		
		
		//���벿��
/*		deptMapper.insertSelective(new Dept(null,"������"));
		deptMapper.insertSelective(new Dept(null,"���Բ�"));
*/		
		//����Ա��
		
//		empMapper.insertSelective(new Emp(null,"TOM","M", "tom@152.com", 1));
		
		//��������
//		
//		  EmpMapper mapper = sqlSession.getMapper(EmpMapper.class); for(int
//		 i=0;i<1000;i++) { String uid = UUID.randomUUID().toString().substring(0,
//		  5)+i; mapper.insertSelective(new Emp(null, uid, "M", uid+"@152.com", 1)); }
//		  System.out.println("����ִ�����");
//		 
		
	
		List<Emp> a = empMapper.selectByExampleWithDept(null);
		for (Emp emp : a) {
			System.out.println(emp.getEmpName());
		}
	}
}
