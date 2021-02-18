package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem {

    public String[] getImages(){
        String images=this.getImage();
        if (images!=null&&images!=""){
            String[] imgs = images.split(",");
            return  imgs;

        }
        return null;


    }

}
