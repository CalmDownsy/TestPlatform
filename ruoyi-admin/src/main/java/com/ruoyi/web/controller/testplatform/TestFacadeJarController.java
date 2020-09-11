package com.ruoyi.web.controller.testplatform;

import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ulpay.testplatform.domain.TestCertManagement;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestFacadeJar;
import com.ulpay.testplatform.service.ITestFacadeJarService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Facade包Controller
 * 
 * @author zhangsy
 * @date 2020-06-16
 */
@Controller
@RequestMapping("/testplatform/testfacadejar")
public class TestFacadeJarController extends BaseController
{
    private String prefix = "testplatform/testfacadejar";

    @Autowired
    private ITestFacadeJarService testFacadeJarService;

    @RequiresPermissions("testplatform:testfacadejar:view")
    @GetMapping()
    public String testfacadejar()
    {
        return prefix + "/testfacadejar";
    }

    /**
     * 查询Facade包列表
     */
    @RequiresPermissions("testplatform:testfacadejar:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestFacadeJar testFacadeJar)
    {
        startPage();
        List<TestFacadeJar> list = testFacadeJarService.selectTestFacadeJarList(testFacadeJar);
        return getDataTable(list);
    }

    /**
     * 导出Facade包列表
     */
    @RequiresPermissions("testplatform:testfacadejar:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestFacadeJar testFacadeJar)
    {
        List<TestFacadeJar> list = testFacadeJarService.selectTestFacadeJarList(testFacadeJar);
        ExcelUtil<TestFacadeJar> util = new ExcelUtil<TestFacadeJar>(TestFacadeJar.class);
        return util.exportExcel(list, "testfacadejar");
    }

    /**
     * 新增Facade包
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存Facade包
     */
    @RequiresPermissions("testplatform:testfacadejar:add")
    @Log(title = "Facade包", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@RequestParam("facadeFile") MultipartFile file, TestFacadeJar testFacadeJar)
    {
        try {
            if (!file.isEmpty()) {
                String facadeFile = FileUploadUtils.upload(Global.getUploadPath(), file);
                testFacadeJar.setJarName(file.getOriginalFilename());
                testFacadeJar.setJarFilepath(facadeFile);
                if(testFacadeJarService.selectTestFacadeJarList(testFacadeJar).size() > 0){
                    logger.info("当前Facade包{}已存在，更新当前Facade包。",file.getOriginalFilename());
                    return editSave(testFacadeJar);
                }else {
                    logger.info("当前Facade包{}为新包，新增。",file.getOriginalFilename());
                    testFacadeJarService.insertTestFacadeJar(testFacadeJar);
                }
                return success();
            }
            return error();
        } catch (Exception e) {
            logger.error("Facade包添加异常：", e);
            return error(e.getMessage());
        }
    }

    /**
     * 修改Facade包
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        TestFacadeJar testFacadeJar = testFacadeJarService.selectTestFacadeJarById(id);
        mmap.put("testFacadeJar", testFacadeJar);
        return prefix + "/edit";
    }

    /**
     * 修改保存Facade包
     */
    @RequiresPermissions("testplatform:testfacadejar:edit")
    @Log(title = "Facade包", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestFacadeJar testFacadeJar)
    {
        return toAjax(testFacadeJarService.updateTestFacadeJar(testFacadeJar));
    }

    /**
     * 删除Facade包
     */
    @RequiresPermissions("testplatform:testfacadejar:remove")
    @Log(title = "Facade包", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testFacadeJarService.deleteTestFacadeJarByIds(ids));
    }
}
