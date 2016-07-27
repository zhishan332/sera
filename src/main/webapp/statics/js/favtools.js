var fav_host = 'http://localhost:8080';

function impDoc(argument) //函数可以单独引入一个js或者css
{

    var file = argument;
    if (file.match(/.*\.js$/)) //以任意开头但是以.js结尾正则表达式
    {
        document.write('<script type="text/javascript" src="' + file + '"></script>');
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.href = file;
        document.getElementsByTagName("head")[0].appendChild(script);
    }
    else if (file.match(/.*\.css$/)) {
        var link = document.createElement("link");
        link.rel = "stylesheet";
        link.type = "text/css";
        link.href = file;
        document.getElementsByTagName("head")[0].appendChild(link);
    }

}
impDoc('http://apps.bdimg.com/libs/jquery/2.1.4/jquery.js');

var curUrl = window.location.href;


$.ajax({
    type: "get",
    async: false,
    url:fav_host + '/open/fav/add?u='+curUrl,
    // url:fav_host + '/open/get',
    dataType: "jsonp",
    jsonp: "callback", //服务端用于接收callback调用的function名的参数
    jsonpCallback: "success_jsonpCallback", //callback的function名称,服务端会把名称和data一起传递回来
    success: function(resp) {
        if (resp == undefined || resp == null) {
            alert("系统异常");
        }else if (resp.status == 1) {
            alert('保存成功');
        } else if (resp.status == 0) {
            alert(resp.msg);
        } else {
            var obj = eval("(" + resp + ")");
            alert(obj.msg);
        }
    }
});


// ajax({
//     url: fav_host + '/fav/add?u='+curUrl+'&callback=foo',              //请求地址
//     type: "GET",                       //请求方式
//     data: {},        //请求参数
//     dataType:'jsonp',
//     success: function (resp, xml) {
//         layer.msg("已收藏");
//     },
//     fail: function (resp, status) {
//         if (resp == undefined || resp == null) {
//             layer.alert("系统异常", {icon: 5});
//         } else if (resp.status == 0) {
//             layer.msg(resp.msg);
//         } else {
//             var obj = eval("(" + resp + ")");
//             layer.alert(obj.msg, {icon: 5});
//         }
//     }
// });



