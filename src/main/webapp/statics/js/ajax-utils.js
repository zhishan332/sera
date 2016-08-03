
function AjaxUtil(){}
AjaxUtil.prototype = {};
//上下文路径
//var waitimg =$("#contextPath").val() +"/statics/images/onLoad.gif";

/**
 * Ajax[POST] 异步数据请求
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
AjaxUtil.post = function(url,param,successFunction){
    AjaxUtil.ajax("POST", url, param, successFunction);
};

/**
 * Ajax[GET] 异步数据请求
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
AjaxUtil.get = function(url,param,successFunction){
    AjaxUtil.ajax("GET", url, param, successFunction);
};

AjaxUtil.getPri = function(url,param,successFunction){
    AjaxUtil.ajaxPre("GET", url, param, successFunction);
};

/**
 * Ajax 异步数据请求
 * @param type	[String] e.g.:"POST" or "GET"
 * @param url	[String] 请求url
 * @param param	[Object] 参数
 * @param successFunction [Function] 成功回调函数
 */
AjaxUtil.ajax = function(type,url,param,successFunction){
    if(param == null){
        param = {};
    }
    var impIndex=null;
    jQuery.ajax({
        type: type,
        url:  url,
        timeout:20000,
        data: param,
        cache: false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        beforeSend: function(jqXHR, settings){
            impIndex = layer.msg('请稍候', {
                icon: 16,
                time: 20000000 //2秒关闭（如果不配置，默认是3秒）
            });
        },
        success: successFunction,
        error: function(jqXHR, textStatus, errorThrown){
            if(null!=impIndex){
                layer.close(impIndex);
            }
            if(jqXHR.status > 0){
                layer.alert("Error:status["+jqXHR.status+"],statusText["+ jqXHR.statusText +"]", {icon: 5});
            }
        },
        complete: function(jqXHR, textStatus){
            if(null!=impIndex){
                layer.close(impIndex);
            }
            if(textStatus=='timeout'){
                layer.alert("请求超时，请重试", {icon: 5});
            }
        }
    });
};

AjaxUtil.ajaxPre = function(type,url,param,successFunction){
    if(param == null){
        param = {};
    }
    jQuery.ajax({
        type: type,
        url:  url,
        timeout:20000,
        data: param,
        cache: false,
        async: false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        beforeSend: function(jqXHR, settings){

        },
        success: successFunction,
        error: function(jqXHR, textStatus, errorThrown){
            if(jqXHR.status > 0){
                layer.alert("Error:status["+jqXHR.status+"],statusText["+ jqXHR.statusText +"]", {icon: 5});
            }
        },
        complete: function(jqXHR, textStatus){
            if(textStatus=='timeout'){
                layer.alert("请求超时，请重试", {icon: 5});
            }
        }
    });
};