package yun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yun.bean.Emp;
import yun.bean.EmpExample;
import yun.bean.EmpExample.Criteria;
import yun.dao.EmpMapper;

@Service
public class EmpService {

	@Autowired
	EmpMapper empMapper;
	
	/**
	 * 查询所有员工
	 * @return
	 */
	public List<Emp> getAll() {
		// TODO Auto-generated method stub
		return empMapper.selectByExampleWithDept(null);
	}

	/**
	 * 员工保存
	 * @param emp
	 */
	public void saveEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.insertSelective(emp);
	}

	/**
	 * 校验用户名是否可用 
	 * @param empName
	 * @return true:代表当前用户名可用  false:用户名不可用
	 */
	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		EmpExample example = new EmpExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = empMapper.countByExample(example);
		return count == 0;
	}

	/**
	 * 按照员工id查询员工
	 * @param id
	 * @return
	 */
	public Emp getEmp(Integer id) {
		// TODO Auto-generated method stub
		Emp emp = empMapper.selectByPrimaryKey(id);
		return emp;
	}

	/**
	 * 员工更新
	 * @param emp
	 */
	public void updateEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.updateByPrimaryKeySelective(emp);
	}

	/**
	 * 员工删除
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		empMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteBatch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmpExample example = new EmpExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		empMapper.deleteByExample(example);
	}

}
