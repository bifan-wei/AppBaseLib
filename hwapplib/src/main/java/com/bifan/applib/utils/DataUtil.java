package com.bifan.applib.utils;

import java.util.Collection;

public class DataUtil {

    public static int size(Collection collection){
        return collection == null ? 0 : collection.size();
    }
    public static Boolean isNull(Object... objects) {
        if (objects.length == 0) {
            return true;
        }
        for (Object o : objects) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static Boolean isNoData(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static Boolean isNoData(Collection... collection) {
        for( Collection c:collection){
            if(isNoData(c)){
                return false;
            }
        }
        return true;
    }

    public static Boolean isStrEqual(String strFrom, String strTo) {
        if (strFrom == null && strTo == null) {
            return true;
        } else if (strFrom != null && strTo != null) {
            return strFrom.equals(strTo);
        }
        return false;
    }
}
