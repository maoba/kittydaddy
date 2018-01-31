layui.use(['form','table','layer','laypage','laydate','jquery'], function(){
	var   form = layui.form,
	      table = layui.table,  
          laydate=layui.laydate,
          layer = parent.layer === undefined ? layui.layer : parent.layer,
          laypage = layui.laypage,
          $ = layui.jquery;
          
		  var contentId = $('#contentId').val();
		  var shortFlag = $('#shortFlag').val();
		  var curnum = 1,limitcount=10;
		  //初始化搜索
		  kvContentItemSourceSearch(shortFlag,contentId,curnum,limitcount);
		  
		  function kvContentItemSourceSearch(shortFlag,contentId,pageIndex,pageSize){
			  //第一个实例
			  table.render({
			     elem: '#kvcontentItemSourceList',
			     height:'250',
			     url: '/kvcontentItemSource/kvcontentItemSourceList?shortFlag='+shortFlag+'&relativeId='+contentId+'&pageIndex='+pageIndex+'&pageSize='+pageSize, //数据接口
			     page: false, //开启分页
			     cols: [[ //表头
			      {type:'checkbox'},      
			      {field: 'source', title: '源', width:200},
			      {field: 'playUrl', title: '播放地址', width:300},
			      {field: 'imageUrl', title: '剧照', width:300, sort: true},
			      {field: 'duration', title: '时长', width: 200, sort: true},
			      {field: 'isFree',title:'收费状态',width:200,sort:true,
			    	    templet:function(d){
			    	    	if(d.isFree == 1){
			    	    		return "免费";
			    	    	}else if(d.isFree == 2){
			    	    		return "收费";
			    	    	}else{
			    	    		return "未知";
			    	    	}
			    	    }  
			      },
			      {field: 'createTime', title: '创建时间', width: 208, 
			    	    templet:function(d){
			    	    	return moment(d.createTime).format('YYYY/MM/DD HH:MM')
			    	    }  
			      },
			      {fixed: 'right', width: 200, title: '操作',align:'center', toolbar: '#kvcontentItemSourceBar'}
			    ]],
			     done: function(res, curr, count){
			    	laypage.render({ 
			    		    elem:'laypageItemSource',
			    		    count:count, 
	                        curr:pageIndex, 
	                        limit:limitcount, 
	                        layout: ['prev', 'page', 'next', 'skip','count','limit'],  
					    	jump:function (obj,first) {  
			                    if(!first){  
			                        curnum = obj.curr;  
			                        limitcount = obj.limit;  
			                        kvContentItemSourceSearch(shortFlag,contentId,curnum,limitcount);  
			                    }  
			                }  
	                        
			    	})
			      }
			  });
			  
			  table.on('tool(kvcontentItemSourceList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
				    var data = obj.data //获得当前行数据
				    ,layEvent = obj.event; //获得 lay-event 对应的值
				    if(layEvent === 'detail_stage_photo'){
                      var imgUrl = data.imageUrl;
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
					      layer.confirm('确定删除该信息？', function(index){
					        obj.del(); //删除对应行（tr）的DOM结构
					        layer.close(index);
					        $.get('/kvcontentItemSource/deleteContentItemSource?itemSourceId='+data.id,function(result){
								if('success' == result){
									layer.msg("删除成功");
								}else{
									layer.msg("删除失败");
								}
		            		})
					      });
				  } else if(layEvent === 'edit'){
						var index = layer.open({
							title:false,
							closeBtn : 0,
							area: ['50%', '52%'],
							type : 2,
							content :  '/kvcontentItemSource/editkvcontentSource?id='+data.id
						})
				    }
			  });
		  }
		  
		  //返回父类
		  $("body").on("click",".back2Item",function(){ 
		    	var index = parent.layer.getFrameIndex(window.name); 
		        parent.layer.close(index);
		 })
		 
});