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
	 * ����ɾ�� ����ɾ������һ
	 * �涨�������
	 * 		������ 1-2-3
	 * 		������ 1
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")String ids) {
		//����ɾ��
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			//��װids������
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			empService.deleteBatch(del_ids);
		}else {
			//����ɾ��
			Integer id = Integer.parseInt(ids);
			empService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	
	
	/*����ɾ��
	 * @ResponseBody
	 * @RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE) public Msg
	 * deleteById(@PathVariable("id")Integer id) { empService.deleteEmp(id); return
	 * Msg.success(); }
	 */
	
	/**
	 * Ա�����·���
	 * @param emp
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	public Msg saveEmp(Emp emp) {
		System.out.println("��Ҫ���µ�����"+emp);
		empService.updateEmp(emp);
		return Msg.success();
	}
	
	/**
	 * ����id��ѯԱ��
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
	 * ����û����Ƿ����
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//���ж��û����Ƿ��ǺϷ��ı��ʽ
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
		if(!empName.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���������2-5λ���Ļ���6-16λӢ�ĺ����ֵ����");
		}
		boolean b = empService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}
	
	/**
	 * Ա������
	 * 1��Ҫ֧��JSR303У�飬��Ҫ����hibernate-validator ��
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Emp emp,BindingResult result) {
		if(result.hasErrors()) {
			//У��ʧ�ܣ�Ӧ�÷���ʧ�ܣ���ģ̬������ʾУ��ʧ�ܵĴ�����Ϣ
			Map<String,Object> map = new HashMap<>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("������ֶ���"+fieldError.getField());
				System.out.println("������Ϣ"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			empService.saveEmp(emp);
			return Msg.success();
		}
	}
			
	/**
	 * ����jackson��
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1") Integer pn) {
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ�����.startPage����������ҳ���Լ�ÿҳ��С
		PageHelper.startPage(pn, 5);
		PageHelper.orderBy("emp_id asc");
		//startPage����������������ѯ���Ƿ�ҳ��ѯ
		List<Emp> emps = empService.getAll();
		System.out.println(emps);
		//ʹ��pageinfo��װ��ѯ��Ľ��
		PageInfo<Emp> page = new PageInfo<Emp>(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * �Լ��涨Resful���  ����ʽ��ͬ��Ӧ���ܲ�ͬ
	 * 	/emp/{id}  GET���󡪡���ѯԱ��
	 * 	/emp	   POST���󡪡�����Ա��
	 * 	/emp/{id}  PUT���󡪡��޸�Ա��
	 * 	/emp/{id}  DELETE���󡪡�ɾ��Ա��
	 */
	
	
	
	/*
	 * @RequestMapping("/emps")
	 * @ResponseBody 
	 * public PageInfo<Emp> getEmpsWithJson(@RequestParam(value =
	 * "pn",defaultValue = "1") Integer pn) { 
	 * //����PageHelper��ҳ���
	 * //�ڲ�ѯ֮ǰֻ�����.startPage����������ҳ���Լ�ÿҳ��С 
	 * PageHelper.startPage(pn, 5);
	 * //startPage����������������ѯ���Ƿ�ҳ��ѯ 
	 * List<Emp> emps = empService.getAll();
	 * System.out.println(emps); 
	 * //ʹ��pageinfo��װ��ѯ��Ľ�� 
	 * PageInfo<Emp> page = newPageInfo<Emp>(emps,5); 
	 * return page; 
	 * }
	 */
	
	
	
	
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn,Model model) {
		//����PageHelper��ҳ���
		//�ڲ�ѯ֮ǰֻ�����.startPage����������ҳ���Լ�ÿҳ��С
		PageHelper.startPage(pn, 5);
		//startPage����������������ѯ���Ƿ�ҳ��ѯ
		List<Emp> emps = empService.getAll();
		System.out.println(emps);
		//ʹ��pageinfo��װ��ѯ��Ľ��
		PageInfo<Emp> page = new PageInfo<Emp>(emps,5);
		model.addAttribute("pageInfo",page);
		return "list"; 
	}  
	
}
