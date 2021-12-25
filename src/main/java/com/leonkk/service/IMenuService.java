package com.leonkk.service;

import com.leonkk.po.Menu;
import com.leonkk.po.Node;
import com.leonkk.po.RoleMenu;

import java.util.List;

public interface IMenuService {
    /**
     * 查询所有的菜单信息
     */
    List<Menu> queryMenuAll();

    /**
     * 添加菜单
     */
    void addMenu(Menu menu);

    /**
     * 删除功能
     */
    void deleteMenuById(Integer id);


    /**
     * 根据id查询菜单详细信息
     */
    Menu queryMenuByID(Integer id);

    /**
     * 修改菜单记录信息
     */
    void updateMenuSubmit(Menu menu);



    /**
     * 根据角色查询关联到的菜单
     */
    List<RoleMenu> queryMenuByRoleId(Integer roleID);

    /**
     * 获取树结构的菜单
     */
    List<Node> queryMenuTree();

    /**
     * 判断是否有子节点
     */
    List<Menu> isChild(Integer id);

}
