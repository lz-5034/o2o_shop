$(function () {
    var shopId = getQueryString('shopId');
    var shopManagementUrl = '/o2o/shop_admin/get_shop_management?shopId=' + shopId;
    $.getJSON(shopManagementUrl,function (data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            //如果有店铺id默认到编辑店铺，没有则是新创建店铺
            $('#shopInfo').attr('href', '/o2o/shop_admin/shop_operation?shopId=' + shopId);
        }
    })
});