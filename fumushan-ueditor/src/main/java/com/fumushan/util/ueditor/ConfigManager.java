package com.fumushan.util.ueditor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fumushan.util.ueditor.define.ActionMap;

/**
 * 配置管理器
 * 
 * @author hancong03@baidu.com
 */
@SuppressWarnings("unused")
public final class ConfigManager {

	private static final String configFileName = "config.json";
	private String parentPath = null;
	private JSONObject jsonConfig = null;
	// 涂鸦上传filename定义
	private final static String SCRAWL_FILE_NAME = "scrawl";
	// 远程图片抓取filename定义
	private final static String REMOTE_FILE_NAME = "remote";

	
	private ConfigManager() throws FileNotFoundException, IOException {
		String configPath = this.getClass().getClassLoader().getResource(configFileName).getPath();//获取资源路径
		String configContent = this.readFile(configPath); // 读取classPath下的config.json文件
		this.jsonConfig = new JSONObject(configContent);
	}

	public static ConfigManager getInstance() {
		try {
			return new ConfigManager();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	// 验证配置文件加载是否正确
	public boolean valid() {
		return this.jsonConfig != null;
	}
	

	//获取全部的配置文件信息
	public JSONObject getAllConfig() {
		return this.jsonConfig;
	}

	//获取部分配置文件信息
	public Map<String, Object> getConfig(int type) {

		Map<String, Object> conf = new HashMap<String, Object>();
		String savePath = null;

		switch (type) {

		case ActionMap.UPLOAD_FILE:
			conf.put("isBase64", "false");
			conf.put("maxSize", this.jsonConfig.getLong("fileMaxSize"));
			conf.put("allowFiles", this.getArray("fileAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
			savePath = this.jsonConfig.getString("filePathFormat");
			break;

		case ActionMap.UPLOAD_IMAGE:
			conf.put("isBase64", "false");
			conf.put("maxSize", this.jsonConfig.getLong("imageMaxSize"));
			conf.put("allowFiles", this.getArray("imageAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
			savePath = this.jsonConfig.getString("imagePathFormat");
			break;

		case ActionMap.UPLOAD_VIDEO:
			conf.put("maxSize", this.jsonConfig.getLong("videoMaxSize"));
			conf.put("allowFiles", this.getArray("videoAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
			savePath = this.jsonConfig.getString("videoPathFormat");
			break;

		case ActionMap.UPLOAD_SCRAWL:
			conf.put("filename", ConfigManager.SCRAWL_FILE_NAME);
			conf.put("maxSize", this.jsonConfig.getLong("scrawlMaxSize"));
			conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
			conf.put("isBase64", "true");
			savePath = this.jsonConfig.getString("scrawlPathFormat");
			break;

		case ActionMap.CATCH_IMAGE:
			conf.put("filename", ConfigManager.REMOTE_FILE_NAME);
			conf.put("filter", this.getArray("catcherLocalDomain"));
			conf.put("maxSize", this.jsonConfig.getLong("catcherMaxSize"));
			conf.put("allowFiles", this.getArray("catcherAllowFiles"));
			conf.put("fieldName", this.jsonConfig.getString("catcherFieldName") + "[]");
			savePath = this.jsonConfig.getString("catcherPathFormat");
			break;

		case ActionMap.LIST_IMAGE:
			conf.put("allowFiles", this.getArray("imageManagerAllowFiles"));
			conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
			conf.put("count", this.jsonConfig.getInt("imageManagerListSize"));
			break;

		case ActionMap.LIST_FILE:
			conf.put("allowFiles", this.getArray("fileManagerAllowFiles"));
			conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
			conf.put("count", this.jsonConfig.getInt("fileManagerListSize"));
			break;
		}

		conf.put("savePath", savePath);
		conf.put("rootPath", this.jsonConfig.getInt("rootPath"));
		conf.put("pictureRediect", this.jsonConfig.getInt("pictureRediect"));

		return conf;

	}

	// 获取根路径
	public static String getRootPath(HttpServletRequest request, Map<String, Object> conf) {
		Object rootPath = request.getAttribute("rootPath");
		if (rootPath != null) {
			return rootPath + "" + File.separatorChar;
		} else {
			return conf.get("rootPath") + "";
		}
	}

	// 获取配置路径
	private String getConfigPath() {
		String path = this.getClass().getResource("/").getPath() + ConfigManager.configFileName;
		if (new File(path).exists()) {
			return path;
		} else {
			return this.parentPath + File.separator + ConfigManager.configFileName;
		}
	}

	// 解析json文件转换成数组
	private String[] getArray(String key) {
		JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
		String[] result = new String[jsonArray.length()];
		for (int i = 0, len = jsonArray.length(); i < len; i++) {
			result[i] = jsonArray.getString(i);
		}
		return result;
	}

	// 读取文件内容
	private String readFile(String path) throws IOException {
		StringBuilder builder = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
			BufferedReader bfReader = new BufferedReader(reader);
			String tmpContent = null;
			while ((tmpContent = bfReader.readLine()) != null) {
				builder.append(tmpContent);
			}
			bfReader.close();
		} catch (UnsupportedEncodingException e) {
			// 忽略
		}
		return this.filter(builder.toString());
	}

	// 过滤字符串
	private String filter(String input) {
		return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
	}

}
