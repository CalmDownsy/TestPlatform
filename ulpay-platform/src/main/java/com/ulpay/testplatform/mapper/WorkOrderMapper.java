package com.ulpay.testplatform.mapper;

import com.ulpay.testplatform.domain.WorkOrder;
import com.ulpay.testplatform.domain.WorkOrderReportData;
import com.ulpay.testplatform.domain.WorkOrderReportRequest;

import java.util.List;
import java.util.Map;

/**
 * Created By: zhangsy
 * Description: 工单表 数据层
 * 2019/8/3 18:12
 */
public interface WorkOrderMapper {


    /**
     * 根据分页查询工单
     *
     * @param workOrder 工单
     * @return 工单集合
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
     * 通过id查询工单
     *
     * @param workOrderId 工单Id
     * @return 结果
     */
    WorkOrder selectWorkOrderById(Long workOrderId);

    /**
     * 新增工单
     *
     * @param workOrder 工单
     * @return 结果
     */
    int insertWorkOrder(WorkOrder workOrder);

    /**
     * 更新工单
     *
     * @param workOrder 工单
     * @return 结果
     */
    int updateWorkOrder(WorkOrder workOrder);

    /**
     * 删除工单
     *
     * @param ids ids
     * @return 结果
     */
    int delWorkOrderByIds(Long[] ids);
}
