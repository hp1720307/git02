package com.xxxx.crm.controller;

import com.xxxx.crm.annotaions.RequirePermission;
import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.dao.UserMapper;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.ModuleService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController  {

    @Resource
    private UserService userService;

    @Resource
    private ModuleService moduleService;

    /**
     * 用户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("user/login")
    @ResponseBody
    public ResultInfo userLogin (String userName, String userPwd) {
        ResultInfo resultInfo = new ResultInfo();

        // 调用Service层的登录方法，得到返回的用户对象
        UserModel userModel = userService.userLogin(userName, userPwd);
        // 将返回的UserModel对象设置到 ResultInfo 对象中
        resultInfo.setResult(userModel);

        return resultInfo;
    }


    /**
     * 用户密码修改
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @PostMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo updateUserPassword (HttpServletRequest request,
                                          String oldPassword, String newPassword, String confirmPassword) {
        ResultInfo resultInfo = new ResultInfo();

        // 获取userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 调用Service层的密码修改方法
        userService.updateUserPassword(userId, oldPassword, newPassword, confirmPassword);
        //返回目标对象
        return resultInfo;
    }

    @RequestMapping("user/toPasswordPage")
    public String toPasswordPage(){

        return "user/password";
    }
    /**
     * 查询所有的销售人员
     * @return
     */
    @RequestMapping("user/queryAllSales")
    @ResponseBody
    public List<Map<String, Object>> queryAllSales() {

        return userService.queryAllSales();
    }
    /**
     * 多条件查询用户数据
     * @param userQuery
     * @return
     */
    @RequestMapping("user/list")
    @ResponseBody
    @RequirePermission(code = "601002")
    public Map<String, Object> queryUserByParams(UserQuery userQuery) {
        return userService.queryUserByParams(userQuery);
    }
    /**
     * 进入用户页面
     * @return
     */
    @RequestMapping("user/index")
    public String index(){
        return "user/user";
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("user/save")
    @ResponseBody
    @RequirePermission(code = "601001")
    public ResultInfo saveUser(User user) {
        userService.saveUser(user);
        return success("用户添加成功！");
    }
    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("user/update")
    @ResponseBody
    @RequirePermission(code = "601003")
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        return success("用户更新成功！");
    }
    /**
     * 进入用户添加或更新页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("user/addOrUpdateUserPage")
    public String addUserPage(Integer id, Model model){
        if(null != id){
            model.addAttribute("user",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }
    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("user/delete")
    @ResponseBody
    @RequirePermission(code = "601004")
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteBatch(ids);
        return success("用户记录删除成功");
    }

}
