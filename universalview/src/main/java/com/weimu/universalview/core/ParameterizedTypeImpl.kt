package com.weimu.universalview.core

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author:你需要一台永动机
 * Date:2019-08-15 10:36
 * Description:参数化类型实现
 */
class ParameterizedTypeImpl(private var clazz: Class<*>) : ParameterizedType {

    //返回原生类型，即 HashMap
    override fun getRawType(): Type = List::class.java

    //示此类型是其成员之一的类型。例如，如果此类型为 O<T>.I<S>，则返回 O<T> 的表示形式。 如果此类型为顶层类型，则返回 null。这里就直接返回null就行了。
    override fun getOwnerType(): Type? = null

    //返回实际类型组成的数据，即new Type[]{String.class,Integer.class}
    override fun getActualTypeArguments(): Array<Type> = arrayOf(clazz)

}