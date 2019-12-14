
function everday(arr){
    if(arr.length == 0){
        return;
    }
    $.each(arr,function(index,obj){
        //每日统计
        if(obj.type == 1){
            dailyRegistMain(obj);
        }else if(obj.type == 2){
            dailyPayMain(obj);
        }else if(obj.type == 3){
            dailyGoldMain(obj);
        }else if(obj.type == 4){
            dailyRevenueMain(obj);
        }else if(obj.type == 5){
            dailyWasteMain(obj);
        }else if(obj.type == 6){
            dailyOpenRoomMain(obj);
        }
    });

}

// 6 房间模式
function dailyOpenRoomMain(o){
    let myChart = $.getEchartObject("dailyOpenRoomMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '平台每日开房',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['房间模式'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '次数'
            /*axisLabel: {
                formatter: '{value}'
            }*/
        },
        series: [
            {
                name:'房间模式',
                type:'line',
                stack: '总量',
                //data:[120, 132, 101, 134, 90, 230, 210],
                data: o.seriesSingtonDataList,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//5 损耗统计
function dailyWasteMain(o){
    let myChart = $.getEchartObject("dailyWasteMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '每日损耗统计',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['游戏损耗'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '金币'
            /*axisLabel: {
                formatter: '{value}'
            }*/
        },
        series: [
            {
                name:'游戏损耗',
                type:'line',
                stack: '总量',
                //data:[120, 132, 101, 134, 90, 230, 210],
                data: o.seriesSingtonDataList,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//4 服务费统计
function dailyRevenueMain(o){
    let myChart = $.getEchartObject("dailyRevenueMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '每日服务费统计',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['游戏服务费','银行服务费','合计服务费'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        /*toolbox: {
            //feature: {
                //saveAsImage: {}
            //}

        },*/
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '金币',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name:'游戏服务费',
                type:'line',
                stack: '总量',
                //data:[120, 132, 101, 134, 90, 230, 210],
                data: o.seriesMultipleDataMap.seriesData1,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'银行服务费',
                type:'line',
                stack: '总量',
                //data:[220, 182, 191, 234, 290, 330, 310],
                data: o.seriesMultipleDataMap.seriesData2,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'合计服务费',
                type:'line',
                stack: '总量',
                //data:[150, 232, 201, 154, 190, 330, 410],
                data: o.seriesMultipleDataMap.seriesData3,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//3 金币统计
function dailyGoldMain(o){
    let myChart = $.getEchartObject("dailyGoldMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '平台每日金币',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }

        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['充值金币','平台金币'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis:  {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '金币',
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name:'充值金币',
                type:'line',
                //data:[11, 11, 15, 13, 12, 13, 10],
                data: o.seriesMultipleDataMap.seriesData2,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'平台金币',
                type:'line',
                //data:[1, -2, 2, 5, 3, 2, 0],
                data: o.seriesMultipleDataMap.seriesData1,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
                /*markPoint: {
                    data: [
                        {name: '周最低', value: -2, xAxis: 1, yAxis: -1.5}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'},
                        [{
                            symbol: 'none',
                            x: '90%',
                            yAxis: 'max'
                        }, {
                            symbol: 'circle',
                            label: {
                                normal: {
                                    position: 'start',
                                    formatter: '最大值'
                                }
                            },
                            type: 'max',
                            name: '最高点'
                        }]
                    ]
                }*/
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

//2 充值统计
function dailyPayMain(o){
    let myChart = $.getEchartObject("dailyPayMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '平台每日充值',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['充值金额'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '金额'
            /*axisLabel: {
                formatter: '{value} 金额'
            }*/
        },
        series: [
            {
                name:'充值金额',
                type:'line',
                stack: '总量',
                //data:[120, 132, 101, 134, 90, 230, 210],
                data:o.seriesSingtonDataList,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    myChart.setOption(option);
}



//1 注册统计
function dailyRegistMain(o){
    let myChart = $.getEchartObject("dailyRegistMain");
    // 指定图表的配置项和数据
    option = {
        title: {
            text: '平台每日用户注册',
            x:'center',
            y:'top',
            textAlign: 'left',
            textStyle: {
                //color: 'red'
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['注册总人数'],
            right: '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            show: true,
            feature: {
                //dataZoom: {
                //	yAxisIndex: 'none'
                //},
                //dataView: {readOnly: true},
                magicType: {type: ['line', 'bar']}
                //restore: {},
                //saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            name: '日期',
            //nameLocation: 'right',
            //data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
            data: o.xaxisDataList,
            /*axisLabel: {
                show: true,
                textStyle: {
                    fontSize: '15',
                    color: '#1f1f1f',
                    verticalAlign: 'bottom'
                }
            }*/
        },
        yAxis: {
            type: 'value',
            name: '人数'
            /*axisLabel: {
                formatter: '{value} 人数'
            }*/
        },
        series: [
            {
                name:'注册总人数',
                type:'line',
                stack: '总量',
                //data:[120, 132, 101, 134, 90, 230, 210],
                data:o.seriesSingtonDataList,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };
    myChart.setOption(option);
}






// 基于准备好的dom，初始化echarts实例
//var myChart = echarts.init(document.getElementById('dailyRegistMain'));
//let myChart = $.getEchartObject("dailyRegistMain");
// 指定图表的配置项和数据
/*option = {
    title: {
        text: '折线图堆叠'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['邮件营销']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    /!*toolbox: {
        //feature: {
            //saveAsImage: {}
        //}

    },*!/
    toolbox: {
        show: true,
        feature: {
            //dataZoom: {
            //	yAxisIndex: 'none'
            //},
            //dataView: {readOnly: true},
            magicType: {type: ['line', 'bar']}
            //restore: {},
            //saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['周一','周二','周三','周四','周五','周六','周日']
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'邮件营销',
            type:'line',
            stack: '总量',
            data:[120, 132, 101, 134, 90, 230, 210],
            markPoint: {
                data: [
                    {type: 'max', name: '最大值'},
                    {type: 'min', name: '最小值'}
                ]
            },
            markLine: {
                data: [
                    {type: 'average', name: '平均值'}
                ]
            }
        }
    ]
};*/


// 使用刚指定的配置项和数据显示图表。
//myChart.setOption(option);