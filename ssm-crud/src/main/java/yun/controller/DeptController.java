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
 * 处理和部门有关的请求
 * @author Administrator
 *
 */
@Controller
public class DeptController {

	@Autowired
	private DeptService deptService;
	
	/**
	 * 返回所以的部门信息
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		//查出的所有部门信息
		List<Dept> list = deptService.getDepts();
		return Msg.success().add("depts", list);
	}
	
	
}
