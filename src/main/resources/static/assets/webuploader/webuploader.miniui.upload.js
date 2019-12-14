/**
 * 封装webuploader附件上传的js<br/>
 * 与miniui整合
 * 
 * @author litiantong
 * 
 */
var uploader;//定义全局变量

var mywebuploader = {
		
		/*
		 * 初始化 uploader
		 *  data 初始化数据
		 *  grid miniui表格ID
		 */
		initWebUploader : function(data , gridId){
			//构建 uploader
			uploader = WebUploader.create({
				
				auto: data.auto, // 选完文件后，是否自动上传 
				
			    // swf文件路径
			    swf: "${pageContext.request.contextPath}/assets/webuploader/Uploader.swf",
		
			    // 文件接收服务端。
			    server: data.serverUrl,
		
			    // 选择文件的按钮。可选。
			    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
			    pick: data.pick,
		
			    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
			    resize: data.resize,
			    
			    //是否可以重复选择   true为可重复   ，false为不可重复
			    duplicate : data.duplicate,
			    
			    fileNumLimit: data.fileNumLimit,// 上传数量限制
			    fileSizeLimit: data.fileSizeLimit * 1024 * 1024,//限制上传所有文件大小 100M
			    fileSingleSizeLimit: data.fileSingleSizeLimit * 1024 * 1024,// 限制上传单个文件大小 10M
			    
			    //自动以参数
			    formData : data.formData
			});
			
			// 当有文件被添加进队列的时候
			uploader.on( 'fileQueued', function( file ) {
				//alert("--:"+file.id + "&&&" + file.name + "&&&" + file.size + "&&&" + file.ext);
				//fileId -- file.id
				var fileId = file.id;
				//fileName -- file.name
				var fileName = file.name;
				//fileType -- file.ext
				var fileType = file.ext;
				//fileSize -- file.size B
				var size = file.size;
				var fileSize;
				if(size < 1024){
					fileSize = size.toFixed(2) + "B";
				}else{
					fileSize = (size/1024).toFixed(2) + "K";
				}
				//"<a href='javascript:void(0)' onclick=deleteFileByRow('" + fileId + "')></a>";
				//var action = "<a href='javascript:void(0)' onclick=deleteFileByRow('" + fileId + "','" + gridId + "')>删除</a>";
				var action = "<button class='layui-btn layui-btn-mini layui-btn-danger' onclick=deleteFileByRow('" + fileId + "','" + gridId + "')><i class='iconfont' data-icon='&#xe626;'>&#xe626;</i><span>删除</span></button>"
				/*action -- <a>删除</a>*/
				var row = {
						fileId:fileId,
						fileName:fileName,
						fileType:fileType,
						fileSize:fileSize,
						action:action
					};
				//判断是否存在 TODO
				var grid = mini.get(gridId);
				var fg = isExistence(row,grid);
				if(!fg){
					//var datagrid1 = mini.get("datagrid1");
					grid.addRow(row);
				}else{
					uploader.removeFile(file);
				}
			});
			
			//上传每个文件之前设置额外参数
		    uploader.on("uploadStart", function (block, data) {
		    	//alert("上传每个文件之前设置额外参数-----uploadStart-------");
		    	//data.id=formData.type;
		    });
		    uploader.on("startUpload", function () {
		    	//alert("上传每个文件之前设置额外参数-----startUpload-------");
		    });
		    
		 	// 文件上传过程中创建进度条实时显示。
			uploader.on( 'uploadProgress', function( file, percentage ) {
			    //alert("文件上传过程中创建进度条实时显示------uploadProgress-----");
				mini.mask({el: document.body,cls: 'mini-mask-loading',html: '处理中...'});
			});
		 	
			//上传成功
			uploader.on( 'uploadSuccess', function( file ) {
				//alert("上传成功-------uploadSuccess---------");
				//mini.alert("上传成功");
			});
			
			//上传失败
			uploader.on( 'uploadError', function( file ) {
				//alert("上传失败-------uploadError---------");
				//mini.alert("上传失败");
			});
			
			//上传服务器接收
			uploader.on("uploadAccept", function( file, data){  
				//mini.alert("上传服务器接收");
			});
			
			// 上传完成
			uploader.on( 'uploadComplete', function( file ) {
				//alert("上传完成-------uploadComplete---------");
			});
			
			//上传结束
			uploader.on("uploadFinished", function (data) {
				//alert("上传结束-------uploadFinished---------");
				//alert(uploader.getStats().uploadFailNum);
		        //uploader.destroy();
		        //initWebUploader(data);
				mini.alert("上传完成");
				//location.reload();
				mini.unmask(document.body);
				CloseWindow("ok");
		    });
		},
		startUpload : function(params){
			uploader.option("formData",params);
		    uploader.upload();
		}
	
};

/*
 * grid中是否存在
 * 	true 存在 false 不存在
 */
function isExistence(row,grid){
	var flage = false;
	var dataArr = grid.getData();
	if(dataArr.length > 0){
		for(var i = 0 ; i < dataArr.length ; i ++){
			var r = dataArr[i];
			if(r.fileName == row.fileName){
				flage = true;
				break;
			}
		}
	}
	return flage;
}


/*
 * 删除grid中的数据
 */
function deleteFileByRow(fileId,gridId){
	var grid = mini.get(gridId);
	var row = grid.getSelected();
	//从队列中删除 file
	var file = uploader.getFile(fileId);
	uploader.removeFile(file);
	if(row.fileId == fileId){
		grid.removeRow(row);
	}
}


//关闭窗口的动作
function CloseWindow(action) {   
	if(window.CloseOwnerWindow){
    	return window.CloseOwnerWindow(action);
    }else{
    	window.close(); 
    }         
}



