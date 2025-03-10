package kopo.poly.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RagConfig {

    @Value("classpath:/data/vectorStore.json")
    private Resource vectorStoreResource; // vector store 내용 저장될 파일명

    @Value("classpath:/docs/spring-boot-reference.pdf")
    private Resource pdfResource; // 대상 pdf 파일

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) throws IOException {

        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);

        if (vectorStoreResource.exists()) {
            log.info("벡터스토어 파일이 존재하므로 기존 데이터 로드");
            simpleVectorStore.load(vectorStoreResource);
        } else {
            log.info("벡터스토어 파일이 존재하지 않으므로, pdf 파일 내용을 읽어서 저장하는 작업 시작");

            PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                    .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                            .withNumberOfBottomTextLinesToDelete(0)
                            .withNumberOfTopTextLinesToDelete(0)
                            .build())
                    .withPagesPerDocument(1)
                    .build();

            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource, config);
            List<Document> documents = pdfReader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.apply(documents);

            simpleVectorStore.add(splitDocuments);
            simpleVectorStore.save(vectorStoreResource.getFile());
        }
        return simpleVectorStore;
    }
}
