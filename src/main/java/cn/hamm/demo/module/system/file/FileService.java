package cn.hamm.demo.module.system.file;

import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.enums.DateTimeFormatter;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.exception.ServiceException;
import cn.hamm.airpower.util.DateTimeUtil;
import cn.hamm.demo.base.BaseService;
import cn.hamm.demo.common.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
@Slf4j
public class FileService extends BaseService<FileEntity, FileRepository> {
    @Autowired
    private AppConfig appConfig;

    private @NotNull String saveLocalFile(@NotNull MultipartFile multipartFile, @NotNull String saveDir, String saveName) throws IOException {
        // 如果saveDir不是/结尾 则自动加上
        if (!saveDir.endsWith(File.separator)) {
            saveDir += File.separator;
        }
        long milliSecond = System.currentTimeMillis();

        // 追加今日文件夹 定时任务将按存储文件夹进行删除过时文件
        String todayDir = DateTimeUtil.format(milliSecond,
                DateTimeFormatter.FULL_DATE.getValue()
                        .replaceAll(Constant.LINE, Constant.EMPTY_STRING)
        );
        String savePath = saveDir + todayDir + File.separator;

        if (!Files.exists(Paths.get(savePath))) {
            Files.createDirectory(Paths.get(savePath));
        }

        // 存储的文件名
        final String fileName = DateTimeUtil.format(milliSecond, DateTimeFormatter.FULL_TIME.getValue()
                .replaceAll(Constant.COLON, Constant.EMPTY_STRING)
        ) + Constant.UNDERLINE + saveName;

        // 拼接最终存储路径
        savePath += fileName;
        Path path = Paths.get(savePath);
        Files.write(path, multipartFile.getBytes());
        return todayDir + File.separator + fileName;
    }

    /**
     * <h3>文件上传</h3>
     *
     * @param multipartFile 文件
     * @return 存储的文件信息
     */
    public FileEntity upload(@NotNull MultipartFile multipartFile) {
        return upload(multipartFile, false);
    }

    /**
     * <h3>文件上传</h3>
     *
     * @param multipartFile 文件
     * @param isTemp        是否临时文件
     * @return 存储的文件信息
     */
    public FileEntity upload(@NotNull MultipartFile multipartFile, boolean isTemp) {
        // 判断文件大小和类型
        ServiceError.FORBIDDEN_UPLOAD_MAX_SIZE.when(multipartFile.getSize() > appConfig.getUploadMaxSize());
        String originalFilename = multipartFile.getOriginalFilename();
        ServiceError.PARAM_INVALID.whenNull(originalFilename, "文件名不能为空");
        String extension = originalFilename.substring(originalFilename.lastIndexOf(Constant.DOT) + 1);
        ServiceError.PARAM_INVALID.whenEmpty(extension, "文件类型不能为空");
        ServiceError.PARAM_INVALID.when(!Arrays.stream(appConfig.getUploadAllowExtensions()).toList().contains(extension), "文件类型不允许上传");

        String saveDir = appConfig.getUploadDirectory();
        if (isTemp) {
            saveDir = appConfig.getUploadDirectoryTemp();
        }

        try {
            // 获取文件的MD5
            String hashMd5 = DigestUtils.md5DigestAsHex(multipartFile.getBytes());
            FileEntity file;
            if (!isTemp) {
                file = repository.getByHashMd5(hashMd5);
                if (Objects.nonNull(file)) {
                    return file;
                }
            }
            String path = switch (appConfig.getUploadPlatform()) {
                case LOCAL -> saveLocalFile(multipartFile, saveDir, hashMd5 + Constant.DOT + extension);
                case ALIYUN_OSS -> throw new ServiceException("暂未实现");
            };

            file = new FileEntity().setExtension(extension)
                    .setSize(multipartFile.getSize())
                    .setPlatform(appConfig.getUploadPlatform().getKey())
                    .setName(multipartFile.getOriginalFilename())
                    .setHashMd5(hashMd5)
                    .setPath(path)
            ;
            if (isTemp) {
                return file;
            }
            long fileId = add(file);
            return get(fileId);
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new ServiceException(exception);
        }
    }
}
