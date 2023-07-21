var url = domain + "/api/prize/queryPrizeList";
var url2 = domain + "/api/prize/doDrawPrize";
var prizes = null;
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
// 请求奖品列表，进行轮盘渲染
$.ajax({
    url: url,
    hearders: {
        'ngrok-skip-browser-warning': false
    },
    // url: 'http://localhost:8082/api/prize/queryPrizeList',
    type: 'GET',
    xhrFields: {
        withCredentials: true // 允许跨域请求携带 Cookie
    },
    crossDomain: true, // 允许跨域请求
    dataType: 'json',
    success: function(data) {
    // 将数据转换为 prizes 数组的格式
    this.prizes = data.data.map(prize => ({
    background: getRandomColor(),
    fonts: [{ text: prize.awardName }]
}));
    myLucky = new LuckyCanvas.LuckyWheel('#my-lucky', {
    width: '300px',
    height: '300px',
    blocks: [{ padding: '10px', background: '#617df2' }],
    prizes: this.prizes,
    buttons: [{
    radius: '35%',
    background: '#8a9bf3',
    pointer: true,
    fonts: [{ text: '开始', top: '-10px' }]
}],
    start: function () {
    // 开始游戏
    myLucky.play();
    // 使用定时器模拟接口
    // 假设后端返回的中奖索引是0
    $.ajax({
        url: url2,
        hearders: {
            'ngrok-skip-browser-warning': false
        },
        // url: 'http://localhost:8082/api/prize/doDrawPrize',
        type: 'GET',
        data: {
            userId: Math.random()
        },
        xhrFields: {
        withCredentials: true // 允许跨域请求携带 Cookie
    },
    crossDomain: true, // 允许跨域请求
    dataType: 'json',
    success: function(data) {
    // console.log(data.data);
    const index = (data.data.awardId - 1);
    // 调用stop停止旋转并传递中奖索引
    myLucky.stop(index);
    setTimeout(function() {
    var content = '<div style="padding: 20px;">' +
    '<p>恭喜你参与活动 ' + data.data.activityId+
    '<p>并且获得 ' + data.data.awardName + '奖品</p>' +
    '<p>详情为' + data.data.awardContent + '</p>' +
    '<p>请注意查收发给您的奖品！！！' + '</p>' +
    '</div>';
    layer.open({
    type: 1,
    title: '中奖信息',
    content: content,
});
}, 5000);
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.log('Error:', textStatus, errorThrown);
}
});
}
})
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.log('Error:', textStatus, errorThrown);
}
});

