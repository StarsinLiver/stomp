// FileDbService : axios 공통 CRUD 함수
// axios 공통함수 : 벡엔드 연동
import http from "../utils/http-common";


// 상세 조회(pid)
const get = (fileRoot: any): Promise<any> => {
  return http.get(`/chat-message/${fileRoot}`);
};


// 저장함수
// uploadProjects : 제목 + 타이틀(내용) 속성 가진 객체
// fileDb       : 실제 이미지(첨부파일)
// FormData 객체를 이용해서 백엔드로 전송
const upload = (formData : FormData): Promise<any> => {

  return http.post("/data", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};

const ChatMessageService = {
  get, // 상세조회
  upload, // 수정
};

export default ChatMessageService;
