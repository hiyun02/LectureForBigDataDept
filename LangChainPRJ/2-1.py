# langchain 제공 loader 중 pyPDFLoader 로드
from langchain.document_loaders import PyPDFLoader
# langchain 제공 splitter 중 RecursiveCharacterTextSplitter 로드
from langchain.text_splitter import RecursiveCharacterTextSplitter

#Load
loader = PyPDFLoader("unsu.pdf")
pages = loader.load_and_split()

#Split
text_splitter = RecursiveCharacterTextSplitter(
    chunk_size = 300, # Split할 글자 수
    chunk_overlap  = 20, # 문장 중간에 Split될 시 중복시킬 단어 수
    length_function = len,
    is_separator_regex = False,
)
texts = text_splitter.split_documents(pages)

#Split된 텍스트들을 한 줄씩 출력
for text in texts :
    print(text)