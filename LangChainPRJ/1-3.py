#.env 파일의 API KEY를 사용하기 위한 패키지 로드
from dotenv import load_dotenv
#.env 파일의 변수 불러오는 함수 실행
load_dotenv()

#streamlit 패키지 로드
import streamlit as st

#langchain 패키지가 지원하는 ChatGPT의 채팅모드 지원 패키지 로드
from langchain.chat_models import ChatOpenAI
#ChatGPT의 채팅모드 객체를 chat_model 변수에 할당
chat_model = ChatOpenAI()
#UI 제목 설정
st.title('인공지능 시인')

#streamlit을 통한 input태그 객체를 안내문구와 함께 content 변수로 할당
content = st.text_input('시의 주제를 제시해주세요.')

#button 객체를 만들고 조건문 실행, 버튼이 클릭될 시 아래 소스코드 실행
if st.button('시 작성 요청하기'):
    #로딩 시간동안 spinner 효과 부여
    with st.spinner('시 작성 중...'):
        #제시어를 완성하여 ChatGPT에게 전달
        result = chat_model.predict(content + "에 대한 시를 써줘")
        #응답 결과로 작성된 시를 표시
        st.write(result)