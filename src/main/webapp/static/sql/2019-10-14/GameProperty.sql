/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : SQL Server
 Source Server Version : 11002100
 Source Host           : 103.137.98.231:1433
 Source Catalog        : WHQJPlatformDB
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11002100
 File Encoding         : 65001

 Date: 14/10/2019 18:51:46
*/


-- ----------------------------
-- Table structure for GameProperty
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[GameProperty]') AND type IN ('U'))
	DROP TABLE [dbo].[GameProperty]
GO

CREATE TABLE [dbo].[GameProperty] (
  [ID] int  IDENTITY(1,1) NOT NULL,
  [ExchangeType] tinyint DEFAULT ((0)) NOT NULL,
  [ExchangeRatio] int DEFAULT ((0)) NOT NULL,
  [Name] nvarchar(31) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [img_url] varchar(255) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [Kind] int DEFAULT ((0)) NOT NULL,
  [UseArea] tinyint DEFAULT ((0)) NOT NULL,
  [ServiceArea] tinyint DEFAULT ((0)) NOT NULL,
  [BuyResultsGold] bigint DEFAULT ((0)) NOT NULL,
  [SendLoveLiness] bigint DEFAULT ((0)) NOT NULL,
  [RecvLoveLiness] bigint DEFAULT ((0)) NOT NULL,
  [UseResultsGold] bigint DEFAULT ((0)) NOT NULL,
  [UseResultsValidTime] int DEFAULT ((0)) NOT NULL,
  [UseResultsValidTimeScoreMultiple] int DEFAULT ((0)) NOT NULL,
  [UseResultsGiftPackage] int DEFAULT ((0)) NOT NULL,
  [RegulationsInfo] nvarchar(128) COLLATE Chinese_PRC_CI_AS DEFAULT '' NOT NULL,
  [Recommend] tinyint DEFAULT ((0)) NOT NULL,
  [SortID] int DEFAULT ((0)) NOT NULL,
  [Nullity] tinyint DEFAULT ((0)) NOT NULL,
  [PlatformKind] tinyint DEFAULT ((0)) NOT NULL,
  [create_date] datetime  NULL,
  [update_date] datetime  NULL,
  [account] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL,
  [merchant] varchar(255) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[GameProperty] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'兑换类别（0、钻石  1、游戏币）',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'ExchangeType'
GO

EXEC sp_addextendedproperty
'MS_Description', N'兑道具比率（type 为0表示1钻石买多少道具  为1表示多少游戏币买1道具）',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'ExchangeRatio'
GO

EXEC sp_addextendedproperty
'MS_Description', N'道具名字',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'Name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'道具类型（1礼物2宝石3双倍卡4护身卡5防踢卡6vip7大喇叭8小喇叭9负分清零10逃跑清零11会员礼包）',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'Kind'
GO

EXEC sp_addextendedproperty
'MS_Description', N'道具使用范围，1大厅,2房间,4游戏中',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'UseArea'
GO

EXEC sp_addextendedproperty
'MS_Description', N'道具作用范围，1自己,2除自己玩家',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'ServiceArea'
GO

EXEC sp_addextendedproperty
'MS_Description', N'使用者增加魅力',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'SendLoveLiness'
GO

EXEC sp_addextendedproperty
'MS_Description', N'被使用者增加魅力',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'RecvLoveLiness'
GO

EXEC sp_addextendedproperty
'MS_Description', N'使用增加金币',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'UseResultsGold'
GO

EXEC sp_addextendedproperty
'MS_Description', N'使用持续时间，单位秒',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'UseResultsValidTime'
GO

EXEC sp_addextendedproperty
'MS_Description', N'使用有效时间内积分倍率',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'UseResultsValidTimeScoreMultiple'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否成为礼包',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'UseResultsGiftPackage'
GO

EXEC sp_addextendedproperty
'MS_Description', N'道具详细描述',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'RegulationsInfo'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否推荐1推荐，0不推荐',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'Recommend'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序标识',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'SortID'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否下架',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'Nullity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'平台类型 0 全部 1 LUA 2 H5 3 U3D',
'SCHEMA', N'dbo',
'TABLE', N'GameProperty',
'COLUMN', N'PlatformKind'
GO


-- ----------------------------
-- Records of GameProperty
-- ----------------------------
SET IDENTITY_INSERT [dbo].[GameProperty] ON
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'2', N'1', N'1000', N'亲吻', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一个亲吻的嘴唇飞到被使用玩家的头像处', N'0', N'3', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'5', N'1', N'1000', N'玫瑰花', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一朵盛开的玫瑰花飞到被使用玩家的头像处', N'0', N'2', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'6', N'1', N'1000', N'西红柿', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一个西红柿砸到被使用玩家的头像处破碎', N'0', N'4', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'7', N'1', N'1000', N'公鸡', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一只公鸡到被使用玩家的头像处时掐住公鸡的脖子', N'0', N'6', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'8', N'1', N'1000', N'鸡蛋', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一个鸡蛋砸到被使用玩家的头像处破碎', N'0', N'5', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'9', N'1', N'1000', N'赞美', N'', N'1', N'4', N'2', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'从使用道具的玩家头像处飞出一个拇指向上的拳头动画到被使用玩家的头像处', N'0', N'1', N'0', N'1', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'10', N'0', N'999', N'大喇叭', N'', N'7', N'7', N'1', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'大喇叭 robinTest', N'0', N'1', N'0', N'0', NULL, NULL, NULL, NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'14', N'1', N'1', N'巧克力', N'/Image/Dota/20191013225023100.png', N'0', N'0', N'0', N'2000000', N'0', N'0', N'3000000', N'0', N'0', N'0', N'巧克力', N'0', N'4', N'0', N'0', N'2019-10-13 22:50:23.100', N'2019-10-14 16:17:04.790', N'admin', NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'16', N'1', N'1', N'dove', N'/Image/Dota/20191013225648183.png', N'0', N'0', N'0', N'20000', N'0', N'0', N'30000', N'0', N'0', N'0', N'', N'0', N'4', N'1', N'0', N'2019-10-13 22:56:48.190', N'2019-10-13 22:56:48.190', N'admin', NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'17', N'1', N'1', N'奶糖', N'/Image/Dota/20191014155240958.png', N'0', N'0', N'0', N'2000000', N'0', N'0', N'3000000', N'0', N'0', N'0', N'奶糖', N'0', N'4', N'0', N'0', N'2019-10-14 14:10:02.933', N'2019-10-14 15:52:40.960', N'admin', NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'18', N'1', N'0', N'士大夫地方', N'/Image/Dota/20191014173845483.png', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'啊实打实的', N'0', N'0', N'0', N'0', N'2019-10-14 14:36:58.090', N'2019-10-14 17:52:04.567', N'admin', NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'19', N'1', N'0', N'3为2323', N'/Image/Dota/20191014173428246.png', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'123321', N'0', N'0', N'0', N'0', N'2019-10-14 17:34:28.250', N'2019-10-14 17:48:36.173', N'admin', NULL)
GO

INSERT INTO [dbo].[GameProperty] ([ID], [ExchangeType], [ExchangeRatio], [Name], [img_url], [Kind], [UseArea], [ServiceArea], [BuyResultsGold], [SendLoveLiness], [RecvLoveLiness], [UseResultsGold], [UseResultsValidTime], [UseResultsValidTimeScoreMultiple], [UseResultsGiftPackage], [RegulationsInfo], [Recommend], [SortID], [Nullity], [PlatformKind], [create_date], [update_date], [account], [merchant]) VALUES (N'20', N'1', N'0', N'旺仔', N'/Image/Dota/20191014184742561.png', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'0', N'', N'0', N'0', N'0', N'0', N'2019-10-14 18:47:44.677', N'2019-10-14 18:47:44.677', N'admin', NULL)
GO

SET IDENTITY_INSERT [dbo].[GameProperty] OFF
GO


-- ----------------------------
-- Primary Key structure for table GameProperty
-- ----------------------------
ALTER TABLE [dbo].[GameProperty] ADD CONSTRAINT [PK__GameProp__33F997E094BB9FE9] PRIMARY KEY CLUSTERED ([ID], [ExchangeType])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO

