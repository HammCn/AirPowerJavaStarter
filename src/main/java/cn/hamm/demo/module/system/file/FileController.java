package cn.hamm.demo.module.system.file;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.model.Json;
import cn.hamm.demo.base.BaseController;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("file")
@Description("文件")
public class FileController extends BaseController<FileEntity, FileService, FileRepository> {
    @PostMapping("upload")
    @Description("文件上传")
    @Permission(authorize = false, login = false)
    public Json upload(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file) {
        return Json.data(service.upload(file), "文件上传成功");
    }

    @PostMapping("uploadTemp")
    @Description("文件上传")
    @Permission(authorize = false, login = false)
    public Json uploadTemp(@NotNull(message = "文件不能为空") @RequestParam("file") MultipartFile file) {
        return Json.data(service.upload(file, true), "文件上传成功");
    }
}
