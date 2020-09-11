package com.ulpay.testplatform.mapper;

import com.ulpay.testplatform.domain.CaseRunResult;

import java.util.List;

/**
 * 用例执行结果Mapper接口
 *
 * @author zhangsy
 * @date 2020-01-13
 */
public interface CaseRunResultMapper {
    /**
     * 查询用例执行结果
     *
     * @param resultId 用例执行结果ID
     * @return 用例执行结果
     */
    public CaseRunResult selectCaseRunResultById(Long resultId);

    /**
     * 查询用例执行结果列表
     *
     * @param caseRunResult 用例执行结果
     * @return 用例执行结果集合
     */
    public List<CaseRunResult> selectCaseRunResultList(CaseRunResult caseRunResult);

    /**
     * 新增用例执行结果
     *
     * @param caseRunResult 用例执行结果
     * @return 结果
     */
    public int insertCaseRunResult(CaseRunResult caseRunResult);

    /**
     * 修改用例执行结果
     *
     * @param caseRunResult 用例执行结果
     * @return 结果
     */
    public int updateCaseRunResult(CaseRunResult caseRunResult);

    /**
     * 删除用例执行结果
     *
     * @param resultId 用例执行结果ID
     * @return 结果
     */
    public int deleteCaseRunResultById(Long resultId);

    /**
     * 批量删除用例执行结果
     *
     * @param resultIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCaseRunResultByIds(String[] resultIds);

    /**
     * 查询报告结果集
     *
     * @param reportId
     * @return
     */
    public List<CaseRunResult> selectResultByReportId(Long reportId);
}
