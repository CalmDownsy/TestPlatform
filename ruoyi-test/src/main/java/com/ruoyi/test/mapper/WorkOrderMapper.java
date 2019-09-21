package com.ruoyi.test.mapper;

import com.ruoyi.test.domain.WorkOrder;

import java.util.List;

/**
 * Created By: zhangsy
 * Description: 工单表 数据层
 * 2019/8/3 18:12
 */
public interface WorkOrderMapper {


    /**
     * 根据分页查询工单
     * @param workOrder 工单
     * @return  工单集合
     */
    List<WorkOrder> selectWorkOrderList(WorkOrder workOrder);

    /**
     * 通过id查询工单
     * @param workOrderId   工单Id
     * @return  结果
     */
    WorkOrder selectWorkOrderById(Long workOrderId);

    /**
     * 新增工单
     * @param workOrder 工单
     * @return  结果
     */
    int insertWorkOrder(WorkOrder workOrder);

    /**
     * 更新工单
     * @param workOrder 工单
     * @return  结果
     */
    int updateWorkOrder(WorkOrder workOrder);

    /**
     * 删除工单
     * @param ids   ids
     * @return  结果
     */
    int delWorkOrderByIds(Long[] ids);
}
