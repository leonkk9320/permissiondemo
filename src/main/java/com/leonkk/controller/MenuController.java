package com.leonkk.controller;

import com.leonkk.po.Menu;
import com.leonkk.po.Node;
import com.leonkk.po.RoleMenu;
import com.leonkk.service.IMenuService;
import com.leonkk.util.JsonObject;
import com.leonkk.util.R;
import com.leonkk.util.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private TreeBuilder treeBuilder;
    /**
     * 查询所有的菜单信息
     */
    @ResponseBody
    @RequestMapping("menu/menuAll")
    public JsonObject queryMenuAll(){
        JsonObject object=new JsonObject();
        List<Menu> list=menuService.queryMenuAll();
        System.out.println(list);
        object.setCode(200);
        object.setCount((long) list.size());
        object.setData(list);
        object.setMsg("ok");
        return object;
    }


    /**
     * 菜单添加操作
     */
    @RequestMapping("menu/addMenuSubmit")
    @ResponseBody
    public R addMenuSubmit(Menu menu){
        menu.setCreateTime(new Date());
        menuService.addMenu(menu);
        return R.ok();
    }

    /**
     * 菜单的删除
     */
    @RequestMapping("menu/deleteMenuById")
    @ResponseBody
    public R deleteMenById(Integer id){
        menuService.deleteMenuById(id);
        return R.ok();
    }

    /**
     * 实现修改操作
     */
    @RequestMapping("menu/updateMenuSubmit")
    @ResponseBody
    public R updateMenuSubmit(Menu menu){
        menuService.updateMenuSubmit(menu);
        return R.ok();
    }

    /**
     * 获取树状结构数据
     */
//    @RequestMapping("menu/queryMenuTree")
//    @ResponseBody
//    public Object queryMenuTree(int id){
//          //查询Node数据
//          List<Node> list=menuService.queryMenuTree();
//          //根据角色查询关联到的菜单
//          List<RoleMenu> roleMenus=menuService.queryMenuByRoleId(id);
//          //渲染已经选中内容
//          for(RoleMenu rm:roleMenus){
//                //获取当前对象的id
//                int menuId=rm.getMenuId();
//                for(Node node:list){
//                    if(node.getId()==menuId){
////                        if(node.getChildren().size()==0){//有子节点
//                            node.setChecked(true);
////                        }
//
//                    }
////                        node.setChecked(false);
//
//                }
//          }
//          //组装树结构
//          String result =treeBuilder.buildTree(list);
//          return result;
//    }

    @RequestMapping("menu/queryMenuTree")
    @ResponseBody
    public Object queryMenuTree(int id){
        //查询Node数据
        List<Node> list=menuService.queryMenuTree();
        System.out.println(list);
        //根据角色查询关联到的菜单
        List<RoleMenu> roleMenus=menuService.queryMenuByRoleId(id);
        //渲染已经选中内容
        for(RoleMenu rm:roleMenus){
            //获取当前对象的id
            int menuId=rm.getMenuId();
            for(Node node:list){
                if(node.getId()==menuId){
                    //判断是否有子节点
                    List<Menu> menusList=  menuService.isChild(menuId);
                    if(menusList.size()==0){//如果没有子节点
                        node.setChecked(true);//设置为true 选中状态
                    }
                }
            }
        }
        /*for(Node node:list){
            node.setChecked(false);
            for(RoleMenu rm:roleMenus){
                int menuId=rm.getMenuId();
                if(node.getId()==menuId){
                    node.setChecked(true);
                }
            }
        }*/
        //组装树结构
        String result =treeBuilder.buildTree(list);
        System.out.println("===============");
        System.out.println();
        return result;
    }


    /**
     * 根据菜单id查询菜单详细信息
     */
    @RequestMapping("menu/queryMenuByID")
    public String queryMenuByID(Integer id,Model model){
        //根据id查询记录详细信息
        Menu menu=menuService.queryMenuByID(id);
        //逐个设定值信息
        model.addAttribute("menu",menu);
        return "pages/updateMenu";
    }



    /***
     * 静态页面的跳转
     */
    @RequestMapping("/menu")
    public String menuIndex(){
        return "pages/menu";
    }

    /**
     * 添加页面
     */
    @RequestMapping("/addMenu")
    public String addMenu(Model model, int type, int parentId){
        System.out.println(type);
        //如果是头部添加
        if(type==-1){
            type=0;
            parentId=-1;
        }else{
            type=type+1;
        }
        model.addAttribute("type",type);
        model.addAttribute("parentId",parentId);
        return "pages/addMenu";
    }
}
