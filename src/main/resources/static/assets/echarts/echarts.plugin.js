
// echarts 插件


$.extend({

    //根据传入id获取 echarts 对象
    getEchartObject : function(id){
        //console.log(id);
        //根据ID获取Jquery对象
        let $main = $("#" + id);
        //console.log($main);
        //将Jquery对象转成dom对象
        let mainObj = $main[0];
        //console.log(mainObj);
        let echartObject = echarts.init(mainObj);
        //console.log(echartObject);
        return echartObject;
    }

});