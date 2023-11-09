#.env 파일의 API KEY를 사용하기 위한 패키지 로드
from dotenv import load_dotenv
#.env 파일의 변수 불러오는 함수 실행
load_dotenv()

#langchain 패키지가 지원하는 ChatGPT의 채팅모드 라이브러리 로드
from langchain.chat_models import ChatOpenAI

#ChatGPT의 채팅모드 객체를 chat_model 변수에 할당
chat_model = ChatOpenAI()

#chat_model 변수에게 제시어를 완성하여 전달하고 응답을 받아옴
content = "코딩"
result = chat_model.predict(content + "에 대한 시를 써줘")
print(result)
