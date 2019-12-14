
function distribute(arr){
    if(arr.length == 0){
        return;
    }
    $.each(arr,function(index,obj){
        //分布
        if(obj.type == 1){
            distributeGoldMain(obj);
        }
    });
}


//1 金币分布统计
function distributeGoldMain(o){
    //console.log(o);
    //身上总金币数
    $("#totalDistributeScore").textbox('setValue',o.totalNumber1);
    //保险柜总金币数
    $("#totalDistributeInsureScore").textbox('setValue',o.totalNumber2);

    //图形
    let myChart = $.getEchartObject("distributeGoldMain");

    // 指定图表的配置项和数据
    option = {
        title: {
            text: '平台金币分布图',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        /*
        1万以下
        1万~10万
        10万~50万
        50万~100万
        100万~500万
        500万~1000万
        1000万~5000万
        5000万以上
        */
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            //bottom: 10,
            //left: 'center',
            //right: 'right',
            right: 'right',
            orient: 'vertical',
            //data: ['1万以下', '1万~10万','10万~50万','50万~100万','100万~500万','500万~1000万','1000万~5000万','5000万以上']
            data: o.nameList
        },
        series : [
            {
                name:'平台金币分布',
                type: 'pie',
                radius : '80%',
                center: ['50%', '60%'],
                selectedMode: 'single',
                /*data:[
                    {value:1548, name: '1万以下'},
                    {value:535, name: '1万~10万'},
                    {value:510, name: '10万~50万'},
                    {value:634, name: '50万~100万'},
                    {value:735, name: '100万~500万'},
                    {value:1535, name: '500万~1000万'},
                    {value:1510, name: '1000万~5000万'},
                    {value:1634, name: '5000万以上'}
                ],*/
                data: o.dataList,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}





