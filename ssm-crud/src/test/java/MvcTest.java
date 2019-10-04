import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;

import yun.bean.Emp;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class MvcTest {

	@Autowired
	WebApplicationContext context;

	
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc() {
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
	
		MockHttpServletRequest request = result.getRequest();
	
		PageInfo<Emp> pi = (PageInfo<Emp>) request.getAttribute("pageInfo");
		System.out.println("��ǰҳ��"+pi.getPageNum());
		System.out.println("��ҳ��"+pi.getPages());
		System.out.println("�ܼ�¼��"+pi.getTotal());
		
		
		//��ȡԱ������
		List<Emp> list = pi.getList();
		for (Emp emp : list) {
			System.out.println("ID: "+emp.getEmpId()+"--Name"+emp.getEmpName());
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
