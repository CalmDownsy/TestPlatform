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
import com.ulpay.testplatform.domain.JobResultRelations;
import com.ulpay.testplatform.service.IJobResultRelationsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 任务结果Controller
 * 
 * @author ruoyi
 * @date 2020-03-20
 */
@Controller
@RequestMapping("/testplatform/jobresultrel")
public class JobResultRelationsController extends BaseController
{
    private String prefix = "testplatform/jobresultrel";

    @Autowired
    private IJobResultRelationsService jobResultRelationsService;

    @RequiresPermissions("testplatform:jobresultrel:view")
    @GetMapping()
    public String jobresultrel()
    {
        return prefix + "/jobresultrel";
    }

    /**
     * 查询任务结果列表
     */
    @RequiresPermissions("testplatform:jobresultrel:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(JobResultRelations jobResultRelations)
    {
        startPage();
        List<JobResultRelations> list = jobResultRelationsService.selectJobResultRelationsList(jobResultRelations);
        return getDataTable(list);
    }

    /**
     * 导出任务结果列表
     */
    @RequiresPermissions("testplatform:jobresultrel:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(JobResultRelations jobResultRelations)
    {
        List<JobResultRelations> list = jobResultRelationsService.selectJobResultRelationsList(jobResultRelations);
        ExcelUtil<JobResultRelations> util = new ExcelUtil<JobResultRelations>(JobResultRelations.class);
        return util.exportExcel(list, "jobresultrel");
    }

    /**
     * 新增任务结果
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存任务结果
     */
    @RequiresPermissions("testplatform:jobresultrel:add")
    @Log(title = "任务结果", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(JobResultRelations jobResultRelations)
    {
        return toAjax(jobResultRelationsService.insertJobResultRelations(jobResultRelations));
    }

    /**
     * 修改任务结果
     */
    @GetMapping("/edit/{relId}")
    public String edit(@PathVariable("relId") Long relId, ModelMap mmap)
    {
        JobResultRelations jobResultRelations = jobResultRelationsService.selectJobResultRelationsById(relId);
        mmap.put("jobResultRelations", jobResultRelations);
        return prefix + "/edit";
    }

    /**
     * 修改保存任务结果
     */
    @RequiresPermissions("testplatform:jobresultrel:edit")
    @Log(title = "任务结果", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(JobResultRelations jobResultRelations)
    {
        return toAjax(jobResultRelationsService.updateJobResultRelations(jobResultRelations));
    }

    /**
     * 删除任务结果
     */
    @RequiresPermissions("testplatform:jobresultrel:remove")
    @Log(title = "任务结果", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(jobResultRelationsService.deleteJobResultRelationsByIds(ids));
    }
}
