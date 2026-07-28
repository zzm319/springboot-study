package com.study.springbootdemo.service.impl;

import com.study.springbootdemo.common.BusinessException;
import com.study.springbootdemo.common.ResultCode;
import com.study.springbootdemo.dto.BookDTO;
import com.study.springbootdemo.entity.Book;
import com.study.springbootdemo.mapper.BookMapper;
import com.study.springbootdemo.service.BookService;
import com.study.springbootdemo.vo.BookVo;
import com.study.springbootdemo.dto.BookUpdateDto;
import com.study.springbootdemo.common.PageResult;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/*
Service 负责 DTO ↔ Entity ↔ VO 转换，Controller 不直接碰 Entity
MyBatis 的 insert/update/delete 返回 影响行数，不是对象
 */

@Service
public class BookServiceImpl2 implements BookService {
    private final static Logger logger = LoggerFactory.getLogger(BookServiceImpl2.class);

    private final BookMapper bookMapper;

    public BookServiceImpl2(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    private BookVo toVo(Book book) {
        BookVo bookVo = new BookVo();
        bookVo.setId(book.getId());
        bookVo.setName(book.getName());
        bookVo.setAuthor(book.getAuthor());
        bookVo.setCreateTime(book.getCreateTime());
        return bookVo;
    }

    @Override
    public String getBookName() {
        return "spring";
    }

    // 添加书籍
    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，如果出现异常，则回滚。rollbackFor属性指定回滚的异常类型。@Transactional 注解表示该方法需要事务支持。
    public BookVo addBook(BookDTO book) {
        // DTO->Entity转换
        Book book1 = new Book();
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        bookMapper.insert(book1);
        Book book2 = bookMapper.selectById(book1.getId());
        logger.info("添加书籍成功{}",book2.getName());
        return toVo(book2);
    }

    // 查询书籍信息
    @Override
    @Cacheable(value = "book", key = "#id") //缓存书籍信息，value为book，key为书籍id。如果缓存中有，则直接返回缓存中的数据，否则查询数据库并缓存。@Cacheable 注解表示该方法的返回值需要被缓存。使用缓存时，需要使用@EnableCaching注解开启缓存。value属性指定缓存的名称，key属性指定缓存的键。
    public BookVo getBookInfo(Long id) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            logger.error("书籍不存在,id={}",id);
            throw new BusinessException(ResultCode.NOT_FOUND, "书籍不存在");
        }
        return toVo(book);
    }

    @CacheEvict(value = "book", key = "#id") //删除书籍需要清空缓存。@CacheEvict 注解表示该方法需要清空缓存。value属性指定缓存的名称，key属性指定缓存的键。
    @Override
    @Transactional(rollbackFor = Exception.class) 
    public String deleteBook(Long id) {
        Book book = bookMapper.selectById(id);
        if (book == null){
            logger.error("书籍不存在,id={}",id);
            throw new BusinessException(ResultCode.NOT_FOUND, "书籍不存在");}
        bookMapper.deleteById(id);
        logger.info("书籍ID为{}的书籍删除成功",id);
        return "书籍ID为" + id + "的书籍删除成功";
    }

    @CacheEvict(value = "book", key = "#id") //改了书籍需要清空缓存。@CacheEvict 注解表示该方法需要清空缓存。value属性指定缓存的名称，key属性指定缓存的键。
    @Override
    @Transactional(rollbackFor = Exception.class) 
    public BookVo updateBook(Long id, BookDTO bookDto) {
        Book book2 = bookMapper.selectById(id);
        if (book2 == null){
            logger.error("书籍不存在,id={}",id);
            throw new BusinessException(ResultCode.NOT_FOUND, "书籍不存在");}
        Book book = new Book();
        book.setId(id);
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        // System.out.println(book);
        bookMapper.updateById(book);
        logger.info("书籍ID为{}的书籍更新成功,更新信息为:{}",id,book.toString());
        return toVo(bookMapper.selectById(id));
    }

    @Override
    public List<BookVo> getAllBooks() {
        logger.info("获取所有书籍成功");
        return bookMapper.selectAll().stream().map(this::toVo).toList();
    }

    @Override
    public List<BookVo> getBooksByCondition(String name, String author) {
        // Book book1 = new Book();
        // book1.setName(name);
        // book1.setAuthor(author);
        logger.info("根据条件查询书籍成功,条件为:name={},author={}",name,author);
        return bookMapper.selectByCondition(name, author).stream().map(this::toVo).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class) 
    @CacheEvict(value = "book", key = "#id") //改了书籍需要清空缓存。@CacheEvict 注解表示该方法需要清空缓存。value属性指定缓存的名称，key属性指定缓存的键。
    public BookVo updateBookSet(Long id, BookUpdateDto bookUpdateDto) {
        Book book2 = bookMapper.selectById(id);
        if (book2 == null){
            logger.warn("书籍不存在,id={}",id); 
            throw new BusinessException(ResultCode.NOT_FOUND, "书籍不存在");}
        Book book = new Book();
        book.setId(id);
        book.setName(bookUpdateDto.getName());
        book.setAuthor(bookUpdateDto.getAuthor());
        bookMapper.updateById(book);
        logger.info("书籍ID为{}的书籍更新成功,更新信息为:{}",id,book.toString());
        return toVo(bookMapper.selectById(id));
    }

    @Override
    @Transactional(readOnly = true)//纯查询、且一次方法里多次查库时，提示数据库这是只读，便于优化
    public PageResult<BookVo> getBooksByConditionWithPage(String name, String author, int page, int size) {
        // 1. 参数兜底
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        if (size > 100) size = 100; // 防止一次查太多

        // 2. 计算偏移量
        int offset = (page - 1) * size;

        // 3. 查询数据
        long total = bookMapper.countByCondition(name, author);
        List<Book> books = bookMapper.selectByConditionWithPage(name, author, offset, size);

        // 4. 转换为VO
        List<BookVo> bookVos = books.stream().map(this::toVo).toList();

        // 5. 返回结果
        logger.info("获取书籍成功,书籍数量为:{}",bookVos.size());
        return new PageResult<>(bookVos, total, page, size);
    }
}
