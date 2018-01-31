layui.config({
	base : "/static/js/content"
}).use(['table','laypage','laydate','jquery'], function(){
		  var table = layui.table,  
          laydate=layui.laydate,  
          laypage = layui.laypage,
          $ = layui.jquery;
          
		  var curnum = 1,limitcount=20;
		  //初始化搜索
		  kvContentSearch(curnum,limitcount);
		  
		  function kvContentSearch(pageIndex,pageSize){
			  //第一个实例
			  table.render({
			     elem: '#kvcontentList',
			     height:'full-153',
			     url: '/kvcontent/kvcontentList?pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
			     page: false, //开启分页
			     cols: [[ //表头
			      {type:'checkbox'},      
			      {field: 'id', title: '内容编号', width:80},
			      {field: 'title', title: '标题', width:80},
			      {field: 'subtitle', title: '副标题', width:80, sort: true},
			      {field: 'rate', title: '评分', width:80}, 
			      {field: 'channel', title: '类别', width: 90, sort: true},
			      {field: 'shortFlag', title: '长短视频', width: 80, sort: true,
			    	  templet: function(d){
			    		    if(d.shortFlag==0){
			    		    	return "长视频";
			    		    }else if(d.shortFlag==1){
			    		    	return "短视频";
			    		    }else{
			    		    	return "";
			    		    }
			    	  }  
			      },
			      {field: 'directors', title: '导演', width: 80, sort: true},
			      {field: 'actors', title: '演员', width: 80, sort: true},
			      {field: 'year', title: '年份', width: 80, sort: true},
			      {field: 'lastSn', title: '更新', width: 80,
			    	  templet: function(d){
			    		    var lastSn = 0;
			    		    if(d.lastSn ==null) {lastSn=1}else{lastSn=d.lastSn};
			    		    return "<span style='color:red'>更新:"+lastSn+"集</span>"
			    	  }  
			      },
			      {field: 'duration', title: '时长(秒)', width: 80, sort: true},
			      {field: 'area', title: '地区', width: 80, sort: true},
			      {field: 'originPubTime', title: '影片发布时间', width: 80, sort: true},
			      {field: 'language', title: '语言', width: 80, sort: true},
			      {field: 'genres', title: '题材', width: 80, sort: true},
			      {field: 'createTime', title: '创建时间', width: 100, 
			    	    templet:function(d){
			    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
			    	    }  
			      },
			      {field: 'isPublish', title: '发布状态', width: 80, sort: true,
			    	    templet:function(d){
			    	    	if(d.isPublish === 0){
			    	    		return "<span style='color:red'>未发布</span>";
			    	    	}else if(d.isPublish === 1){
			    	    		return "<span style='color:green'>已发布</span>"
			    	    	}
			    	    }  
			      },
			      {fixed: 'right', width: 250, align:'center', toolbar: '#kvcontentBar'}
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
			                        kvContentSearch(curnum,limitcount);  
			                    }  
			                }  
	                        
			    	})
			      }
			  });
			  
			  table.on('tool(content_list)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				    var data = obj.data //获得当前行数据
				    ,layEvent = obj.event; //获得 lay-event 对应的值
				    if(layEvent === 'view_cover_pic'){
				      var imgUrl = data.imgLargeUrl;
				      layer.open({
				    	  type: 1,
				    	  title: false,
				    	  closeBtn: 0,
				    	  area: ['630px', '360px'],
				    	  skin: 'layui-layer-nobg', //没有背景色
				    	  shadeClose: true,
				    	  content: '<img style="width:100%;height:100%" src='+imgUrl+'>'
				    	});
				      
				    } else if(layEvent === 'del'){
				      layer.confirm('真的删除行么', function(index){
				        obj.del(); //删除对应行（tr）的DOM结构
				        layer.close(index);
				        //向服务端发送删除指令
				      });
				    } else if(layEvent === 'edit'){
						var index = layer.open({
							title:false,
							closeBtn : 0,
							area: ['100%', '100%'],
							type : 2,
							content :  '/kvcontent/editkvContent?contentId='+data.id
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