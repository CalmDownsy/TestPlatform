package com.ruoyi.web.controller.testplatform;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ulpay.testplatform.domain.TestMessage;
import com.ulpay.testplatform.service.ITestMessageService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报文实体Controller
 * 
 * @author zhangsy
 * @date 2019-12-09
 */
@Controller
@RequestMapping("/testplatform/message")
public class TestMessageController extends BaseController
{
    private String prefix = "testplatform/message";

    @Autowired
    private ITestMessageService testMessageService;

    @RequiresPermissions("testplatform:message:view")
    @GetMapping()
    public String message()
    {
        return prefix + "/message";
    }

    /**
     * 查询报文实体列表
     */
    @RequiresPermissions("testplatform:message:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestMessage testMessage)
    {
        startPage();
        List<TestMessage> list = testMessageService.selectTestMessageList(testMessage);
        return getDataTable(list);
    }

    /**
     * 查询报文实体列表,不用表分页
     */
    @RequiresPermissions("testplatform:message:list")
    @PostMapping("/getMessageList")
    @ResponseBody
    public List<TestMessage> getMessageList(TestMessage testMessage) {
        return testMessageService.selectTestMessageList(testMessage);
    }

    /**
     * 导出报文实体列表
     */
    @RequiresPermissions("testplatform:message:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TestMessage testMessage)
    {
        List<TestMessage> list = testMessageService.selectTestMessageList(testMessage);
        ExcelUtil<TestMessage> util = new ExcelUtil<TestMessage>(TestMessage.class);
        return util.exportExcel(list, "message");
    }

    /**
     * 新增报文实体
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存报文实体
     */
    @RequiresPermissions("testplatform:message:add")
    @Log(title = "报文实体", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TestMessage testMessage)
    {
        testMessage.setCreateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(testMessageService.insertTestMessage(testMessage));
    }

    /**
     * 修改报文实体
     */
    @GetMapping("/edit/{messageId}")
    public String edit(@PathVariable("messageId") Long messageId, ModelMap mmap)
    {
        TestMessage testMessage = testMessageService.selectTestMessageById(messageId);
        mmap.put("testMessage", testMessage);
        return prefix + "/edit";
    }

    /**
     * 修改保存报文实体
     */
    @RequiresPermissions("testplatform:message:edit")
    @Log(title = "报文实体", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TestMessage testMessage)
    {
        testMessage.setUpdateBy(ShiroUtils.getLoginName());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(testMessageService.updateTestMessage(testMessage));
    }

    /**
     * 删除报文实体
     */
    @RequiresPermissions("testplatform:message:remove")
    @Log(title = "报文实体", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(testMessageService.deleteTestMessageByIds(ids));
    }
}
