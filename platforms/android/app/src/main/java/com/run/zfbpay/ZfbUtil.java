package com.run.zfbpay;

/**
 * Created by liudong on 2018-3-21.
 */

public class ZfbUtil {

    /** 支付宝appid */
    public final static String ZFB_APPID = "2018031902406017";

    //支付宝商户私钥
    public final static String ZFB_PRIVATE_RSA = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCLo0WLyOGsn8sPEsgrIPpItX3Ye0aVI2bVAxlV0PCZXLrEylkIfwDvvjg7JJakTZMHCwylsrtip7qZIXzo6HHvWTHuIRRXbnQSB590cDG2xfmJ4jRemUVgAVLjvZjdG4eh7XjMGCNNQHGwW1+sMz/RFna9Bwp9qhbtVteKKCgTbbWUD7004HKjRUDPZhypwm5enA+9xwX8DJga6HSsoaSxZ7vKPJG6F0P/72tGIcOJTQq1VyGMwmJ0onVB9SBrvlpsBlcAFUPkapkq76Ppzsj5zGdWjLCW50TMHBmegFaY5MoBzLooUQdHoCsEHSX364CYkdBP1ri9pgc8Po5Pf+z5AgMBAAECggEAQaKkLPlmgnK1yscESal9m7GbjqcbXTy/uD51qS3F+gBpVdjbErBH62jZG4uuN1qzUmsyLayegdffUN05zmx8KYoeexUXb6Veb58fp2aZJ5VVHyexgAnBX0ggAyZ6dOvczBNIZX6vET/KcJ/yj4mjZI+PjniByOGrVzIrrCgF9mMzGKImrYMA/ubroEzL+iTcioONlU8al8dMPBMS1wNkn8hAP63lLFQfZLzG0WwzzIt95khkOWY3SSrTvzFNeNqvANDIACEAsBUBsg9/ZA+8T+39zvZTbMIcXiexhwWfa+DSAWdRKQZ311q8KMZ6O9+Q3U05zXlHiZcfaqztX5i9NQKBgQDxTfZFMDZorPZxsK9Qlr7f9Ltzhq8RIdq1VQtkJQprKI4kvqzgf4apUG44bKJk5U9qDAY+vcYxwX8jH6UyxYdhXlitoy4r8yG9i3Xl0M833eK/26AwHqiTFChkMhP9t8sIAg6yyNrC8rBBJbObTYHoZIpz2PEvA+Miiv2DaJVMGwKBgQCUJEhcieoy5k+m/u6KZe1v0fJtQS9Xskw/AZqfxjUKhYKfP3/txqqZ7jRsUF5TcLrqszgu9TnDn5UyuIVsvOz0MA9eJZKLsC8W4if3D3T9I05vldCiUSgsEclHWv7FjKhBk9RyBmL0benK+ZtSDX+RuweLC+AaWf7rOdtINhrUewKBgEjJ5KTAQ1lV+00WJ/weTNuPnk/d68c2OBwE2FfQGJF0fVQ2ND3QGhXOzkC0/FOFHGx3zhZ09mkh3jEBJbrn/MtxQzy1gYL+Z0oe3GSDlU5b3z9Sh8KcLOuY5Ao9VOxu2Y7D2B00G1E9Fc/BCKmrOqmYUm9ZnH87I/PSi2BtVsPLAoGAXSCd+MaeWtcb9aBHdutyoxv5aaNTzq6Abg9lE9H3YCDkJ5Y0ptaAQvpTLFAtrAivCIIX7LUuVgMJOPR0tBHkZ/Gah5XZFUVxpNIRS6gE5GmAknc8y/124pVf0iKrDKdK0sbLA89t/7jlAzt8380qiANqT4fsnsQ6k8cwSanxnecCgYBbyeMxdO2WShTuHxtjHHE+GOwQBDkiBJ6vasN5w+236wA6LUxqvCA7KqnzcqCJFeSDT8C315KlhBGnBRwoAcnj8J8/VAuJlruRKIg5gpuyh6SdeLn4abXyUE4TQPdF+p9BmdMdx3eZM3kgYIjOhtWuZNl30u2e7gX41ROZh6ex4Q==";

    //支付宝商户公钥
    public final static String ZFB_PUBLIC_RAS = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi6NFi8jhrJ/LDxLIKyD6SLV92HtGlSNm1QMZVdDwmVy6xMpZCH8A7744OySWpE2TBwsMpbK7Yqe6mSF86Ohx71kx7iEUV250EgefdHAxtsX5ieI0XplFYAFS472Y3RuHoe14zBgjTUBxsFtfrDM/0RZ2vQcKfaoW7VbXiigoE221lA+9NOByo0VAz2YcqcJuXpwPvccF/AyYGuh0rKGksWe7yjyRuhdD/+9rRiHDiU0KtVchjMJidKJ1QfUga75abAZXABVD5GqZKu+j6c7I+cxnVoywludEzBwZnoBWmOTKAcy6KFEHR6ArBB0l9+uAmJHQT9a4vaYHPD6OT3/s+QIDAQAB";

    /** 支付宝授权接口  */
    public final static String ZFB_AUTH = "com.alipay.account.auth";

    /** 支付宝支付接口 */
    public final static String ZFB_PAY = "alipay.trade.app.pay";

    /** 商户类型标识 */
    public final static String ZFB_MERCHANT_TYPE = "mc";

    /** 业务类型 */
    public final static String ZFB_BUSINESS_TYPE = "openservice";

    /** 产品码 */
    public final static String ZFB_PRODUCT_ID = "APP_FAST_LOGIN";

    /** 授权范围 -快捷 */
    public final static String ZFB_SCOPE_KUAIJIE = "kuaijie";

    /** 商户唯一标识 */
    public final static String ZFB_TARGET_ID = "kuaijie";

    /** 授权类型 */
    public final static String ZFB_AUTH_TYPE = "AUTHACCOUNT";

    /** 支付宝支付标识 */
    public final static int SDK_PAY_FLAG = 1;

    /** 支付宝授权标识 */
    public final static int SDK_AUTH_FLAG = 2;

    /** 该笔订单允许的最晚付款时间 30m **/
    public final static String TIMEOUT_EXPRESS = "30m";

    /** 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY  */
    public final static String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    /** 对一笔交易的具体描述信息  */
    public final static String BODY = "食堂饭卡充值";

    /**商品的标题/交易标题/订单标题/订单关键字等。 */
    public final static String SUBJECT = "食堂饭卡一卡通充值";




    /**
     * 格式化金额,保留2位小数
     * @param amount 金额
     * @return
     */
    public static String formatAmount(String amount){
        double money = Double.parseDouble(amount);

        return String.format("%.2f", money);
    }


    public static void main(String[] args) {
        System.out.println(formatAmount("100"));
    }


}
