function navBar(strData) {
    var data;

    if (typeof(strData) == "string") {
        data = JSON.parse(strData); //部分用户解析出来的是字符串，转换一下
    } else {
        data = strData;
    }

    var ulHtml = '<ul class="layui-nav layui-nav-tree">';
    // for (var i = 0; i < data.length; i++) {
    for (var i in data) {
        ulHtml += '<li class="layui-nav-item">';

        if (data[i].permType == "menu") {
            ulHtml += '<a href="javascript:;">';
            if (data[i].icon != undefined && data[i].icon != '') {
                if (data[i].icon.indexOf("icon-") != -1) {
                    ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
                } else {
                    ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
                }
            }

            // 添加菜单标题
            ulHtml += '<cite>' + data[i].permName + '</cite>';
            // 添加子菜单图标
            ulHtml += '<span class="layui-nav-more"></span>';
            ulHtml += '</a>';
            // 子菜单开始
            ulHtml += '<dl class="layui-nav-child">';
            // for (var j = 0; j < data.length; j++) {
            for (var j in data) {
                if (data[j].permId == data[i].permId) {
                    continue;
                }
                if (data[j].permType == "permission" && data[j].parentId == data[i].permId) {
                    ulHtml += '<dd><a href="javascript:;" data-url="' + data[j].url + '">';

                    if (data[j].icon != undefined && data[j].icon != '') {
                        if (data[j].icon.indexOf("icon-") != -1) {
                            ulHtml += '<i class="iconfont ' + data[j].icon + '" data-icon="' + data[j].icon + '"></i>';
                        } else {
                            ulHtml += '<i class="layui-icon" data-icon="' + data[j].icon + '">' + data[j].icon + '</i>';
                        }
                    }
                    ulHtml += '<cite>' + data[j].permName + '</cite></a></dd>';
                }
            }
            ulHtml += "</dl>";
        }
        else if (data[i].permType == "permission") {
            var isFlag = false;

            // 判断上级父菜单是否存在
            for (var k in data) {
                if (data[i].permId != data[k].permId && data[i].parentId == data[k].permId) {
                    isFlag = true;
                    continue;
                }
            }

            if (!isFlag) {
                ulHtml += '<a href="javascript:;" data-url="' + data[i].url + '">';

                if (data[i].icon != undefined && data[i].icon != '') {
                    if (data[i].icon.indexOf("icon-") != -1) {
                        ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
                    } else {
                        ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
                    }
                }

                ulHtml += '<cite>' + data[i].permName + '</cite></a>';
            }

        }
        ulHtml += '</li>';
    }
    ulHtml += '</ul>';

    return ulHtml;
}
