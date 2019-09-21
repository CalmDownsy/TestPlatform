package com.ruoyi.web.controller.test;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created By: zhangsy
 * Description:
 * 2019/8/18 15:19
 */
@Controller
@RequestMapping("/test/testcase")
public class TestCaseController {

    private String prefix = "test/testcase";

    @RequiresPermissions("test:testcase:view")
    @GetMapping()
    public String testcase() {
        return prefix + "/testcase";
    }

}
