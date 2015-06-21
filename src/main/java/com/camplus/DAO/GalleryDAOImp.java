package com.camplus.DAO;

import com.camplus.entity.GalleryImage;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 5/26/15.
 */

@Repository
public class GalleryDAOImp extends GeneralDAOImp<GalleryImage> implements GalleryDAO {

    public GalleryDAOImp() {
        super(GalleryImage.class);
    }


}
