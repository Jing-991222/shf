package com.jing.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jing.entity.HouseImage;
import com.jing.result.Result;
import com.jing.service.HouseImageService;
import com.jing.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {
    @Reference
    private HouseImageService houseImageService;

    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String uploadShow(@PathVariable Long houseId, @PathVariable Integer type, Model model){
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return "house/upload";
    }

    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId,
                         @PathVariable Integer type,
                         @RequestParam("file") MultipartFile[] files) throws IOException {
        if (files.length > 0){
            for (MultipartFile file : files) {
                //使用UUID作为唯一标识的图片名称
                String fileName = UUID.randomUUID().toString();
                String originalFilename = file.getOriginalFilename();
                fileName += originalFilename.substring(originalFilename.lastIndexOf("."));
                //上传图片到七牛云
                QiniuUtil.upload2Qiniu(file.getBytes(),fileName);
                //获取图片在七牛云上的地址
                String url = "http://rh76lbt0k.hb-bkt.clouddn.com/" + fileName;
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageName(fileName);
                houseImage.setImageUrl(url);
                houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId,@PathVariable Long id){
        HouseImage houseImage = houseImageService.getById(id);
        houseImageService.delete(id);
        QiniuUtil.deleteFileFromQiniu(houseImage.getImageName());
        return "redirect:/house/"+houseId;
    }
}
