package com.camplus.service;

import com.camplus.entity.GalleryComment;
import com.camplus.entity.GalleryImage;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by mark on 5/26/15.
 */
public interface GalleryService {

    //get all entities in GalleryImage
    List<GalleryImage> queryAll();

    int getCurrentSize();

    //get the origin images name and thumbnail name
    List<Pair<String, String>> getImages();

    List<GalleryComment> getAllComment();

    void upload(GalleryImage gi);
}
