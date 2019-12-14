/*点击亲友圈配置→创建条件→清除掉建条件描述中‘2，消耗钻石’，‘检测用户钻石质量*/
UPDATE [WHQJGroupDB].[dbo].[IMGroupOption] 
SET OptionDescribe = '创建群组的条件：0、没有条件 1、消耗金币 2、检测用户金币数量' 
WHERE
	OptionName = 'CreateGroupTakeIngot';
	
	
	
/*点击亲友圈配置→创建消耗→清除掉其中钻石还有积分*/
UPDATE [WHQJGroupDB].[dbo].[IMGroupOption] 
SET OptionDescribe = '与创建条件配合使用，当创建条件的配置不为0时，消耗或者检测金币的数量，不配置则等同没有条件，键值等于数量' 
WHERE
	OptionName = 'CreateGroupDeductIngot';
	
	
/*点击亲友圈配置→创建房间类型→清除掉其中钻石还有积分*/
UPDATE [WHQJGroupDB].[dbo].[IMGroupOption] 
SET OptionDescribe = '设定玩家可以创建的房间类型：1、开启金币房卡' 
WHERE
	OptionName = 'GroupRoomType';