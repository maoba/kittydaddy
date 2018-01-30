layui.config({
	base : "/static/js/system"
}).use(['form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		$ = layui.jquery;

        //手动新增源
		$(".add_source_btn").click(function(){
			var index = layer.open({
				title:false,
				closeBtn : 0,
				area: ['50%', '52%'],
				type : 2,
				content : '/kvcontentItemSource/addkvcontentSource?relativeId='+$('#contentId').val()+'&shortFlag='+$('#shortFlag').val()
			})
		});
		
		
		 //监听提交
	    form.on('submit(addPlaySource)', function(data){
		    $.post('/kvcontentItemSource/saveUpdateKVContentItemSource',data.field,function(result){
		    	if('success' == result){
		    		 layer.alert('新增播放源成功', {icon: 1}, function(index){
		    			 parent.location.reload(); //刷新父页面
		    			 layer.close(index);
		    		 });
		    		 return true;
		    	}else{
		    		layer.alert('新增播放源失败', {icon: 2});
		    	}
		    })
		    return false;
		});
	    
	  //返回父类
	    $("body").on("click",".back",function(){ 
	    	var index = parent.layer.getFrameIndex(window.name); 
	        parent.layer.close(index);
		})
		
		
		//手动新增源
		$(".addExposideOrPlayUrl").click(function(){
			var shortFlag = $('#shortFlag').val();
			if(shortFlag == 1){//短视频的时候，新增播放地址
				var index = layer.open({
					title:false,
					closeBtn : 0,
					area: ['50%', '52%'],
					type : 2,
					content : '/kvcontentItemSource/addkvcontentSource?relativeId='+$('#contentId').val()+'&shortFlag='+$('#shortFlag').val()
				})
				
			}else{//长视频的时候，新增剧集
				
			}
		});
})		