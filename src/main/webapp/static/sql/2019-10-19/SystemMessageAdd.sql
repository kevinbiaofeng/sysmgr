/*第一步  添加商户号字段*/
ALTER TABLE WHQJPlatformDB.dbo.SystemMessage ADD merchant varchar(255) COLLATE Chinese_PRC_CI_AS  NULL;


/*第二步	对该字段添加注释*/
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商户号' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SystemMessage', @level2type=N'COLUMN',@level2name=N'merchant'
GO
