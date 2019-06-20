package com.hottop.core.model.community.bean;

import com.hottop.core.model.zpoj.bean.BusinessEntityIndicator;
import com.hottop.core.model.zpoj.bean.Media;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class FeedEntity implements Serializable {
    private ArrayList<BusinessEntityIndicator> businessEntities;

    private ArrayList<Media> medias;

    private ArrayList<Link> links;

    private ArrayList<HashTag> hashTags;

    private ArrayList<At> ats;
}
