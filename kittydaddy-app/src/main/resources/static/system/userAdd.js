layui.config({
	base : "/static/js/system"
}).use(['form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		$ = layui.jquery;
	
	
	//监听提交
    form.on('submit(addUser)', function(data){
	    $.post('/user/saveUpdateUser',data.field,function(result){
	    	if('success' == result){
	    		 layer.alert('新增用户成功', {icon: 1}, function(index){
	    			 parent.location.reload(); //刷新父页面
	    		     layer.close(index);
	    		 });
	    		 return true;
	    	}else{
	    		layer.alert('新增用户失败', {icon: 2});
	    	}
	    })
	    return false;
	});
    
    $.post('/tenant/queryTenantList',{pageIndex:0,pageSize:0},function(data){
    	$.each(data.list,function(index,item){
    		$('#tenantNames').append("<option value='"+item.id+"'>"+item.name+"</option>");
    	})
    	//重新进行渲染
    	form.render();
    })
    
    
    //返回父类
    $("body").on("click","#back",function(){ 
    	parent.location.reload(); //刷新父页面
    	var index = parent.layer.getFrameIndex(window.name); 
        parent.layer.close(index);
	})
})
