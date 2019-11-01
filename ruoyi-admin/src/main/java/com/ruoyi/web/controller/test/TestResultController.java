package com.ruoyi.web.controller.test;

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
import com.ruoyi.test.domain.TestResult;
import com.ruoyi.test.service.ITestResultService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试计划执行结果Controller
 * 
 * @author zhangsy
 * @date 2019-10-31
 */
@Controller
@RequestMapping("/test/result")
public class TestResultController extends BaseController
{
    private String prefix = "test/result";

    @Autowired
    private ITestResultService testResultService;

    @RequiresPermissions("test:result:view")
    @GetMapping()
    public String result()
    {
        return prefix + "/result";
    }

    /**
     * 查询测试计划执行结果列表
     */
    @RequiresPermissions("test:result:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestResult testResult)
    {
        startPage();
        List<TestResult> list = testResultService.selectTestResultList(testResult);
        return getDataTable(list);
    }

    /**
     * 导出测试计划执行结果列表
     */
    @RequiresPermissions("test:result:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestResult testResult)
    {
        List<TestResult> list = testResultService.selectTestResultList(testResult);
        ExcelUtil<TestResult> util = new ExcelUtil<TestResult>(TestResult.class);
        return util.exportExcel(list, "result");
    }

    /**
     * 新增测试计划执行结果
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存测试计划执行结果
     */
    @RequiresPermissions("test:result:add")
    @Log(title = "测试计划执行结果", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestResult testResult)
    {
        return toAjax(testResultService.insertTestResult(testResult));
    }

    /**
     * 修改测试计划执行结果
     */
    @GetMapping("/edit/{resultId}")
    public String edit(@PathVariable("resultId") Long resultId, ModelMap mmap)
    {
        TestResult testResult = testResultService.selectTestResultById(resultId);
        mmap.put("testResult", testResult);
        return prefix + "/edit";
    }

    /**
     * 修改保存测试计划执行结果
     */
    @RequiresPermissions("test:result:edit")
    @Log(title = "测试计划执行结果", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestResult testResult)
    {
        return toAjax(testResultService.updateTestResult(testResult));
    }

    /**
     * 删除测试计划执行结果
     */
    @RequiresPermissions("test:result:remove")
    @Log(title = "测试计划执行结果", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testResultService.deleteTestResultByIds(ids));
    }
}
