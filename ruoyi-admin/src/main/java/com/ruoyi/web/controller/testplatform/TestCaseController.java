package com.ruoyi.web.controller.testplatform;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ulpay.testplatform.domain.CaseRunData;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.domain.TestCase;
import com.ulpay.testplatform.service.ICaseRunResultService;
import com.ulpay.testplatform.service.IJobResultRelationsService;
import com.ulpay.testplatform.service.ITestCaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用例实体Controller
 * 
 * @author zhangsy
 * @date 2020-01-02
 */
@Controller
@RequestMapping("/testplatform/case")
public class TestCaseController extends BaseController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseController.class);
    private String prefix = "testplatform/case";

    @Autowired
    private ITestCaseService testCaseService;

    @Autowired
    private ICaseRunResultService caseRunResultService;

    @Autowired
    private IJobResultRelationsService jobResultRelationsService;

    @RequiresPermissions("testplatform:case:view")
    @GetMapping()
    public String case_v()
    {
        return prefix + "/case";
    }

    /**
     * 查询用例实体列表
     */
    @RequiresPermissions("testplatform:case:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestCase testCase)
    {
        startPage();
        List<TestCase> list = testCaseService.selectTestCaseList(testCase);
        return getDataTable(list);
    }

    /**
     * 获取用例列表选择界面
     * @return
     */
    @GetMapping("/selectCaseTable")
    public String selectCaseTable(){
        return prefix + "/selectCaseTable";
    }

    /**
     * 导出用例实体列表
     */
    @RequiresPermissions("testplatform:case:export")
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
    @RequiresPermissions("testplatform:case:add")
    @Log(title = "用例实体", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestCase testCase)
    {
        try {
            if (testCaseService.insertTestCase(testCase) > 0) {
                return success();
            }
            return error();
        } catch (Exception e) {
            logger.error("更新用例异常：", e);
            return error(e.getMessage());
        }
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
    @RequiresPermissions("testplatform:case:edit")
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
    @RequiresPermissions("testplatform:case:remove")
    @Log(title = "用例实体", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testCaseService.deleteTestCaseByIds(ids));
    }

    /**
     * 跳转执行用例
     */
    @GetMapping("/runCase/{caseId}")
    public String runCase(@PathVariable("caseId") Long caseId, ModelMap mmap) {
        TestCase testCase = testCaseService.selectTestCaseById(caseId);
        mmap.put("testCase", testCase);
        return prefix + "/runCase";
    }

    /**
     * 执行用例
     */
    @RequiresPermissions("testplatform:case:edit")
    @Log(title = "用例实体", businessType = BusinessType.UPDATE)
    @PostMapping("/runCase")
    @ResponseBody
    public AjaxResult runCaseSave(CaseRunData caseRunData) {
        AjaxResult ajaxResult;
        try {
            CaseRunResult caseRunResult = testCaseService.runCase(caseRunData);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultId", caseRunResult.getResultId());
            jsonObject.put("runStatus", caseRunResult.getRunStatus());
            ajaxResult = AjaxResult.success("执行用例结束", jsonObject);
            return ajaxResult;
        } catch (Exception e) {
            LOGGER.error("执行用例失败: {}", e.getMessage());
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }

    /**
     * 跳转执行用例
     */
    @GetMapping("/showResult/{resultId}")
    public String showResult(@PathVariable("resultId") Long resultId, ModelMap mmap) {
        CaseRunResult caseRunResult = caseRunResultService.selectCaseRunResultById(resultId);
        mmap.put("caseRunResult", caseRunResult);
        return prefix + "/showResult";
    }

}
