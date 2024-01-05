// FileDbService : axios 공통 CRUD 함수
// axios 공통함수 : 벡엔드 연동
import http from "../utils/http-common";

const addChatUser = (chatUser: any) => {
  return http.post(`/chat-user`, chatUser);
};

const ChatUserService = {
  addChatUser,
};

export default ChatUserService;
