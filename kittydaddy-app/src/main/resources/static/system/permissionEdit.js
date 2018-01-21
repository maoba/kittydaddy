layui.config({
	base : "/static/js/system"
}).use(['form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		$ = layui.jquery;
	
	
	//监听提交
    form.on('submit(editPermission)', function(data){
    	debugger
    	alert(JSON.stringify(data));
	    $.post('/permission/saveUpdatePermission',data.field,function(result){
	    	if('success' == result){
	    		 layer.alert('权限更新成功', {icon: 1}, function(index){
	    			 parent.location.reload(); //刷新父页面
	    		     layer.close(index);
	    		 });
	    		 return true;
	    	}else{
	    		layer.alert('权限更新失败', {icon: 2});
	    	}
	    })
	    return false;
	});
    

	$.ajax({ 
	        type : "get", 
	        url : "/tenant/queryTenantList?pageIndex=0&pageSize=0", 
	        async : false, 
	        success : function(data){ 
	        	$.each(data.data,function(index,item){
	        		$('#tenantNames').append("<option value='"+item.id+"'>"+item.name+"</option>");
	       	    })
	       	  //重新进行渲染
			   	form.render();
	        }
		 })
	
	$.ajax({ 
	        type : "get", 
	        url : "/permission/permissionList?pageIndex=0&pageSize=0&permissionType=0", 
	        async : false, 
	        success : function(data){ 
	        	$.each(data.data,function(index,item){
	       		   $('#parentIds').append("<option value='"+item.id+"'>"+item.moduleName+"</option>");
	       	    })
	       	  //重新进行渲染
			   	form.render();
	         }
		 })
    
    //返回父类
    $("body").on("click","#back",function(){ 
    	var index = parent.layer.getFrameIndex(window.name); 
        parent.layer.close(index);
	})
})
