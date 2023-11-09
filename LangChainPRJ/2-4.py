from dotenv import load_dotenv
load_dotenv()
from langchain.document_loaders import PyPDFLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.vectorstores import Chroma
from langchain.embeddings import OpenAIEmbeddings
from langchain.chat_models import ChatOpenAI
from langchain.chains import RetrievalQA
import streamlit as st
#업로드된 파일을 임시 디렉터리에 저장하기 위한 내장 패키지 tempfile, os
import tempfile
import os

#제목
st.title("ChatPDF")
st.write("---")

#파일 업로드
uploaded_file = st.file_uploader("Choose a file")
st.write("---")
def pdf_to_document(uploaded_file):
    #임시 디렉터리 생성
    temp_dir = tempfile.TemporaryDirectory()
    #생성된 임시 디렉터리에 업로드된 파일의 공간 생성
    temp_filepath = os.path.join(temp_dir.name, uploaded_file.name)
    #해당 공간에 업로드된 파일을 저장
    with open(temp_filepath, "wb") as f:
        f.write(uploaded_file.getvalue())
    #업로드 후 저장된 PDF파일을 Loader로 불러와서 페이지 별 분할 작업 수행
    loader = PyPDFLoader(temp_filepath)
    pages = loader.load_and_split()
    return pages

#업로드 되면 동작하는 코드
if uploaded_file is not None:
    #업로드된 PDF는 페이지 별로 분할되어 리턴됨
    pages = pdf_to_document(uploaded_file)

    #Split
    text_splitter = RecursiveCharacterTextSplitter(
        # Set a really small chunk size, just to show.
        chunk_size = 300,
        chunk_overlap  = 20,
        length_function = len,
        is_separator_regex = False,
    )
    texts = text_splitter.split_documents(pages)

    # Embedding. API-KEY를 통해 OpenAI 제공 임베딩 모델 로드, 비용 발생
    embeddings_model = OpenAIEmbeddings()

    #ChromaDB에 Split된 데이터 texts를 OpenAI Embedding 모델로 벡터화하여 저장함(./chroma 폴더에 저장)
    db = Chroma.from_documents(texts, embeddings_model, persist_directory="./chroma")

    #Question
    st.header("PDF에게 질문해보세요!!")
    question = st.text_input('질문을 입력하세요')

    # button 객체를 만들고 조건문 실행, 버튼이 클릭될 시 아래 소스코드 실행
    if st.button('질문하기'):
        # 로딩 시간동안 spinner 효과 부여
        with st.spinner('Wait for it...'):
            # 제시어를 완성하여 ChatGPT의 채팅모델에게 전달
            llm = ChatOpenAI(model_name="gpt-3.5-turbo", temperature=0)
            #db에 질의를 던져 질의와 유사한 문서를 가져오고 응답을 반환할 RetrievalQA 객체 할당.
            qa_chain = RetrievalQA.from_chain_type(llm,retriever=db.as_retriever())
            # RetrievalQA에게 질의를 넘김으로서 결과값 반환
            result = qa_chain({"query": question})
            # 응답 결과를 표시
            st.write(result["result"])