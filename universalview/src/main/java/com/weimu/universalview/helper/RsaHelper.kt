package com.weimu.universalview.helper

import android.util.Base64
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

/**
 * Author:你需要一台永动机
 * Date:2017/12/27 15:38
 * Description:
 */
object RsaHelper {
    //Rsa签名和Rsa解密加密 是不同的

    val RSA = "RSA"
    val CHAR_SET = "utf-8"
    val SIGN_ALGORITHMS = "MD5WithRSA"


    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     */
    fun generateRSAKeyPair(keyLength: Int = 1024): KeyPair? {
        val kpg = KeyPairGenerator.getInstance(RSA)
        kpg.initialize(keyLength)
        return kpg.genKeyPair()
    }

    fun sign(content: String, privateKey: String): String? {

        try {
            val priPKCS8 = PKCS8EncodedKeySpec(Base64.decode(privateKey, Base64.DEFAULT))
            val keyf = KeyFactory.getInstance(RSA)
            val priKey = keyf.generatePrivate(priPKCS8)

            val signature = Signature.getInstance(SIGN_ALGORITHMS)

            signature.initSign(priKey)
            signature.update(content.toByteArray(charset(CHAR_SET)))

            val signed = signature.sign()

            return String(Base64.encode(signed, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun doCheck(content: String, sign: String, publicKey: String): Boolean {
        try {
            val keyFactory = KeyFactory.getInstance(RSA)
            val encodedKey = Base64.decode(publicKey, Base64.DEFAULT)
            val pubKey = keyFactory.generatePublic(X509EncodedKeySpec(encodedKey))

            val signature = Signature.getInstance(SIGN_ALGORITHMS)

            signature.initVerify(pubKey)
            signature.update(content.toByteArray(charset(CHAR_SET)))


            return signature.verify(Base64.decode(sign, Base64.DEFAULT))

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

}