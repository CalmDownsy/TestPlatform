package com.ulpay.testplatform.service;

import com.ulpay.testplatform.domain.WorkOrder;

import java.util.List;

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
}
