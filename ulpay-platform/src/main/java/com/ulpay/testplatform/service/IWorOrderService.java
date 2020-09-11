package com.ulpay.testplatform.service;

import com.alibaba.fastjson.JSONObject;
import com.ulpay.testplatform.domain.WorkOrder;
import com.ulpay.testplatform.domain.WorkOrderReportData;
import com.ulpay.testplatform.domain.WorkOrderReportRequest;

import java.util.List;
import java.util.Map;

/**
 * Created By: zhangsy
 * Description: 工单业务层
 * 2019/8/3 18:06
 */
public interface IWorOrderService {


    /**
     * 根据分页条件查询工单
     * @param workOrder 工单
     * @return  工单集合
     */
    List<WorkOrder> selectWorkOrderList(WorkOrder workOrder);

    /**
     * 查询工单分组数据
     *
     * @param reportRequest 请求
     * @return 合集
     */
    List<WorkOrderReportData> selectWorkOrderReportList(WorkOrderReportRequest reportRequest);



    /**
     * 根据Id查询工单
     * @param workOrderId   工单Id
     * @return  结果
     */
    WorkOrder selectWorkOrderById(Long workOrderId);

    /**
     * 新增工单
     * @param workOrder 工单信息
     * @return  结果
     */
    int insertWorkOrder(WorkOrder workOrder);

    /**
     * 修改工单
     * @param workOrder 工单信息
     * @return  结果
     */
    int updateWorkOrder(WorkOrder workOrder);

    /**
     * 删除工单
     * @param ids   工单Id
     * @return
     */
    int delWorkerOrderByIds(String ids);

    JSONObject selectWorkOrderProportion(WorkOrderReportRequest reportRequest);

    JSONObject selectWorkOrderRend(WorkOrderReportRequest reportRequest);

    JSONObject selectCumProportion(WorkOrderReportRequest reportRequest);
}
