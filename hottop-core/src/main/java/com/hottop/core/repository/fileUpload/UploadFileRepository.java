package com.hottop.core.repository.fileUpload;

import com.hottop.core.model.fileUpload.UploadFile;
import com.hottop.core.repository.EntityBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends EntityBaseRepository<UploadFile, Long> {
}
