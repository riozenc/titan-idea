package com.wisdom.auth.data.provider.multiple;

public class DataSourceContextHolder {

//    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();
    private static final ThreadLocal contextHolder = new ThreadLocal<>();

    /**
     *  设置数据源
     * @param db
     */
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDataSource(){
        return (String)contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clear(){
        contextHolder.remove();
    }

}
