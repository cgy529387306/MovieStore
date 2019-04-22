package com.android.mb.movie.entity;

public class InviteBean {

    /**
     * qrCodeUrl : http://39.96.68.92:8080/res/file/image/fadfdcc442c14c8dbaba45b4362cf114.png?nocache=1555899062560
     * downloadUrl : http://www.baidu.com
     * shareText : 1、成人视频App黄瓜视频，永久免费在线观看，无需翻墙！\n
     2、超快加载，海量正版高清片源，支持后台缓存下载！\n
     3、使用推广码 MYAVFT 还可以获取专属观影特权\n
     下载请戳我， 使劲戳不会疼哦↓↓↓ ({main})（如果链接打不开请复制到浏览器中打开）\n
     劲爆视频：黄瓜自制全球首部中语中字日本AV、苍井空唯一超清无码片\n
     * promoCode : ZKKPFK
     */

    private String qrCodeUrl;
    private String downloadUrl;
    private String shareText;
    private String promoCode;

    public String getQrCodeUrl() {
        return qrCodeUrl == null ? "" : qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl == null ? "" : downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getShareText() {
        return shareText == null ? "" : shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public String getPromoCode() {
        return promoCode == null ? "" : promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
