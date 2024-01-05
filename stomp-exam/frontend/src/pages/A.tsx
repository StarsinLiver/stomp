import React, { useEffect, useRef, useState } from "react";
import script from "../assets/script";
import { CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import ChatMessageService from "../service/ChatMessageService";
import { Link, useNavigate, useParams } from "react-router-dom";
import ChatRoomService from "../service/ChatRoomService";

function A() {
  // 현재 시각
  // 채팅룸의 메시지
  const { roomId }: any = useParams();
  const [chatRoom, setChatRoom] = useState<any>();
  const [chatRoomList, setChatRoomList] = useState<Array<any>>();
  const [room, setRoom] = useState<any>();

  const user = JSON.parse(localStorage.getItem("user") as string);
  //   todo: 현재 선택한 파일을 저장할 배열변수
  const [selectedFiles, setSelectedFiles] = useState<FileList | any>();

  const client = useRef<CompatClient>();

  // todo : 파일 선택상자에서 이미지 선택시 실행되는 함수
  // 파일 선택상자 html 태그 : <input type="file" />
  const selectFile = (event: any) => {
    // 화면에서 이미지 선택시 저장된 객체 : event.target.files
    // 변수명 as 타입명 : 개발자가 변수가 무조건 특정타입이라고 보증함
    //                   (타입스크립트에서 체크 안함)
    setSelectedFiles(event.target.files as FileList);
    console.log(selectedFiles);
  };

  useEffect(() => {
    connectHaner();
    return () => {
      if (client.current) {
        client.current.disconnect();
      }
    };
  }, []);

  const connectHaner = () => {
    client.current = Stomp.over(() => {
      const sock = new SockJS("http://localhost:8080/ws");
      return sock;
    });
    client.current.connect(
      {
        // 여기에서 유효성 검증을 위해 header를 넣어줄 수 있음.
        // ex)
        // Authorization: token
      },
      () => {
        // callback 함수 설정, 대부분 여기에 sub 함수 씀
        client.current?.subscribe(
          `/sub/chat/${roomId}`,
          (message) => {
            setChatRoom(JSON.parse(message.body));
            console.log(JSON.parse(message.body));
            // naviRef가 존재하면 해당 엘리먼트로 스크롤
          },
          {
            // 여기에도 유효성 검증을 위한 header 넣어 줄 수 있음
            // Authorization: token
          }
        );
      }
    );
  };

  // 채팅방 가져오기
  const getAll = async () => {
    const result: any = await ChatRoomService.getAll();
    setChatRoomList(result.data);
    const room = result.data.filter((value: any) => value.roomId == roomId);
    setRoom(room);
  };

  useEffect(() => {
    getAll();
  }, []);

  const sendHandler = () => {
    const messageInput: any = document.getElementById("message-to-send");
    const message: string = messageInput.value;
    const fileInput: any = document.getElementById("inputGroupFile02");

    const timestamp = new Date().toLocaleString();

    const type: string = selectedFiles ? "DATA" : "CHAT";

    if (type == "CHAT") {
      // 웹 소캣은 바이너리 데이터를 줄 수 없답니다 ㅎㅎㅎㅎㅎㅎㅎ 시뎅
      client.current?.send(
        "/pub/chat/sendMessage",
        {},
        JSON.stringify({
          sender: user.uid,
          roomId: roomId,
          content: message,
          type: type,
          timestamp: timestamp,
        })
      );
    } else if (type == "DATA") {
      const formData = new FormData();
      formData.append("sender", user.uid.toString());
      formData.append("roomId", roomId.toString());
      formData.append("content", message);
      formData.append("type", type);
      formData.append("fileRoot", "");
      formData.append("file", selectedFiles[0]);
      formData.append("timestamp", new Date().toLocaleString());

      ChatMessageService.upload(formData);
    }

    messageInput.value = "";
    setSelectedFiles(null);
    fileInput.value = "";
  };

  return (
    <>
      <div className="container clearfix">
        <div className="people-list" id="people-list">
          <div className="search">
            <input type="text" placeholder="search" />
            <i className="fa fa-search"></i>
            {/* 그냥 리스트들 */}
          </div>
          <br />
          <Link to="/" style={{ color: "white", marginLeft: "70px" }}>
            {" "}
            메인으로 가기
          </Link>
          <ul className="list">
            {chatRoomList &&
              chatRoomList?.map((value: any, index: any) => (
                <>
                  <li className="clearfix" key={index}>
                    <a href={`/chat/${value.roomId}`}>
                      <img
                        src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/195612/chat_avatar_09.jpg"
                        alt="avatar"
                      />
                      <div className="about">
                        <div className="name" style={{ color: "white" }}>
                          {value.roomName}
                        </div>
                        <div className="status">
                          <i className="fa fa-circle offline"></i> 채팅방
                          들어가기
                        </div>
                      </div>
                    </a>
                  </li>
                </>
              ))}
          </ul>
        </div>

        {/* 채팅 */}
        <div className="chat">
          <div className="chat-header clearfix">
            <img
              src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/195612/chat_avatar_01_green.jpg"
              alt="avatar"
            />

            <div className="chat-about">
              <div className="chat-with">
                채팅방 : {room && room[0].roomName}
              </div>
              <div className="chat-num-messages">나의 이름 : {user.name}</div>
            </div>
            <i className="fa fa-star"></i>
          </div>

          <div className="chat-history">
            <ul>
              {chatRoom &&
                chatRoom.map((value: any, index: any) => (
                  <>
                    {user.uid == value.sender && (
                      <li className="clearfix" key={index}>
                        <div className="message-data align-right">
                          <span className="message-data-time">
                            {value.timestamp}
                          </span>{" "}
                          &nbsp; &nbsp;
                          <span className="message-data-name">
                            {value.sender}
                          </span>{" "}
                          <i className="fa fa-circle me"></i>
                        </div>
                        <div className="message other-message float-right">
                          {value.type == 1 && (
                            <>
                              <img
                                src={value.fileRoot}
                                style={{ width: "100px" }}
                              />
                              <br />
                            </>
                          )}
                          {value.content}
                        </div>
                      </li>
                    )}

                    {user.uid != value.sender && (
                      <li>
                        <div className="message-data">
                          <span className="message-data-name">
                            <i className="fa fa-circle online"></i>{" "}
                            {value.sender}
                          </span>
                          <span className="message-data-time">
                            {value.timestamp}
                          </span>
                        </div>
                        <div className="message my-message">
                          {value.type == 1 && (
                            <>
                              <img
                                src={value.fileRoot}
                                style={{ width: "100px" }}
                              />
                              <br />
                            </>
                          )}
                          {value.content}
                        </div>
                      </li>
                    )}
                  </>
                ))}
            </ul>
          </div>

          {/* 채팅 보내기 */}
          <div className="chat-message clearfix">
            <textarea
              name="message-to-send"
              id="message-to-send"
              placeholder="Type your message"
              rows={3}
            ></textarea>
            <div className="input-group mb-3">
              {/* upload 선택상자/버튼 start */}
              <input
                type="file"
                className="form-control mb-3"
                id="inputGroupFile02"
                // multiple -- 힘들것 같은데 ㅋㅋㅋㅋㅋㅋㅋㅋ
                onChange={selectFile}
              />
            </div>
            <i className="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
            <i className="fa fa-file-image-o"></i>
            <button onClick={() => sendHandler()}>Send</button>
          </div>
        </div>
        {/* 채팅 상단 끝 */}
      </div>
    </>
  );
}

export default A;
