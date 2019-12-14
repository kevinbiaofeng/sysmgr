<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>测试记录</title>
    <%@ include file="/static/pub/include.jsp"%>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="dailyGoldMain" style="width: 100%;height:450px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('dailyGoldMain'));

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
				data: ['2019-07-11','2019-07-12','2019-07-13','2019-07-14','2019-07-15','2019-07-16','2019-07-17'],
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
					data:[11, 11, 15, 13, 12, 13, 10],
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
					data:[1, -2, 2, 5, 3, 2, 0],
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
    </script>
</body>
</html>