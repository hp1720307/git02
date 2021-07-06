package com.xxxx.crm.controller;

import com.xxxx.crm.annotaions.RequirePermission;
import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    /**
     * 多条件分页查询营销机会
     * @param query
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    public Map<String, Object> querySaleChanceByParams (SaleChanceQuery query, Integer flag, HttpServletRequest request) {
        // 查询参数 flag=1 代表当前查询为开发计划数据，设置查询分配人参数
        if (null != flag && flag == 1) {
            // 获取当前登录用户的ID
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
            query.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(query);
    }
    /**
     * 进入营销机会页面
     * @return
     */
    @RequestMapping("index")
    public String index () {
        return "saleChance/sale_chance";
    }
    /**
     * 添加营销机会数据
     * @param request
     * @param saleChance
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    @RequirePermission(code = "101002")
    public ResultInfo saveSaleChance(HttpServletRequest request, SaleChance saleChance){
        // 获取用户ID
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 获取用户的真实姓名
        String trueName = userService.selectByPrimaryKey(userId).getTrueName();
        // 设置营销机会的创建人
        saleChance.setCreateMan(trueName);
        // 添加营销机会的数据
        saleChanceService.saveSaleChance(saleChance);
        return success("营销机会数据添加成功！");
    }

    /**
     * 更新营销机会数据
     * @param request
     * @param saleChance
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    @RequirePermission(code = "101004")
    public ResultInfo updateSaleChance(HttpServletRequest request, SaleChance saleChance){

        // 更新营销机会的数据
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功！");
    }
    /**
     * 机会数据添加与更新表单页面视图转发
     *      id为空  添加操作
     *      id非空  修改操作
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model){
        // 如果id不为空，表示是修改操作，修改操作需要查询被修改的数据
        if (null != id) {
            // 通过主键查询营销机会数据
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            // 将数据存到作用域中
            model.addAttribute("saleChance", saleChance);
        }
        return "saleChance/add_update";
    }
    /**
     * 删除营销机会数据
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    @RequirePermission(code = "101003")
    public ResultInfo deleteSaleChance (Integer[] ids) {
        // 删除营销机会的数据
        saleChanceService.deleteBatch(ids);
        return success("营销机会数据删除成功！");
    }
}
