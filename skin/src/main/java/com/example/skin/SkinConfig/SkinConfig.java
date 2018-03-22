package com.example.skin.SkinConfig;

/**
 * @author tys
 * @date 2017/9/24
 */

public class SkinConfig {

    /**
     * EditText输入框， RadioButton ，进度条颜色 KEY
     */
    public static final String COLO_ACCENT = "colorAccent";

    /**
     * Title， ActionBar颜色 KEY
     */
    public static final String COLO_PRIMARY = "colorPrimary";

    /**
     * 状态栏颜色 KEY
     */
    public static final String COLO_PRINARY_DARK = "colorPrimaryDark";

    /**
     * 布局xml文件中声明命名空间名称，用于获取xml中属性，以判断该控件是否需要更换属性
     */
    public static final String SKIN_NAMESPACE = "http://schemas.android.com/apk/skin";

    /**
     * 布局xml文件中属性名称，用于获取xml中属性，以判断该控件是否需要更换属性
     */
    public static final String SKIN_ATTR_ENABLE = "enable";


    ////////////////////////////////////navigation bar///////////////////////////////////////////
    /**
     * 插件中，navigation bar的颜色的名字，未配置则保持不变
     */
    public static String NAVIGATION_BAR_COLOR_VAL = "";

    static final String NAVIGATION_BAR_COLOR_KEY = "NAVIGATION_BAR_COLOR_KEY";


    ///////////////////////////////////status bar////////////////////////////////////////////
    /**
     * 插件中，status bar的颜色的名字，未配置则保持不变
     */
    public static String STATUS_BAR_COLOR_VAL = "";

    static final String STATUS_BAR_COLOR_KEY = "STATUS_BAR_COLOR_KEY";


    ///////////////////////////////////Res//////////////////////////////////////////
    /**
     * 期望（当前）资源名称的后缀。SKIN_RES_NAME_SUFFIX_EXPECT的Val
     */
    public static String SKIN_RES_NAME_SUFFIX_VAL = "";

    public static final String SKIN_RES_NAME_SUFFIX_KEY = "SKIN_RES_NAME_SUFFIX_KEY";


    //////////////////////////////////font///////////////////////////////////////////
    /**
     * 默认字体路径
     * 字体目录 assets/font
     */
    public static final String SKIN_TYPEFACE_DIR = "font/";

    /**
     * 期望（当前）字体名称。SKIN_RES_NAME_SUFFIX_EXPECT的Val
     * 字体目录 assets/font
     */

    public static String SKIN_TYPEFACE_NAME_VAL = "";

    /**
     * SharedPreferences中的Key，保存的值：期望的字体换资源名称
     * 字体目录 assets/font
     */
    public static final String SKIN_TYPEFACE_NAME_KEY = "SKIN_TYPEFACE_NAME_KEY";


    /**
     * 分隔符。新资源名称 = 旧资源名称+分隔符+后缀
     */
    public static String SEPARATOR = "_";

}
