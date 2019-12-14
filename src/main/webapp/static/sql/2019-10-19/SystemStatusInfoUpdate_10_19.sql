/*修改系统设置→银行存取，只需存在一个就好，设置一个，游戏中的银行都一致*/
UPDATE [WHQJAccountsDB].[dbo].[SystemStatusInfo] 
SET StatusTip = '银行存取1'
WHERE
	StatusName = 'BankOPButton1';
	
	
UPDATE [WHQJAccountsDB].[dbo].[SystemStatusInfo] 
SET StatusTip = '银行存取2'
WHERE
	StatusName = 'BankOPButton2';
	
	
UPDATE [WHQJAccountsDB].[dbo].[SystemStatusInfo] 
SET StatusTip = '银行存取3'
WHERE
	StatusName = 'BankOPButton3';
	
	
UPDATE [WHQJAccountsDB].[dbo].[SystemStatusInfo] 
SET StatusTip = '存取条件Max'
WHERE
	StatusName = 'BankGameMax';
	
	
UPDATE [WHQJAccountsDB].[dbo].[SystemStatusInfo] 
SET StatusTip = '存取条件Min'
WHERE
	StatusName = 'BankPrerequisite';