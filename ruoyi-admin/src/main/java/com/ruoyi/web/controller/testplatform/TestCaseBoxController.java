package com.ruoyi.web.controller.testplatform;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.domain.*;
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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.service.ITestCaseBoxService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用例集合Controller
 * 
 * @author ruoyi
 * @date 2020-02-25
 */
@Controller
@RequestMapping("/testplatform/testcasebox")
public class TestCaseBoxController extends BaseController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCaseBoxController.class);

    private String prefix = "testplatform/testcasebox";

    @Autowired
    private ITestCaseBoxService testCaseBoxService;

    @Autowired
    private ITestCaseService testCaseService;

    @Autowired
    private ICaseRunResultService caseRunResultService;

    @Autowired
    private IJobResultRelationsService jobResultRelationsService;

    @RequiresPermissions("testplatform:testcasebox:view")
    @GetMapping()
    public String testcasebox()
    {
        return prefix + "/testcasebox";
    }

    /**
     * 查询用例集合列表
     */
    @RequiresPermissions("testplatform:testcasebox:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestCaseBox testCaseBox)
    {
        startPage();
        List<TestCaseBox> list = testCaseBoxService.selectTestCaseBoxList(testCaseBox);
        List<TestCaseBox> resList = new ArrayList<TestCaseBox>();
        for(TestCaseBox caseBox : list){
            caseBox = covertToPage(caseBox);
            resList.add(caseBox);
        }
        return getDataTable(resList);
    }

    /**
     * 导出用例集合列表
     */
    @RequiresPermissions("testplatform:testcasebox:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestCaseBox testCaseBox)
    {
        List<TestCaseBox> list = testCaseBoxService.selectTestCaseBoxList(testCaseBox);
        ExcelUtil<TestCaseBox> util = new ExcelUtil<TestCaseBox>(TestCaseBox.class);
        return util.exportExcel(list, "testcasebox");
    }

    /**
     * 新增用例集合
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存用例集合
     */
    @RequiresPermissions("testplatform:testcasebox:add")
    @Log(title = "用例集合", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestCaseBox testCaseBox)
    {
        testCaseBox = covertToDatabase(testCaseBox);
        return toAjax(testCaseBoxService.insertTestCaseBox(testCaseBox));
    }

    /**
     * 修改用例集合
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        TestCaseBox testCaseBox = testCaseBoxService.selectTestCaseBoxById(id);
        testCaseBox = covertToPage(testCaseBox);
        mmap.put("testCaseBox", testCaseBox);
        return prefix + "/edit";
    }

    /**
     * 修改保存用例集合
     */
    @RequiresPermissions("testplatform:testcasebox:edit")
    @Log(title = "用例集合", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestCaseBox testCaseBox)
    {
        if(!StringUtils.isEmpty(testCaseBox.getCaseBox())){
            testCaseBox = covertToDatabase(testCaseBox);
        }
        return toAjax(testCaseBoxService.updateTestCaseBox(testCaseBox));
    }

    /**
     * 删除用例集合
     */
    @RequiresPermissions("testplatform:testcasebox:remove")
    @Log(title = "用例集合", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testCaseBoxService.deleteTestCaseBoxByIds(ids));
    }

    /**
     * 跳转用例集执行
     * @return
     */
    @GetMapping("/runCaseBox/{id}")
    public String runCaseBox(@PathVariable("id") Long id, ModelMap modelMap){
        TestCaseBox testCaseBox = testCaseBoxService.selectTestCaseBoxById(id);
        modelMap.put("testCaseBox",testCaseBox);
        return prefix +"/runCaseBox";
    }

    /**
     *
     * @param id
     * @param env
     * @return
     */
    @RequiresPermissions("testplatform:testcasebox:edit")
    @Log(title = "用例集执行", businessType = BusinessType.UPDATE)
    @GetMapping("/runCaseBox/{id}/{env}")
    @ResponseBody
    public AjaxResult runCaseBoxSave(@PathVariable("id") Long id, @PathVariable("env") String env){
        AjaxResult ajaxResult;
        try {
            int[] batchRunCases = testCaseService.batchRunCases(id, env);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("runStatus", 1);
            jsonObject.put("resultIds",batchRunCases[1]);
            ajaxResult = AjaxResult.success("执行结束", jsonObject);
        } catch (Exception e) {
            LOGGER.error("执行用例集失败: {}", e.getMessage());
            ajaxResult = AjaxResult.error(e.getMessage());
        }
        return ajaxResult;
    }

    @GetMapping("/showResult/{results}")
    public String showResult(@PathVariable("results") String results,ModelMap modelMap){
        modelMap.put("resultIds",results);

        //TODO 如果需要维护用例集结果报告，可以在这加处理
        return prefix + "/showResult" ;
    }

    @PostMapping("/showResult")
    @ResponseBody
    public TableDataInfo resultDetails(@RequestParam("resultIds") String resultIds){
        startPage();
        List<CaseRunResult> resultList = new ArrayList<CaseRunResult>();
        if(resultIds.contains(",")){
            String [] rsltIds = resultIds.split(",");
            for(String resultId : rsltIds){
                CaseRunResult caseRunResult = caseRunResultService.selectCaseRunResultById(Long.parseLong(resultId));
                caseRunResult.setCaseName(testCaseService.selectTestCaseById(caseRunResult.getCaseId()).getCaseName());
                resultList.add(caseRunResult);
            }
        }else{
            CaseRunResult caseRunResult = caseRunResultService.selectCaseRunResultById(Long.parseLong(resultIds));
            caseRunResult.setCaseName(testCaseService.selectTestCaseById(caseRunResult.getCaseId()).getCaseName());
            resultList.add(caseRunResult);
        }
        return getDataTable(resultList);
    }


    /**
     * 将数据库中的用例集信息转换成页面展示的名称
     * @param testCaseBox
     * @return
     */
    private TestCaseBox covertToPage(TestCaseBox testCaseBox){
        String caseNames = "";
        List<TestCase> caseList = JSON.parseArray(testCaseBox.getCaseBox(),TestCase.class);
        for(TestCase testCase : caseList){
            caseNames += testCase.getCaseName()+",";
        }
        caseNames = caseNames.substring(0,caseNames.lastIndexOf(","));
        testCaseBox.setCaseBox(caseNames);
        return testCaseBox;
    }

    /**
     * 将页面用例集转换为数据库存储的json
     * @param testCaseBox
     * @return
     */
    private TestCaseBox covertToDatabase(TestCaseBox testCaseBox){
        String caseBoxes = testCaseBox.getCaseBox();
        String caseJson = "";
        if(caseBoxes.contains(",")){
            caseJson += "[";
            String [] caseids = caseBoxes.split(",");
            for(String id : caseids){
                TestCase testCase = testCaseService.selectTestCaseById(Long.parseLong(id));
                logger.info("testCase-->"+testCase.toString());
                caseJson += "{"+"\"caseId\":"+id+",\"caseName\":\""+testCase.getCaseName()+"\",\"preActionId\":"+testCase.getPreActionId()+"},";
            }
            caseJson = caseJson.substring(0,caseJson.lastIndexOf(","));
            caseJson += "]";
        }else{
            TestCase testCase = testCaseService.selectTestCaseById(Long.parseLong(caseBoxes));
            caseJson += "{"+"\"caseId\":"+caseBoxes+",\"caseName\":\""+testCase.getCaseName()+"\",\"preActionId\":"+testCase.getPreActionId()+"}";
        }
        testCaseBox.setCaseBox(caseJson);
        return testCaseBox;
    }
}
