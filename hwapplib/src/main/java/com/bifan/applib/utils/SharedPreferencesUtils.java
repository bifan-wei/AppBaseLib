package com.bifan.applib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 注意这里要保存的对象的属性必须是public权限的
 */
public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mUtils;
    private Context mContext;

    public static SharedPreferencesUtils getInstance(Context context) {
        if (mUtils == null) {
            mUtils = new SharedPreferencesUtils();
            mUtils.mContext = context;
        }
        return mUtils;
    }

    public static List<Field> getPublicFields(Class<?> clazz) {
        // 用来存储clazz中用public修饰的属性的list
        List<Field> list = new ArrayList<>();
        // 获得clazz中所有用public修饰的属性
        Field[] fields = clazz.getFields();
        // 将fields加入到list中
        if (fields != null) {
            for (Field f : fields) {
                list.add(f);
            }
        }
        return list;

    }

    /**
     * @param shareName shareName
     * @param obj       如果为null的话就执行清除操作
     *                  --------------------
     *                  TODO
     *                  --------------------
     *                  author: huangwei
     *                  2017年1月13日下午3:01:36
     */
    public void putObjectToShare(String shareName, Object obj) {
        // 获得SharedPreferences实例
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        // 获得Editor
        Editor edit = sharedPreferences.edit();
        // 存储数据之前先将之前的旧数据清掉
        edit.clear();
        // 调用commit提交数据(这里为了清掉数据)
        edit.apply();

        if (obj == null) {
            edit.commit();
            return;
        }
        List<Field> publicFields = getPublicFields(obj.getClass());

        for (Field f : publicFields) {
            String name = f.getName();
            try {
                // 获得当前属性的类型和值
                // 类型的话如果是基本类型，会自动装箱
                Object type = f.get(obj);
                // 判断各种类型，调用各种类型的put方法将数据存储进去
                if (type instanceof String) {
                    edit.putString(name, (String) type);
                } else if (type instanceof Integer) {
                    edit.putInt(name, (Integer) type);
                } else if (type instanceof Float) {
                    edit.putFloat(name, (Float) type);
                } else if (type instanceof Long) {
                    edit.putLong(name, (Long) type);
                } else if (type instanceof Boolean) {
                    edit.putBoolean(name, (Boolean) type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 调用commit，提交数据
            edit.commit();
        }
    }

    public void putStringToShare(String shareName, String obj) {
        // 获得SharedPreferences实例
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        // 获得Editor
        Editor edit = sharedPreferences.edit();
        // 存储数据之前先将之前的旧数据清掉
        edit.clear();
        // 调用commit提交数据(这里为了清掉数据)
        edit.apply();

        if (obj == null) {
            edit.commit();
            return;
        }

        edit.putString(shareName, obj);

        edit.commit();

    }

    /**
     * @param shareName shareName
     * @return 没有数据返回null
     */
    public String getStringfromShare(String shareName) {
        // 获得SharedPreferences实例
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(shareName, null);

    }

    /**
     * @param shareName shareName
     * @param clazz     clazz
     * @return 可能会返回空
     */
    public <T> T getObjectFromShare(String shareName, Class<T> clazz) {
        // 获得SharedPreferences实例
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        // T是一个泛型，根据clazz不同而不同
        T t = null;
        try {
            // 获得sharedPreferences中所有的数据，数据为键值对保存在map中
            Map<String, ?> map = sharedPreferences.getAll();
            // 调用getPublicFields方法得到clazz中所有的公有属性
            List<Field> publicFields = getPublicFields(clazz);

            // 如果两者都不为空的话
            if (map.size() > 0 && publicFields.size() > 0) {
                // 将T实例化出来
                t = clazz.newInstance();
                // 遍历map中所有的键值对
                for (Map.Entry<String, ?> entry : map.entrySet()) {
                    // map中的键
                    String key = entry.getKey();
                    // map中的值
                    Object value = entry.getValue();
                    // 遍历clazz中的所有公有属性
                    for (Field field : publicFields) {
                        // 获得属性名
                        String name = field.getName();
                        // 如果属性名与键相同
                        if (name.equalsIgnoreCase(key)) {
                            // 相当于给对象T中的属性field赋值，值为value
                            field.set(t, value);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 整个遍历结束后，我们的对象中的属性也都有值了
        return t;
    }

}
