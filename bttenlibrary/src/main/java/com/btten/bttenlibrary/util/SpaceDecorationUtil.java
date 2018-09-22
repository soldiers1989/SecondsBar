package com.btten.bttenlibrary.util;

/**
 * SpaceDecoration 工厂类 用于构建各种样式的SpaceDecoration
 */
public class SpaceDecorationUtil
{

    /**
     * 构建SpaceDecoration对象
     *
     * @param spaceSize                //Space大小
     * @param isSetPaddingEdgeSide     //设置是否填充边缘
     * @param isSetPaddingHeaderFooter //设置是否填充头部和尾部
     * @param isSetPaddingStart        //设置是否从开始处填充
     * @return
     */
    public static SpaceDecoration getDecoration(int spaceSize, boolean isSetPaddingEdgeSide, boolean isSetPaddingHeaderFooter, boolean isSetPaddingStart)
    {
        SpaceDecoration decoration = new SpaceDecoration(spaceSize);
        decoration.setPaddingEdgeSide(isSetPaddingEdgeSide);
        decoration.setPaddingHeaderFooter(isSetPaddingHeaderFooter);
        decoration.setPaddingStart(isSetPaddingStart);
        return decoration;
    }
}
