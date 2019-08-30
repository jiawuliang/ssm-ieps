package com.ieps.util.miaodiyun;

import java.net.URLEncoder;

/**
 * 验证码通知短信接口
 * 
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 *
 */
public class IndustrySMS {
	private static String operation = "/industrySMS/sendSMS";

	private static int validateTime = 5;
	private static int affMark = (int) (Math.random() * 1000000);

	private static String accountSid = Config.ACCOUNT_SID;
	
	private static String smsContent = "【舞动科技】您的验证码为：" + affMark + "，请于 " + validateTime + " 分钟内正确输入，如非本人操作，请忽略此短信。";

	/**
	 * 验证码通知短信
	 */
	public static String execute(String telPhoneNum) {
		
		affMark = (int) (Math.random() * 1000000);
		System.out.println("验证码：" + affMark);
		smsContent = "【舞动科技】您的验证码为：" + affMark + "，请于 " + validateTime + " 分钟内正确输入，如非本人操作，请忽略此短信。";
		
		String tmpSmsContent = null;
		try {
			tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
		} catch (Exception e) {

		}
		String url = Config.BASE_URL + operation;
		String body = "accountSid=" + accountSid + "&to=" + telPhoneNum + "&smsContent=" + tmpSmsContent
				+ HttpUtil.createCommonParam();

		// 提交请求
		String result = HttpUtil.post(url, body);
		System.out.println("result:" + System.lineSeparator() + result);
		
		System.out.println(affMark + "  短信验证码！");
		
		return String.valueOf(affMark);
	}
}
