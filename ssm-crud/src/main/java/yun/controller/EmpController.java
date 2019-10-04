package yun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import yun.bean.Emp;
import yun.bean.Msg;
import yun.service.EmpService;

@Controller
public class EmpController {

	@Autowired
	EmpService empService;
	
	
	
	/**
	 * 单个删除 批量删除二合一
	 * 规定传入参数
	 * 		批量： 1-2-3
	 * 		单个： 1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids) {
		//批量删除
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//组装ids的数组
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			empService.deleteBatch(del_ids);
		}else {
			//单个删除
			Integer id = Integer.parseInt(ids);
			empService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	
	
	/*单个删除
	 * @ResponseBody
	 * @RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE) public Msg
	 * deleteById(@PathVariable("id")Integer id) { empService.deleteEmp(id); return
	 * Msg.success(); }
	 */
	
	/**
	 * 员工更新方法
	 * @param emp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	public Msg saveEmp(Emp emp) {
		System.out.println("将要更新的数据"+emp);
		empService.updateEmp(emp);
		return Msg.success();
	}
	
	/**
	 * 根据id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Emp emp = empService.getEmp(id);
		return Msg.success().add("emp", emp);
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//先判断用户名是否是合法的表达式
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者6-16位英文和数字的组合");
		}
		boolean b = empService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	/**
	 * 员工保存
	 * 1、要支持JSR303校验，需要导入hibernate-validator 包
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Emp emp,BindingResult result) {
		if(result.hasErrors()) {
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String,Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名"+fieldError.getField());
				System.out.println("错误信息"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			empService.saveEmp(emp);
			return Msg.success();
		}
	}
			
	/**
	 * 导入jackson包
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn) {
		//引入PageHelper分页插件
		//在查询之前只需调用.startPage方法，传入页码以及每页大小
		PageHelper.startPage(pn, 5);
		PageHelper.orderBy("emp_id asc");
		//startPage方法后紧跟的这个查询就是分页查询
		List<Emp> emps = empService.getAll();
		System.out.println(emps);
		//使用pageinfo包装查询后的结果
		PageInfo<Emp> page = new PageInfo<Emp>(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 自己规定Resful风格  请求方式不同对应功能不同
	 * 	/emp/{id}  GET请求——查询员工
	 * 	/emp	   POST请求——保存员工
	 * 	/emp/{id}  PUT请求——修改员工
	 * 	/emp/{id}  DELETE请求——删除员工
	 */
	
	
	
	/*
	 * @RequestMapping("/emps")
	 * @ResponseBody 
	 * public PageInfo<Emp> getEmpsWithJson(@RequestParam(value =
	 * "pn",defaultValue = "1") Integer pn) { 
	 * //引入PageHelper分页插件
	 * //在查询之前只需调用.startPage方法，传入页码以及每页大小 
	 * PageHelper.startPage(pn, 5);
	 * //startPage方法后紧跟的这个查询就是分页查询 
	 * List<Emp> emps = empService.getAll();
	 * System.out.println(emps); 
	 * //使用pageinfo包装查询后的结果 
	 * PageInfo<Emp> page = newPageInfo<Emp>(emps,5); 
	 * return page; 
	 * }
	 */
	
	
	
	
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn,Model model) {
		//引入PageHelper分页插件
		//在查询之前只需调用.startPage方法，传入页码以及每页大小
		PageHelper.startPage(pn, 5);
		//startPage方法后紧跟的这个查询就是分页查询
		List<Emp> emps = empService.getAll();
		System.out.println(emps);
		//使用pageinfo包装查询后的结果
		PageInfo<Emp> page = new PageInfo<Emp>(emps,5);
		model.addAttribute("pageInfo",page);
		return "list"; 
	}  
	
}
