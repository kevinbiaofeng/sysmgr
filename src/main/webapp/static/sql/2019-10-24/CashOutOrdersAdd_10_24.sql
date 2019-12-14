/*第一步  添加财务状态字段*/
ALTER TABLE WHQJTreasureDB.dbo.CashOutOrders ADD financialStatus int DEFAULT ((0)) NOT NULL;


/*第二步	对该字段添加注释*/
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'财务状态' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CashOutOrders', @level2type=N'COLUMN',@level2name=N'financialStatus'
GO


/*第一步  添加财务审核操作员字段*/
ALTER TABLE WHQJTreasureDB.dbo.CashOutOrders ADD financialOperator varchar(255) COLLATE Chinese_PRC_CI_AS DEFAULT NULL NULL;


/*第二步	对该字段添加注释*/
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'财务审核操作员' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CashOutOrders', @level2type=N'COLUMN',@level2name=N'financialOperator'
GO


/*第一步  添加财务审核操作时间字段*/
ALTER TABLE WHQJTreasureDB.dbo.CashOutOrders ADD financialTime datetime DEFAULT NULL NULL;


/*第二步	对该字段添加注释*/
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'财务审核操作时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'CashOutOrders', @level2type=N'COLUMN',@level2name=N'financialTime'
GO
