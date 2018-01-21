var areaData = address;
var $form;
var form;
var $;
layui.config({
	base : "../../js/"
}).use(['form','layer','upload','laydate'],function(){
	form = layui.form;
	var layer = parent.layer === undefined ? layui.layer : parent.layer;
		$ = layui.jquery;
		$form = $('form');
		laydate = layui.laydate;
        loadProvince();
//        layui.upload({
//        	url : "../../json/userface.json",
//        	success: function(res){
//        		var num = parseInt(4*Math.random());  //生成0-4的随机数
//        		//随机显示一个头像信息
//		    	userFace.src = res.data[num].src;
//		    	window.sessionStorage.setItem('userFace',res.data[num].src);
//		    }
//        });

        //添加验证规则
        form.verify({
            oldPwd : function(value, item){
            	var description = null;
            	$.ajax({ 
                    type : "get", 
                    url : "/user/checkPassword", 
                    data : "oldPassword=" + value, 
                    async : false, 
                    success : function(data){ 
                       if('success'!= data){
                    	   description = "旧密码输入不正确！";
                       }
                    } 
                });
            	return description;
            },
            newPwd : function(value, item){
                if(value.length < 6){
                    return "密码长度不能小于6位！";
                }
            },
            confirmPwd : function(value, item){
                if(!new RegExp($("#oldPwd").val()).test(value)){
                    return "两次输入密码不一致，请重新输入！";
                }
            }
        })

        //判断是否修改过头像，如果修改过则显示修改后的头像，否则显示默认头像
        if(window.sessionStorage.getItem('userFace')){
        	$("#userFace").attr("src",window.sessionStorage.getItem('userFace'));
        }else{
        	$("#userFace").attr("src","http://c.hiphotos.baidu.com/image/pic/item/267f9e2f07082838adcbb409b299a9014d08f1ed.jpg");
        }

        //提交个人资料
        form.on("submit(changeUser)",function(data){
        	var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
            	$.post('/user/saveUpdateUser',data.field,function(result){
         	    	if('success' == result){
         	    		 layer.alert('个人资料更新成功！', {icon: 1}, function(index){
         	    			 layer.close(index);
         	    		 });
         	    		 return true;
         	    	}else{
         	    		layer.alert('个人资料更新失败', {icon: 2});
         	    		return false;
         	    	}
         	    })
            },500);
        	return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        })

        
        //修改密码
        form.on("submit(changePwd)",function(data){
        	var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        	debugger
            setTimeout(function(){
            	 $.post('/user/saveUpdateUser',data.field,function(result){
         	    	if('success' == result){
         	    		 layer.alert('密码修改成功,请重新登录！', {icon: 1}, function(index){
         	    			 parent.location.href = "/logout";
         	    			 layer.close(index);
         	    			 $(".pwd").val('');
         	    		 });
         	    		 return true;
         	    	}else{
         	    		layer.alert('角色更新失败', {icon: 2});
         	    		return false;
         	    	}
         	    })
            },500);
        	return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        })
})

 //加载省数据
function loadProvince() {
    var proHtml = '';
    
    for (var i = 0; i < areaData.length; i++) {
        proHtml += '<option value="' + areaData[i].provinceName + '_' + areaData[i].mallCityList.length + '_' + i + '">' + areaData[i].provinceName + '</option>';
    }
    //初始化省数据
    $form.find('select[name=province]').append(proHtml);
    form.render();
    form.on('select(province)', function(data) {
        $form.find('select[name=area]').html('<option value="">请选择县/区</option>');
        var value = data.value;
        var d = value.split('_');
        var code = d[0];
        var count = d[1];
        var index = d[2];
        if (count > 0) {
            loadCity(areaData[index].mallCityList);
        } else {
            $form.find('select[name=city]').attr("disabled","disabled");
        }
    });
}
 //加载市数据
function loadCity(citys) {
    var cityHtml = '<option value="">请选择市</option>';
    for (var i = 0; i < citys.length; i++) {
        cityHtml += '<option value="' + citys[i].cityName + '_' + citys[i].mallAreaList.length + '_' + i + '">' + citys[i].cityName + '</option>';
    }
    $form.find('select[name=city]').html(cityHtml).removeAttr("disabled");
    form.render();
    form.on('select(city)', function(data) {
        var value = data.value;
        var d = value.split('_');
        var code = d[0];
        var count = d[1];
        var index = d[2];
        if (count > 0) {
            loadArea(citys[index].mallAreaList);
        } else {
            $form.find('select[name=area]').attr("disabled","disabled");
        }
    });
}
 //加载县/区数据
function loadArea(areas) {
    var areaHtml = '<option value="">请选择县/区</option>';
    for (var i = 0; i < areas.length; i++) {
        areaHtml += '<option value="' + areas[i].areaName + '">' + areas[i].areaName + '</option>';
    }
    $form.find('select[name=area]').html(areaHtml).removeAttr("disabled");
    form.render();
    form.on('select(area)', function(data) {
        //console.log(data);
    });
}