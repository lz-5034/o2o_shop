$(function () {
    //从URL里获取productId参数值
    var productId = getQueryString('productId');
    //通过productId获取 商品信息 的URL
    var infoUrl = '/o2o/shop_admin/get_product_by_id?productId=' + productId;
    //获取当前店铺下所有 商品类别 的URL
    var categoryUrl = '/o2o/shop_admin/get_product_category_list';
    //更新商品信息的URL
    var productPostUrl = '/o2o/shop_admin/product_modify';

    //商品添加与编辑用同一个页面，通过isEdit判断 false：添加 true：编辑
    var isEdit = false;

    if (productId) {
        //若URL传入productId则为编辑商品
        getInfo(productId);
        isEdit = true;
    } else {
        getCategory();
        productPostUrl = '/o2o/shop_admin/product_add';
    }

    //获取需要编辑的商品的商品信息，渲染给前台
    function getInfo(id) {
        $.getJSON(infoUrl,function (data) {
            if (data.success) {
                //从返回的JSON中获取product对象的信息，并赋值给表单
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#original-price').val(product.originalPrice);
                $('#discount-price').val(product.discountPrice);
                //获取原本的商品类别和该店铺下所有商品类别
                var optionHtml = '';
                var optionArr = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;
                //生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
                optionArr.map(function (item, index) {
                    var isSelect = optionSelected === item.productCategoryId ? 'selected' : '';
                    optionHtml += '<option data-value="'+item.productCategoryId+'"'+isSelect+'>'
                    + item.productCategoryName + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }
    
    //添加商品时提供在该店铺下所有的商品类别
    function getCategory() {
        $.getJSON(categoryUrl,function (data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'+item.productCategoryId+'">'
                    + item.productCategoryName+'</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    /*针对商品详情图控件组，若该控件组的最后一个元素发生变化（上传了图片），且
    控件总数未达到6个，则生成新的一个文件上传控件*/
    $('.detail-img-div').on('change','.detail-img:last-child',function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    //提交按钮的事件响应，分别对商品的添加和编辑操作做不同响应
    $('#submit').click(
        function () {
            //创建商品json对象，并从表单里面获取对应的属性值
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#priority').val();
            product.originalPrice = $('#original-price').val();
            product.discountPrice = $('#discount-price').val();
            //获取选定的商品类别值
            product.productCategory = {
                productCategoryId : $('#category').find('option').not(
                    function () {
                        return !this.selected;
                    }
                ).data('value')
            };
            product.productId = productId;

            //获取缩略图文件流
            var thumbnail = $('#small-img')[0].files[0];
            //生成表单对象，用于接收参数并传递给后台
            var formData = new FormData();
            formData.append('thumbnail', thumbnail);
            //遍历商品详情图控件，获取里面的文件流
            $('.detail-img').map(
                function (index, item) {
                    //判断该控件是否已选择了文件
                    if ($('.detail-img')[index].files.length > 0) {
                        //将第i个文件流赋值给  key为productImgi  的表单键值对里
                        formData.append('productImg' + index, $('.detail-img')[index].files[0]);
                    }
                }
            );
            //将product的json对象转换成字符流保存至表单对象 key为productStr 的键值对里
            formData.append('productStr', JSON.stringify(product));
            //获取表单里输入的验证码
            var verifyCodeActual = $('#kaptcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append('verifyCodeActual', verifyCodeActual);

            //提交数据至后台进行相关操作
            $.ajax({
                url: productPostUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                cache:false,
                success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！'+data.msg);
                    }
                    $('#kaptcha_img').click();
                }
            });
        }
    );
});