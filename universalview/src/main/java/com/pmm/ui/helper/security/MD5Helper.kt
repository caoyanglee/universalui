package com.pmm.ui.helper.security

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Author:你需要一台永动机
 * Date:2017/12/27 14:56
 * Description:
 */
object MD5Helper {

    //Md5签名
    //slat可以是固定的值，也可以是content的哈希值，也可是随机值
    fun sign(content: String, slat: String = ""): String {
        val targetContent = if (slat.isNotBlank()) "$content&slat=$slat" else content
        return md5Digest(targetContent.toByteArray())
    }

    //MD5校验
    fun doCheck(content: String, sign: String, slat: String = ""): Boolean {
        val targetContent = if (slat.isNotBlank()) "$content&slat=$slat" else content
        return md5Digest(targetContent.toByteArray()) == sign
    }

    //MD5 摘要计算(byte[]).
    private fun md5Digest(src: ByteArray): String {
        val alg: MessageDigest
        try {
            // MD5 is 32 bit message digest
            alg = MessageDigest.getInstance("MD5")
            val bytes = alg.digest(src)
            val result = StringBuilder()
            for (b in bytes) {
                var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
                if (temp.length == 1) {
                    temp = "0" + temp
                }
                result.append(temp)
            }
            return result.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}