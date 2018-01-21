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
	  roleSearch('',curnum,limitcount);
	  
	  function roleSearch(name,pageIndex,pageSize){
		  //第一个实例
		  table.render({
			 id:'roleId',
		     elem: '#roleList',
		     height:'full-153',
		     url: '/role/roleList?name='+name+'&pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
		     page: false, //开启分页
		     cols: [[ //表头
		      {type:'checkbox'},      
		      {field: 'roleName', title: '角色名称', width:300},
		      {field: 'roleCode', title: '角色编码', width:350},
		      {field: 'description', title: '描述信息', width:320, sort: true},
		      {field: 'createTime', title: '创建时间', width: 330, 
		    	    templet:function(d){
		    	    	if(d.createTime == null){
		    	    		return "";
		    	    	}
		    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
		    	    }  
		      },
		      {fixed: 'right', title: '操作', width: 350, align:'center', toolbar: '#roleBar'}
		    ]],
		     done: function(res, curr, count){
		    	laypage.render({ 
	    		     elem:'laypage',
	    		     count:count, 
                   curr:pageIndex, 
                   limit:res.data.length, 
                   layout: ['prev', 'page', 'next', 'skip','count','limit'],  
				    	jump:function (obj,first) {  
		                    if(!first){  
		                        curnum = obj.curr;  
		                        limitcount = obj.limit;  
		                        roleSearch(name,curnum,limitcount);  
		                    }  
		                }  
                   
		    	})
		      }
		  });
		  
		  table.on('tool(roleList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			    var data = obj.data,//获得当前行数据
			    layEvent = obj.event; //获得 lay-event 对应的值
			    if(layEvent === 'del'){
			      layer.confirm('确定删除该信息？', function(index){
			        obj.del(); //删除对应行（tr）的DOM结构
			        layer.close(index);
			        $.get('/role/deleteRole?roleId='+data.id,function(result){
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
						content :  '/role/roleEdit?roleId='+data.id
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
        	roleSearch(name,curnum,limitcount);  
            layer.close(index);
		}else{
			location.reload();
			layer.msg("请输入需要查询的内容");
		}
	})

	//添加角色
	$(".roleAdd_btn").click(function(){
		var _this = $(this)
		var index =  layui.layer.open({
			title:false,
			closeBtn : 0,
			area: ['100%', '100%'],
			type : 2,
			content : '/role/roleAdd'
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})

	//分配权限
	$(".grant_permission").click(function(){
		var checkData = table.checkStatus('roleId'); //test即为基础参数id对应的值
		if(checkData.data !=null && checkData.data.length==1){
			var roleId = checkData.data[0].id;
			debugger
			var index =  layui.layer.open({
				title:"分配权限",
				closeBtn : 0,
				fixed: true,
				shadeClose: true,  
			    move: false, 
				scrollbar: false,
				area: ['300px', '500px'],
				type : 2,
				content : ['/permission/permissionTree?roleId=' + roleId,'no'],
				btn: ['确认分配', '返回'],
				yes: function(index, layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
			        var data = iframeWin.onCheck();
			        if(data){
			        	layer.alert('权限分配成功', {icon: 1});
			        	form.render();
			            location.reload();
			        }else{
			        	layer.alert('分配失败，请选择权限！', {icon: 2});
			        }
				}
			})
		}else{
			layer.msg("请选择唯一角色！");
		}
	})

	//批量删除
	$(".batchDel").click(function(){
		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
			var checkData = table.checkStatus('roleId'); //test即为基础参数id对应的值
			
			if(checkData.data !=null && checkData.data.length>0){
				var indexSuccess = 0;
				var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
				$.each(checkData.data,function(index,item){
					$.ajax({ 
				        type : "get", 
				        url : "/role/deleteRole?roleId="+item.id, 
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
