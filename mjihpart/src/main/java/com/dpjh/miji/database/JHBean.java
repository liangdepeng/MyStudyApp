package com.dpjh.miji.database;

/**
 * java类作用描述
 * <p>
 * Date: 2024/12/12 17:29
 * Author: 唐朝
 */
public class JHBean {

    private String jhId;
    private String jhDesc;
    private String jhCode;

    private String jhDataTypeName;
    private String jhDataTextName;
//    private int isCollected;

    public String getJhId() {
        return jhId;
    }

    public void setJhId(String jhId) {
        this.jhId = jhId;
    }

    public String getJhDesc() {
        return jhDesc;
    }

    public void setJhDesc(String jhDesc) {
        this.jhDesc = jhDesc;
    }

    public String getJhCode() {
        return jhCode;
    }

    public void setJhCode(String jhCode) {
        this.jhCode = jhCode;
    }

    public String getJhDataTypeName() {
        return jhDataTypeName;
    }

    public void setJhDataTypeName(String jhDataTypeName) {
        this.jhDataTypeName = jhDataTypeName;
    }

    public String getJhDataTextName() {
        return jhDataTextName;
    }

    public void setJhDataTextName(String jhDataTextName) {
        this.jhDataTextName = jhDataTextName;
    }

    @Override
    public String toString() {
        return "JHBean{" +
                ", jhDesc='" + jhDesc + '\'' +
                ", jhCode='" + jhCode + '\'' +
                ", jhDataTypeName='" + jhDataTypeName + '\'' +
                ", jhDataTextName='" + jhDataTextName + '\'' +
                '}';
    }

    //    public int isCollected() {
//        return isCollected;
//    }
//
//    public void setCollected(int collected) {
//        isCollected = collected;
//    }
}
