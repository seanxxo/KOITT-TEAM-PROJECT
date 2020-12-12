package koitt.ratta.doeat.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import koitt.ratta.doeat.config.UploadPath;
import koitt.ratta.doeat.service.FileNamingEncoder;

@Controller
public class CK5ImageUploadController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private FileNamingEncoder fileNamingEncoder;

	/**
	 * ck editor5 파일 업로드 이벤트 처리
	 * 
	 * @param model
	 * @param fileload
	 * @return
	 */
	@RequestMapping(value = "/imageUploadCK5", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String fileUpload(Model model, @RequestParam(value = "upload", required = false) MultipartFile fileload,
			HttpServletRequest request) throws JsonProcessingException {

		String json = "";
		Map<String, Object> map = new HashMap<>(); // json 파일 변환용 map
		ObjectMapper mapper = new ObjectMapper();

		// 서버에 파일을 저장할 때에는 파일명을 시간값으로 변경
		// DB에 저장할 때에는 원본 파일명과 시간값을 모두 저장
		// filename 취득
		String filename = fileload.getOriginalFilename();

		// 업로드 폴더

		// 업로드 수행
		filename = fileNamingEncoder.enFilename(filename);
		File file = new File("../upload/recipe_img/" + filename);

		try {
			map.put("uploaded", true);
			map.put("url", UploadPath.recipePath + filename);

			json = mapper.writeValueAsString(map);

			// 실제 파일 업로드(저장)
			FileUtils.writeByteArrayToFile(file, fileload.getBytes());
			
		} catch (IOException e) {
			map.put("uploaded", false);
			map.put("error", "{ \"message\": \"업로드 중 에러가 발생했습니다. 다시 시도해 주세요.\" }");

			json = mapper.writeValueAsString(map);
		}
		
		return json;
	}
}
