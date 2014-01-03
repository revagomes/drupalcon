package com.drupalcon.prague;

import java.util.List;

public class Session {

    int nid;
    int vid;
    int uuid;
    int uid;
    String title;
    String description = "";
    String type = "";
    int created;
    int changed;
    int status = 0;
    int promoted = 0;
    int sticky = 0;
    int special = 0;

    // Others
    int startDate;
    int endDate;
    int level = 0;
    int day = 0;
    int favorite = 0;
    String track = "";
    String room = "";
    List<Speaker> speakerList;

    // Empty constructor.
    public Session() {}

    // Full constructor, without speaker list.
    public Session(int nid, int vid, int uuid, int uid, String title, String description,  String type, int created, int changed, int status, int promoted, int sticky) {
        this.nid = nid;
        this.vid = vid;
        this.uuid = uuid;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.type = type;
        this.created = created;
        this.changed = changed;
        this.status = status;
        this.promoted = promoted;
        this.sticky = sticky;

        // other defaults
        this.special = 0;
        this.startDate = 1379919600;
        this.endDate = 1379942100;
        this.level = 1;
        this.day = 24;
        this.room = "North Hall · Exove";
        this.track = "Site Building";
        this.favorite = 1;
        //this.speakerList = speakerList;
    }
    
    public int getId() {
        return this.nid;
    }

    public void setId(int nid) {
        this.nid = nid;
    }

    public int getVid() {
        return this.vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }
    
    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    
    public int getUuid() {
        return this.uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }
    
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }    
    
    public int getCreated() {
        return this.created;
    }

    public void setCreated(int created) {
        this.created = created;
    }
    
    public int getChanged() {
        return this.changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }
    
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getPromoted() {
        return this.promoted;
    }

    public void setPromoted(int promoted) {
        this.promoted = promoted;
    }
    
    public int getSticky() {
        return this.sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }
    
    // Others
    public int getSpecial() {
        return this.special;
    }

    public void setSpecial(int special) {
        this.special = special;
    }

    public int getStartDate() {
        return this.startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return this.endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getFavorite() {
        return this.favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTrack() {
        return this.track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
    
    public List<Speaker> getSpeakers() {
        return this.speakerList;
    }
    
}
