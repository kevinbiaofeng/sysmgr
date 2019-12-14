USE [WHQJAccountsDB]
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD phone varchar(100);
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_phone]  DEFAULT ('') FOR [phone]
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'联系电话' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchant', @level2type=N'COLUMN',@level2name=N'phone'
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD mail varchar(100);
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_mail]  DEFAULT ('') FOR [mail]
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'电子邮箱' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchant', @level2type=N'COLUMN',@level2name=N'mail'
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD qq_account
varchar(100);
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_qq_account]  DEFAULT ('') FOR [qq_account]
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'qq账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchant', @level2type=N'COLUMN',@level2name=N'qq_account'
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD wechat_account
varchar(100);
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_wechat_account]  DEFAULT ('') FOR [wechat_account]
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'微信账号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchant', @level2type=N'COLUMN',@level2name=N'wechat_account'
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD safe_password
varchar(50);
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_safe_password]  DEFAULT ('') FOR [safe_password]
GO


EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'安全密码' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchant', @level2type=N'COLUMN',@level2name=N'safe_password'
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD create_user bigint;
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_create_user]  DEFAULT (1) FOR [create_user]
GO



ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD create_time datetime;
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_create_time]  DEFAULT (getdate()) FOR [create_time]
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD update_user bigint;
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_update_user]  DEFAULT (1) FOR [update_user]
GO



ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD update_time datetime;
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchant] ADD  CONSTRAINT [DF_TbMerchant_update_time]  DEFAULT (getdate()) FOR [update_time]
GO

CREATE TABLE [JavaSystemManager].[dbo].[TbMerchantDomain](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[domain_url] [nvarchar] (50) NOT NULL,
	[status] [tinyint] NOT NULL,
	[type] [tinyint] NOT NULL,
	[user_id] [bigint] NOT NULL,
	[remark] [nvarchar] (255) NOT NULL,
	[create_user] [bigint] NOT NULL,
	[create_date] datetime  NULL,
	[update_user] [bigint] NOT NULL,
  	[update_date] datetime  NULL,
  	
 CONSTRAINT [PK_MerchantDomain] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchantDomain] ADD  CONSTRAINT [DF_TbMerchantDomain_domain_url]  DEFAULT ('') FOR[domain_url]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'域名' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchantDomain', @level2type=N'COLUMN',@level2name=N'domain_url'
GO


ALTER TABLE [JavaSystemManager].[dbo].[TbMerchantDomain] ADD  CONSTRAINT [DF_TbMerchantDomain_status]  DEFAULT ((0)) FOR [status]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态 0: 正常 1:删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchantDomain', @level2type=N'COLUMN',@level2name=N'status'
GO

ALTER TABLE [JavaSystemManager].[dbo].[TbMerchantDomain] ADD  CONSTRAINT [DF_TbMerchantDomain_type]  DEFAULT ('') FOR [type]
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'域名类型 1:推广  2：API' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TbMerchantDomain', @level2type=N'COLUMN',@level2name=N'type'
GO