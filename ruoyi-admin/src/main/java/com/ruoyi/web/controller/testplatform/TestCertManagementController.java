package com.ruoyi.web.controller.testplatform;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestCertManagement;
import com.ulpay.testplatform.service.ITestCertManagementService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 证书管理Controller
 * 
 * @author ruoyi
 * @date 2020-01-17
 */
@Controller
@RequestMapping("/testplatform/testcertmgt")
public class TestCertManagementController extends BaseController
{
    private String prefix = "testplatform/testcertmgt";

    @Autowired
    private ITestCertManagementService testCertManagementService;

    @RequiresPermissions("testplatform:testcertmgt:view")
    @GetMapping()
    public String testcertmgt()
    {
        return prefix + "/testcertmgt";
    }

    /**
     * 查询证书列表，支持分页
     */
    @RequiresPermissions("testplatform:testcertmgt:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestCertManagement testCertManagement)
    {
        startPage();
        List<TestCertManagement> list = testCertManagementService.selectTestCertManagementList(testCertManagement);
        return getDataTable(list);
    }

    /**
     * 查询证书列表
     */
    @RequiresPermissions("testplatform:testcertmgt:list")
    @Log(title = "证书查询" , businessType = BusinessType.OTHER)
    @PostMapping("/certlist")
    @ResponseBody
    public List<TestCertManagement> getAllCert(TestCertManagement testCertManagement)
    {
        startPage();
        List<TestCertManagement> list = testCertManagementService.selectTestCertManagementList(testCertManagement);
        logger.info("证书列表：{}",list.toString());
        return list;
    }

    /**
     * 导出证书管理列表
     */
    @RequiresPermissions("testplatform:testcertmgt:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestCertManagement testCertManagement, HttpServletResponse response)
    {
        List<TestCertManagement> list = testCertManagementService.selectTestCertManagementList(testCertManagement);
        ExcelUtil<TestCertManagement> util = new ExcelUtil<TestCertManagement>(TestCertManagement.class);
        return util.exportExcel(list, "testcertmgt");
    }

    /**
     * 新增证书管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存证书管理
     */
    @RequiresPermissions("testplatform:testcertmgt:add")
    @Log(title = "证书管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("certFile")MultipartFile file, TestCertManagement testCertManagement)
    {
        try {
            if (!file.isEmpty()) {
                String certFile = FileUploadUtils.upload(Global.getUploadPath(), file);
                testCertManagement.setCertFilename(file.getOriginalFilename());
                testCertManagement.setCertFilepath(certFile);
                //判断当前证书是否重复，重复的直接更新，不重复的新增
                TestCertManagement paramCert = new TestCertManagement();
                paramCert.setCertFilename(file.getOriginalFilename());
                if(testCertManagementService.selectTestCertManagementList(paramCert).size() > 0){
                    logger.info("当前证书{}已存在，更新当前证书。",file.getOriginalFilename());
                    return editSave(testCertManagement);
                }else {
                    logger.info("当前证书{}为新证书，新增。",file.getOriginalFilename());
                    testCertManagementService.insertTestCertManagement(testCertManagement);
                }
                return success();
            }
            return error();
        } catch (Exception e) {
            logger.error("证书添加异常：", e);
            return error(e.getMessage());
        }
    }

    /**
     * 修改证书管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        TestCertManagement testCertManagement = testCertManagementService.selectTestCertManagementById(id);
        mmap.put("testCertManagement", testCertManagement);
        return prefix + "/edit";
    }

    /**
     * 修改保存证书管理
     */
    @RequiresPermissions("testplatform:testcertmgt:edit")
    @Log(title = "证书管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestCertManagement testCertManagement)
    {
        logger.info("更新证书的参数：{}",testCertManagement.toString());
        return toAjax(testCertManagementService.updateTestCertManagement(testCertManagement));
    }

    /**
     * 删除证书管理
     */
    @RequiresPermissions("testplatform:testcertmgt:remove")
    @Log(title = "证书管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testCertManagementService.deleteTestCertManagementByIds(ids));
    }

    /**
     * 根据传入的文件名称查询是否重复
     */
    @RequiresPermissions("testplatform:testcertmgt:list")
    @RequestMapping("/query/{certName}")
    @ResponseBody
    public AjaxResult queryByTestCertManagement(@PathVariable("certName") String fileName){
        if(FileUtils.isValidFilename(fileName)){
            String path = testCertManagementService.selectTestCertManagementByName(fileName).getCertFilepath();
            if(path == null || path.equals("")){
                return success("0");
            }
            return success("1");
        }else{
            logger.info("前台传入的文件名为{}，不符合规范。",fileName);
            return error();
        }
    }
}
