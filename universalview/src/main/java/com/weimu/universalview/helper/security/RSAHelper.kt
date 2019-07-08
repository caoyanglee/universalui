package com.weimu.universalview.helper.security

import android.util.Base64
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.math.BigInteger
import java.security.*
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPublicKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


/**
 * Author:你需要一台永动机
 * Date:2017/12/27 15:38
 * Description:
 */
object RSAHelper {
    //Rsa签名和Rsa解密加密 是不同的
    private val TAG = "ras"
    private val RSA = "RSA"
    private val CHAR_SET = "utf-8"
    private val SIGN_ALGORITHMS = "MD5WithRSA"


    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     */
    fun generateRSAKeyPair(keyLength: Int = 1024): KeyPair {
        if (!(keyLength in 512..2048)) throw IllegalArgumentException("keyLength must in 512..2048")
        val kpg = KeyPairGenerator.getInstance(RSA)
        kpg.initialize(keyLength)
        return kpg.genKeyPair()
    }

    /**
     * 签名
     */
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

    /**
     * 校验
     */
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


    /**
     * 用公钥加密
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     * 需加密数据的byte数据
     * @param pubKey
     * 公钥
     * @return 加密后的byte型数据
     */
    fun encrypt(data: ByteArray, publicKey: PublicKey): ByteArray? {
        try {
            val cipher = Cipher.getInstance(RSA)
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 用公钥加密
     */
    fun encrypt(data: String, publicKey: String): String {
        val byte = encrypt(data.toByteArray(), loadPublicKey(publicKey))
                ?: return ""
        return Base64Helper.encode(byte)
    }

    /**
     * 用私钥解密
     *
     * @param encryptedData
     * 经过encryptedData()加密返回的byte数据
     * @param privateKey
     * 私钥
     * @return
     */
    fun decrypt(encryptedData: ByteArray, privateKey: PrivateKey): ByteArray? {
        try {
            val cipher = Cipher.getInstance(RSA)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)
            return cipher.doFinal(encryptedData)
        } catch (e: Exception) {
            return null
        }
    }

    /**
     * 用私钥解密
     */
    fun decrypt(encryptedData: String, privateKey: String): String {
        val decodedBytes = Base64Helper.decode(encryptedData) ?: return ""
        val originBytes = decrypt(decodedBytes, loadPrivateKey(privateKey))
                ?: return ""
        return String(originBytes)
    }

    /**
     * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPublicKey(keyBytes: ByteArray): PublicKey {
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param keyBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivateKey(keyBytes: ByteArray): PrivateKey {
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePrivate(keySpec)
    }

    /**
     * 使用N、d值还原私钥
     *
     * @param modulus
     * @param privateExponent
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun getPrivateKey(modulus: String, privateExponent: String): PrivateKey {
        val bigIntModulus = BigInteger(modulus)
        val bigIntPrivateExponent = BigInteger(privateExponent)
        val keySpec = RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent)
        val keyFactory = KeyFactory.getInstance(RSA)
        return keyFactory.generatePrivate(keySpec)
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     * 公钥数据字符串
     * @throws Exception
     * 加载公钥时产生的异常
     */
    @Throws(Exception::class)
    fun loadPublicKey(publicKeyStr: String): PublicKey {
        try {

            val buffer = Base64Helper.decode(publicKeyStr)
            val keyFactory = KeyFactory.getInstance(RSA)
            val keySpec = X509EncodedKeySpec(buffer)
            return keyFactory.generatePublic(keySpec) as RSAPublicKey
        } catch (e: NoSuchAlgorithmException) {
            throw Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw Exception("公钥非法")
        } catch (e: NullPointerException) {
            throw Exception("公钥数据为空")
        }

    }

    /**
     * 从字符串中加载私钥<br></br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun loadPrivateKey(privateKeyStr: String): PrivateKey {
        try {
            val buffer = Base64Helper.decode(privateKeyStr)
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            val keySpec = PKCS8EncodedKeySpec(buffer)
            val keyFactory = KeyFactory.getInstance(RSA)
            return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
        } catch (e: NoSuchAlgorithmException) {
            throw Exception("无此算法")
        } catch (e: InvalidKeySpecException) {
            throw Exception("私钥非法")
        } catch (e: NullPointerException) {
            throw Exception("私钥数据为空")
        }

    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in
     * 公钥输入流
     * @throws Exception
     * 加载公钥时产生的异常
     */
    @Throws(Exception::class)
    fun loadPublicKey(`in`: InputStream): PublicKey {
        try {
            return loadPublicKey(readKey(`in`))
        } catch (e: IOException) {
            throw Exception("公钥数据流读取错误")
        } catch (e: NullPointerException) {
            throw Exception("公钥输入流为空")
        }

    }

    /**
     * 从文件中加载私钥
     *
     * @param keyFileName
     * 私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    @Throws(Exception::class)
    fun loadPrivateKey(`in`: InputStream): PrivateKey {
        try {
            return loadPrivateKey(readKey(`in`))
        } catch (e: IOException) {
            throw Exception("私钥数据读取错误")
        } catch (e: NullPointerException) {
            throw Exception("私钥输入流为空")
        }

    }

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun readKey(`in`: InputStream): String {
        val br = BufferedReader(InputStreamReader(`in`))
        var readLine: String? = null
        val sb = StringBuilder()
        while (true) {
            readLine = br.readLine()
            if (readLine == null) break
            if (readLine[0] == '-') {
                continue
            } else {
                sb.append(readLine)
                sb.append('\r')
            }
        }

        return sb.toString()
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    fun printPublicKeyInfo(publicKey: PublicKey) {
        val rsaPublicKey = publicKey as RSAPublicKey
        Log.d(TAG, """
            ----------RSAPublicKey----------
            Modulus.length=" + ${rsaPublicKey.modulus.bitLength()}
            Modulus=${rsaPublicKey.modulus}
            PublicExponent.length=${rsaPublicKey.publicExponent.bitLength()}
            PublicExponent=${rsaPublicKey.publicExponent}
        """.trimIndent())
    }

    /**
     * 打印私钥信息
     *
     * @param publicKey
     */
    fun printPrivateKeyInfo(privateKey: PrivateKey) {
        val rsaPrivateKey = privateKey as RSAPrivateKey
        Log.d(TAG, """
            ----------RSAPrivateKey----------
            Modulus.length=" + ${rsaPrivateKey.modulus.bitLength()}
            Modulus=${rsaPrivateKey.modulus}
            PrivateExponent.length=${rsaPrivateKey.privateExponent.bitLength()}
            PrivatecExponent=${rsaPrivateKey.privateExponent}
        """.trimIndent())
    }


}