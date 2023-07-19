
// 请求ajax
$.ajax({
    url: domain + ":" + port +  "/api/activity/queryActivityListPage",
    // url: 'http://localhost:8082/api/activity/queryActivityListPage',
    type: 'GET',
    xhrFields: {
    withCredentials: true // 允许跨域请求携带 Cookie
},
    crossDomain: true, // 允许跨域请求
    dataType: 'json',
    success: function(data) {
    // 将数据渲染到表格中
    layui.table.render({
    elem: '#mytable',
    cols: [[
{type: "checkbox", width: 50, fixed: "left"},
{field:'activityId',width:100, title:'活动ID'},
{field:'activityName',width:120, title:'活动名称'},
{field:'beginDateTime',width:200, title:'开始时间'},
{field:'endDateTime',width:200, title:'结束时间'},
    // {field:'stockCount',width:120, title:'库存'},
    // {field:'stockSurplusCount',width:100, title:'剩余库存'},
    // {field:'strategyId',width:100, title:'抽奖策略ID'},
{field:'creator',width:100, title:'创建人'},
{field:'state',width:60, title:'状态', align: 'center', templet: function (d) {
    // 活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启
    if(d.state === 1) return '编辑';
    if(d.state === 2) return '提升';
    if(d.state === 3) return '撤审';
    if(d.state === 4) return '通过';
    if(d.state === 5) return '运行';
    if(d.state === 6) return '拒绝';
    if(d.state === 7) return '关闭';
    if(d.state === 8) return '开启';
}},
    // {field:'createTime',width:200, title:'创建时间'},
    // {field:'updateTime',width:200, title:'修改时间'},
    ]],
    data: data.data,
    limits: [10, 25, 50, 100],
    limit: 10,
    page: true
});
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.log('Error:', textStatus, errorThrown);
}
});
