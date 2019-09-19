package com.pmm.ui.helper

import android.app.Activity
import android.content.CursorLoader
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import java.io.*
import java.util.*


object FileHelper {

    private val GB: Long = 1073741824 // 1024 * 1024 * 1024
    private val MB: Long = 1048576 // 1024 * 1024
    private val KB: Long = 1024


    private val FILENAME_REGIX = "^[^\\/?\"*:<>\\]{1,255}$"

    //存放常用地址
    val EXTERNAL_STORAGE_DIR = Environment.getExternalStorageDirectory()//外置存储卡  /storage/emulated/0

    /**
     * 是否存在文件
     * @param filePath文件路径
     * @return 文件是否存在
     */
    fun isExist(path: String): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }
        val file = File(path)
        return file.exists()
    }


    /**
     * 把uri转为File对象
     *
     * @param activity Activity
     * @param uri      文件Uri
     * @return File对象
     */
    fun uri2File(activity: Activity, uri: Uri): File {
        if (Build.VERSION.SDK_INT < 11) {
            // 在API11以下可以使用：managedQuery
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val actualimagecursor = activity.managedQuery(uri, proj, null, null, null)
            val actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            actualimagecursor.moveToFirst()
            val img_path = actualimagecursor
                    .getString(actual_image_column_index)
            return File(img_path)
        } else {
            // 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val loader = CursorLoader(activity, uri, projection, null, null, null)
            val cursor = loader.loadInBackground()
            val column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return File(cursor.getString(column_index))
        }
    }


    //Bitmap对象保存为图片文件
    fun saveBitmapFile(
            bitmap: Bitmap,
            filePath: String,
            format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
            quality: Int = 100
    ): File {
        val file = File(filePath)//将要保存图片的路径
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(format, quality, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }


    /**
     * 创建文件夹
     * @param filePath 文件夹地址
     * @return 是否创建成功
     */
    fun makeDirs(dirPath: String): Boolean {
        val folderName = getFolderName(dirPath)
        if (TextUtils.isEmpty(folderName)) {
            return false
        }

        val folder = File(folderName!!)
        return folder.exists() && folder.isDirectory() || folder.mkdirs()
    }

    /**
     * 获取路径名
     * @param filePath 文件夹地址
     * @return 路径名称
     */
    fun getFolderName(filePath: String): String? {

        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val filePosi = filePath.lastIndexOf(File.separator)
        return if (filePosi == -1) "" else filePath.substring(0, filePosi)
    }


    /**
     * 获取文件大小获取
     *
     * @param file File对象
     * @return 文件大小字符串
     */
    fun getFileSize(file: File): String {
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(file)
            val length = fis.available()
            return if (length >= GB) {
                String.format("%.2f GB", length * 1.0 / GB)
            } else if (length >= MB) {
                String.format("%.2f MB", length * 1.0 / MB)
            } else {
                String.format("%.2f KB", length * 1.0 / KB)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fis?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return "0"
    }


    /**
     * read file
     *
     * @param filePath    文件路径
     * @param charsetName The name of a supported [                    &lt;/code&gt;charset&lt;code&gt;][java.nio.charset.Charset]
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    fun readFile(filePath: String, charsetName: String): StringBuilder? {
        val file = File(filePath)
        val fileContent = StringBuilder("")
        if (!file.isFile) {
            return null
        }

        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(
                    file), charsetName)
            reader = BufferedReader(`is`)
            var line: String
            while (true) {
                val line = reader.readLine() ?: break
                if (fileContent.toString() != "") {
                    fileContent.append("\r\n")
                }
                fileContent.append(line)
            }
            reader.close()
            return fileContent
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    throw RuntimeException("IOException occurred. ", e)
                }

            }
        }
    }


    /**
     * read file to string list, a element of list is a line
     * 按行读取文件，并返回List<String>的集合
     *
     * @param filePath    文件路径
     * @param charsetName 编码方式
     * The name of a supported [                    &lt;/code&gt;charset&lt;code&gt;][java.nio.charset.Charset]
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    fun readFileToList(filePath: String, charsetName: String): List<String>? {
        val file = File(filePath)
        val fileContent = ArrayList<String>()
        if (!file.isFile) {
            return null
        }

        var reader: BufferedReader? = null
        try {
            val `is` = InputStreamReader(FileInputStream(
                    file), charsetName)
            reader = BufferedReader(`is`)
            var line: String? = null
            while (true) {
                val line = reader.readLine()
                if ((line) == null) break
                fileContent.add(line)
            }
            reader.close()
            return fileContent
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    throw RuntimeException("IOException occurred. ", e)
                }

            }
        }
    }


    /**
     * 输入流转byte[]
     *
     * @param inStream InputStream
     * @return Byte数组
     */
    fun input2byte(inStream: InputStream?): ByteArray? {
        if (inStream == null)
            return null
        val swapStream = ByteArrayOutputStream()
        val buff = ByteArray(100)
        var rc = 0
        try {
            while (true) {
                val rc = inStream.read(buff, 0, 100)
                if (rc > 0)
                    swapStream.write(buff, 0, rc)
                else
                    break
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return swapStream.toByteArray()
    }


    /**
     * get file name from path, include suffix
     * 获取文件名称（文件名+后缀）
     *
     *
     * <pre>
     * getFileName(null)               =   null
     * getFileName("")                 =   ""
     * getFileName("   ")              =   "   "
     * getFileName("a.mp3")            =   "a.mp3"
     * getFileName("a.b.rmvb")         =   "a.b.rmvb"
     * getFileName("abc")              =   "abc"
     * getFileName("c:\\")              =   ""
     * getFileName("c:\\a")             =   "a"
     * getFileName("c:\\a.b")           =   "a.b"
     * getFileName("c:a.txt\\a")        =   "a"
     * getFileName("/home/admin")      =   "admin"
     * getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
    </pre> *
     *
     * @param filePath 文件路径
     * @return file name from path, include suffix
     */
    fun getFileName(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val filePosi = filePath.lastIndexOf(File.separator)
        return if (filePosi == -1) filePath else filePath.substring(filePosi + 1)
    }


    /**
     * get file name from path, not include suffix
     * 获取文件名称（文件名）
     *
     *
     * <pre>
     * getFileNameWithoutExtension(null)               =   null
     * getFileNameWithoutExtension("")                 =   ""
     * getFileNameWithoutExtension("   ")              =   "   "
     * getFileNameWithoutExtension("abc")              =   "abc"
     * getFileNameWithoutExtension("a.mp3")            =   "a"
     * getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     * getFileNameWithoutExtension("c:\\")              =   ""
     * getFileNameWithoutExtension("c:\\a")             =   "a"
     * getFileNameWithoutExtension("c:\\a.b")           =   "a"
     * getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     * getFileNameWithoutExtension("/home/admin")      =   "admin"
     * getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
    </pre> *
     *
     * @param filePath 文件路径
     * @return file name from path, not include suffix
     * @see
     */
    fun getFileNameWithoutSuffix(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val extenPosi = filePath.lastIndexOf("")
        val filePosi = filePath.lastIndexOf(File.separator)
        if (filePosi == -1) {
            return if (extenPosi == -1)
                filePath
            else
                filePath.substring(0,
                        extenPosi)
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1)
        }
        return if (filePosi < extenPosi)
            filePath.substring(filePosi + 1,
                    extenPosi)
        else
            filePath.substring(filePosi + 1)
    }


    /**
     * get suffix of file from path
     * 获取文件后缀
     *
     * <pre>
     * getFileExtension(null)               =   ""
     * getFileExtension("")                 =   ""
     * getFileExtension("   ")              =   "   "
     * getFileExtension("a.mp3")            =   "mp3"
     * getFileExtension("a.b.rmvb")         =   "rmvb"
     * getFileExtension("abc")              =   ""
     * getFileExtension("c:\\")              =   ""
     * getFileExtension("c:\\a")             =   ""
     * getFileExtension("c:\\a.b")           =   "b"
     * getFileExtension("c:a.txt\\a")        =   ""
     * getFileExtension("/home/admin")      =   ""
     * getFileExtension("/home/admin/a.txt/b")  =   ""
     * getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
    </pre> *
     *
     * @param filePath 文件路径
     */
    fun getFileSuffix(filePath: String): String? {
        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }

        val extenPosi = filePath.lastIndexOf("")
        val filePosi = filePath.lastIndexOf(File.separator)
        if (extenPosi == -1) {
            return ""
        }
        return if (filePosi >= extenPosi) "" else filePath.substring(extenPosi + 1)
    }


    /**
     * write file
     *
     * @param filePath 文件路径
     * @param content  内容
     * @param append   is append, if true, write to the end of file, else clear
     * content of file and write into it
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    fun writeFile(filePath: String, content: String,
                  append: Boolean): Boolean {
        if (TextUtils.isEmpty(content)) {
            return false
        }

        var fileWriter: FileWriter? = null
        try {
            makeDirs(filePath)
            fileWriter = FileWriter(filePath, append)
            fileWriter.write(content)
            fileWriter.close()
            return true
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close()
                } catch (e: IOException) {
                    throw RuntimeException("IOException occurred. ", e)
                }

            }
        }
    }

    /**
     * write file
     *
     * @param filePath    文件路径
     * @param contentList 内容List
     * @param append      is append, if true, write to the end of file, else clear
     * content of file and write into it
     * @return return false if contentList is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    fun writeFile(filePath: String, contentList: List<String>?,
                  append: Boolean): Boolean {
        if (contentList == null || contentList.size < 1) {
            return false
        }

        var fileWriter: FileWriter? = null
        try {
            makeDirs(filePath)
            fileWriter = FileWriter(filePath, append)
            var i = 0
            for (line in contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n")
                }
                fileWriter.write(line)
            }
            fileWriter.close()
            return true
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close()
                } catch (e: IOException) {
                    throw RuntimeException("IOException occurred. ", e)
                }

            }
        }
    }

    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath 文件路径
     * @param content  内容
     * @return 执行结果
     */
    fun writeFile(filePath: String, content: String): Boolean {
        return writeFile(filePath, content, false)
    }

    /**
     * write file, the string list will be written to the begin of the file
     *
     * @param filePath    文件路径
     * @param contentList 内容List
     * @return 执行结果
     */
    fun writeFile(filePath: String, contentList: List<String>): Boolean {
        return writeFile(filePath, contentList, false)
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath 文件路径
     * @param stream   InputStream
     * @return 执行结果
     * @see {@link .writeFile
     */
    fun writeFile(filePath: String, stream: InputStream): Boolean {
        return writeFile(filePath, stream, false)
    }

    /**
     * write file
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if `true`, then bytes will be written to the end of
     * the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    fun writeFile(filePath: String?, stream: InputStream,
                  append: Boolean): Boolean {
        return writeFile(if (filePath != null) File(filePath) else null, stream, append)
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file   File对象
     * @param stream InputStream
     * @return
     * @see {@link .writeFile
     */
    fun writeFile(file: File, stream: InputStream): Boolean {
        return writeFile(file, stream, false)
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if `true`, then bytes will be written to the end of
     * the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    fun writeFile(file: File?, stream: InputStream, append: Boolean): Boolean {
        if (file == null) return false
        var o: OutputStream? = null
        try {
            makeDirs(file.absolutePath)
            o = FileOutputStream(file, append)
            val data = ByteArray(1024)
            var length = -1

            while (true) {
                val length = stream.read(data)
                if (length == -1) break
                o.write(data, 0, length)
            }
            o.flush()
            return true
        } catch (e: FileNotFoundException) {
            throw RuntimeException("FileNotFoundException occurred. ", e)
        } catch (e: IOException) {
            throw RuntimeException("IOException occurred. ", e)
        } finally {
            if (o != null) {
                try {
                    o.close()
                    stream.close()
                } catch (e: IOException) {
                    throw RuntimeException("IOException occurred. ", e)
                }

            }
        }
    }

    /**
     * copy file
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     * @return 执行结果
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    fun copyFile(sourceFilePath: String, destFilePath: String): Boolean {
        var inputStream: InputStream? = null
        try {
            inputStream = FileInputStream(sourceFilePath)
        } catch (e: FileNotFoundException) {
            throw RuntimeException("FileNotFoundException occurred. ", e)
        }

        return writeFile(destFilePath, inputStream)
    }


    /**
     * delete file or directory
     * 删除文件或目录
     *
     * if path is null or empty, return true
     * if path not exist, return true
     * if path exist, delete recursion. return true
     *
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    fun deleteFile(path: String): Boolean {
        if (TextUtils.isEmpty(path)) {
            return true
        }

        val file = File(path)
        if (!file.exists()) {
            return true
        }
        if (file.isFile) {
            return file.delete()
        }
        if (!file.isDirectory) {
            return false
        }
        for (f in file.listFiles()) {
            if (f.isFile) {
                f.delete()
            } else if (f.isDirectory) {
                deleteFile(f.absolutePath)
            }
        }
        return file.delete()
    }


    /**
     * 重命名文件和文件夹
     *
     * @param file        File对象
     * @param newFileName 新的文件名
     * @return 执行结果
     */
    fun renameFile(file: File, newFileName: String): Boolean {
        if (newFileName.matches(FILENAME_REGIX.toRegex())) {
            var newFile: File? = null
            if (file.isDirectory) {
                newFile = File(file.parentFile, newFileName)
            } else {
                val temp = newFileName + file.name.substring(
                        file.name.lastIndexOf('.'))
                newFile = File(file.parentFile, temp)
            }
            if (file.renameTo(newFile)) {
                return true
            }
        }
        return false
    }

}