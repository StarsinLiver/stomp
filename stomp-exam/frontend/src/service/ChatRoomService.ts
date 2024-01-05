// FileDbService : axios 공통 CRUD 함수
// axios 공통함수 : 벡엔드 연동
import http from "../utils/http-common";

const getAll = () => {
  return http.get(`/chat-room`);
};

const addChatRoom = (chatRoom: any) => {
  return http.post(`/chat-room`, chatRoom);
};

const ChatRoomService = {
  getAll,
  addChatRoom,
};

export default ChatRoomService;
