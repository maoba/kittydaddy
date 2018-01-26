layui.config({
	base : "/static/js/system"
}).use(['table','form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		table = layui.table,
		$ = layui.jquery;
	
	 var curnum = 1,limitcount=20;
	  //初始化搜索
	  permissionSearch('',curnum,limitcount);
	  
	  function permissionSearch(name,pageIndex,pageSize){
		  //第一个实例
		  table.render({
			 id:'permissionId',
		     elem: '#permissionList',
		     height:'full-153',
		     url: '/permission/permissionList?name='+name+'&pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
		     page: false, //开启分页
		     cols: [[ //表头
		      {type:'checkbox'},      
		      {field: 'moduleName', title: '权限名称', width:210},
		      {field: 'permissionCode', title: '权限编码', width:230},
		      {field: 'permissionUrl', title: '请求地址', width:240, sort: true},
		      {field: 'permissionType', title: '权限类型', width:240,templet:function(d){
		    	  debugger
		    	  if(d.permissionType == 1){
		    		  return "资源";
		    	  }else if(d.permissionType == 0){
		    		  return "目录";
		    	  }else{
		    		  return "";
		    	  }
		      }}, 
		      {field: 'parentName', title: '父名称', width: 240},
		      {field: 'createTime', title: '创建时间', width: 240, 
		    	    templet:function(d){
		    	    	if(d.createTime == null){
		    	    		return "";
		    	    	}
		    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
		    	    }  
		      },
		      {fixed: 'right', title: '操作', width: 240, align:'center', toolbar: '#permissionBar'}
		    ]],
		     done: function(res, curr, count){
		    	laypage.render({ 
	    		     elem:'laypage',
	    		     count:count, 
                     curr:pageIndex, 
                     limit:limitcount, 
                     layout: ['prev', 'page', 'next', 'skip','count','limit'],  
				    	jump:function (obj,first) {  
		                    if(!first){  
		                        curnum = obj.curr;  
		                        limitcount = obj.limit;  
		                        permissionSearch(name,curnum,limitcount);  
		                    }  
		                }  
                     
		    	})
		      }
		  });
		  
		  table.on('tool(permissionList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			    var data = obj.data,//获得当前行数据
			    layEvent = obj.event; //获得 lay-event 对应的值
			    if(layEvent === 'del'){
			      layer.confirm('确定删除该信息？', function(index){
			        obj.del(); //删除对应行（tr）的DOM结构
			        layer.close(index);
			        $.get('/permission/deletePermission?permissionId='+data.id,function(result){
						if('success' == result){
							layer.msg("删除成功");
						}else{
							layer.msg("删除失败");
						}
           		   })
			      });
			    }else if(layEvent === 'edit'){
			    	var _this = $(this)
					var index =  layui.layer.open({
						title:false,
						closeBtn : 0,
						area: ['100%', '100%'],
						type : 2,
						content : '/permission/permissionEdit?permissionId='+data.id
					})
					
					//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
					$(window).resize(function(){
						layui.layer.full(index);
					})
					 layui.layer.full(index);
			    }
			    
		  });
	  }

	//清空查询
    $(".clear_btn").click(function(){
    	$(".search_input").val('');
    })
	
    //查询
	$(".search_btn").click(function(){
		var newArray = [];
		if($(".search_input").val() != ''){
			var index = layer.msg('查询中，请稍候',{icon: 16,time:false,shade:0.8});
        	var name = $(".search_input").val()
        	permissionSearch(name,curnum,limitcount);  
            layer.close(index);
		}else{
			location.reload();
			layer.msg("请输入需要查询的内容");
		}
	})

	//添加权限
	$(".permissionAdd_btn").click(function(){
		var _this = $(this)
		var index =  layui.layer.open({
			title:false,
			closeBtn : 0,
			area: ['100%', '100%'],
			type : 2,
			content : '/permission/permissionAdd'
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})


	//批量删除
	$(".batchDel").click(function(){
		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
			var checkData = table.checkStatus('permissionId'); //test即为基础参数id对应的值
			var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
			if(checkData.data !=null && checkData.data.length>0){
				var indexSuccess = 0;
				$.each(checkData.data,function(index,item){
					$.ajax({ 
				        type : "get", 
				        url : "/permission/deletePermission?permissionId="+item.id, 
				        async : false, 
				        success : function(data){ 
				        	indexSuccess ++
				        }
					})
				})
				if(indexSuccess == checkData.data.length){
					location.reload();
	                layer.close(index);
					layer.msg("删除成功");
				}else{
				    layer.msg("删除失败")	
				}
			}
		})
	})
})
