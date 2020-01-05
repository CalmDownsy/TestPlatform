package com.ulpay.testplatform.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ulpay.testplatform.domain.WorkOrder;
import com.ulpay.testplatform.mapper.WorkOrderMapper;
import com.ulpay.testplatform.service.IWorOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created By: zhangsy
 * Description: 工单业务层处理
 * 2019/8/3 18:09
 */
@Service
public class WorkOrderServiceImpl implements IWorOrderService{

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Override
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder) {
        return workOrderMapper.selectWorkOrderList(workOrder);
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
}
