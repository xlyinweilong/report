package com.yin.report.system.task;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.common.CommonDao;
import com.yin.report.etl.dw.dao.FactChannelBillDao;
import com.yin.report.etl.dw.dao.FactChannelStockDao;
import com.yin.report.etl.dw.dao.FactSaleDao;
import com.yin.report.etl.dw.dao.FactWarehouseStockDao;
import com.yin.report.etl.source.lijing.service.*;
import com.yin.report.system.task.dao.TaskDao;
import com.yin.report.system.task.dao.TaskLogDao;
import com.yin.report.system.task.entity.Task;
import com.yin.report.system.task.entity.TaskLog;
import com.yin.report.system.task.entity.TaskLogDetail;
import com.yin.report.system.task.enums.TaskLogStatus;
import com.yin.report.system.task.service.TaskLogDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * 执行任务
 *
 * @author yin.weilong
 * @date 2018.11.03
 */
@Service
public class ExecutionTaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskLogDao taskLogDao;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private ChannelBillService channelBillService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private VipService vipService;
    @Autowired
    private ChannelStockService channelStockService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private WarehouseStockService warehouseStockService;
    @Autowired
    private FactChannelBillDao factChannelBillDao;
    @Autowired
    private FactChannelStockDao factChannelStockDao;
    @Autowired
    private FactWarehouseStockDao factWarehouseStockDao;
    @Autowired
    private FactSaleDao factSaleDao;
    @Autowired
    private TaskLogDetailService taskLogDetailService;
    @Autowired
    private CommonDao commonDao;

    private static final Logger log = LoggerFactory.getLogger(ExecutionTaskService.class);

    /**
     * 开启一个线程执行任务
     */
    @Async
    public void executionTask(Task task) {
        log.info("开始执行任务 {}", task.getId());
        Date now = new Date();
        TaskLog taskLog = new TaskLog();
        taskLog.setTaskId(task.getId());
        taskLog.setTaskStartTime(now);
        taskLog.setTaskStatus(TaskLogStatus.DOING.name());
        taskLog.setTaskLog(TaskLogStatus.DOING.getMean());
        taskLogDao.insert(taskLog);
        try {
            if (task.getTaskLastSuccessDate() == null) {
                DBIdentifier.setProjectCode(task.getTaskDwDb());
                factChannelBillDao.truncateTable();
                factChannelStockDao.truncateTable();
                factWarehouseStockDao.truncateTable();
                factSaleDao.truncateTable();
            }

            //--事实收集
            //销售
            TaskLogDetail td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "销售");
            checkService.etlCheck(task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskLastSuccessDate());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "fact_sale", task.getTaskLastSuccessDate()));
            taskLogDetailService.updateTaskLogDetail(td);
            //单据
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "渠道单据");
            channelBillService.etlChannelBill(task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskLastSuccessDate());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "fact_channel_bill", task.getTaskLastSuccessDate()));
            taskLogDetailService.updateTaskLogDetail(td);
            //渠道库存
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "渠道库存");
            channelStockService.etlChannelStock(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "fact_channel_stock", task.getTaskLastSuccessDate()));
            taskLogDetailService.updateTaskLogDetail(td);
            //仓库库存
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "仓库库存");
            warehouseStockService.etlWarehouseStock(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "fact_warehouse_stock", task.getTaskLastSuccessDate()));
            taskLogDetailService.updateTaskLogDetail(td);
            //--维度更新
            //更新货品
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新货品");
            goodsService.etlGoods(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_goods", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新渠道");
            channelService.etlChannel(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_channel", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新员工
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新员工");
            clerkService.etlClerk(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_clerk", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新颜色
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新颜色");
            colorService.etlColor(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_color", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新尺码
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新尺码");
            sizeService.etlSize(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_size", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新VIP
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新VIP");
            vipService.etlVip(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_vip", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //更新仓库
            td = taskLogDetailService.createDetail(task.getId(), taskLog.getId(), "更新仓库");
            warehouseService.etlWarehouse(task.getTaskErpDb(), task.getTaskDwDb());
            td.setLogCount(commonDao.findCount(task.getTaskDwDb(), "dim_warehouse", null));
            taskLogDetailService.updateTaskLogDetail(td);
            //执行完成后更新
            task.setTaskFinishDate(LocalDate.now());
            task.setTaskLastSuccessDate(now);
            taskDao.update(task);
            taskLog.setTaskStatus(TaskLogStatus.SUCCESS.name());
            taskLog.setTaskLog(TaskLogStatus.SUCCESS.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.update(taskLog);
        } catch (Throwable e) {
            e.printStackTrace();
            //发生了一个错误
            taskLog.setTaskStatus(TaskLogStatus.UNKNOWN_ERROR.name());
            taskLog.setTaskLog(TaskLogStatus.UNKNOWN_ERROR.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.update(taskLog);
        }
    }
}
