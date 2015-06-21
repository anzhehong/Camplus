package com.camplus.controller;

import com.camplus.entity.GalleryImage;
import com.camplus.entity.User;
import com.camplus.service.GalleryService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by fowafolo on 15/5/26.
 */

@Controller
@RequestMapping("/gallery")
public class GalleryController {
    @Autowired
    private GalleryService service;

    @RequestMapping("")
    public String gallery(Model model){
        model.addAttribute("Images", service.queryAll());
        return "Gallery/galleryHome";
    }


    @RequestMapping("/hotComment")
    String hotComment(Model model){
        model.addAttribute("comments", service.getAllComment());
        return "Gallery/galleryHotComment";
    }

    @RequestMapping("/comment")
    String newComment(String imageId,String message,Model model) {
        model.addAttribute("imageId",imageId);
        model.addAttribute("message",message);


        return "Gallery/galleryNewComment";
    }

    @RequestMapping("/mySpace")
    String mySpace(Model model,HttpSession session,String imageid){
        if(imageid!=null){
            service.removeById(imageid);
        }
        User user=(User)session.getAttribute("userSession");
        String uid=user.getUserId();
        ArrayList<GalleryImage> arrimg=(ArrayList)service.getImagesByUID(uid);
        model.addAttribute("images",arrimg);
        return "Gallery/galleryMySpace";
    }

    @RequestMapping("/upload")
    String uploadimg(@RequestParam("image")MultipartFile image,HttpSession session,Model model) {
        try {
            if(image.isEmpty()){
                model.addAttribute("givenMessage","Please select a file!");
            }else {
                String path = session.getServletContext().getRealPath("/Images/gallery");
                String tmpName = image.getOriginalFilename();
                Scanner sc = new Scanner(tmpName);
                sc.useDelimiter("\\.");
                String prefix = sc.next();
                String suffix = sc.next();
                if (!suffix.equals("jpg")) {
                    int i = service.getCurrentSize();
                    String s = Integer.toString(++i);
                    int zerocnt = 6 - s.length();
                    StringBuffer sb = new StringBuffer("");
                    for (int x = 0; x < zerocnt; x++) {
                        sb.append("0");
                    }
                    sb.append(s);
                    //6 Digit
                    String realName = sb.toString();
                    String fullname=path+File.separator+realName+".jpg";
                    String fullname_comp=path+File.separator+"s"+realName+".png";
                    FileUtils.copyInputStreamToFile(image.getInputStream(),new File(tmpName));
                    File srcFile=new File(tmpName);
                    File destFile=new File(fullname_comp);
                    File odestFile=new File(fullname);
                    BufferedImage src= ImageIO.read(srcFile);
                    int width=256,height=200;
                    int owidth=src.getWidth();
                    int oheight=src.getHeight();
                    Image im=src.getScaledInstance(width,height,Image.SCALE_DEFAULT);
                    Image oim=src.getScaledInstance(owidth,oheight,Image.SCALE_DEFAULT);
                    BufferedImage tag=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
                    BufferedImage otag=new BufferedImage(owidth,oheight,BufferedImage.TYPE_INT_RGB);
                    Graphics g=tag.getGraphics();
                    Graphics og=otag.getGraphics();
                    g.drawImage(im,0,0,null);
                    og.drawImage(oim,0,0,null);
                    g.dispose();
                    og.dispose();
                    boolean flag;
                    flag=ImageIO.write(tag,"png",new FileOutputStream(destFile));
                    flag=ImageIO.write(otag,"jpg",new FileOutputStream(odestFile));
                    GalleryImage gi=new GalleryImage();
                    gi.setGalleryImageId(realName);
                    gi.setGalleryImageLoveCount(0);
                    String id=((User)session.getAttribute("userSession")).getUserId();
                    gi.setGalleryUserId(id);
                    service.upload(gi);
                } else {
                    int i = service.getCurrentSize();
                    String s = Integer.toString(++i);
                    int zerocnt = 6 - s.length();
                    StringBuffer sb = new StringBuffer("");
                    for (int x = 0; x < zerocnt; x++) {
                        sb.append("0");
                    }
                    sb.append(s);
                    //6 Digit
                    String realName = sb.toString();
                    String fullname=path+File.separator+realName+".jpg";
                    String fullname_comp=path+File.separator+"s"+realName+".png";
                    FileUtils.copyInputStreamToFile(image.getInputStream(),new File(fullname));
                    File srcFile=new File(fullname);
                    File destFile=new File(fullname_comp);
                    BufferedImage src= ImageIO.read(srcFile);
                    int width=256,height=200;
                    Image im=src.getScaledInstance(width,height,Image.SCALE_DEFAULT);
                    BufferedImage tag=new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
                    Graphics g=tag.getGraphics();
                    g.drawImage(im,0,0,null);
                    g.dispose();
                    boolean flag=ImageIO.write(tag,"png",new FileOutputStream(destFile));
                    GalleryImage gi=new GalleryImage();
                    gi.setGalleryImageId(realName);
                    gi.setGalleryImageLoveCount(0);
                    String id=((User)session.getAttribute("userSession")).getUserId();
                    gi.setGalleryUserId(id);
                    service.upload(gi);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            model.addAttribute("givenMessage","Successfully Uploaded!");
            return "Gallery/galleryNotification";
        }
    }
}
