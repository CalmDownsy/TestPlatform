package com.ruoyi.web.controller.testplatform;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestInterfaceMock;
import com.ulpay.testplatform.service.ITestInterfaceMockService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Mock配置Controller
 * 
 * @author zhangsy
 * @date 2020-06-19
 */
@Controller
@RequestMapping("/testplatform/interfacemock")
public class TestInterfaceMockController extends BaseController
{
    private String prefix = "testplatform/interfacemock";

    @Autowired
    private ITestInterfaceMockService testInterfaceMockService;

    @RequiresPermissions("testplatform:interfacemock:view")
    @GetMapping()
    public String interfacemock()
    {
        return prefix + "/interfacemock";
    }

    /**
     * 查询Mock配置列表
     */
    @RequiresPermissions("testplatform:interfacemock:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestInterfaceMock testInterfaceMock)
    {
        startPage();
        List<TestInterfaceMock> list = testInterfaceMockService.selectTestInterfaceMockList(testInterfaceMock);
        return getDataTable(list);
    }

    @PostMapping("/mockexist")
    @ResponseBody
    public JSONObject mockExist(@RequestBody TestInterfaceMock testInterfaceMock) {
        List<TestInterfaceMock> list = testInterfaceMockService.selectTestInterfaceMockList(testInterfaceMock);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mockId", list.get(0).getMockId());
        // TODO: 2020/6/30   把实际参数带上。如果存在就去遍历规则集
        return jsonObject;
    }

    /**
     * 导出Mock配置列表
     */
    @RequiresPermissions("testplatform:interfacemock:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestInterfaceMock testInterfaceMock)
    {
        List<TestInterfaceMock> list = testInterfaceMockService.selectTestInterfaceMockList(testInterfaceMock);
        ExcelUtil<TestInterfaceMock> util = new ExcelUtil<TestInterfaceMock>(TestInterfaceMock.class);
        return util.exportExcel(list, "interfacemock");
    }

    /**
     * 新增Mock配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Mock配置
     */
    @RequiresPermissions("testplatform:interfacemock:add")
    @Log(title = "Mock配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestInterfaceMock testInterfaceMock)
    {
        return toAjax(testInterfaceMockService.insertTestInterfaceMock(testInterfaceMock));
    }

    /**
     * 修改Mock配置
     */
    @GetMapping("/edit/{mockId}")
    public String edit(@PathVariable("mockId") Long mockId, ModelMap mmap)
    {
        TestInterfaceMock testInterfaceMock = testInterfaceMockService.selectTestInterfaceMockById(mockId);
        mmap.put("testInterfaceMock", testInterfaceMock);
        return prefix + "/edit";
    }

    /**
     * 修改保存Mock配置
     */
    @RequiresPermissions("testplatform:interfacemock:edit")
    @Log(title = "Mock配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestInterfaceMock testInterfaceMock)
    {
        return toAjax(testInterfaceMockService.updateTestInterfaceMock(testInterfaceMock));
    }

    /**
     * 删除Mock配置
     */
    @RequiresPermissions("testplatform:interfacemock:remove")
    @Log(title = "Mock配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testInterfaceMockService.deleteTestInterfaceMockByIds(ids));
    }
}
