package com.ruoyi.web.controller.test;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.test.domain.WorkOrder;
import com.ruoyi.test.service.IWorOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/test/workorder")
public class WorkOrderController extends BaseController {

    private String prefix= "test/workorder";

    @Autowired
    private IWorOrderService worOrderService;

    /**
     * 工单主页面
     * @return
     */
    @RequiresPermissions("test:workorder:view")
    @GetMapping()
    public String workOrder() {
        return prefix + "/workorder";
    }

    /**
     * 获取工单列表
     */
    @RequiresPermissions("test:workorder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WorkOrder workOrder) {
        startPage();
        List<WorkOrder> list = worOrderService.selectWorkOrderList(workOrder);
        return getDataTable(list);
    }

    /**
     * 新增工单页面
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存操作
     */
    @RequiresPermissions("test:workorder:add")
    @Log(title = "工单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated WorkOrder workOrder) {
        workOrder.setCreateBy(ShiroUtils.getLoginName());
        workOrder.setStatus("0");
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(worOrderService.insertWorkOrder(workOrder));
    }

    /**
     * 修改工单页面
     */
    @GetMapping("/edit/{workOrderId}")
    public String edit(@PathVariable("workOrderId") Long workOrderId, ModelMap modelMap) {
        WorkOrder workOrder = worOrderService.selectWorkOrderById(workOrderId);
        modelMap.put("workOrder", workOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存操作
     */
    @RequiresPermissions("test:workorder:edit")
    @Log(title = "工单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated WorkOrder workOrder) {
        workOrder.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(worOrderService.updateWorkOrder(workOrder));
    }

    /**
     * 删除工单操作
     */
    @RequiresPermissions("test:workorder:remove")
    @Log(title = "工单管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(worOrderService.delWorkerOrderByIds(ids));
    }

}
