package kr.pe.fourj;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import kr.pe.fourj.controller.TeacherController;

@SpringBootTest
class PickTeacherApplicationTests {
	
	@Autowired
	private TeacherController controller;
	
	private MockMvc mock;
	
	@BeforeEach
	public void init() {
		mock = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void contextLoads() {
		try {
			//선생님 정보 저장
//			mock.perform(post("/teacher").content("{\"idx\":\"9\", \"id\":\"jolida\", \"pw\":\"pw9\", \"name\":\"doon\", \"gender\":\"9\", \"address\":\"주소9\", \"phone\":\"010-xxxx-xxxx\", \"career\":\"전공9\", \"major\":\"전공9\", \"school\":\"학교9\", \"enrollDate\":\"{{timestamp}}\"}")
//			        .contentType(MediaType.APPLICATION_JSON)
//			        .accept(MediaType.APPLICATION_JSON))
//			        .andExpect(status().isOk())
//			        .andDo(print());
			
			//모든 선생님 정보 조회
//			mock.perform(get("/teacherall")).andExpect(status().isOk()).andDo(print());
			
			//선생님 이름으로 조회
//			mock.perform(get("/teacher-name1").content("{\"name\":\"doon\"}")
//			        .contentType(MediaType.APPLICATION_JSON)
//			        .accept(MediaType.APPLICATION_JSON))
//			        .andExpect(status().isOk())
//			        .andDo(print());
			
			//선생님 이름 일부로 조회
//			mock.perform(get("/teacher-name2").content("{\"name\":\"선생님\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 성별로 조회
//			mock.perform(get("/teacher-gender").content("{\"gender\":\"9\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 출신학교명으로 조회
//			mock.perform(get("/teacher-school1").content("{\"school\":\"학교1\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 출신학교명 일부로 조회
//			mock.perform(get("/teacher-school2").content("{\"school\":\"학교\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 전공명으로 조회
//			mock.perform(get("/teacher-major1").content("{\"major\":\"전공1\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 전공명 일부로 조회
//			mock.perform(get("/teacher-major2").content("{\"major\":\"전공\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
			//선생님 정보 수정
//			mock.perform(put("/teacher").content("{\"idx\":\"5\", \"address\":\"ㅈㅅ\", \"phone\":\"010-9-9\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
//			//선생님 정보 삭제
//			mock.perform(delete("/teacher").content("{\"idx\":\"3\"}")
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(status().isOk())
//			.andDo(print());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
