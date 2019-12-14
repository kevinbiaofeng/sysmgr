/*昵称应该是唯一的，但查出来两条，所以修改脏数据*/
UPDATE [WHQJAccountsDB].[dbo].[AccountsInfo] 
SET NickName = 'cs012' 
WHERE
	UserID = 1040;