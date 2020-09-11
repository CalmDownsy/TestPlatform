package com.ruoyi.web.controller.testplatform;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itrus.util.Test;
import com.ulpay.testplatform.domain.CaseRunResult;
import com.ulpay.testplatform.service.ITestCaseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestReport;
import com.ulpay.testplatform.service.ITestReportService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试报告Controller
 *
 * @author zhangsy
 * @date 2020-05-14
 */
@Controller
@RequestMapping("/testplatform/testreport")
public class TestReportController extends BaseController {
    private String prefix = "testplatform/testreport";

    @Autowired
    private ITestReportService testReportService;

    @Autowired
    private ITestCaseService testCaseService;

    @RequiresPermissions("testplatform:testreport:view")
    @GetMapping()
    public String testreport() {
        return prefix + "/testreport";
    }

    /**
     * 查询测试报告列表
     */
    @RequiresPermissions("testplatform:testreport:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestReport testReport) {
        startPage();
        List<TestReport> list = testReportService.selectTestReportList(testReport);
        return getDataTable(list);
    }

    /**
     * 导出测试报告列表
     */
    @RequiresPermissions("testplatform:testreport:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestReport testReport) {
        List<TestReport> list = testReportService.selectTestReportList(testReport);
        ExcelUtil<TestReport> util = new ExcelUtil<TestReport>(TestReport.class);
        return util.exportExcel(list, "testreport");
    }

    /**
     * 新增测试报告
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存测试报告
     */
    @RequiresPermissions("testplatform:testreport:add")
    @Log(title = "测试报告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestReport testReport) {
        return toAjax(testReportService.insertTestReport(testReport));
    }


    @GetMapping("/reportDetails/{reportId}")
    public String reportDetails(@PathVariable("reportId") Long reportId, ModelMap mmap) {
        mmap.put("reportId", reportId);
        return prefix + "/reportDetails";
    }

    @PostMapping("/showResult")
    @ResponseBody
    public TableDataInfo resultDetails(@RequestParam("reportId") Long reportId) {
        startPage();
        List<CaseRunResult> resultList = new ArrayList<CaseRunResult>();
        TestReport testReport = testReportService.selectTestReportById(reportId);
        String resultDetails = testReport.getResultDetails();
        JSONObject resultObject = JSONObject.parseObject(resultDetails);
        Object data = resultObject.getObject("data", (TypeReference) null);
        if (data instanceof JSONArray) {
            resultList = ((JSONArray) data).toJavaList(CaseRunResult.class);
        }
        if (data instanceof JSONObject) {
            resultList.add(((JSONObject) data).toJavaObject(CaseRunResult.class));
        }
        for (CaseRunResult result: resultList) {
            result.setCaseName(testCaseService.selectTestCaseById(result.getCaseId()).getCaseName());
        }
        return getDataTable(resultList);
    }

    /**
     * 修改测试报告
     */
    @GetMapping("/edit/{reportId}")
    public String edit(@PathVariable("reportId") Long reportId, ModelMap mmap) {
        TestReport testReport = testReportService.selectTestReportById(reportId);
        mmap.put("testReport", testReport);
        return prefix + "/edit";
    }

    /**
     * 修改保存测试报告
     */
    @RequiresPermissions("testplatform:testreport:edit")
    @Log(title = "测试报告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestReport testReport) {
        return toAjax(testReportService.updateTestReport(testReport));
    }

    /**
     * 删除测试报告
     */
    @RequiresPermissions("testplatform:testreport:remove")
    @Log(title = "测试报告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(testReportService.deleteTestReportByIds(ids));
    }
}
