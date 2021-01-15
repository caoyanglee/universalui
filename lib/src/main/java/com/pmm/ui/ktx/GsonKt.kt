package com.pmm.ui.ktx

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Author:你需要一台永动机
 * Date:2019/3/13 10:33
 * Description:Gson的常用扩展
 */

//全局Gson对象
val mGson by lazy { Gson() }

//参数化类型实现
class ParameterizedTypeImpl(private var clazz: Class<*>) : ParameterizedType {

    //返回原生类型，即 HashMap
    override fun getRawType(): Type = List::class.java

    //示此类型是其成员之一的类型。例如，如果此类型为 O<T>.I<S>，则返回 O<T> 的表示形式。 如果此类型为顶层类型，则返回 null。这里就直接返回null就行了。
    override fun getOwnerType(): Type? = null

    //返回实际类型组成的数据，即new Type[]{String.class,Integer.class}
    override fun getActualTypeArguments(): Array<Type> = arrayOf(clazz)

}


/**
 * 集合转为Json数组
 */
fun <T> List<T>.toJsonArray(): JsonArray = mGson.toJsonTree(this) as JsonArray

/**
 * 集合转为字符串
 */
fun <T> List<T>.toJsonStr(): String = mGson.toJson(this)

/**
 * Json字符串转集合
 * reified 的意思是具体化。 而作为 Kotlin 的一个方法 泛型 关键字，它代表你可以在方法体内访问泛型指定的JVM类对象。
 */
inline fun <reified T> String.toArrayList(): List<T> = mGson.fromJson(this, ParameterizedTypeImpl(T::class.java))

/**
 * 对象转为Json对象
 */
fun Any.toJsonObject(): JsonObject = mGson.toJsonTree(this) as JsonObject

/**
 * 对象转为字符串
 */
fun Any.toJsonStr(): String = mGson.toJson(this)

/**
 * Json字符串转对象
 * reified 的意思是具体化。 而作为 Kotlin 的一个方法 泛型 关键字，它代表你可以在方法体内访问泛型指定的JVM类对象。
 */
inline fun <reified T> String.toObject(): T? = mGson.fromJson(this, T::class.java)

/**
 * Map转为字符串
 */
fun <T, E> Map<T, E>.toJsonStr(): String = mGson.toJson(this)

/**
 * json字符串转Map
 */
fun <T, E> String.toMap(): Map<T, E>? = mGson.fromJson(this, object : TypeToken<Map<T, E>>() {}.type) as Map<T, E>?
