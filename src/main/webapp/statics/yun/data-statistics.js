!function(a,b,c,d,e,f,g){a.GoogleAnalyticsObject=e,a[e]=a[e]||function(){(a[e].q=a[e].q||[]).push(arguments)},a[e].l=1*new Date,f=b.createElement(c),g=b.getElementsByTagName(c)[0],f.async=1,f.src=d,g.parentNode.insertBefore(f,g)}(window,document,"script","//www.google-analytics.com/analytics.js","ga"),ga("create","UA-40435133-1","aliyun.com"),ga("send","pageview");var addStaticInfo=function(){var img=document.createElement("img");img.height=0,img.width=0,img.style.left="-9999px",img.style.borderStyle="none",img.style.position="absolute",img.src="//googleads.g.doubleclick.net/pagead/viewthroughconversion/977293844/?value=0&amp;label=BPPnCOT7oQUQlKSB0gM&amp;guid=ON&amp;script=0",document.body.appendChild(img),function(){function doMonitorForDataGa(target){var target=target,_ga=target.getAttribute("data-ga");if("undefined"!=typeof ga&&""!=_ga)if(_ga.indexOf("_gaq.push")>-1)eval(_ga);else{var _gaArr=_ga.split(".");if(_gaArr.length<2)return;var category="Click",action="",label="";2==_gaArr.length?(category="Click",action=_gaArr[0],label=_gaArr[1]):(category=_gaArr.shift(),action=_gaArr.shift(),label=_gaArr.join(".")),ga("send","event",category,action,label)}}function addEvent(a,b,c){a.attachEvent?(a["e"+b+c]=c,a[b+c]=function(){a["e"+b+c](window.event)},a.attachEvent("on"+b,a[b+c])):a.addEventListener(b,c,!1)}var _elemArr=document.getElementsByTagName("A"),_elemLen=_elemArr.length,_body=document.body||document.documentElement;addEvent(_body,"click",function(a){var a=a?a:window.event,b=a.srcElement?a.srcElement:a.target;b.getAttribute("data-ga")?doMonitorForDataGa(b):"IMG"==b.nodeName&&b.parentNode.getAttribute("data-ga")&&doMonitorForDataGa(b.parentNode)})}(),function(){var a=function(a,b,c){a.attachEvent?(a["e"+b+c]=c,a[b+c]=function(){a["e"+b+c](window.event)},a.attachEvent("on"+b,a[b+c])):a.addEventListener(b,c,!1)};a(document.body,"click",function(a){a=a||window.event;var b=a.target||a.srcElement,c=b.getAttribute("data-lingjian");if(b.parentNode&&(c=c||b.parentNode.getAttribute("data-lingjian")),c){var d=window.lingjianImg=new Image;d.src="http://gm.mmstat.com/"+c+"?cache="+(new Date).getTime()}})}()},checkBodyReady=function(){setTimeout(function(){null!=document.body?addStaticInfo():checkBodyReady()},1e3)};checkBodyReady();