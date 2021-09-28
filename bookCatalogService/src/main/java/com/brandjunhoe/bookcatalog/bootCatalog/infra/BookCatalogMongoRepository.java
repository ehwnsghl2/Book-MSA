package com.brandjunhoe.bookcatalog.bootCatalog.infra;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalog;
import com.brandjunhoe.bookcatalog.bootCatalog.domain.BookCatalogRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Create by DJH on 2021/09/27.
 */
public interface BookCatalogMongoRepository extends MongoRepository<BookCatalog, String>, BookCatalogRepository {

}
