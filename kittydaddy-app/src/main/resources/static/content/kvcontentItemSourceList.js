layui.use(['table','layer','laypage','laydate','jquery'], function(){
		  var table = layui.table,  
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
			  debugger
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
			    		    elem:'laypage',
			    		    count:count, 
	                        curr:pageIndex, 
	                        limit:res.data.length, 
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
			  
			  table.on('tool(source_list)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
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