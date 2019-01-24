package com.android.mb.movie.entity;

public class Avatar {

    /**
     * avatar_large : http://39.96.68.92:8080/res/file/avatar/2019/01/15/00000001/thumb.400x400.png?nocache=1548320151003
     * avatar_hd : http://39.96.68.92:8080/res/file/avatar/2019/01/15/00000001/thumb.800x800.png?nocache=1548320151003
     * avatar_org : http://39.96.68.92:8080/res/file/avatar/2019/01/15/00000001/org.png?nocache=1548320151003
     * avatar_small : http://39.96.68.92:8080/res/file/avatar/2019/01/15/00000001/thumb.80x80.png?nocache=1548320151003
     */

    private String avatar_large;
    private String avatar_hd;
    private String avatar_org;
    private String avatar_small;

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    public String getAvatar_hd() {
        return avatar_hd;
    }

    public void setAvatar_hd(String avatar_hd) {
        this.avatar_hd = avatar_hd;
    }

    public String getAvatar_org() {
        return avatar_org;
    }

    public void setAvatar_org(String avatar_org) {
        this.avatar_org = avatar_org;
    }

    public String getAvatar_small() {
        return avatar_small;
    }

    public void setAvatar_small(String avatar_small) {
        this.avatar_small = avatar_small;
    }
}
