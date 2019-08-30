// getParam.js
(function ($) {
    $.extend({
        //1、取值使用    $.Request("name")
        Request: function (name) {
            var sValue = location.search.match(new RegExp("[\?\&]" + name + "=([^\&]*)(\&?)", "i"));
            //decodeURIComponent解码
            return sValue ? decodeURIComponent(sValue[1]) : decodeURIComponent(sValue);
 
        },
        //2、给url加参数    $.UrlUpdateParams(url, "add", 11111);
        UrlUpdateParams: function (url, name, value) {
            var r = url;
            if (r != null && r != 'undefined' && r != "") {
                value = encodeURIComponent(value);
                var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");
                var tmp = name + "=" + value;
                if (url.match(reg) != null) {
                    r = url.replace(eval(reg), tmp);
                } else {
                    if (url.match("[\?]")) {
                        r = url + "&" + tmp;
                    } else {
                        r = url + "?" + tmp;
                    }
                }
            }
            return r;
        }
    });
})(jQuery);