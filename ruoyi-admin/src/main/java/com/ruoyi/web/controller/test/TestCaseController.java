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
import com.ruoyi.test.domain.TestCase;
import com.ruoyi.test.service.ITestCaseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用例实体Controller
 * 
 * @author zhangsy
 * @date 2020-01-02
 */
@Controller
@RequestMapping("/test/case")
public class TestCaseController extends BaseController
{
    private String prefix = "test/case";

    @Autowired
    private ITestCaseService testCaseService;

    @RequiresPermissions("test:case:view")
    @GetMapping()
    public String case_v()
    {
        return prefix + "/case";
    }

    /**
     * 查询用例实体列表
     */
    @RequiresPermissions("test:case:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        return getDataTable(list);
    }

    /**
     * 导出用例实体列表
     */
    @RequiresPermissions("test:case:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestCase testCase)
    {
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        ExcelUtil<TestCase> util = new ExcelUtil<TestCase>(TestCase.class);
        return util.exportExcel(list, "case");
    }

    /**
     * 新增用例实体
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用例实体
     */
    @RequiresPermissions("test:case:add")
    @Log(title = "用例实体", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestCase testCase)
    {
        return toAjax(testCaseService.insertTestCase(testCase));
    }

    /**
     * 修改用例实体
     */
    @GetMapping("/edit/{caseId}")
    public String edit(@PathVariable("caseId") Long caseId, ModelMap mmap)
    {
        TestCase testCase = testCaseService.selectTestCaseById(caseId);
        mmap.put("testCase", testCase);
        return prefix + "/edit";
    }

    /**
     * 修改保存用例实体
     */
    @RequiresPermissions("test:case:edit")
    @Log(title = "用例实体", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestCase testCase)
    {
        return toAjax(testCaseService.updateTestCase(testCase));
    }

    /**
     * 删除用例实体
     */
    @RequiresPermissions("test:case:remove")
    @Log(title = "用例实体", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testCaseService.deleteTestCaseByIds(ids));
    }
}
