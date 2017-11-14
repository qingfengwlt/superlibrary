package ken.superlibrary.com.appdemo.enity;

/**
 * Created by PC_WLT on 2017/5/15.
 */

public class ExaMinationBean {
     String Name;//名称
     String text;//简介
     String imgUrl;//图片简介

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
