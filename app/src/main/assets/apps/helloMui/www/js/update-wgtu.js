/**
 * 参考文档：http://ask.dcloud.net.cn/article/431
 * 升级文件为JSON格式数据，如下：
 * 
 * 需升级
 {
	"status":1,
	"version": "2.6.0",
	"title": "Hello MUI版本更新",
	"note": "修复“选项卡+下拉刷新”示例中，某个选项卡滚动到底时，会触发所有选项卡上拉加载事件的bug；\n修复Android4.4.4版本部分手机上，软键盘弹出时影响图片轮播组件，导致自动轮播停止的bug；",
	"url": "http://www.dcloud.io/hellomui/HelloMUI.apk"
}
*
* 无需升级
{"status":0}
 */
/*var server = "http://www.dcloud.io/check/update"; //获取升级描述文件服务器地址
function update() {
	mui.getJSON(server, {
		"appid": plus.runtime.appid,
		"version": plus.runtime.version,
		"imei": plus.device.imei
	}, function(data) {
		if (data.status) {
			plus.nativeUI.confirm(data.note, function(event) {
				if (0 == event.index) {
					plus.runtime.openURL(data.url);
				}
			}, data.title, ["立即更新", "取　　消"]);
		}
	});

}*/
//mui.os.plus && !mui.os.stream && mui.plusReady(checkUpdate);
 var wgtVer=null;
 function plusReady(){
 	// Android处理返回键
 	plus.key.addEventListener('backbutton',function(){
 		if(confirm('确认退出？')){
 			plus.runtime.quit();
 		}
 	},false);
 	// 获取本地应用资源版本号
 	plus.runtime.getProperty(plus.runtime.appid,function(inf){
 		wgtVer=inf.version;
 		console.log("当前应用版本："+wgtVer);
 	});
 	checkUpdate();//检查更新
 }
 if(window.plus){
 	plusReady();
 }else{
 	document.addEventListener('plusready',plusReady,false);
 }

var checkUrl="http://demo.dcloud.net.cn/test/update/check.php";
function checkUpdate(){
	plus.nativeUI.showWaiting("检测更新...");
	var xhr=new XMLHttpRequest();
	xhr.onreadystatechange=function(){
		switch(xhr.readyState){
			case 4:
			plus.nativeUI.closeWaiting();
			if(xhr.status==200){
				console.log("检测更新成功："+xhr.responseText);
				var newVer=xhr.responseText;
				if(wgtVer&&newVer&&(wgtVer!=newVer)){
					downWgt();	// 下载升级包
				}else{
					plus.nativeUI.alert("无新版本可更新！");
				}
			}else{
				console.log("检测更新失败！");
				plus.nativeUI.alert("检测更新失败！");
			}
			break;
			default:
			break;
		}
	}
	xhr.open('GET',checkUrl);
	xhr.send();
}
// 下载wgtu文件
var url="http://172.18.3.32:8080/helloMui.wgtu";
function downWgt(){
	plus.nativeUI.showWaiting("下载wgtu文件...");
	plus.downloader.createDownload( url,  {method:"GET"}, function(d,status){
		if ( status == 200 ) { 
			console.log("下载wgtu成功："+d.filename);
			installWgtu(d.filename);	// 安装wgtu包
		} else {
			console.log("下载wgtu失败！");
			plus.nativeUI.alert("下载wgtu失败！");
		}
		plus.nativeUI.closeWaiting();
	}).start();
}
// 更新应用资源
function installWgtu(path){
	plus.nativeUI.showWaiting("安装wgtu文件...");
	plus.runtime.install(path,{},function(){
		plus.nativeUI.closeWaiting();
		console.log("安装wgtu文件成功！");
		plus.nativeUI.alert("应用资源更新完成！",function(){
			plus.runtime.restart();
		});
	},function(e){
		plus.nativeUI.closeWaiting();
		console.log("安装wgtu文件失败["+e.code+"]："+e.message);
		plus.nativeUI.alert("安装wgtu文件失败["+e.code+"]："+e.message);
	});
}
