package com.yin.report.system.task;

import com.yin.report.common.datasource.config.DBIdentifier;
import com.yin.report.etl.dw.dao.FactChannelBillDao;
import com.yin.report.etl.dw.dao.FactChannelStockDao;
import com.yin.report.etl.dw.dao.FactSaleDao;
import com.yin.report.etl.dw.dao.FactWarehouseStockDao;
import com.yin.report.etl.source.lijing.service.*;
import com.yin.report.system.task.dao.TaskDao;
import com.yin.report.system.task.dao.TaskLogDao;
import com.yin.report.system.task.entity.Task;
import com.yin.report.system.task.entity.TaskLog;
import com.yin.report.system.task.enums.TaskLogStatus;
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
        try {
            if(task.getTaskLastSuccessDate() == null){
                DBIdentifier.setProjectCode(task.getTaskDwDb());
                factChannelBillDao.truncateTable();
                factChannelStockDao.truncateTable();
                factWarehouseStockDao.truncateTable();
                factSaleDao.truncateTable();
            }

            //--事实收集
            //销售
            checkService.etlCheck(task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskLastSuccessDate());
            //单据
            channelBillService.etlChannelBill(task.getTaskErpDb(), task.getTaskDwDb(), task.getTaskLastSuccessDate());
            //渠道库存
            channelStockService.etlChannelStock(task.getTaskErpDb(), task.getTaskDwDb());
            //仓库库存
            warehouseStockService.etlWarehouseStock(task.getTaskErpDb(), task.getTaskDwDb());
            //--维度更新
            //更关货品
            goodsService.etlGoods(task.getTaskErpDb(), task.getTaskDwDb());
            //更新
            channelService.etlChannel(task.getTaskErpDb(), task.getTaskDwDb());
            //更新员工
            clerkService.etlClerk(task.getTaskErpDb(), task.getTaskDwDb());
            //更新颜色
            colorService.etlColor(task.getTaskErpDb(), task.getTaskDwDb());
            //更新尺码
            sizeService.etlSize(task.getTaskErpDb(), task.getTaskDwDb());
            //更新VIP
            vipService.etlVip(task.getTaskErpDb(), task.getTaskDwDb());
            //更新仓库
            warehouseService.etlWarehouse(task.getTaskErpDb(), task.getTaskDwDb());
            //执行完成后更新
            task.setTaskFinishDate(LocalDate.now());
            task.setTaskLastSuccessDate(now);
            taskDao.update(task);
            taskLog.setTaskStatus(TaskLogStatus.SUCCESS.name());
            taskLog.setTaskLog(TaskLogStatus.SUCCESS.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.insert(taskLog);
        } catch (Throwable e) {
            e.printStackTrace();
            //发生了一个错误
            taskLog.setTaskStatus(TaskLogStatus.UNKNOWN_ERROR.name());
            taskLog.setTaskLog(TaskLogStatus.UNKNOWN_ERROR.getMean());
            taskLog.setTaskEndTime(new Date());
            taskLogDao.insert(taskLog);
        }
    }
}
