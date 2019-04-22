package com.android.mb.movie.entity;

public class VersionBean {

    /**
     * androidDownloadUrl : http://android/xx.apk
     * androidUpdContent : 安卓更新
     * androidVersion : 1
     * apkUrl : http://39.96.68.92:8080/res/file/?nocache=1555919894078
     * id : 00000001
     * iosDownloadUrl : http://ios/xx.ipa
     * iosUpdContent : ios更新
     * iosVersion : 1
     * ipaUrl : http://39.96.68.92:8080/res/file/?nocache=1555919894078
     * isForce : 1
     */

    private String androidDownloadUrl;
    private String androidUpdContent;
    private String androidVersion;
    private String apkUrl;
    private String id;
    private String iosDownloadUrl;
    private String iosUpdContent;
    private String iosVersion;
    private String ipaUrl;
    private int isForce;
    private int androidIsForce;

    public String getAndroidDownloadUrl() {
        return androidDownloadUrl == null ? "" : androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
        this.androidDownloadUrl = androidDownloadUrl;
    }

    public String getAndroidUpdContent() {
        return androidUpdContent == null ? "" : androidUpdContent;
    }

    public void setAndroidUpdContent(String androidUpdContent) {
        this.androidUpdContent = androidUpdContent;
    }

    public String getAndroidVersion() {
        return androidVersion == null ? "" : androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getApkUrl() {
        return apkUrl == null ? "" : apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIosDownloadUrl() {
        return iosDownloadUrl == null ? "" : iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
        this.iosDownloadUrl = iosDownloadUrl;
    }

    public String getIosUpdContent() {
        return iosUpdContent == null ? "" : iosUpdContent;
    }

    public void setIosUpdContent(String iosUpdContent) {
        this.iosUpdContent = iosUpdContent;
    }

    public String getIosVersion() {
        return iosVersion == null ? "" : iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getIpaUrl() {
        return ipaUrl == null ? "" : ipaUrl;
    }

    public void setIpaUrl(String ipaUrl) {
        this.ipaUrl = ipaUrl;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public int getAndroidIsForce() {
        return androidIsForce;
    }

    public void setAndroidIsForce(int androidIsForce) {
        this.androidIsForce = androidIsForce;
    }
}
