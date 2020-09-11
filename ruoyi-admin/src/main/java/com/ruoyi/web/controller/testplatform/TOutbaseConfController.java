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
import com.ulpay.testplatform.domain.TOutbaseConf;
import com.ulpay.testplatform.service.ITOutbaseConfService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 外部数据源维护Controller
 * 
 * @author ruoyi
 * @date 2020-03-27
 */
@Controller
@RequestMapping("/testplatform/outbaseconf")
public class TOutbaseConfController extends BaseController
{
    private String prefix = "testplatform/outbaseconf";

    @Autowired
    private ITOutbaseConfService tOutbaseConfService;

    @RequiresPermissions("testplatform:outbaseconf:view")
    @GetMapping()
    public String outbaseconf()
    {
        return prefix + "/outbaseconf";
    }

    /**
     * 查询外部数据源维护列表
     */
    @RequiresPermissions("testplatform:outbaseconf:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TOutbaseConf tOutbaseConf)
    {
        startPage();
        List<TOutbaseConf> list = tOutbaseConfService.selectTOutbaseConfList(tOutbaseConf);
        return getDataTable(list);
    }

    /**
     * 导出外部数据源维护列表
     */
    @RequiresPermissions("testplatform:outbaseconf:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TOutbaseConf tOutbaseConf)
    {
        List<TOutbaseConf> list = tOutbaseConfService.selectTOutbaseConfList(tOutbaseConf);
        ExcelUtil<TOutbaseConf> util = new ExcelUtil<TOutbaseConf>(TOutbaseConf.class);
        return util.exportExcel(list, "outbaseconf");
    }

    /**
     * 新增外部数据源维护
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存外部数据源维护
     */
    @RequiresPermissions("testplatform:outbaseconf:add")
    @Log(title = "外部数据源维护", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TOutbaseConf tOutbaseConf)
    {
        return toAjax(tOutbaseConfService.insertTOutbaseConf(tOutbaseConf));
    }

    /**
     * 修改外部数据源维护
     */
    @GetMapping("/edit/{baseId}")
    public String edit(@PathVariable("baseId") Long baseId, ModelMap mmap)
    {
        TOutbaseConf tOutbaseConf = tOutbaseConfService.selectTOutbaseConfById(baseId);
        mmap.put("tOutbaseConf", tOutbaseConf);
        return prefix + "/edit";
    }

    /**
     * 修改保存外部数据源维护
     */
    @RequiresPermissions("testplatform:outbaseconf:edit")
    @Log(title = "外部数据源维护", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TOutbaseConf tOutbaseConf)
    {
        return toAjax(tOutbaseConfService.updateTOutbaseConf(tOutbaseConf));
    }

    /**
     * 删除外部数据源维护
     */
    @RequiresPermissions("testplatform:outbaseconf:remove")
    @Log(title = "外部数据源维护", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(tOutbaseConfService.deleteTOutbaseConfByIds(ids));
    }
}
