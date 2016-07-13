package cn.lts.mobile.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTools {
    
    private final static Logger logger = LoggerFactory.getLogger(FileTools.class);

    public static void uploadFile(File file, String toFilePath) throws FileNotFoundException, IOException {
        InputStream is = null;
        BufferedOutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new BufferedOutputStream(new FileOutputStream(toFilePath));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (os != null)
                os.close();
            if (is != null)
                is.close();
        }
    }

    public static void uploadFile(InputStream fis, String toFilePath) throws FileNotFoundException, IOException {
        InputStream is = null;
        BufferedOutputStream os = null;
        try {
            is = fis;
            os = new BufferedOutputStream(new FileOutputStream(toFilePath));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (os != null)
                os.close();
            if (is != null)
                is.close();
        }
    }

    public static String getFileContent(String filePath) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = in.readLine()) != null) {
                if (temp.trim().length() > 0) {
                    sb.append(temp).append("\n");
                }
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }

    }

    public static String getFileContent(String filePath, long line) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            long currentLine = 0;
            while ((temp = in.readLine()) != null && currentLine < line) {
                if (temp.trim().length() > 0) {
                    sb.append(temp).append("\n");
                    ++currentLine;
                }
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }

    }

    public static String getFileContentForUploadPlan(String filePath, String filterString) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = in.readLine()) != null) {
                if (temp.trim().length() > 0) {
                    temp = temp.trim();
                    String[] filterCode = filterString.split("#");
                    for (int i = 0; i < filterCode.length; i++) {
                        temp = temp.replaceAll(filterCode[i], "");
                    }
                    sb.append(temp.trim()).append("\n");
                }
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }

    }

    public static String getFileContent(File file) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = in.readLine()) != null) {
                sb.append(temp).append("\n");
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static String getFileContent(File file, String charset) throws Exception {
		BufferedReader in = null;
		try {
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is, charset);
			in = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = in.readLine()) != null) {
				sb.append(temp).append("\r\n");
			}
			return sb.toString();
		} finally {
			if (in != null)
				in.close();
		}
	}
    public static String getFileContent(File file, long line) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String temp = null;
            long currentLine = 0;
            while ((temp = in.readLine()) != null && currentLine < line) {
                if (temp.trim().length() > 0) {
                    sb.append(temp).append("\n");
                    ++currentLine;
                }
            }
            return sb.toString();
        } finally {
            if (in != null)
                in.close();
        }
    }

    public static void setFileContent(String content, String filePath) throws Exception {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath);
            fw.write(content);
        } finally {
            if (fw != null) {
                fw.close();
            }
        }

    }
    
    /**
     * 原子性操作修改文件内容
     * @param content
     * @param filePath
     * @throws Exception
     * @create_time 2013-1-16 上午9:44:53
     */
    public static void setFileContentAtomic(String content, String filePath) throws Exception {
        File tmpFile = null;
        FileWriter fw = null;
        try {
            tmpFile = new File(filePath+".tmp");
            fw = new FileWriter(tmpFile);
            fw.write(content);
            fw.flush();
        } finally {
            if (fw != null) {
                fw.close();
            }
        }
        if (tmpFile!=null) {
          //now rename temp file to desired name, this operation is  atomic operation under most os
          if(!tmpFile.renameTo(new File(filePath))) {
              //we may want to retry if move fails
              logger.error("move fails filePath:"+filePath);
              tmpFile.delete();
              FileTools.setFileContent(content, filePath);
          }
        }
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    public static boolean mkdir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    public static boolean mkdirs(String filePath) {
        File file = new File(filePath);
        return file.mkdirs();
    }

    public static void deleteDirectory(File dir) throws IOException {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir + " is not a directory. ");
        }
        File[] entries = dir.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (entries[i].isDirectory()) {
                deleteDirectory(entries[i]);
            } else {
                if (!entries[i].delete()) {
                    throw new IllegalArgumentException(entries[i] + " can not be delete. ");
                }
                ;
            }
        }
        if (!dir.delete()) {
            throw new IllegalArgumentException("Argument " + dir + " can not be delete. ");
        }
    }

    public static void buildHtml(String target_url, String save_path) throws FileNotFoundException, IOException {
        try {
            URL openurl = new URL(target_url);
            URLConnection urlConn = openurl.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(false);
            urlConn.setUseCaches(false);
            urlConn.connect();
            InputStream is = urlConn.getInputStream();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(save_path));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
            os = null;
            is = null;

        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    public static String getFileExt(String name) {
        if (name.lastIndexOf(".") == -1) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1, name.length());
    }

    public static String getFileName(String name) {
        if (name.lastIndexOf(".") == -1) {
            return "";
        }
        return name.substring(0, name.lastIndexOf("."));

    }

    /**
     * 读取文件得到注数
     * 
     */
    public static int getFileCount(String filePath) throws Exception {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(filePath));
            int count = 0;
            String line = "";
            while ((line = in.readLine()) != null) {
                count++;
            }
            return count;
        } catch (FileNotFoundException fnfe) {
            throw fnfe;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (in != null)
                in.close();
        }

    }

    public static String getRandomFileName() {
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = fmt.format(dt);
        fileName = fileName + ".txt"; //extension,   you   can   change   it.   
        return fileName;
    }

    /**
     * 获取文件内容
     * @param filePath
     * @param charSet
     * @return
     * @throws Exception
     * @create_time 2011-7-20 下午03:58:46
     */
    public static String getFileContentByCharSet(String filePath, String charSet) throws Exception {
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            File file = new File(filePath);
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, charSet);
            in = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String temp = null;
            while ((temp = in.readLine()) != null) {
                if (temp.trim().length() > 0) {
                    sb.append(temp).append("\n");
                }
            }
            return sb.toString();
        } finally {
            if (in != null) {
                in.close();
            }
            if (null != isr) {
                isr.close();
            }
            if (null != is) {
                is.close();
            }
        }
    }
}