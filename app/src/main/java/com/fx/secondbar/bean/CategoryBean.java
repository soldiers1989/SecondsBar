package com.fx.secondbar.bean;

/**
 * function:栏目实体信息
 * author: frj
 * modify date: 2018/9/24
 */
public class CategoryBean
{
    private String category_ID;//栏目id
    private String name;//栏目名称
    private String sorts;//排序
    private String img;//图片

    public String getCategory_ID()
    {
        return category_ID;
    }

    public void setCategory_ID(String category_ID)
    {
        this.category_ID = category_ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSorts()
    {
        return sorts;
    }

    public void setSorts(String sorts)
    {
        this.sorts = sorts;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }
}
