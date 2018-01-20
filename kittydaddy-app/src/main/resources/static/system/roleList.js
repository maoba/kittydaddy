layui.config({
	base : "/static/js/system"
}).use(['form','layer','jquery','laypage','laydate'],function(){
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		laydate = layui.laydate,
		$ = layui.jquery;
	
	//初始化查询参数
    var queryParam = {
        pageIndex:1,//页码
        pageSize:10,//行数
    };
	
	//加载页面数据
	var newsData = '';
	
	//首次查询
	pageQuery(queryParam);
	
	//分页查询
    function pageQuery(queryParam) {
        var queryUrl = '/role/roleList';
        $.post(queryUrl, queryParam, function (data) {
        	//渲染数据
        	renderData(data);
    		//分页
    		var nums = data.pageSize; //每页出现的数据量
    		laypage({
    			cont : "role_page",
    			pages : data.pages,
    			curr: data.pageNum, //当前页  
    			jump : function(obj,first){
    				$(".role_content").html(renderData(data));
    				$('.role_list thead input[type="checkbox"]').prop("checked",false);
    				if(!first){
    					queryParam.pageIndex = obj.curr;
                        pageQuery();
    				}
    		    	form.render();
    			}
    		})
        	
        });
    }
	
    //渲染数据
	function renderData(data){
		var dataHtml = '';
		var currData = data.list;
		if(currData.length != 0){
			for(var i=0;i<currData.length;i++){
				dataHtml += '<tr>'
		    	+'<td><input type="checkbox" name="checked" lay-skin="primary" lay-filter="choose"></td>'
		    	+'<td align="left">'+currData[i].roleName+'</td>'
		    	+'<td>'+currData[i].roleCode+'</td>'
		    	+'<td>'+currData[i].description+'</td>'
		    	+'<td>'+moment(currData[i].createTime).format('YYYY/MM/DD HH:MM')+'</td>'
		    	+'<td>'
				+  '<a class="layui-btn layui-btn-mini role_edit" data-id="'+currData[i].id+'"><i class="iconfont icon-edit"></i>编辑</a>'
				+  '<a class="layui-btn layui-btn-danger layui-btn-mini role_del" data-id="'+currData[i].id+'"><i class="layui-icon">&#xe640;</i> 删除</a>'
		        +'</td>'
		    	+'</tr>';
			}
		}else{
			dataHtml = '<tr><td colspan="8">暂无数据</td></tr>';
		}
	    return dataHtml;
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
        	var nameParam = {'name':$(".search_input").val()}
        	queryParam = $.extend(queryParam, nameParam);
        	pageQuery(queryParam);
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
		var $checkbox = $(".role_list").find('tbody input[type="checkbox"]:not([name="show"])');
		var $checked = $('.role_list tbody input[type="checkbox"][name="checked"]:checked');
		if($checkbox.is(":checked")&&($checked.length==1)){
			var roleId = $checked.eq(0).parents("tr").find(".role_del").attr("data-id");
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
			        	layer.alert('角色分配成功', {icon: 1});
			        	form.render();
			            location.reload();
			        }else{
			        	layer.alert('分配失败，请选择权限！', {icon: 2});
			        }
				}
			})
		}else{
			layer.msg("请选择唯一一个分配的角色");
		}
	})

	//批量删除
	$(".batchDel").click(function(){
		var $checkbox = $('.role_list tbody input[type="checkbox"][name="checked"]');
		var $checked = $('.role_list tbody input[type="checkbox"][name="checked"]:checked');
		if($checkbox.is(":checked")){
			layer.confirm('确定删除选中的信息？',{icon:3, title:'提示信息'},function(index){
				var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
	            setTimeout(function(){
	            	//删除数据
	            	for(var j=0;j<$checked.length;j++){
	            		var roleId = $checked.eq(j).parents("tr").find(".role_del").attr("data-id")
	            		$.get('/role/deleteRole?roleId='+roleId,function(result){
							if('success' == result){
								$('.role_list thead input[type="checkbox"]').prop("checked",false);
				            	form.render();
				            	location.reload();
				                layer.close(index);
								layer.msg("删除成功");
							}else{
								layer.msg("删除失败");
							}
	            		})
	            	}
	            },500);
	        })
		}else{
			layer.msg("请选择需要删除的角色");
		}
	})

	//全选
	form.on('checkbox(allChoose)', function(data){
		var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]:not([name="show"])');
		child.each(function(index, item){
			item.checked = data.elem.checked;
		});
		form.render('checkbox');
	});
 
	//操作
	$("body").on("click",".role_edit",function(){  //编辑
		var _this = $(this)
		var index =  layui.layer.open({
			title:false,
			closeBtn : 0,
			area: ['100%', '100%'],
			type : 2,
			content : '/role/roleEdit?roleId='+_this.attr('data-id')
		})
		
		//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
		$(window).resize(function(){
			layui.layer.full(index);
		})
		 layui.layer.full(index);
	})


	$("body").on("click",".role_del",function(){  //删除
		var _this = $(this);
		layer.confirm('确定删除此信息？',{icon:3, title:'提示信息'},function(index){
	    var index = layer.msg('删除中，请稍候',{icon: 16,time:false,shade:0.8});
	    //删除数据
		$.get('/role/deleteRole?roleId='+_this.attr("data-id"),function(result){
			if('success' == result){
				form.render();
				location.reload();
				layer.close(index);
				layer.msg('删除成功');
			}else {
				layer.msg('删除失败');
			}
        })
		});
	})
})
