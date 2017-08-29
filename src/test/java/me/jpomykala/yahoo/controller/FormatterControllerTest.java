package me.jpomykala.yahoo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import me.jpomykala.yahoo.FormatterApp;
import me.jpomykala.yahoo.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Jakub Pomykala on 8/29/17.
 * @project yahoo-react-intl-formatter
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FormatterApp.class)
@AutoConfigureMockMvc
public class FormatterControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void formatFile_properFile_returnOk() throws Exception {
		//given
		String fileName = "translations.xlsx";
		byte[] readFile = TestUtils.readFile(fileName);
		MockMultipartFile mockFile = new MockMultipartFile("file", readFile);

		//when
		mockMvc
				.perform(fileUpload("/format")
						.file(mockFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk());

	}

	@Test
	public void formatFile_noFile_returnBadRequest() throws Exception {
		//given

		//when
		mockMvc
				.perform(fileUpload("/format")
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isBadRequest());

	}

}