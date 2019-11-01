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
import com.ruoyi.test.domain.Testcase;
import com.ruoyi.test.service.ITestcaseService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试用例Controller
 * 
 * @author zhangsy
 * @date 2019-10-24
 */
@Controller
@RequestMapping("/test/testcase")
public class TestcaseController extends BaseController
{
    private String prefix = "test/testcase";

    @Autowired
    private ITestcaseService testcaseService;

    @RequiresPermissions("test:testcase:view")
    @GetMapping()
    public String testcase()
    {
        return prefix + "/testcase";
    }

    /**
     * 查询测试用例列表
     */
    @RequiresPermissions("test:testcase:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Testcase testcase)
    {
        startPage();
        List<Testcase> list = testcaseService.selectTestcaseList(testcase);
        return getDataTable(list);
    }

    /**
     * 导出测试用例列表
     */
    @RequiresPermissions("test:testcase:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Testcase testcase)
    {
        List<Testcase> list = testcaseService.selectTestcaseList(testcase);
        ExcelUtil<Testcase> util = new ExcelUtil<Testcase>(Testcase.class);
        return util.exportExcel(list, "testcase");
    }

    /**
     * 新增测试用例
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存测试用例
     */
    @RequiresPermissions("test:testcase:add")
    @Log(title = "测试用例", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Testcase testcase)
    {
        return toAjax(testcaseService.insertTestcase(testcase));
    }

    /**
     * 修改测试用例
     */
    @GetMapping("/edit/{testCaseId}")
    public String edit(@PathVariable("testCaseId") Long testCaseId, ModelMap mmap)
    {
        Testcase testcase = testcaseService.selectTestcaseById(testCaseId);
        mmap.put("testcase", testcase);
        return prefix + "/edit";
    }

    /**
     * 修改保存测试用例
     */
    @RequiresPermissions("test:testcase:edit")
    @Log(title = "测试用例", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Testcase testcase)
    {
        return toAjax(testcaseService.updateTestcase(testcase));
    }

    /**
     * 删除测试用例
     */
    @RequiresPermissions("test:testcase:remove")
    @Log(title = "测试用例", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testcaseService.deleteTestcaseByIds(ids));
    }
}
