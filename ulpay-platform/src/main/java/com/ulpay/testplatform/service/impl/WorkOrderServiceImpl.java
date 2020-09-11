package com.ulpay.testplatform.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ulpay.testplatform.common.enums.BusinessLine;
import com.ulpay.testplatform.common.enums.WorkOrderType;
import com.ulpay.testplatform.domain.WorkOrder;
import com.ulpay.testplatform.domain.WorkOrderReportData;
import com.ulpay.testplatform.domain.WorkOrderReportRequest;
import com.ulpay.testplatform.mapper.WorkOrderMapper;
import com.ulpay.testplatform.service.IWorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created By: zhangsy
 * Description: 工单业务层处理
 * 2019/8/3 18:09
 */
@Service
public class WorkOrderServiceImpl implements IWorOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Override
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder) {
        return workOrderMapper.selectWorkOrderList(workOrder);
    }

    @Override
    public List<WorkOrderReportData> selectWorkOrderReportList(WorkOrderReportRequest reportRequest) {
        return workOrderMapper.selectWorkOrderReportList(reportRequest);
    }

    @Override
    public WorkOrder selectWorkOrderById(Long workOrderId) {
        return workOrderMapper.selectWorkOrderById(workOrderId);
    }

    @Override
    @Transactional
    public int insertWorkOrder(WorkOrder workOrder) {
        return workOrderMapper.insertWorkOrder(workOrder);
    }

    @Override
    @Transactional
    public int updateWorkOrder(WorkOrder workOrder) {
        return workOrderMapper.updateWorkOrder(workOrder);
    }

    @Override
    public int delWorkerOrderByIds(String ids) {
        Long[] workOrders = Convert.toLongArray(ids);
        return workOrderMapper.delWorkOrderByIds(workOrders);
    }

    @Override
    public JSONObject selectWorkOrderProportion(WorkOrderReportRequest reportRequest) {
        JSONObject echartsOption = new JSONObject();
        JSONObject seriesData = new JSONObject();
        List<WorkOrderReportData> reportDataList = selectWorkOrderReportList(reportRequest);
        if (reportDataList.isEmpty()) {
            echartsOption.put("series", 0);
            return echartsOption;
        }

        Date beginTime = reportRequest.getBeginTime();
        Date endTime = reportRequest.getEndTime();
        List<String> weeks = DateUtils.getWeeksBetweenDays(beginTime, endTime);

        for (WorkOrderReportData workOrderReportData : reportDataList) {
            String busiLine = BusinessLine.getBusiLine(workOrderReportData.getBusiLine());
            // 展示数据
            if (!seriesData.containsKey(busiLine)) {
                seriesData.put(busiLine, workOrderReportData.getCount());
            } else {
                seriesData.put(busiLine, seriesData.getInteger(busiLine) + workOrderReportData.getCount());
            }
        }
        echartsOption.put("seriesData", seriesData);
        echartsOption.put("titleText", weeks.get(0) + "周 至 " +
                weeks.get(weeks.size() - 1) + "周【" +
                WorkOrderType.getWorkOrderType(reportDataList.get(0).getWorkOrderType()) + "】工单占比");

        return echartsOption;
    }

    @Override
    public JSONObject selectWorkOrderRend(WorkOrderReportRequest reportRequest) {
        JSONObject echartsOption = new JSONObject();
        JSONArray xAxisData = new JSONArray();
        JSONObject series = new JSONObject();
        if("".equals(reportRequest.getBusiLine())) {
            reportRequest.setBusiLine(null);
        }
        List<WorkOrderReportData> reportDataList = selectWorkOrderReportList(reportRequest);
        if (reportDataList.isEmpty()) {
            echartsOption.put("series", 0);
            return echartsOption;
        }

        // x轴时间线
        Date beginTime = reportRequest.getBeginTime();
        Date endTime = reportRequest.getEndTime();
        List<String> weeks = DateUtils.getWeeksBetweenDays(beginTime, endTime);
        xAxisData.addAll(weeks);

        // 第一条数据工单类型
        String workOrderType = reportRequest.getWorkOrderType();
        String busiLine = StringUtils.isEmpty(reportRequest.getBusiLine()) ? BusinessLine.RDC.getCode() : reportDataList.get(0).getBusiLine();
        JSONArray countData = new JSONArray();
        int conCount;

        // 遍历每周的数据
        for (String week : weeks) {
            // 当前周期内是否有数据
            boolean hasCon = false;
            for (WorkOrderReportData workOrderReportData : reportDataList) {
                // 获取当前工单类型数量
                if (week.equals(workOrderReportData.getWeekTime())) {
                    conCount = workOrderReportData.getCount();
                    countData.add(conCount);
                    hasCon = true;
                    break;
                }
            }
            // 没有补0
            if (!hasCon) {
                countData.add(0);
            }
        }

        series.put(WorkOrderType.getWorkOrderType(workOrderType) + "数量", countData);

        echartsOption.put("xAxisData", xAxisData);
        echartsOption.put("series", series);
        echartsOption.put("titleText", weeks.get(0) + "周 至 " +
                weeks.get(weeks.size() - 1) + "周【" +
                BusinessLine.getBusiLine(busiLine) + "】工单趋势");

        return echartsOption;
    }

    @Override
    public JSONObject selectCumProportion(WorkOrderReportRequest reportRequest) {
        JSONObject echartsOption = new JSONObject();
        JSONArray xAxisData = new JSONArray();
        JSONObject series = new JSONObject();
        if("".equals(reportRequest.getBusiLine())) {
            reportRequest.setBusiLine(null);
        }
        List<WorkOrderReportData> reportDataList = selectWorkOrderReportList(reportRequest);
        if (reportDataList.isEmpty()) {
            echartsOption.put("series", 0);
            return echartsOption;
        }

        // x轴时间线
        Date beginTime = reportRequest.getBeginTime();
        Date endTime = reportRequest.getEndTime();
        List<String> weeks = DateUtils.getWeeksBetweenDays(beginTime, endTime);
        xAxisData.addAll(weeks);

        // 第一条数据工单类型
        String workOrderType = reportRequest.getWorkOrderType();
        String busiLine = StringUtils.isEmpty(reportRequest.getBusiLine()) ? BusinessLine.RDC.getCode() : reportDataList.get(0).getBusiLine();


        JSONArray conCountData = new JSONArray();
        JSONArray pjCountData = new JSONArray();
        JSONArray proportionData = new JSONArray();
        JSONArray cumProportionData = new JSONArray();

        DecimalFormat df = new DecimalFormat("0.00");
        String proportion = "0";
        int conCount = 0;
        int pjCount;
        // 查询对应时间内PJ工单数
        reportRequest.setWorkOrderType(WorkOrderType.PJ.getCode());
        List<WorkOrderReportData> pjReportDataList = selectWorkOrderReportList(reportRequest);

        // 遍历每周的数据
        for (String week : weeks) {
            // 当前周期内是否有数据
            boolean hasCon = false;
            for (WorkOrderReportData workOrderReportData : reportDataList) {
                // 获取当前工单类型数量
                if (week.equals(workOrderReportData.getWeekTime())) {
                    conCount = workOrderReportData.getCount();
                    conCountData.add(conCount);
                    hasCon = true;
                    break;
                }
            }
            // 没有补0
            if (!hasCon) {
                conCountData.add(0);
            }

            boolean hasPj = false;
            for (WorkOrderReportData workOrderReportData : pjReportDataList) {
                // 获取PJ工单类型数量,计算每周占比
                if (week.equals(workOrderReportData.getWeekTime())) {
                    pjCount = workOrderReportData.getCount();
                    pjCountData.add(pjCount);
                    hasPj = true;
                    proportion = (df.format((double) conCount / ((double) conCount + (double) pjCount) * 100));
                    break;
                }
            }
            // 没有补0
            if (!hasPj) {
                pjCountData.add(0);
                // 没有单PJ,有DB/BG
                if (hasCon) {
                    proportion = "100";
                } else {
                    proportion = "0";
                }
            }
            proportionData.add(proportion);
        }

        int totalPjCount = 0;
        int totalConCount = 0;
        // 每周的累计占比
        for (int i = 0; i < pjCountData.size(); i++) {
            totalPjCount = totalPjCount + pjCountData.getInteger(i);
            totalConCount = totalConCount + conCountData.getInteger(i);
            // 均没有
            if (totalPjCount == 0 && totalConCount == 0) {
                proportion = "0";
            }
            if (totalPjCount == 0 && totalConCount != 0) {
                proportion = "100";
            }
            if (totalPjCount != 0) {
                proportion = (df.format((double) totalConCount / ((double) totalConCount + (double) totalPjCount) * 100));
            }
            cumProportionData.add(proportion);
        }

        series.put(WorkOrderType.getWorkOrderType(workOrderType) + "占比", proportionData);
        series.put(WorkOrderType.getWorkOrderType(workOrderType) + "累计占比", cumProportionData);

        echartsOption.put("xAxisData", xAxisData);
        echartsOption.put("series", series);
        echartsOption.put("titleText", weeks.get(0) + "周 至 " +
                weeks.get(weeks.size() - 1) + "周【" +
                BusinessLine.getBusiLine(busiLine) + "】工单占比趋势");

        return echartsOption;
    }
}
