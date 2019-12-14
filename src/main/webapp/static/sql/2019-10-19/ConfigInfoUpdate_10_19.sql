/*修改站点配置中的字段内容*/
UPDATE [WHQJNativeWebDB].[dbo].[ConfigInfo] 
SET ConfigString = '参数说明
字段1：网站二维码地址 
字段2：网站图片服务器地址
字段3：网站前台服务器地址
字段4：网站分享链接地址
字段5： 网站后台管理图片地址',
Field5 = 'http://localhost:8082' 
WHERE
	ConfigID = 3;