package com.ulpay.testplatform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ulpay.testplatform.service.ICaseRunResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ulpay.testplatform.mapper.TestReportMapper;
import com.ulpay.testplatform.domain.TestReport;
import com.ulpay.testplatform.service.ITestReportService;
import com.ruoyi.common.core.text.Convert;

/**
 * 测试报告Service业务层处理
 *
 * @author zhangsy
 * @date 2020-05-14
 */
@Service
public class TestReportServiceImpl implements ITestReportService {
    @Autowired
    private TestReportMapper testReportMapper;

    @Autowired
    private ICaseRunResultService caseRunResultService;

    /**
     * 查询测试报告
     *
     * @param reportId 测试报告ID
     * @return 测试报告
     */
    @Override
    public TestReport selectTestReportById(Long reportId) {
        return testReportMapper.selectTestReportById(reportId);
    }

    /**
     * 查询测试报告列表
     *
     * @param testReport 测试报告
     * @return 测试报告
     */
    @Override
    public List<TestReport> selectTestReportList(TestReport testReport) {
        return testReportMapper.selectTestReportList(testReport);
    }

    /**
     * 新增测试报告
     *
     * @param testReport 测试报告
     * @return 结果
     */
    @Override
    public int insertTestReport(TestReport testReport) {
        testReport.setCreateTime(DateUtils.getNowDate());
        return testReportMapper.insertTestReport(testReport);
    }

    /**
     * 修改测试报告
     *
     * @param testReport 测试报告
     * @return 结果
     */
    @Override
    public int updateTestReport(TestReport testReport) {
        testReport.setUpdateTime(DateUtils.getNowDate());
        return testReportMapper.updateTestReport(testReport);
    }

    /**
     * 删除测试报告对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTestReportByIds(String ids) {
        return testReportMapper.deleteTestReportByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除测试报告信息
     *
     * @param reportId 测试报告ID
     * @return 结果
     */
    public int deleteTestReportById(Long reportId) {
        return testReportMapper.deleteTestReportById(reportId);
    }

    /**
     * 设置报告详情
     *
     * @param testReport 报告
     * @return
     */
    @Override
    public String setReportDetails(TestReport testReport) {
        // 存放报告属性
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 统计用例数量
        testReport.caseCount();

        // 报告概要
        Map<String,Object> resume = new HashMap<>();
        resume.put("title", testReport.getCaseboxName() + "- 测试报告");
        resume.put("createTime", testReport.getCreateTime());
        resume.put("totalNum", testReport.getCaseNum());
        resume.put("succNum", testReport.getSuccessNum());
        resume.put("failNum", testReport.getFailNum());
        resume.put("skipNum", testReport.getSkipNum());
        resume.put("succRate", String.format("%.2f", (double) (testReport.getSuccessNum() / testReport.getCaseNum() * 100)));

        resultMap.put("resume", resume);
        resultMap.put("data", caseRunResultService.selectResultByReportId(testReport.getReportId()));
        return JSONObject.toJSONString(resultMap);
    }
}
