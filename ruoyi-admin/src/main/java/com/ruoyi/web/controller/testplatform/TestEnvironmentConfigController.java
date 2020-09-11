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
import com.ulpay.testplatform.domain.TestEnvironmentConfig;
import com.ulpay.testplatform.service.ITestEnvironmentConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 环境配置Controller
 * 
 * @author ruoyi
 * @date 2020-01-06
 */
@Controller
@RequestMapping("/testplatform/envconf")
public class TestEnvironmentConfigController extends BaseController
{
    private String prefix = "testplatform/envconf";

    @Autowired
    private ITestEnvironmentConfigService testEnvironmentConfigService;

    @RequiresPermissions("testplatform:envconf:view")
    @GetMapping()
    public String envconf()
    {
        return prefix + "/envconf";
    }

    /**
     * 查询环境配置列表
     */
    @RequiresPermissions("testplatform:envconf:list")
    @Log(title = "查询环境配置列表",businessType = BusinessType.OTHER)
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestEnvironmentConfig testEnvironmentConfig)
    {
        startPage();
        List<TestEnvironmentConfig> list = testEnvironmentConfigService.selectTestEnvironmentConfigList(testEnvironmentConfig);
        return getDataTable(list);
    }

    /**
     * 查询环境配置列表,不用表分页
     */
    @RequiresPermissions("testplatform:envconf:list")
    @PostMapping("/getEnvconfList")
    @ResponseBody
    public List<TestEnvironmentConfig> getEnvconfList(TestEnvironmentConfig testEnvironmentConfig)
    {
        return testEnvironmentConfigService.selectTestEnvironmentConfigList(testEnvironmentConfig);
    }

    /**
     * 导出环境配置列表
     */
    @RequiresPermissions("testplatform:envconf:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestEnvironmentConfig testEnvironmentConfig)
    {
        List<TestEnvironmentConfig> list = testEnvironmentConfigService.selectTestEnvironmentConfigList(testEnvironmentConfig);
        ExcelUtil<TestEnvironmentConfig> util = new ExcelUtil<TestEnvironmentConfig>(TestEnvironmentConfig.class);
        return util.exportExcel(list, "envconf");
    }

    /**
     * 新增环境配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存环境配置
     */
    @RequiresPermissions("testplatform:envconf:add")
    @Log(title = "环境配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestEnvironmentConfig testEnvironmentConfig)
    {
        return toAjax(testEnvironmentConfigService.insertTestEnvironmentConfig(testEnvironmentConfig));
    }

    /**
     * 修改环境配置
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        TestEnvironmentConfig testEnvironmentConfig = testEnvironmentConfigService.selectTestEnvironmentConfigById(id);
        mmap.put("testEnvironmentConfig", testEnvironmentConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存环境配置
     */
    @RequiresPermissions("testplatform:envconf:edit")
    @Log(title = "环境配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestEnvironmentConfig testEnvironmentConfig)
    {
        return toAjax(testEnvironmentConfigService.updateTestEnvironmentConfig(testEnvironmentConfig));
    }

    /**
     * 删除环境配置
     */
    @RequiresPermissions("testplatform:envconf:remove")
    @Log(title = "环境配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testEnvironmentConfigService.deleteTestEnvironmentConfigByIds(ids));
    }

    /**
     * 去重查询环境维护列表中的环境名称
     * @return
     */
    @RequiresPermissions("testplatform:envconf:list")
    @PostMapping("/getEnvNameList")
    @ResponseBody
    public List<TestEnvironmentConfig> getDistinctEnvName(){
        return testEnvironmentConfigService.selectDistinctEnvNameList();
    }
}
