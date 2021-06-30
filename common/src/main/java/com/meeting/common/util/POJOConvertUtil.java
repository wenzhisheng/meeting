package com.meeting.common.util;

import com.meeting.common.exception.DescribeException;
import com.meeting.common.exception.ExceptionEnum;
import com.meeting.common.pojo.base.PageResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: dameizi
 * @description: POJO转换工具
 * @dateTime 2019-03-29 15:30
 * @className com.meeting.common.utils.POJOConvertUtil
 */
public class POJOConvertUtil {

    private static final Logger logger = LogManager.getLogger(POJOConvertUtil.class);

    /** 缓存字段 */
    private static final Map<String, Map<String, Field>> cacheFields = new ConcurrentHashMap<String, Map<String, Field>>();
    /** 基础字段 */
    private static final String[] baseFields = {"creationTime", "creationBy", "lastUpdatedTime", "lastUpdatedBy"};
    private static final String MOTHED_SET = "set";
    private static final String MOTHED_GET = "get";
    /** 基础类型 */
    private static final Set<Class> basicClass = new HashSet<Class>();
    static {
        basicClass.add(Integer.class);
        basicClass.add(Character.class);
        basicClass.add(Byte.class);
        basicClass.add(Float.class);
        basicClass.add(Double.class);
        basicClass.add(Boolean.class);
        basicClass.add(Long.class);
        basicClass.add(Short.class);
        basicClass.add(String.class);
        basicClass.add(BigDecimal.class);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:35
     * @description: 将具有相同属性的类型进行转换
     * @param: [obj, targetClass]
     * @return: T
     */
    public static <T> T convertPojo(Object obj, Class<T> targetClass) {
        return convertPojo(obj, targetClass, false);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:35
     * @description: 将具有相同属性的类型进行转换
     * @param: [obj, targetClass, hasBaseField]
     * @return: T
     */
    public static <T> T convertPojo(Object obj, Class<T> targetClass, boolean hasBaseField) {
        try {
            T target = targetClass.newInstance();
            /** 获取源对象的所有变量 */
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (isStatic(field)) {
                    continue;
                }
                /** 获取目标方法 */
                Field targetField = getTargetField(targetClass, field.getName());
                if (targetField == null) {
                    continue;
                }
                Object value = getFiledValue(field, obj);
                if (value == null) {
                    continue;
                }
                Class type1 = field.getType();
                Class type2 = targetField.getType();
                //两个类型是否相同
                boolean sameType = type1.equals(type2);
                if (isBasicType(type1)) { //基础类型
                    if (sameType) {
                        setFieldValue(targetField, target, value);
                    }
                } else if (value instanceof Map && Map.class.isAssignableFrom(type2)){//对map
                    setMap((Map)value, field, targetField, target);
                } else if (value instanceof Set && Set.class.isAssignableFrom(type2)) {//对set
                    setCollection((Collection)value, field, targetField, target);
                } else if (value instanceof List && List.class.isAssignableFrom(type2)) {//对list
                    setCollection((Collection)value, field, targetField, target);
                } else if (value instanceof Enum && Enum.class.isAssignableFrom(type2)) {//对enum
                    setEnum((Enum)value, field, targetField, target);
                } else if (value instanceof Date &&
                        Date.class.isAssignableFrom(type2)) {//对日期类型，不处理如joda包之类的扩展时间，不处理calendar
                    setDate((Date)value, targetField, type2, target, sameType);
                }
            }
            if(hasBaseField) {
                // creationTime, creationBy, lastUpdatedTime, lastUpdatedTime, lastUpdatedTime 属性的设值
                for (String fieldName : baseFields) {
                    try {
                        Field field = targetClass.getDeclaredField(fieldName);
                        Method getMethod = obj.getClass().getMethod(MOTHED_GET + toUpperCaseFirstOne(fieldName));
                        Object result = getMethod.invoke(obj);
                        if(result != null) {
                            Method setMethod = targetClass.getMethod(MOTHED_SET + toUpperCaseFirstOne(fieldName), result.getClass());
                            setMethod.invoke(target, result);
                        }
                    } catch (NoSuchFieldException e) {
                        continue;
                    }
                }
            }
            return target;
        } catch (Throwable t) {
            logger.error("转换失败:" + t.getMessage());
            throw new DescribeException(ExceptionEnum.UNKNOW_ERROR);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:35
     * @description: 获取字段值
     * @param: [field, obj]
     * @return: java.lang.Object
     */
    private static Object getFiledValue(Field field, Object obj) throws IllegalAccessException {
        //获取原有的访问权限
        boolean access = field.isAccessible();
        try {
            //设置可访问的权限
            field.setAccessible(true);
            return field.get(obj);
        } finally {
            //恢复访问权限
            field.setAccessible(access);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:35
     * @description: 设置方法值
     * @param: [field, obj, value]
     * @return: void
     */
    private static void setFieldValue(Field field, Object obj, Object value) throws IllegalAccessException {
        //获取原有的访问权限
        boolean access = field.isAccessible();
        try {
            //设置可访问的权限
            field.setAccessible(true);
            field.set(obj, value);
        } finally {
            //恢复访问权限
            field.setAccessible(access);
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:36
     * @description: 转换list
     * @param: [objList, targetClass]
     * @return: java.util.List<T>
     */
    public static <T> List<T> convertPojoList(List objList, Class<T> targetClass) {
        return convertPojoList(objList, targetClass, false);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:36
     * @description: 转换list
     * @param: [objList, targetClass, hasBaseField]
     * @return: java.util.List<T>
     */
    public static <T> List<T> convertPojoList(List objList, Class<T> targetClass, boolean hasBaseField) {
        List<T> list = new ArrayList<T>(objList.size());
        for (Object object : objList) {
            list.add(convertPojo(object, targetClass, hasBaseField));
        }
        return list;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:34
     * @description: 转换分页结果
     * @param: [pageResult, targetClass]
     * @return: PageResult<T>
     */
    public static <T> PageResult<T> convertPojoPageResult(PageResult pageResult, Class<T> targetClass) {
        return convertPojoPageResult(pageResult, targetClass, false);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:33
     * @description: 转换分页结果
     * @param: [pageResult, targetClass, hasBaseField]
     * @return: com.meeting.common.pojo.base.PageResultVO<T>
     */
    public static <T> PageResult<T> convertPojoPageResult(PageResult pageResult, Class<T> targetClass, boolean hasBaseField) {
        PageResult<T> result = new PageResult<T>();
        result.setTotalPage(pageResult.getTotalPage());
        result.setPageNo(pageResult.getPageNo());
        result.setPageSize(pageResult.getPageSize());
        result.setTotalRecord(pageResult.getTotalRecord());
        if(pageResult.getResult() != null && pageResult.getResult().size() > 0) {
            result.setResult(convertPojoList(pageResult.getResult(), targetClass, hasBaseField));
        }
        return result;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:32
     * @description: 设置Map
     * @param: [value, origField, targetField, targetObject]
     * @return: void
     */
    private static <T> void setMap(Map value, Field origField, Field targetField, T targetObject) throws IllegalAccessException, InstantiationException{
        Type origType = origField.getGenericType();
        Type targetType = targetField.getGenericType();
        if (origType instanceof ParameterizedType && targetType instanceof ParameterizedType) {//泛型类型
            ParameterizedType origParameterizedType = (ParameterizedType)origType;
            Type[] origTypes = origParameterizedType.getActualTypeArguments();
            ParameterizedType targetParameterizedType = (ParameterizedType)targetType;
            Type[] targetTypes = targetParameterizedType.getActualTypeArguments();
            if (origTypes != null && origTypes.length == 2 && targetTypes != null && targetTypes.length == 2) {//正常泛型,查看第二个泛型是否不为基本类型
                Class clazz = (Class)origTypes[1];
                if (!isBasicType(clazz) && !clazz.equals(targetTypes[1])) {//如果不是基本类型并且泛型不一致，则需要继续转换
                    Set<Map.Entry> entries = value.entrySet();
                    Map targetMap = value.getClass().newInstance();
                    for (Map.Entry entry : entries) {
                        targetMap.put(entry.getKey(), convertPojo(entry.getValue(), (Class) targetTypes[1]));
                    }
                    setFieldValue(targetField, targetObject, targetMap);
                    return;
                }
            }
        }
        setFieldValue(targetField, targetObject, value);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:32
     * @description: 设置集合
     * @param: [value, origField, targetField, targetObject]
     * @return: void
     */
    private static <T> void setCollection(Collection value, Field origField, Field targetField, T targetObject) throws IllegalAccessException, InstantiationException{
        Type origType = origField.getGenericType();
        Type targetType = targetField.getGenericType();
        if (origType instanceof ParameterizedType && targetType instanceof ParameterizedType) {//泛型类型
            ParameterizedType origParameterizedType = (ParameterizedType)origType;
            Type[] origTypes = origParameterizedType.getActualTypeArguments();
            ParameterizedType targetParameterizedType = (ParameterizedType)targetType;
            Type[] targetTypes = targetParameterizedType.getActualTypeArguments();
            if (origTypes != null && origTypes.length == 1 && targetTypes != null && targetTypes.length == 1) {//正常泛型,查看第二个泛型是否不为基本类型
                Class clazz = (Class)origTypes[0];
                if (!isBasicType(clazz) && !clazz.equals(targetTypes[0])) {//如果不是基本类型并且泛型不一致，则需要继续转换
                    Collection collection = value.getClass().newInstance();
                    for (Object obj : value) {
                        collection.add(convertPojo(obj, (Class) targetTypes[0]));
                    }
                    setFieldValue(targetField, targetObject, collection);
                    return;
                }
            }
        }
        setFieldValue(targetField, targetObject, value);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:32
     * @description: 设置枚举类型
     * @param: [value, origField, targetField, targetObject]
     * @return: void
     */
    private static <T> void setEnum(Enum value, Field origField, Field targetField, T targetObject) throws Exception{
        if (origField.equals(targetField)) {
            setFieldValue(targetField, targetObject, value);
        } else {
            //枚举类型都具有一个static修饰的valueOf方法
            Method method = targetField.getType().getMethod("valueOf", String.class);
            setFieldValue(targetField, targetObject, method.invoke(null, value.toString()));
        }
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:32
     * @description: 设置日期类型
     * @param: [value, targetField, targetFieldType, targetObject, sameType]
     * @return: void
     */
    private static <T> void setDate(Date value, Field targetField, Class targetFieldType, T targetObject, boolean sameType) throws IllegalAccessException {
        Date date = null;
        if (sameType) {
            date = value;
        } else if (targetFieldType.equals(java.sql.Date.class)) {
            date = new java.sql.Date(value.getTime());
        } else if (targetFieldType.equals(Date.class)) {
            date = new Date(value.getTime());
        } else if (targetFieldType.equals(java.sql.Timestamp.class)) {
            date = new java.sql.Timestamp(value.getTime());
        }
        setFieldValue(targetField, targetObject, date);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:31
     * @description: 获取适配方法
     * @param: [clazz, fieldName]
     * @return: java.lang.reflect.Field
     */
    public static Field getTargetField(Class clazz, String fieldName) {
        String classKey = clazz.getName();
        Map<String, Field> fieldMap = cacheFields.get(classKey);
        if (fieldMap == null) {
            fieldMap = new HashMap<String, Field>();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (isStatic(field)) {
                    continue;
                }
                fieldMap.put(field.getName(), field);
            }
            cacheFields.put(classKey, fieldMap);
        }
        return fieldMap.get(fieldName);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:31
     * @description: 确实是否为基础类型
     * @param: [clazz]
     * @return: boolean
     */
    public static boolean isBasicType(Class clazz) {
        return clazz.isPrimitive() || basicClass.contains(clazz);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:31
     * @description: 判断变量是否有静态修饰符static
     * @param: [field]
     * @return: boolean
     */
    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 15:31
     * @description: 首字母转大写
     * @param: [s]
     * @return: java.lang.String
     */
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}
