package main.XMLAnalysis;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLAnalysis {
    public static void main(String[] args){
        XMLAnalysis xmlAnalysis = new XMLAnalysis();
        xmlAnalysis.new SAX_XMLAnalysis().Analysis();
    }

    /**
     * 一、DOM解析
     *
     * 　　DOM的全称是Document Object Model，也即文档对象模型。在应用程序中，基于DOM的XML分析器将一个XML文档转换成一个对象模型的集合（通常称DOM树），应用程序正是通过对这个对象模型的操作，来实现对XML文档数据的操作。通过DOM接口，应用程序可以在任何时候访问XML文档中的任何一部分数据，因此，这种利用DOM接口的机制也被称作随机访问机制。
     *
     * 　　DOM接口提供了一种通过分层对象模型来访问XML文档信息的方式，这些分层对象模型依据XML的文档结构形成了一棵节点树。无论XML文档中所描述的是什么类型的信息，即便是制表数据、项目列表或一个文档，利用DOM所生成的模型都是节点树的形式。也就是说，DOM强制使用树模型来访问XML文档中的信息。由于XML本质上就是一种分层结构，所以这种描述方法是相当有效的。
     *
     * 　　DOM树所提供的随机访问方式给应用程序的开发带来了很大的灵活性，它可以任意地控制整个XML文档中的内容。然而，由于DOM分析器把整个XML文档转化成DOM树放在了内存中，因此，当文档比较大或者结构比较复杂时，对内存的需求就比较高。而且，对于结构复杂的树的遍历也是一项耗时的操作。所以，DOM分析器对机器性能的要求比较高，实现效率不十分理想。不过，由于DOM分析器所采用的树结构的思想与XML文档的结构相吻合，同时鉴于随机访问所带来的方便，因此，DOM分析器还是有很广泛的使用价值的。
     *
     * 　　　　优点：
     *
     * 　　　　　　1、形成了树结构，有助于更好的理解、掌握，且代码容易编写。
     *
     * 　　　　　　2、解析过程中，树结构保存在内存中，方便修改。
     *
     * 　　　　缺点：
     *
     * 　　　　　　1、由于文件是一次性读取，所以对内存的耗费比较大。
     *
     * 　　　　　　2、如果XML文件比较大，容易影响解析性能且可能会造成内存溢出。
     */
    public class DOM_XMLAnalysis{
        public void Analysis(){
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse("D:\\ProjectFiles\\JavaBaseDemo\\src\\main\\XMLAnalysis\\books.xml");
                NodeList bookList = document.getElementsByTagName("book");
                System.out.println("一共有"+bookList.getLength()+"本书");

                for (int i =0; i<bookList.getLength();i++){
                    System.out.println("===========下面开始遍历第"+(i+1)+"本书的内容============");
                    Node book = bookList.item(i);

                    NamedNodeMap attrs = book.getAttributes();
                    System.out.println("第"+(i+1)+"本书共有"+attrs.getLength()+"个属性");
                    for (int j=0; j<attrs.getLength(); j++){
                        Node attr = attrs.item(j);
                        System.out.println("属性名："+attr.getNodeName()+"  属性值: "+attr.getNodeValue());
                    }

                    NodeList childNodes = book.getChildNodes();
                    System.out.println("第"+(i+1)+"本书共有"+childNodes.getLength()+"个子节点");
                    for (int k=0; k<childNodes.getLength(); k++){
                        if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE){
                            System.out.print("第"+(i+1)+"个节点的节点名："+childNodes.item(k).getNodeName());
                            //childNodes.item(k).getFirstChild().getNodeValue() 要加getFirstChild() 否则值为null
                            System.out.println("  第"+(i+1)+"个节点的节点值: "+childNodes.item(k).getFirstChild().getNodeValue());
                        }
                    }
                    System.out.println("==================结束遍历第"+(i+1)+"个节点");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 二、SAX解析
     *
     * 　　SAX的全称是Simple APIs for XML，也即XML简单应用程序接口。与DOM不同，SAX提供的访问模式是一种顺序模式，这是一种快速读写XML数据的方式。当使用SAX分析器对XML文档进行分析时，会触发一系列事件，并激活相应的事件处理函数，应用程序通过这些事件处理函数实现对XML文档的访问，因而SAX接口也被称作事件驱动接口。
     *
     * 　　　　优点：
     *
     * 　　　　　　1、采用事件驱动模式，对内存耗费比较小。
     *
     * 　　　　　　2、适用于只处理XML文件中的数据时。
     *
     * 　　　　缺点：
     *
     * 　　　　　　1、编码比较麻烦。
     *
     * 　　　　　　2、很难同时访问XML文件中的多处不同数据。
     */
    public class SAX_XMLAnalysis{
        public void Analysis(){
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            try{
                SAXParser parser = parserFactory.newSAXParser();
                SAXParserHandler handler = new SAXParserHandler();
                parser.parse("D:\\ProjectFiles\\JavaBaseDemo\\src\\main\\XMLAnalysis\\books.xml",handler);
                System.out.println("~！~！~！共有" + handler.getBookList().size()
                        + "本书");

                for (Book book : handler.getBookList()) {
                    System.out.println(book.getId());
                    System.out.println(book.getName());
                    System.out.println(book.getAuthor());
                    System.out.println(book.getYear());
                    System.out.println(book.getPrice());
                    System.out.println(book.getLanguage());
                    System.out.println("----finish----");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class SAXParserHandler extends DefaultHandler{
        String value = null;
        Book book = null;
        private ArrayList<Book> bookList = new ArrayList<Book>();
        public ArrayList<Book> getBookList(){
            return bookList;
        }

        int bookIndex = 0;
        /**
         * 用来标识解析开始
         */
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            System.out.println("SAX解析开始");
        }

        /**
         * 用来表示解析开始
         */
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            System.out.println("SAX解析结束");
        }

        /**
         * 解析xml元素
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (qName.equals("book")){
                bookIndex++;
                //创建一个book对象
                book = new Book();
                //开始解析book元素的属性
                System.out.println("===================开始遍历某一本书的内容=================");
                //不知道book元素下属性的名称以及个数，如何获取属性名以及属性值
                int num = attributes.getLength();
                for (int i=0; i<num; i++){
                    System.out.println("book元素的第" + (i + 1) +  "个属性名是："
                            + attributes.getQName(i));
                    System.out.println("---属性值是：" + attributes.getValue(i));
                    if (attributes.getQName(i).equals("id")){
                        book.setId(attributes.getValue(i));
                    }
                }
            }
            else if (!qName.equals("name") && !qName.equals("bookstore")){
                System.out.println("节点名是：" + qName + "---");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //调用DefaultHandler类的endElement方法
            super.endElement(uri, localName, qName);
            //判断是否针对一本书已经遍历结束
            if (qName.equals("book")){
                bookList.add(book);
                book = null;
                System.out.println("======================结束遍历某一本书的内容=================");
            }
            else if (qName.equals("name")) {
                book.setName(value);
            }
            else if (qName.equals("author")) {
                book.setAuthor(value);
            }
            else if (qName.equals("year")) {
                book.setYear(value);
            }
            else if (qName.equals("price")) {
                book.setPrice(value);
            }
            else if (qName.equals("language")) {
                book.setLanguage(value);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            value = new String(ch,start,length);
            if (!value.trim().equals("")) {
                System.out.println("节点值是：" + value);
            }
        }
    }

    /**
     * 三、JDOM解析
     *
     * 　　　　特征：
     *
     * 　　　　　　1、仅使用具体类，而不使用接口。
     *
     * 　　　　　　2、API大量使用了Collections类。
     */
    public class JDOM_XMLAnalysis{

    }
}

