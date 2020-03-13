$(function () {
    //获取此店铺下的商品列表的URL
    var listUrl = '/o2o/shop_admin/get_product_list?pageIndex=1&pageSize=999';
    //商品下架URL
    var statusUrl = '/o2o/shop_admin/product_modify';

    getList();

    //获取此店铺下的商品列表
    function getList() {
        //从后台获取此店铺的商品列表
        $.getJSON(listUrl,function (data) {
            if (data.success) {
                var productList = data.productList;
                var tempHtml = '';
                //遍历每条商品信息，拼接成一行显示
                productList.map(function (item,index) {
                    var textOp = "下架";
                    var contraryStatus = 0;
                    if (item.enableStatus == 0) {
                        textOp = "上架";
                        contraryStatus = 1;
                    } else {
                        contraryStatus = 0;
                    }
                    //拼接每件商品的行信息
                    tempHtml += ''
                        + '<div class="row row-product">'
                        + '<div class="col-33">' + item.productName + '</div>'
                        + '<div class="col-20">' + item.priority + '</div>'
                        + '<div class="col-40">'
                        + '<a href="#" class="edit" data-id="'+item.productId+'" data-status="'+item.enableStatus+'">编辑</a>'
                        + '<a href="#" class="status" data-id="'+item.productId+'" data-status="'+contraryStatus+'">'+textOp+'</a>'
                        + '<a href="#" class="preview" data-id="'+item.productId+'" data-status="'+item.enableStatus+'">预览</a>'
                        + '</div>'
                        + '</div>'
                });
                //将拼接好的信息赋值到html控件中
                $('.product-wrap').html(tempHtml);
            }
        });
    }

    //将class为product-wrap里面的a标签绑定点击事件
    $('.product-wrap').on('click','a',function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/o2o/shop_admin/product_operation?productId=' + e.currentTarget.dataset.id;
        }else if (target.hasClass('status')) {
            changeItemStatus(e.currentTarget.dataset.id, e.currentTarget.dataset.status);
        }else if (target.hasClass('preview')) {
            //去前台的商品详情展示页面
            window.location.href = '/o2o/frontend/product_detail?productId=' + e.currentTarget.dataset.id;
        }
    });

    function changeItemStatus(id, enableStatus) {
        //定义product json对象并添加productId以及状态（上架/下架）
        var product = {};
        product.productId = id;
        product.enableStatus = enableStatus;
        $.confirm('确定修改状态？',function () {
            $.ajax({
                url: statusUrl,
                type: 'POST',
                data: {
                    productStr: JSON.stringify(product),
                    statusChange: true
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！'+data.msg);
                    }
                }
            });
        });
    }
});