package com.ruoyi.web.controller.testplatform;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestInterfaceInfo;
import com.ulpay.testplatform.service.ITestInterfaceInfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 接口信息实体Controller
 * 
 * @author zhangsy
 * @date 2019-12-06
 */
@Controller
@RequestMapping("/testplatform/interface")
public class TestInterfaceInfoController extends BaseController
{
    private String prefix = "testplatform/interface";

    @Autowired
    private ITestInterfaceInfoService testInterfaceInfoService;

    @RequiresPermissions("testplatform:interface:view")
    @GetMapping()
    public String interface_v()
    {
        return prefix + "/interface";
    }

    /**
     * 查询接口信息实体列表
     */
    @RequiresPermissions("testplatform:interface:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestInterfaceInfo testInterfaceInfo)
    {
        startPage();
        List<TestInterfaceInfo> list = testInterfaceInfoService.selectTestInterfaceInfoList(testInterfaceInfo);
        return getDataTable(list);
    }

    /**
     * 通过Id查询接口实例
     * @return
     */
    @GetMapping("/selectById/{interfaceId}")
    @ResponseBody
    public TestInterfaceInfo selectInterfaceById(@PathVariable("interfaceId") Long interfaceId){
        return testInterfaceInfoService.selectTestInterfaceInfoById(interfaceId);
    }

    /**
     * 获取接口列表弹出窗
     */
    @GetMapping("/selectInterfaceTable")
    public String selectInterfaceTable() {

        return prefix + "/interfaceTable";
    }

    /**
     * 导出接口信息实体列表
     */
    @RequiresPermissions("testplatform:interface:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestInterfaceInfo testInterfaceInfo)
    {
        List<TestInterfaceInfo> list = testInterfaceInfoService.selectTestInterfaceInfoList(testInterfaceInfo);
        ExcelUtil<TestInterfaceInfo> util = new ExcelUtil<TestInterfaceInfo>(TestInterfaceInfo.class);
        return util.exportExcel(list, "interface");
    }

    /**
     * 新增接口信息实体
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存接口信息实体
     */
    @RequiresPermissions("testplatform:interface:add")
    @Log(title = "接口信息实体", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestInterfaceInfo testInterfaceInfo)
    {
        return toAjax(testInterfaceInfoService.insertTestInterfaceInfo(testInterfaceInfo));
    }

    /**
     * 修改接口信息实体
     */
    @GetMapping("/edit/{interfaceId}")
    public String edit(@PathVariable("interfaceId") Long interfaceId, ModelMap mmap)
    {
        TestInterfaceInfo testInterfaceInfo = testInterfaceInfoService.selectTestInterfaceInfoById(interfaceId);
        mmap.put("testInterfaceInfo", testInterfaceInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存接口信息实体
     */
    @RequiresPermissions("testplatform:interface:edit")
    @Log(title = "接口信息实体", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestInterfaceInfo testInterfaceInfo)
    {
        return toAjax(testInterfaceInfoService.updateTestInterfaceInfo(testInterfaceInfo));
    }

    /**
     * 删除接口信息实体
     */
    @RequiresPermissions("testplatform:interface:remove")
    @Log(title = "接口信息实体", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testInterfaceInfoService.deleteTestInterfaceInfoByIds(ids));
    }
}
