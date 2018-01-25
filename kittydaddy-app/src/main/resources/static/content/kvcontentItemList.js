layui.use(['table','laypage','laydate','jquery'], function(){
		  var table = layui.table,  
          laydate=layui.laydate,  
          laypage = layui.laypage,
          $ = layui.jquery;
          
		  var contentId = $('#contentId').val();
		  var curnum = 1,limitcount=20;
		  //初始化搜索
		  kvContentItemSearch(contentId,curnum,limitcount);
		  
		  function kvContentItemSearch(contentId,pageIndex,pageSize){
			  //第一个实例
			  table.render({
			     elem: '#kvcontentItemList',
			     height:'full-153',
			     url: '/kvcontentItem/kvcontentItemList?contentId='+contentId+'&pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
			     page: false, //开启分页
			     cols: [[ //表头
			      {type:'checkbox'},      
			      {field: 'itemChannel', title: '剧集类型', width:300},
			      {field: 'itemTitle', title: '标题', width:300},
			      {field: 'itemSn', title: '期（集）数', width:300, sort: true},
			      {field: 'itemPeriod', title: '阶段', width: 300, sort: true},
			      {field: 'createTime', title: '创建时间', width: 200, 
			    	    templet:function(d){
			    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
			    	    }  
			      },
			      {fixed: 'right', width: 200, title: '操作',align:'center', toolbar: '#kvcontentItemBar'}
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
			                        kvContentItemSearch(contentId,curnum,limitcount);  
			                    }  
			                }  
	                        
			    	})
			      }
			  });
			  
			  table.on('tool(content_list)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				    var data = obj.data //获得当前行数据
				    ,layEvent = obj.event; //获得 lay-event 对应的值
				    if(layEvent === 'detail'){
				      layer.msg('查看操作');
				    } else if(layEvent === 'del'){
				      layer.confirm('真的删除行么', function(index){
				        obj.del(); //删除对应行（tr）的DOM结构
				        layer.close(index);
				        //向服务端发送删除指令
				      });
				    } else if(layEvent === 'edit'){
				    	var _this = $(this)
						var index =  layui.layer.open({
							title:false,
							closeBtn : 0,
							area: ['100%', '100%'],
							type : 2,
							content :  '/kvcontent/editkvContent?kvcontentId='+data.id
						})
						
						//改变窗口大小时，重置弹窗的高度，防止超出可视区域（如F12调出debug的操作）
						$(window).resize(function(){
							layui.layer.full(index);
						})
						 layui.layer.full(index);
				    }
			  });
		  }
});