<%@ include file="/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>后台大布局</title>
<link rel="stylesheet" href="${path}/resource/form/src/css/layui.css">
<script src="${path}/resource/form/src/vue.min.js"></script>
<script src="${path}/resource/form/src/jquery.min.js"></script>
<style>
	iframe{
		width: 100%;
		height: 100%;
		overflow: auto;
		display: block;
	}	
</style>
</head>
<body class="layui-layout-body">
<div id="app" class="layui-layout layui-layout-admin">
  <div class="layui-header">
    <div class="layui-logo">后台布局</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-left">
      <li class="layui-nav-item layui-this"><a href="javascript:;">控制台</a></li>
      <li class="layui-nav-item"><a href="javascript:;">商品管理</a></li>
      <li class="layui-nav-item"><a href="javascript:;">用户</a></li>
      <li class="layui-nav-item">
        <a href="javascript:;">其它系统</a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:;">邮件管理</a></dd>
          <dd><a href="javascript:;">消息管理</a></dd>
          <dd><a href="javascript:;">授权管理</a></dd>
        </dl>
      </li>
    </ul>
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
          <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
          贤心
        </a>
        <dl class="layui-nav-child">
          <dd><a href="javascript:;">基本资料</a></dd>
          <dd><a href="javascript:;">安全设置</a></dd>
        </dl>
      </li>
      <li class="layui-nav-item"><a href="javascript:;">退了</a></li>
    </ul>
  </div>
  
  <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item layui-nav-itemed">
          <a class="" href="javascript:;">所有UI</a>
          <dl class="layui-nav-child">
            <dd><a style="background:#009688;color:#fff" href="javascript:;" @click="show('element',$event)">元素</a></dd>
            <dd><a href="javascript:;" @click="show('button',$event)">按钮</a></dd>
            <dd><a href="javascript:;" @click="show('form',$event)">表单</a></dd>
            <dd><a href="javascript:;" @click="show('laydate',$event)">时间选择</a></dd>
            <dd><a href="javascript:;" @click="show('layer',$event)">提示框</a></dd>
            <dd><a href="javascript:;" @click="show('table',$event)">表格</a></dd>
            <dd><a href="javascript:;" @click="show('laypage',$event)">分页</a></dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
  
  <div class="layui-body">
    <!-- 内容主体区域 -->
    <div style="height: 100%;box-sizing: border-box;" v-html="ifm">
		</div>
  </div>
  
  <div class="layui-footer">
    <!-- 底部固定区域 -->
    © layui.com - 底部固定区域
  </div>
</div>
<script src="./src/layui.js"></script>
<script>
//JavaScript代码区域
//	layui.use('element', function(){
//			var element = layui.element;
//
//		});

var vm = new Vue({
	el: '#app',
	data: {
		ifm: '<iframe src="./examples/element.jsp" frameborder="0"></iframe>'
	},
	created: function(){
		layui.use('element', function(){
			var element = layui.element;

		});
	},
	methods: {
		show: function(path,el){
			this.ifm = '<iframe src="./examples/'+path+'.html" frameborder="0"></iframe>';
			$('.layui-nav-child a').css('background','');
			$('.layui-nav-child a').css('color','rgba(255,255,255,.7)');
			console.log(el.currentTarget)
			el.currentTarget.style.background = '#009688'
			el.currentTarget.style.color = '#fff'
		}
	}
})

</script>
</body>
</html>
