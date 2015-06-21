package com.camplus.DAO;

import com.camplus.entity.GalleryComment;
import org.springframework.stereotype.Repository;

/**
 * Created by mark on 5/26/15.
 */
@Repository
public class GalleryCommentDAOImp extends GeneralDAOImp<GalleryComment> implements GalleryCommentDAO  {

    public GalleryCommentDAOImp() {
        super(GalleryComment.class);
    }
}
