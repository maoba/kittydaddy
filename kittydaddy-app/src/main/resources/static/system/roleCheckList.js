layui.config({
	base : "/static/js/system"
}).use(['form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		$ = layui.jquery;
	
	
    $.post('/role/roleList',{pageIndex:0,pageSize:0},function(data){
    	$.each(data.list,function(index,item){
    		$('#roleId').append(" <input type='checkbox' name='roleId["+item.id+"]' value="+item.id+" title="+item.roleName+">");
    	})
    	//重新进行渲染
    	form.render();
    })
})
