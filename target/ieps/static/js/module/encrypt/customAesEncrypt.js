/**
 * Created by ljw
 */
/*****************************************************
 * AES加密
 * @param content 加密内容
 * @param key 加密密码，由字母或数字组成
 　　　　　　此方法使用AES-128-ECB加密模式，key需要为16位
 　　　　　　加密解密key必须相同，如：abcd1234abcd1234
 * @return 加密密文
 ****************************************************/

function encrypt(content, key) {
    var sKey = CryptoJS.enc.Utf8.parse(key);
    var sContent = CryptoJS.enc.Utf8.parse(content);
    var encrypted = CryptoJS.AES.encrypt(sContent, sKey, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

/*****************************************************
 * AES解密
 * @param content 加密密文
 * @param key 加密密码，由字母或数字组成
 　　　　　　此方法使用AES-128-ECB加密模式，key需要为16位
 　　　　　　加密解密key必须相同，如：abcd1234abcd1234
 * @return 解密明文
 ****************************************************/

function decrypt(content, key) {
    var sKey = CryptoJS.enc.Utf8.parse(key);
    var decrypt = CryptoJS.AES.decrypt(content, sKey, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}
