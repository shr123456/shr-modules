<%@ include file="/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>分页 - layui</title>

<link rel="stylesheet" href="${path}/resource/form/src/css/layui.css">

<style>
body{padding: 10px;}
</style>
</head>
<body>

总页数低于页码总数：<div id="demo0"></div>
总页数大于页码总数：<div id="demo1"></div>
自定义主题：<div id="demo2"></div>
自定义首页、尾页、上一页、下一页文本：<div id="demo3"></div>
不显示首页尾页：<div id="demo4"></div>
开启HASH：<div id="demo5"></div>
只显示上一页、下一页：<div id="demo6"></div>
显示完整功能：<div id="demo7"></div>
自定义排版：<div id="demo8"></div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
  <legend>将一段已知数组分页展示</legend>
</fieldset>
 
<div id="demo9"></div>
 
<ul id="biuuu_city_list"></ul>  


<script src="../src/layui.js"></script>
<script>

layui.use(['laypage', 'layer'], function(){
  var laypage = layui.laypage
  ,layer = layui.layer;

  
  //总页数低于页码总数
  laypage.render({
    elem: 'demo0'
    ,count: 50 //数据总数
  });
  
  //总页数大于页码总数
  laypage.render({
    elem: 'demo1'
    ,count: 70 //数据总数
    ,jump: function(obj){
      console.log(obj)
    }
  });
  
  //自定义样式
  laypage.render({
    elem: 'demo2'
    ,count: 100
    ,theme: '#1E9FFF'
  });
  
  //自定义首页、尾页、上一页、下一页文本
  laypage.render({
    elem: 'demo3'
    ,count: 100
    ,first: '首页'
    ,last: '尾页'
    ,prev: '<em>←</em>'
    ,next: '<em>→</em>'
  });
  
  //不显示首页尾页
  laypage.render({
    elem: 'demo4'
    ,count: 100
    ,first: false
    ,last: false
  });
  
  //开启HASH
  laypage.render({
    elem: 'demo5'
    ,count: 500
    ,curr: location.hash.replace('#!fenye=', '') //获取hash值为fenye的当前页
    ,hash: 'fenye' //自定义hash值
  });
  
  //只显示上一页、下一页
  laypage.render({
    elem: 'demo6'
    ,count: 50
    ,layout: ['prev', 'next']
    ,jump: function(obj, first){
      if(!first){
        layer.msg('第 '+ obj.curr +' 页');
      }
    }
  });
  
  //完整功能
  laypage.render({
    elem: 'demo7'
    ,count: 100
    //,groups: 0
    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    ,jump: function(obj){
      console.log(obj)
    }
  });
  
  //自定义排版
  laypage.render({
    elem: 'demo8'
    ,count: 1000
    ,layout: ['limit', 'prev', 'page', 'next']
    //,limits: [100, 200, 300]
    ,limit: 200
  });
  
  
  
  //将一段数组分页展示
  
  //测试数据
  var data = [
    '北京',
    '上海',
    '广州',
    '深圳',
    '杭州',
    '长沙',
    '合肥',
    '宁夏',
    '成都',
    '西安',
    '南昌',
    '上饶',
    '沈阳',
    '济南',
    '厦门',
    '福州',
    '九江',
    '宜春',
    '赣州',
    '宁波',
    '绍兴',
    '无锡',
    '苏州',
    '徐州',
    '东莞',
    '佛山',
    '中山',
    '成都',
    '武汉',
    '青岛',
    '天津',
    '重庆',
    '南京',
    '九江',
    '香港',
    '澳门',
    '台北'
  ];
  
  //调用分页
  laypage.render({
    elem: 'demo9'
    ,count: data.length
    ,jump: function(obj){
      //模拟渲染
      document.getElementById('biuuu_city_list').innerHTML = function(){
        var arr = []
        ,thisData = data.concat().splice(obj.curr*obj.limit - obj.limit, obj.limit);
        layui.each(thisData, function(index, item){
          arr.push('<li>'+ item +'</li>');
        });
        return arr.join('');
      }();
    }
  });
  
});
</script>
</body>
</html>
