package com.camplus.service;

import com.camplus.DAO.GalleryCommentDAO;
import com.camplus.DAO.GalleryDAO;
import com.camplus.entity.GalleryComment;
import com.camplus.entity.GalleryImage;
import javafx.util.Pair;
import org.omg.CosNaming.BindingIteratorOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
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
    public void addNewComment(GalleryComment gc) {
        galleryCommentDAO.insert(gc);
    }

    @Override
    public List<Pair<String, String>> getImages() {
        return null;
    }

    @Override
    public List<GalleryComment> getAllComment() {
        return galleryCommentDAO.queryAll();
    }

    public List<GalleryImage> getImagesByUID(String uid) {
        return galleryDAO.getImageByUID(uid);
    }

    @Override
    public void upload(GalleryImage gi){ galleryDAO.insert(gi); }

    @Override
    public void removeById(String mid) {
        GalleryImage gi=galleryDAO.queryById(mid);
        List<GalleryComment> list=galleryCommentDAO.getAllbyGID(mid);
        Iterator<GalleryComment> iter=list.iterator();
        while(iter.hasNext()){
            galleryCommentDAO.delete(iter.next());
        }
        galleryDAO.delete(gi);
    }

}
