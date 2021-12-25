package com.leonkk.service;

import com.leonkk.dao.IDeptDao;
import com.leonkk.po.Dept;
import com.leonkk.po.Node;
import com.leonkk.util.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("deptService")
public class IDeptServiceImpl implements IDeptService {
    @Autowired
    private IDeptDao deptDao;
    @Autowired
    private TreeBuilder treeBuilder;
    @Override
    public List<Dept> queryDeptAll() {
        return deptDao.queryDeptAll();
    }

    @Override
    public void addDept(Dept dept) {
        deptDao.addDept(dept);
    }

    @Override
    public Dept queryDeptByID(Integer id) {
        return deptDao.queryDeptByID(id);
    }

    @Override
    public void updateDept(Dept dept) {
         dept.setCreateTime(new Date());
         deptDao.updateDept(dept);
    }

    @Override
    public void deleteDeptById(Integer id) {
        deptDao.deleteDeptById(id);
    }

    @Override
    public List<Node> queryDeptTree() {
        List nodeList=deptDao.queryDeptTree();
        //包装树结构
        treeBuilder.buildTree(nodeList);
        return nodeList;
    }

}
