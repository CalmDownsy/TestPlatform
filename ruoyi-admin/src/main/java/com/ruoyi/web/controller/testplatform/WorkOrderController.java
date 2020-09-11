package com.ruoyi.web.controller.testplatform;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ulpay.testplatform.common.enums.BusinessLine;
import com.ulpay.testplatform.common.enums.WorkOrderType;
import com.ulpay.testplatform.domain.WorkOrder;
import com.ulpay.testplatform.domain.WorkOrderReportData;
import com.ulpay.testplatform.domain.WorkOrderReportRequest;
import com.ulpay.testplatform.service.IWorOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testplatform/workorder")
public class WorkOrderController extends BaseController {

    private String prefix = "testplatform/workorder";

    @Autowired
    private IWorOrderService worOrderService;

    /**
     * 工单主页面
     *
     * @return
     */
    @RequiresPermissions("testplatform:workorder:view")
    @GetMapping()
    public String workOrder() {
        return prefix + "/workorder";
    }

    /**
     * 工单占比页面
     *
     * @return
     */
    @RequiresPermissions("testplatform:workorder:view")
    @GetMapping("/proportion")
    public String proportion() {
        return prefix + "/proportion";
    }

    /**
     * 工单趋势页面
     *
     * @return
     */
    @RequiresPermissions("testplatform:workorder:view")
    @GetMapping("/rend")
    public String proportionDetail() {
        return prefix + "/rend";
    }

    /**
     * 累计占比页面
     *
     * @return
     */
    @RequiresPermissions("testplatform:workorder:view")
    @GetMapping("/cumproportion")
    public String cumProportion() {
        return prefix + "/cumproportion";
    }

    /**
     * 获取工单列表
     */
    @RequiresPermissions("testplatform:workorder:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(WorkOrder workOrder) {
        startPage();
        List<WorkOrder> list = worOrderService.selectWorkOrderList(workOrder);
        return getDataTable(list);
    }

    /**
     * 获取工单占比
     */
    @RequiresPermissions("testplatform:workorder:list")
    @PostMapping("/selectWorkOrderProportion")
    @ResponseBody
    public AjaxResult selectWorkOrderProportion(WorkOrderReportRequest reportRequest) {
        AjaxResult ajaxResult;
        JSONObject echartsOption = worOrderService.selectWorkOrderProportion(reportRequest);
        if (echartsOption.get("series") instanceof Integer) {
            return AjaxResult.warn("未查询到结果");
        }
        ajaxResult = AjaxResult.success(echartsOption);
        return ajaxResult;
    }

    /**
     * 获取工单趋势
     */
    @RequiresPermissions("testplatform:workorder:list")
    @PostMapping("/selectWorkOrderRend")
    @ResponseBody
    public AjaxResult selectWorkOrderRend(WorkOrderReportRequest reportRequest) {
        AjaxResult ajaxResult;
        JSONObject echartsOption = worOrderService.selectWorkOrderRend(reportRequest);
        if (echartsOption.get("series") instanceof Integer) {
            return AjaxResult.warn("未查询到结果");
        }
        ajaxResult = AjaxResult.success(echartsOption);
        return ajaxResult;
    }

    /**
     * 获取工单累计占比
     */
    @RequiresPermissions("testplatform:workorder:list")
    @PostMapping("/selectCumProportion")
    @ResponseBody
    public AjaxResult selectCumProportion(WorkOrderReportRequest reportRequest) {
        AjaxResult ajaxResult;
        JSONObject echartsOption = worOrderService.selectCumProportion(reportRequest);
        if (echartsOption.get("series") instanceof Integer) {
            return AjaxResult.warn("未查询到结果");
        }
        ajaxResult = AjaxResult.success(echartsOption);
        return ajaxResult;
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
    @RequiresPermissions("testplatform:workorder:add")
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
    @RequiresPermissions("testplatform:workorder:edit")
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
    @RequiresPermissions("testplatform:workorder:remove")
    @Log(title = "工单管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(worOrderService.delWorkerOrderByIds(ids));
    }

}
