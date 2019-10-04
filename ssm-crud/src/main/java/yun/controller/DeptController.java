package yun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yun.bean.Dept;
import yun.bean.Msg;
import yun.service.DeptService;

/**
 * ����Ͳ����йص�����
 * @author Administrator
 *
 */
@Controller
public class DeptController {

	@Autowired
	private DeptService deptService;
	
	/**
	 * �������ԵĲ�����Ϣ
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//��������в�����Ϣ
		List<Dept> list = deptService.getDepts();
		return Msg.success().add("depts", list);
	}
	
	
}
