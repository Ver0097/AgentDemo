package com.zsyyzs.agentdemo.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;

import java.nio.file.Path;
import java.util.List;

public class DocumentLoader {

    public static List<Document> loadDocuments() {
        return FileSystemDocumentLoader.loadDocuments(
                Path.of("src/main/resources")
        );
    }
}
