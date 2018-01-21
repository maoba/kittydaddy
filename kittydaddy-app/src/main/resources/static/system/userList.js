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
	  kvUserSearch('',curnum,limitcount);
	  
	  function kvUserSearch(name,pageIndex,pageSize){
		  //第一个实例
		  table.render({
			 id: 'userId',
		     elem: '#userList',
		     height:'full-153',
		     url: '/user/userList?name='+name+'&pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
		     page: false, //开启分页
		     cols: [[ //表头
		      {type:'checkbox'},      
		      {field: 'userName', title: '用户名称', width:200},
		      {field: 'tenantName', title: '从属租户', width:200},
		      {field: 'cellPhoneNum', title: '手机号码', width:200, sort: true},
		      {field: 'email', title: '邮箱地址', width:210}, 
		      {field: 'status', title: '状态', width: 210,
		    	  templet:function(d){
		    	    	if(d.status==0){
		    	    		return "失效"
		    	    	}else if(d.status==1){
		    	    		return "正常"
		    	    	}
		    	    }      
		      },
		      {field: 'sex', title: '性别', width: 200,
		    	  templet:function(d){
		    	    	if(d.sex==0){
		    	    		return "男"
		    	    	}else if(d.sex==1){
		    	    		return "女"
		    	    	}else{
		    	    		return "未知"
		    	    	}
		    	    }    
		      },
		      {field: 'createTime', title: '创建时间', width: 240, 
		    	    templet:function(d){
		    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
		    	    }  
		      },
		      {fixed: 'right', title: '操作', width: 200, align:'center', toolbar: '#kvuserBar'}
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
		                        kvContentSearch(name,curnum,limitcount);  
		                    }  
		                }  
                      
		    	})
		      }
		  });
		  
		  table.on('tool(userList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
			    var data = obj.data,//获得当前行数据
			    layEvent = obj.event; //获得 lay-event 对应的值
			    if(layEvent === 'del'){
			      layer.confirm('确定删除该信息？', function(index){
			        obj.del(); //删除对应行（tr）的DOM结构
			        layer.close(index);
			        $.get('/user/deleteUser?userId='+data.id,function(result){
						if('success' == result){
							layer.msg("删除成功");
						}else{
							layer.msg("删除失败");
						}
            		})
			      });
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
        	kvUserSearch(name,curnum,limitcount);  
            layer.close(index);
		}else{
			location.reload();
			layer.msg("请输入需要查询的内容");
		}
	})

	//添加角色
	$(".userAdd_btn").click(function(){
		var _this = $(this)
		var index =  layui.layer.open({
			title:false,
			closeBtn : 0,
			area: ['100%', '100%'],
			type : 2,
			content : '/user/userAdd'
		})
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		layui.layer.full(index);
	})

	//分配角色
	$(".grant_role").click(function(){
		var checkData = table.checkStatus('userId'); //test即为基础参数id对应的值
		if(checkData.data !=null && checkData.data.length==1){
			var userId = checkData.data[0].id;
			debugger
			var index =  layui.layer.open({
				title:"分配角色",
				closeBtn : 0,
				fixed: true,
				shadeClose: true,  
			    move: false, 
				scrollbar: false,
				area: ['900px', '150px'],
				type : 2,
				content : ['/user/roleCheckList?checkUserId=' + userId,'no'],
				btn: ['确认分配', '返回'],
				yes: function(index, layero){
					var iframeWin = window[layero.find('iframe')[0]['name']];
			        var data = iframeWin.submitForm();
			        if(data){
			        	layer.alert('角色分配成功', {icon: 1});
			        	form.render();
			            location.reload();
			        }else{
			        	layer.alert('分配失败，请选择角色！', {icon: 2});
			        }
				}
			})
		}else{
			layer.msg("请选择唯一分配的角色");
		}
	})
	//批量删除
	$(".batchDel").click(function(){
		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
			var checkData = table.checkStatus('userId'); //test即为基础参数id对应的值
			
			if(checkData.data !=null && checkData.data.length>0){
				var indexSuccess = 0;
				var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
				$.each(checkData.data,function(index,item){
					$.ajax({ 
				        type : "get", 
				        url : "/user/deleteUser?userId="+item.id, 
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
