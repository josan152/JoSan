package yun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yun.bean.Dept;
import yun.dao.DeptMapper;

@Service
public class DeptService {

	@Autowired
	private DeptMapper deptMapper;
	
	public List<Dept> getDepts() {
		List<Dept> list = deptMapper.selectByExample(null);
		return list;
	}

}
