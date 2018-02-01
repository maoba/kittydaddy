package com.kittydaddy.search;

import java.nio.file.FileSystems;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Search {
	/** 
     * 搜索 
     */  
    public void search(String keyWord) {  
        DirectoryReader directoryReader = null;  
        try {  
            // 1、创建Directory  
            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("C:/LuceneDemo/data/index"));
            // 2、创建IndexReader  
            directoryReader = DirectoryReader.open(directory);  
            // 3、根据IndexReader创建IndexSearch  
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);  

            // 4、创建搜索的Query  
            Analyzer analyzer = new StandardAnalyzer();  
            // 创建parser来确定要搜索文件的内容，第一个参数为搜索的域  
            QueryParser queryParser = new QueryParser("content", analyzer);  
            // 创建Query表示搜索域为content包含UIMA的文档  
            Query query = queryParser.parse(keyWord);  

            // 5、根据searcher搜索并且返回TopDocs  
            TopDocs topDocs = indexSearcher.search(query, 10);  
            System.out.println("查找到的文档总共有："+topDocs.totalHits);

            // 6、根据TopDocs获取ScoreDoc对象  
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;  
            for (ScoreDoc scoreDoc : scoreDocs) {  

                // 7、根据searcher和ScoreDoc对象获取具体的Document对象  
                Document document = indexSearcher.doc(scoreDoc.doc);  

                // 8、根据Document对象获取需要的值  
                System.out.println(document.get("filename") + " " + document.get("filepath"));  
            }  

        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (directoryReader != null) {  
                    directoryReader.close();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
