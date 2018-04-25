<%@ include file="/common/common.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="en">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>表格操作 - layui</title>

	<link rel="stylesheet" href="${path}/resource/form/src/css/layui.css">

	<style>
		body {
			padding: 20px;
		}

	</style>
</head>

<body>

	<div class="layui-tab-item layui-show">
		<div class="layui-main">
			<div id="LAY_preview">
				<!--
<blockquote class="layui-elem-quote">
  本页面只是演示静态表格，如果你需要的是数据表格，可前往：
  <a class="layui-btn layui-btn-normal" href="table.jsp" target="_blank">表格模块示例</a>
</blockquote>   
-->

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
					<legend>默认表格</legend>
				</fieldset>

				<div class="layui-form">
					<table class="layui-table">
						<colgroup>
							<col width="150">
								<col width="150">
									<col width="200">
										<col>
						</colgroup>
						<thead>
							<tr>
								<th>人物</th>
								<th>民族</th>
								<th>出场时间</th>
								<th>格言</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>贤心</td>
								<td>汉族</td>
								<td>1989-10-14</td>
								<td>人生似修行</td>
							</tr>
							<tr>
								<td>张爱玲</td>
								<td>汉族</td>
								<td>1920-09-30</td>
								<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
							</tr>
							<tr>
								<td>Helen Keller</td>
								<td>拉丁美裔</td>
								<td>1880-06-27</td>
								<td> Life is either a daring adventure or nothing.</td>
							</tr>
							<tr>
								<td>岳飞</td>
								<td>汉族</td>
								<td>1103-北宋崇宁二年</td>
								<td>教科书再滥改，也抹不去“民族英雄”的事实</td>
							</tr>
							<tr>
								<td>孟子</td>
								<td>华夏族（汉族）</td>
								<td>公元前-372年</td>
								<td>猿强，则国强。国强，则猿更强！ </td>
							</tr>
						</tbody>
					</table>
				</div>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
					<legend>行边框表格</legend>
				</fieldset>

				<table class="layui-table" lay-skin="line">
					<colgroup>
						<col width="150">
							<col width="150">
								<col width="200">
									<col>
					</colgroup>
					<thead>
						<tr>
							<th>人物</th>
							<th>民族</th>
							<th>出场时间</th>
							<th>格言</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>贤心</td>
							<td>汉族</td>
							<td>1989-10-14</td>
							<td>人生似修行</td>
						</tr>
						<tr>
							<td>张爱玲</td>
							<td>汉族</td>
							<td>1920-09-30</td>
							<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
						</tr>
						<tr>
							<td>Helen Keller</td>
							<td>拉丁美裔</td>
							<td>1880-06-27</td>
							<td> Life is either a daring adventure or nothing.</td>
						</tr>
						<tr>
							<td>岳飞</td>
							<td>汉族</td>
							<td>1103-北宋崇宁二年</td>
							<td>教科书再滥改，也抹不去“民族英雄”的事实</td>
						</tr>
						<tr>
							<td>孟子</td>
							<td>华夏族（汉族）</td>
							<td>公元前-372年</td>
							<td>猿强，则国强。国强，则猿更强！ </td>
						</tr>
					</tbody>
				</table>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
					<legend>列边框表格</legend>
				</fieldset>

				<table class="layui-table" lay-even="" lay-skin="row">
					<colgroup>
						<col width="150">
							<col width="150">
								<col width="200">
									<col>
					</colgroup>
					<thead>
						<tr>
							<th>人物</th>
							<th>民族</th>
							<th>出场时间</th>
							<th>格言</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>贤心</td>
							<td>汉族</td>
							<td>1989-10-14</td>
							<td>人生似修行</td>
						</tr>
						<tr>
							<td>张爱玲</td>
							<td>汉族</td>
							<td>1920-09-30</td>
							<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
						</tr>
						<tr>
							<td>Helen Keller</td>
							<td>拉丁美裔</td>
							<td>1880-06-27</td>
							<td> Life is either a daring adventure or nothing.</td>
						</tr>
						<tr>
							<td>岳飞</td>
							<td>汉族</td>
							<td>1103-北宋崇宁二年</td>
							<td>教科书再滥改，也抹不去“民族英雄”的事实</td>
						</tr>
						<tr>
							<td>孟子</td>
							<td>华夏族（汉族）</td>
							<td>公元前-372年</td>
							<td>猿强，则国强。国强，则猿更强！ </td>
						</tr>
					</tbody>
				</table>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
					<legend>无边框表格</legend>
				</fieldset>

				<table class="layui-table" lay-even="" lay-skin="nob">
					<colgroup>
						<col width="150">
							<col width="150">
								<col width="200">
									<col>
					</colgroup>
					<thead>
						<tr>
							<th>人物</th>
							<th>民族</th>
							<th>出场时间</th>
							<th>格言</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>贤心</td>
							<td>汉族</td>
							<td>1989-10-14</td>
							<td>人生似修行</td>
						</tr>
						<tr>
							<td>张爱玲</td>
							<td>汉族</td>
							<td>1920-09-30</td>
							<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
						</tr>
						<tr>
							<td>Helen Keller</td>
							<td>拉丁美裔</td>
							<td>1880-06-27</td>
							<td> Life is either a daring adventure or nothing.</td>
						</tr>
						<tr>
							<td>岳飞</td>
							<td>汉族</td>
							<td>1103-北宋崇宁二年</td>
							<td>教科书再滥改，也抹不去“民族英雄”的事实</td>
						</tr>
						<tr>
							<td>孟子</td>
							<td>华夏族（汉族）</td>
							<td>公元前-372年</td>
							<td>猿强，则国强。国强，则猿更强！ </td>
						</tr>
					</tbody>
				</table>

				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
					<legend>其它尺寸表格</legend>
				</fieldset>

				<table class="layui-table" lay-size="lg">
					<colgroup>
						<col width="150">
							<col width="200">
								<col>
					</colgroup>
					<thead>
						<tr>
							<th>昵称</th>
							<th>加入时间</th>
							<th>签名</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>贤心</td>
							<td>2016-11-29</td>
							<td>人生就像是一场修行</td>
						</tr>
						<tr>
							<td>许闲心</td>
							<td>2016-11-28</td>
							<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
						</tr>
						<tr>
							<td>sentsin</td>
							<td>2016-11-27</td>
							<td> Life is either a daring adventure or nothing.</td>
						</tr>
					</tbody>
				</table>
				<table class="layui-table" lay-size="sm">
					<colgroup>
						<col width="150">
							<col width="200">
								<col>
					</colgroup>
					<thead>
						<tr>
							<th>昵称</th>
							<th>加入时间</th>
							<th>签名</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>贤心</td>
							<td>2016-11-29</td>
							<td>人生就像是一场修行</td>
						</tr>
						<tr>
							<td>许闲心</td>
							<td>2016-11-28</td>
							<td>于千万人之中遇见你所遇见的人，于千万年之中，时间的无涯的荒野里…</td>
						</tr>
						<tr>
							<td>sentsin</td>
							<td>2016-11-27</td>
							<td> Life is either a daring adventure or nothing.</td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="layui-elem-quote" style="margin-top: 20px;">
				<p>精心雕琢</p>
			</div>
		</div>
	</div>

	<script src="../src/layui.js" charset="utf-8"></script>
	<script>
		layui.use('table', function() {
			var table = layui.table;

			//方法级渲染
			window.demoTable = table.render({
				elem: '#demo'
					//,url: 'json/table/demo1.json'
					,
				data: [{
					"id": "10001",
					"username": "杜甫",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "116",
					"ip": "192.168.0.8",
					"logins": "108",
					"joinTime": "2016-10-14"
				}, {
					"id": "10002",
					"username": "李白",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "12",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14",
					"LAY_CHECKED": true
				}, {
					"id": "10003",
					"username": "王勃",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "65",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}, {
					"id": "10004",
					"username": "贤心",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "666",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}, {
					"id": "10005",
					"username": "贤心",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "86",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}, {
					"id": "10006",
					"username": "贤心",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "12",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}, {
					"id": "10007",
					"username": "贤心",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "16",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}, {
					"id": "10008",
					"username": "贤心",
					"email": "xianxin@layui.com",
					"sex": "男",
					"city": "浙江杭州",
					"sign": "人生恰似一场修行",
					"experience": "106",
					"ip": "192.168.0.8",
					"logins": "106",
					"joinTime": "2016-10-14"
				}],
				width: 900
					//,height: 274
					,
				cols: [
					[ //标题栏
						{
							space: true,
							fixed: true
						}, {
							checkbox: true,
							LAY_CHECKED: true
						}, {
							field: 'id',
							title: 'ID',
							width: 80,
							sort: true
						}, {
							field: 'username',
							title: '用户名',
							width: 120
						}, {
							field: 'email',
							title: '邮箱',
							width: 150
						}, {
							field: 'sign',
							title: '签名',
							width: 150
						}, {
							field: 'sex',
							title: '性别',
							width: 80
						}, {
							field: 'city',
							title: '城市',
							width: 100
						}, {
							field: 'experience',
							title: '积分',
							width: 80,
							sort: true
						}
					]
				]

				,
				id: 'test111',
				skin: 'row' //表格风格
					,
				even: true
					//,size: 'lg' //尺寸

				,
				page: true //是否显示分页
					,
				limits: [3, 5, 10],
				limit: 3 //每页默认显示的数量
					//,loading: false //请求数据时，是否显示loading
					//,id: 'test' //ID
			});


			//监听表格复选框选择
			table.on('checkbox(test)', function(obj) {
				console.log(obj)
			});

			//监听单元格编辑
			table.on('edit(test)', function(obj) {
				var value = obj.value //得到修改后的值
					,
					data = obj.data //得到所在行所有键值
					,
					field = obj.field; //得到字段

			});

			//监听工具条
			table.on('tool(test)', function(obj) {
				var data = obj.data;
				if (obj.event === 'del') {
					layer.confirm('真的删除行么', function(index) {
						obj.del();
						layer.close(index);
					});
				} else if (obj.event === 'edit') {
					layer.prompt({
						formType: 2,
						value: data.username
					}, function(value, index) {
						obj.update({
							username: value
						});
						layer.close(index);
					});
				}
			});

			//监听排序
			table.on('sort(test)', function(obj) {
				console.log(this, obj.field, obj.type)

				return;
				table.reload('test', {
					initSort: obj,
					where: { //重新请求服务端
						key: obj.field //排序字段
							,
						order: obj.type //排序方式
					}
					//,height: 300
				});
			});


			var $ = layui.jquery,
				active = {
					getCheckData: function() {
						var checkStatus = table.checkStatus('test'),
							data = checkStatus.data;
						layer.alert(JSON.stringify(data));
					},
					getCheckLength: function() {
						var checkStatus = table.checkStatus('test'),
							data = checkStatus.data;
						layer.msg('选中了：' + data.length + ' 个');
					},
					isAll: function() {
						var checkStatus = table.checkStatus('test');
						layer.msg(checkStatus.isAll ? '全选' : '未全选')
					},
					parseTable: function() {
						table.init('parse-table-demo');
					}
				};

			$('.layui-btn').on('click', function() {
				var type = $(this).data('type');
				active[type] ? active[type].call(this) : '';
			});
		});

	</script>
</body>

</html>
