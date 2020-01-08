package com.pmm.ui.helper.security

import java.lang.IllegalArgumentException
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * Author:你需要一台永动机
 * Date:2019-07-08 15:05
 * Description:此处使用AES-128-ECB加密模式，key需要为16位
 * 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
 * AES/CBC/NoPadding             16                          不支持
 * AES/CBC/PKCS5Padding          32                          16
 * AES/CBC/ISO10126Padding       32                          16
 * AES/CFB/NoPadding             16                          原始数据长度
 * AES/CFB/PKCS5Padding          32                          16
 * AES/CFB/ISO10126Padding       32                          16
 * AES/ECB/NoPadding             16                          不支持
 * AES/ECB/PKCS5Padding          32                          16
 * AES/ECB/ISO10126Padding       32                          16
 * AES/OFB/NoPadding             16                          原始数据长度
 * AES/OFB/PKCS5Padding          32                          16
 * AES/OFB/ISO10126Padding       32                          16
 * AES/PCBC/NoPadding            16                          不支持
 * AES/PCBC/PKCS5Padding         32                          16
 * AES/PCBC/ISO10126Padding      32                          16
 */
object AESHelper {

    // 加密
    @Throws(Exception::class)
    fun encrypt(sSrc: String, sKey: String?): String {
        if (sKey == null) {
            throw IllegalArgumentException("Key为空null")
        }
        // 判断Key是否为16位
        if (sKey.length != 16) {
            throw IllegalArgumentException("Key长度不是16位")
        }
        val raw = sKey.toByteArray(Charsets.UTF_8)
        val skeySpec = SecretKeySpec(raw, "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val encrypted = cipher.doFinal(sSrc.toByteArray(charset("utf-8")))
        return Base64Helper.encode(encrypted)//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    @Throws(Exception::class)
    fun decrypt(sSrc: String, sKey: String?): String {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                throw IllegalArgumentException("Key为空null")
            }
            // 判断Key是否为16位
            if (sKey.length != 16) {
                throw IllegalArgumentException("Key长度不是16位")
            }
            val raw = sKey.toByteArray(Charsets.UTF_8)
            val skeySpec = SecretKeySpec(raw, "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec)
            val encrypted1 = Base64Helper.decode(sSrc)//先用base64解密
            try {
                val original = cipher.doFinal(encrypted1)
                return String(original, Charsets.UTF_8)
            } catch (e: Exception) {
                println(e.toString())
                return ""
            }

        } catch (ex: Exception) {
            return ""
        }

    }
}