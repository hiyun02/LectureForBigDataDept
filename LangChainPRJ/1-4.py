#streamlit 패키지 로드
import streamlit as st
#langchain 패키지로부터, LLaMA 연동을 위한
#CTransformers 패키지 로드
from langchain.llms import CTransformers

#CTransformers 함수를 실행시켜, LLaMA 모덾 파일과 model_type을 지정하고
#LLaMA 연동 객체를 생성하여 변수 llm에 할당
llm = CTransformers(
    model="llama-2-7b-chat.ggmlv3.q2_K.bin",
    model_type="llama"
)
#UI 제목 설정
st.title('인공지능 시인')
#streamlit을 통한 input태그 객체를 안내문구와 함께 content 변수로 할당
content = st.text_input('시의 주제를 제시해주세요.')

#button 객체를 만들고 조건문 실행, 버튼이 클릭될 시 아래 소스코드 실행
if st.button('시 작성 요청하기'):
    with st.spinner('시 작성 중...'):
        result = llm.predict("write a poem about " + content + ": ")
        st.write(result)
