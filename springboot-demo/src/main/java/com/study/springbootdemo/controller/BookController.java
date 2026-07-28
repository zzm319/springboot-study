package com.study.springbootdemo.controller;

import com.study.springbootdemo.dto.BookDTO;
import com.study.springbootdemo.service.BookService;
import com.study.springbootdemo.vo.BookVo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.study.springbootdemo.common.PageResult;
import com.study.springbootdemo.common.Result;
import com.study.springbootdemo.dto.BookUpdateDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    // @Qualifier("bookServiceImpl2") 如果是通过构造器注入，需要将注解放到构造器参数上
    // @Autowired // Spring 从容器里找一个 BookService 类型的 Bean，赋给这个字段(方式一：字段注入)，
    // 字段注入不能使用final定义变量，因为final 字段必须在 构造器结束之前 完成赋值，字段注入发生在 对象创建之后
    // private BookService bookService;
    // @Autowired
    // @Qualifier("bookServiceImpl2")
    // private  BookService bookService;

    
    // 构造器注入：最常用，适合 final 字段，保证线程安全（多实现类时 @Qualifier 要写在 构造器参数 上）
    private final BookService bookService;

    public BookController(@Qualifier("bookServiceImpl2") BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * 注入时 Spring 怎么匹配 Bean？
     * 默认规则：按类型匹配。
     * 容器里只有一个 BookService 实现 → 直接注入
     * 有多个实现 → 需要 @Qualifier("beanName") 指定名字，或 @Primary 标记默认实现
     * @Qualifier("beanName"）在依赖注入的时候指定；@Primary在实现类里指定，比如在BookServiceImpl实现类里使用
     **/

    // 方式二：构造器注入
    // public BookController(BookService bookService){
    // this.bookService = bookService;
    // }

    // 方式三：setter注入
    // public void setBookService(BookService bookService){
    // this.bookService = bookService;
    // }

    // GetMapping Get方法
    // @GetMapping("/bookname")
    // public String getBookName() {
    // return bookService.getBookName();
    // }

    // Query 参数 — @RequestParam
    /**
     * @RequestParam：从 URL 的 ? 后面取值
     *                 参数名默认和方法参数名一致（这里都是 name）
     *                 可加 required = false, defaultValue = "访客" 设置可选和默认值
     */
    // @GetMapping("/getBook")
    // public String getBook(@RequestParam(required = false, defaultValue = "999")
    // Integer id,
    // @RequestParam(required = false) String name) {
    // return "找到了ID为：" + id + "的书籍";
    // }

    // 路径参数 — @PathVariable
    /**
     * {id} 是路径占位符
     * 
     * @PathVariable 把路径里的值绑到方法参数
     */
    // @GetMapping("getBook/{id}")
    // public String getBookById(@PathVariable long id) {
    // return "书籍ID" + id;
    // }

    // PostMapping Post方法
    /**
     * @RequestBody：从请求体获取 JSON 数据，自动转换为 Book 对象
     * @RequestBody：把 HTTP Body 的 JSON 反序列化成 Java 对象
     *                需要 Content-Type: application/json
     *                通常配合 @PostMapping / @PutMapping 使用
     *                Java 对象（DTO/Entity）需要 getter/setter，或用 Lombok 的 @Data 简化。
     *                DTO 是什么？ 专门用来和前端交换数据的对象，和业务实体、数据库实体分开。
     */
    @PostMapping("/book")
    public Result<BookVo> addBook(@Valid @RequestBody BookDTO book) {
        logger.info("添加书籍:{}", book.getName());
        BookVo bookVo = bookService.addBook(book);
        return Result.success(bookVo);
    }

    @GetMapping("/book/{id}")
    /**
     * Controller 返回 Java 对象时，Spring 自动用 Jackson 转成 JSON：
     * {"id":"1","name":"Java编程思想","author":"Bruce Eckel"}
     */
    public Result<BookVo> getBookInfo(@PathVariable long id) {
        logger.info("获取书籍信息:{}", id);
        BookVo bookVo = bookService.getBookInfo(id);
        return Result.success(bookVo);
    }

    @DeleteMapping("/book/{id}")
    public Result<String> deleteBook(@PathVariable long id) {
        logger.info("删除书籍:{}", id);
        return Result.success(bookService.deleteBook(id));
    }

    @PutMapping("/book/{id}")
    public Result<BookVo> updateBook(@PathVariable long id, @Valid @RequestBody BookDTO book) {
        logger.info("更新书籍:{}", id);
        return Result.success(bookService.updateBook(id, book));
    }

    @GetMapping("/books")
    public Result<List<BookVo>> getAllBooks(@RequestParam(required = false) String name,
            @RequestParam(required = false) String author) {
        logger.info("获取所有书籍:{}", name);
        List<BookVo> bookVos = bookService.getBooksByCondition(name, author);
        return Result.success(bookVos);
    }

    @PutMapping("/book/update/{id}")
    public Result<BookVo> updateBookSet(@PathVariable long id, @RequestBody BookUpdateDto book) {
        logger.info("更新书籍:{}", id);
        return Result.success(bookService.updateBookSet(id, book));
    }

    @GetMapping("/books/page")
    public Result<PageResult<BookVo>> getBooksByConditionWithPage(@RequestParam(required = false) String name,
            @RequestParam(required = false) String author,
            @RequestParam(required = false,defaultValue = "1") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size) {
        logger.info("获取所有书籍:{}", name);
        return Result.success(bookService.getBooksByConditionWithPage(name, author, page, size));
    }
}
