package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.TestReport;

import java.util.List;

/**
 * 测试报告Service接口
 *
 * @author zhangsy
 * @date 2020-05-14
 */
public interface ITestReportService {
    /**
     * 查询测试报告
     *
     * @param reportId 测试报告ID
     * @return 测试报告
     */
    public TestReport selectTestReportById(Long reportId);

    /**
     * 查询测试报告列表
     *
     * @param testReport 测试报告
     * @return 测试报告集合
     */
    public List<TestReport> selectTestReportList(TestReport testReport);

    /**
     * 新增测试报告
     *
     * @param testReport 测试报告
     * @return 结果
     */
    public int insertTestReport(TestReport testReport);

    /**
     * 修改测试报告
     *
     * @param testReport 测试报告
     * @return 结果
     */
    public int updateTestReport(TestReport testReport);

    /**
     * 批量删除测试报告
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTestReportByIds(String ids);

    /**
     * 删除测试报告信息
     *
     * @param reportId 测试报告ID
     * @return 结果
     */
    public int deleteTestReportById(Long reportId);

    /**
     * 设置报告详情
     *
     * @param testReport 报告
     * @return 报告详情json串
     */
    public String setReportDetails(TestReport testReport);
}
