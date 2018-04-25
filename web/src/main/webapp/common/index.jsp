<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 瀵洖鍙� echarts.js -->
    <script type="text/javascript"  language="javascript"  src="${path}/resource/echarts/echarts.min.js"></script>
    <script type="text/javascript"  language="javascript"  src="${path}/resource/form/src/jquery.min.js"></script>
</head>
<body>
<!-- 娑撶瘝Charts閸戝棗顦稉锟芥稉顏勫徔婢跺洤銇囩亸蹇ョ礄鐎逛粙鐝敍澶屾畱Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    var path = "${path}";
    $.ajax({
        // url:path + "/app/echarts/collectTotalPAt",
        // url:path + "/app/echarts/collectByMonth",
        url: path + "/app/echarts/userCost?userId=228d0298-11a1-4f3b-8718-38c9e57913ce&endDate=2017-10-27&divId=energyIndex",
        type:"post",
        dataType:"json",
        success:function(result) {
// 閸╄桨绨崙鍡楊槵婵傜晫娈慸om閿涘苯鍨垫慨瀣echarts鐎圭偘绶�
            var myChart = echarts.init(document.getElementById('main'));
            console.log(result)
            // 閹稿洤鐣鹃崶鎹愩�冮惃鍕帳缂冾噣銆嶉崪灞炬殶閹癸拷
            var option = result.content.option;
            var tt = option.toString();
//            var option = {
//                title : {
//                    text: '閺屾劗鐝悙鍦暏閹寸柉顔栭梻顔芥降濠э拷',
//                    subtext: '缁绢垰鐫橀搹姘��1111',
//                    x:'center'
//                },
//                tooltip : {
//                    trigger: 'item',
//                    formatter: "{a} <br/>{b} : {c} ({d}%)"
//                },
//                legend: {
//                    orient: 'vertical',
//                    left: 'left',
//                    data: ['閻╁瓨甯寸拋鍧楁６','闁喕娆㈤拃銉╂敘','閼辨梻娲橀獮鍨啞','鐟欏棝顣堕獮鍨啞','閹兼粎鍌ㄥ鏇熸惛']
//                },
//                series : [
//                    {
//                        name: '鐠佸潡妫堕弶銉︾爱',
//                        type: 'pie',
//                        radius : '55%',
//                        center: ['50%', '60%'],
//                        data:[
//                            {value:335, name:'閻╁瓨甯寸拋鍧楁６'},
//                            {value:310, name:'闁喕娆㈤拃銉╂敘'},
//                            {value:234, name:'閼辨梻娲橀獮鍨啞'},
//                            {value:135, name:'鐟欏棝顣堕獮鍨啞'},
//                            {value:1548, name:'閹兼粎鍌ㄥ鏇熸惛'}
//                        ],
//                        itemStyle: {
//                            emphasis: {
//                                shadowBlur: 10,
//                                shadowOffsetX: 0,
//                                shadowColor: 'rgba(0, 0, 0, 0.5)'
//                            }
//                        }
//                    }
//                ]
//            };

            // 娴ｈ法鏁ら崚姘瘹鐎规氨娈戦柊宥囩枂妞ょ懓鎷伴弫鐗堝祦閺勫墽銇氶崶鎹愩�冮妴锟�
            myChart.setOption(option);
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });

</script>
</body>
</html>
