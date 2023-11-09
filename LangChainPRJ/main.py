#openai 의 llm을 자동완성 모드로 사용함

from langchain.llms import OpenAI

llm = OpenAI(openai_api_key="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")

result = llm.predict("hi!")

print(result)

