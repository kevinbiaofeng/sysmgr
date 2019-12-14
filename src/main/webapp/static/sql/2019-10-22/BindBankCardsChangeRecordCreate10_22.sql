/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : SQL Server
 Source Server Version : 11002100
 Source Host           : 103.137.98.231:1433
 Source Catalog        : WHQJTreasureDB
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11002100
 File Encoding         : 65001

 Date: 22/10/2019 19:54:00
*/


-- ----------------------------
-- Table structure for BindBankCardsChangeRecord
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[BindBankCardsChangeRecord]') AND type IN ('U'))
	DROP TABLE [dbo].[BindBankCardsChangeRecord]
GO

CREATE TABLE [dbo].[BindBankCardsChangeRecord] (
  [id] int  IDENTITY(1,1) NOT NULL,
  [user_id] int  NOT NULL,
  [merchant] varchar(255) COLLATE Chinese_PRC_CI_AS DEFAULT NULL NULL,
  [bank_choice] int  NOT NULL,
  [bank_card_id] varchar(255) COLLATE Chinese_PRC_CI_AS DEFAULT NULL NOT NULL,
  [bank_name] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [id_card_name] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [bind_time] datetime  NOT NULL,
  [nullity] int DEFAULT ((0)) NOT NULL,
  [operator] varchar(255) COLLATE Chinese_PRC_CI_AS  NOT NULL,
  [create_time] smalldatetime  NOT NULL
)
GO

ALTER TABLE [dbo].[BindBankCardsChangeRecord] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'主键自增id',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户标识ID',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'商户号',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'merchant'
GO

EXEC sp_addextendedproperty
'MS_Description', N'卡类型(0支付宝;1中国银行;2农行;3建行;4工行;5交行;6招行;7光大;8浦东发展;9广发;10北京;11兴业;12邮政;13上海;14平安;15华夏)',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'bank_choice'
GO

EXEC sp_addextendedproperty
'MS_Description', N'银行卡号',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'bank_card_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'银行名称',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'bank_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'实名(即该卡绑定的用户名)',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'id_card_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'绑定时间',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'bind_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'禁用标识:0启用1禁用',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'nullity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'操作员',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'operator'
GO

EXEC sp_addextendedproperty
'MS_Description', N'生成该条记录的时间',
'SCHEMA', N'dbo',
'TABLE', N'BindBankCardsChangeRecord',
'COLUMN', N'create_time'
GO


-- ----------------------------
-- Primary Key structure for table BindBankCardsChangeRecord
-- ----------------------------
ALTER TABLE [dbo].[BindBankCardsChangeRecord] ADD CONSTRAINT [PK__BindBank__3213E83F080B4800] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

