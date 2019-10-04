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
	 * ��ѯ����Ա��
	 * @return
	 */
	public List<Emp> getAll() {
		// TODO Auto-generated method stub
		return empMapper.selectByExampleWithDept(null);
	}

	/**
	 * Ա������
	 * @param emp
	 */
	public void saveEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.insertSelective(emp);
	}

	/**
	 * У���û����Ƿ���� 
	 * @param empName
	 * @return true:����ǰ�û�������  false:�û���������
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
	 * ����Ա��id��ѯԱ��
	 * @param id
	 * @return
	 */
	public Emp getEmp(Integer id) {
		// TODO Auto-generated method stub
		Emp emp = empMapper.selectByPrimaryKey(id);
		return emp;
	}

	/**
	 * Ա������
	 * @param emp
	 */
	public void updateEmp(Emp emp) {
		// TODO Auto-generated method stub
		empMapper.updateByPrimaryKeySelective(emp);
	}

	/**
	 * Ա��ɾ��
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		empMapper.deleteByPrimaryKey(id);
	}

	/**
	 * ����ɾ��
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
