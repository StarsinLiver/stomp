import React, { useEffect, useState } from "react";
import ChatRoomService from "../service/ChatRoomService";
import { Link } from "react-router-dom";
import ChatUserService from "../service/ChatUserService";

function Main() {
  const [chatRoom, setChatRoom] = useState<Array<any>>();
  const initailText: any = {
    userName: "",
    roomName: "",
  };
  const [text, setText] = useState<any>(initailText);

  // 채팅방 가져오기
  const getAll = async () => {
    const result: any = await ChatRoomService.getAll();
    setChatRoom(result.data);
    console.log(result.data);
  };

  useEffect(() => {
    getAll();
  }, []);

  const handleInputChange = (event: any) => {
    const { value, name } = event.target;
    setText({ ...text, [name] : value });
    console.log(text);
  };

  //  유저 생성
  const addUser = async () => {
    let data = {
      name : text.userName
    }
   const result : any= await ChatUserService.addChatUser(data)
   console.log(result.data)
   localStorage.setItem("user" , JSON.stringify(result.data));
   setText(initailText);
  };

  // 채팅 방 생성
  const addChatRoom = async () => {
    let data = {
      roomName: text.roomName
    }
   const result : any = await ChatRoomService.addChatRoom(data)
   window.location.reload();
  };

  return (
    <>
      <div className="container clearfix">
        <div className="people-list" id="people-list">
          <div className="search">
            <input type="text" placeholder="search" />
            <i className="fa fa-search"></i>
            {/* 채팅방 리스트 올리기 */}
          </div>
          <ul className="list">
            {chatRoom &&
              chatRoom?.map((value: any, index: any) => (
                <>
                  <li className="clearfix" key={index}>
                    <Link to={`/chat/${value.roomId}`}>
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
                    </Link>
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
              <div className="chat-with">어드민</div>
              <div className="chat-num-messages">하하하하ㅏ핳ㅎㅎㅎ</div>
            </div>
            <i className="fa fa-star"></i>
          </div>

          <div className="chat-history">
            <ul>
              <li className="clearfix">
                <div className="message-data align-right">
                  <span className="message-data-time">
                    {new Date().toLocaleString()}
                  </span>{" "}
                  &nbsp; &nbsp;
                  <span className="message-data-name">어드민</span>{" "}
                  <i className="fa fa-circle me"></i>
                </div>
                <div className="message other-message float-right">
                  안녕하세요 채팅 웹 프로그램에 오신것을 환영합니다.
                </div>
              </li>

              <li>
                <div className="message-data">
                  <span className="message-data-name">
                    <i className="fa fa-circle online"></i> 설명
                  </span>
                  <span className="message-data-time">
                    {new Date().toLocaleString()}
                  </span>
                </div>
                <div className="message my-message">
                  유저를 생성하고 싶어요!
                </div>
              </li>

              <li className="clearfix">
                <div className="message-data align-right">
                  <span className="message-data-time">
                    {new Date().toLocaleString()}
                  </span>{" "}
                  &nbsp; &nbsp;
                  <span className="message-data-name">어드민</span>{" "}
                  <i className="fa fa-circle me"></i>
                </div>
                <div className="message other-message float-right">
                  이곳에서 유저를 생성해 보세요!!
                  <input
                    type="text"
                    name="userName"
                    value={text.userName}
                    onChange={handleInputChange}
                  ></input>
                  <button onClick={addUser}>유저 생성</button>
                </div>
              </li>

              <li>
                <div className="message-data">
                  <span className="message-data-name">
                    <i className="fa fa-circle online"></i> 설명
                  </span>
                  <span className="message-data-time">
                    {new Date().toLocaleString()}
                  </span>
                </div>
                <div className="message my-message">
                  채팅 방을 생성하고 싶어요!
                </div>
              </li>

              <li className="clearfix">
                <div className="message-data align-right">
                  <span className="message-data-time">
                    {new Date().toLocaleString()}
                  </span>{" "}
                  &nbsp; &nbsp;
                  <span className="message-data-name">어드민</span>{" "}
                  <i className="fa fa-circle me"></i>
                </div>
                <div className="message other-message float-right">
                  이곳에서 채팅 방을 만들 수 있어요!
                  <input
                    type="text"
                    name="roomName"
                    value={text.roomName}
                    onChange={handleInputChange}
                  ></input>
                  <button onClick={addChatRoom}>채팅방 생성</button>
                </div>
              </li>
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
                // onChange={selectFile}
              />
            </div>
            <i className="fa fa-file-o"></i> &nbsp;&nbsp;&nbsp;
            <i className="fa fa-file-image-o"></i>
            <button>Send</button>
          </div>
        </div>
        {/* 채팅 상단 끝 */}
      </div>
    </>
  );
}

export default Main;
