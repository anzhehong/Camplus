package com.camplus.service;

import com.camplus.DAO.GalleryCommentDAO;
import com.camplus.DAO.GalleryDAO;
import com.camplus.entity.GalleryComment;
import com.camplus.entity.GalleryImage;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mark on 5/26/15.
 */

@Service("galleryService")
@Transactional
public class GalleryServiceImp implements GalleryService {
    @Autowired
    private GalleryDAO galleryDAO;

    @Autowired
    private GalleryCommentDAO galleryCommentDAO;

    @Override
    public List<GalleryImage> queryAll() {
        return galleryDAO.queryAll();
    }

    public int getCurrentSize(){
        return galleryDAO.queryAll().size();
    }

    @Override
    public List<Pair<String, String>> getImages() {
        return null;
    }

    @Override
    public List<GalleryComment> getAllComment() {
        return galleryCommentDAO.queryAll();
    }

    @Override
    public void upload(GalleryImage gi){ galleryDAO.insert(gi); }
}
