//package com.kittydaddy.search;
//
//import java.io.File;
//import java.io.FileReader;
//import java.nio.file.FileSystems;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.document.Field;
//import org.apache.lucene.document.TextField;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
///**
// * 
// * @author kittydaddy
// * 这个类是用于索引的原始数据，这样我们就可以使用Lucene库，使其可搜索。
// */
//public class Indexer {
//	// 建立索引
//    public void index() {
//        IndexWriter indexWriter = null;
//
//        try {
//            // 1、创建Directory
//            //JDK 1.7以后 open只能接收Path
//            Directory directory = FSDirectory.open(FileSystems.getDefault().getPath("C:/LuceneDemo/data/index"));
//            // 2、创建IndexWriter
//            Analyzer analyzer = new StandardAnalyzer();
//            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//            indexWriter = new IndexWriter(directory, indexWriterConfig);
//            indexWriter.deleteAll();//清除以前的index
//            //要搜索的File路径
//            File dFile = new File("C:/LuceneDemo/data/data");
//            File[] files = dFile.listFiles();
//            for (File file : files) {
//                // 3、创建Document对象
//                Document document = new Document();
//                // 4、为Document添加Field
//                // 第三个参数是FieldType 但是定义在TextField中作为静态变量，看API也不好知道怎么写
//                document.add(new Field("content", new FileReader(file), TextField.TYPE_NOT_STORED));
//                document.add(new Field("filename", file.getName(), TextField.TYPE_STORED));
//                document.add(new Field("filepath", file.getAbsolutePath(), TextField.TYPE_STORED));
//
//                // 5、通过IndexWriter添加文档到索引中
//                indexWriter.addDocument(document);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (indexWriter != null) {
//                    indexWriter.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
